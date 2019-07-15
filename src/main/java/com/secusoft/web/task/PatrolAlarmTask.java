package com.secusoft.web.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.secusoft.web.config.ServiceApiConfig;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.core.util.UploadUtil;
import com.secusoft.web.mapper.*;
import com.secusoft.web.model.*;
import com.secusoft.web.serviceapi.ServiceApiClient;
import com.secusoft.web.websocket.WebSock;
import com.secusoft.web.websocket.WebSocketMessageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 巡逻告警同步
 *
 * @author wangzezhou
 * @since 2019/7/11 15:32
 */
@Component
@Configurable
@EnableScheduling
public class PatrolAlarmTask {
    private static Logger log = LoggerFactory.getLogger(PatrolAlarmTask.class);

    @Resource
    PatrolAlarmMapper patrolAlarmMapper;

    @Resource
    PatrolAlarmDetailMapper patrolAlarmDetailMapper;

    @Resource
    ViPrivateMemberMapper viPrivateMemberMapper;

    @Resource
    ViBasicMemberMapper viBasicMemberMapper;

    @Resource
    DeviceMapper deviceMapper;

    @Autowired
    WebSock webSock;

    //0 0/1 * * * ? 每分钟执行一次
    //@Scheduled(cron = "0 0/1 * * * ?")
    public void patrolAlaram() throws IOException {
        log.info("开始获取实时告警数据");
        String responseStr = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getGetPatrolAlarm(), "");
        if(responseStr == null){
            log.info("实时告警数据接口请求失败");
            return;
        }
        JSONObject jsonObject = (JSONObject) JSONObject.parse(responseStr);
        String code = jsonObject.getString("code");
        String data = jsonObject.getString("data");
        List<PatrolAlarmDetailResponse> detailResponses = new ArrayList<>();
        if (String.valueOf(BizExceptionEnum.OK.getCode()).equals(code) && (!data.isEmpty() || !"null".equals(data))) {
            List<PatrolAlarmVo> patrolAlarmVoLists = JSONArray.parseArray(data, PatrolAlarmVo.class);
            for (PatrolAlarmVo alaramVo : patrolAlarmVoLists) {
                PatrolAlarmBean patrolAlarmBean = new PatrolAlarmBean();
                patrolAlarmBean.setTaskId(alaramVo.getTaskId());
                PatrolAlarmBean bean = patrolAlarmMapper.getPatrolAlarmByBean(patrolAlarmBean);
                //巡逻报警
                if (null == bean) {
                    //复制对象
                    BeanCopier beanCopier = BeanCopier.create(PatrolAlarmBean.class, PatrolAlarmBean.class, false);
                    beanCopier.copy(alaramVo.getSrc(), patrolAlarmBean, null);
                    //base64转存本地图片
                    patrolAlarmBean.setCropImage(UploadUtil.downLoadFromBase64(patrolAlarmBean.getCropImage(), "Alarm"));
                    patrolAlarmBean.setOrigImage(UploadUtil.downLoadFromBase64(patrolAlarmBean.getOrigImage(), "Alarm"));
                    patrolAlarmBean.setPersonImage(UploadUtil.downLoadFromBase64(patrolAlarmBean.getPersonImage(), "Alarm"));
                    patrolAlarmMapper.insertPatrolAlarm(patrolAlarmBean);
                } else {
                    patrolAlarmBean = bean;
                }
                //查找设备信息
                DeviceBean deviceBean = deviceMapper.selectDeviceByDeviceId(alaramVo.getSrc().getCameraId());
                //人员报警布控图比对
                for (PatrolAlarmDetailBean beans : alaramVo.getSimilar()) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = null;
                    try {
                        date = simpleDateFormat.parse(beans.getTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    beans.setTime(sdf.format(date));
                    beans.setAlarmId(patrolAlarmBean.getId());
                    beans.setTaskId(alaramVo.getTaskId());
                    beans.setAlarmType("312312");
                    beans.setPatrolAlarmBean(patrolAlarmBean);
                    beans.setOssUrl(UploadUtil.downLoadFromBase64(beans.getOssUrl(), "Alarm"));

                    PatrolAlarmDetailResponse patrolAlarmDetailResponse = new PatrolAlarmDetailResponse();
                    patrolAlarmDetailResponse.setName(beans.getName());
                    patrolAlarmDetailResponse.setOssUrl(beans.getOssUrl());
                    patrolAlarmDetailResponse.setCropImage(patrolAlarmBean.getCropImage());
                    patrolAlarmDetailResponse.setOrigImage(patrolAlarmBean.getOrigImage());
                    patrolAlarmDetailResponse.setPersonImage(patrolAlarmBean.getPersonImage());

                    ViPrivateMemberBean viPrivateMemberBean = new ViPrivateMemberBean();
                    viPrivateMemberBean.setObjectId(patrolAlarmBean.getObjId());
                    //判断在哪个库
                    ViPrivateMemberBean viPrivateMemberByBean = viPrivateMemberMapper.getViPrivateMemberByBean(viPrivateMemberBean);
                    if (null == viPrivateMemberByBean) {
                        ViBasicMemberBean viBasicMemberBean = new ViBasicMemberBean();
                        viBasicMemberBean.setObjectId(patrolAlarmBean.getObjId());
                        ViBasicMemberBean basicMemberBean = viBasicMemberMapper.getViBasicMemberByObjectId(viBasicMemberBean);
                        if (null != basicMemberBean) {
                            patrolAlarmDetailResponse.setBkname(basicMemberBean.getViRepoBean().getBkname());
                        } else {
                            patrolAlarmDetailResponse.setBkname("测试人员库");
                        }
                    } else {
                        patrolAlarmDetailResponse.setBkname(viPrivateMemberByBean.getViRepoBean().getBkname());
                    }
                    patrolAlarmDetailMapper.insertPatrolAlarmDetail(beans);
                    patrolAlarmDetailResponse.setAlarmDetailId(beans.getId());
                    patrolAlarmDetailResponse.setAlarmStatus(beans.getAlarmStatus());
                    SimpleDateFormat sdfs = new SimpleDateFormat("MM/dd HH:mm:ss");
                    patrolAlarmDetailResponse.setTime(sdfs.format(date));
                    if(null != deviceBean){
                        patrolAlarmDetailResponse.setDeviceRoadName(deviceBean.getDeviceName());
                    }
                    detailResponses.add(patrolAlarmDetailResponse);
                }
            }

            Map<String, Object> map = new HashMap<>();
            map.put("patrolAlarmBk", detailResponses);
            WebSocketMessageVO webSocketMessageVO = new WebSocketMessageVO();
            webSocketMessageVO.setData(map);
            webSock.sendMessage(JSON.toJSONString(webSocketMessageVO));
            System.out.println(JSON.toJSONString(webSocketMessageVO));
        }
        //webSock.sendMessage("{\"data\":{\"psurveAlarmBk\":[{\"alarmDetailId\":53,\"bkname\":\"测试人员库\",\"cropImage\":\"/static/Alarm/201907/Alarm_bNw5xWZIhse9WUIlWEwxCf.jpg\",\"name\":\"\",\"origImage\":\"/static/Alarm/201907/Alarm_eiB1agFe0jKeJPvzuGO1Pg.jpg\",\"ossUrl\":\"/static/Alarm/201907/Alarm_gpA9EkXH4uD6AFEffrsyin.jpg\",\"personImage\":\"/static/Alarm/201907/Alarm_d0Ha3XMWsoTehekOYB7hT6.jpg\",\"similarity\":\"77%\"},{\"alarmDetailId\":54,\"bkname\":\"测试人员库\",\"cropImage\":\"/static/Alarm/201907/Alarm_bfv7vM98yiL5NnalgpIMLW.jpg\",\"name\":\"\",\"origImage\":\"/static/Alarm/201907/Alarm_cf6nLl39LLhaVxxiKDuldP.jpg\",\"ossUrl\":\"/static/Alarm/201907/Alarm_fFx4GM94NPIkubRJcShweZ.jpg\",\"personImage\":\"/static/Alarm/201907/Alarm_ggGK307oJtp66tPAiEPpGN.jpg\",\"similarity\":\"77%\"},{\"alarmDetailId\":55,\"bkname\":\"测试人员库\",\"cropImage\":\"/static/Alarm/201907/Alarm_dSDksMZKD3g35Xwg7Lturd.jpg\",\"name\":\"\",\"origImage\":\"/static/Alarm/201907/Alarm_fDG3hG7KxvCjVyRpUejZDB.jpg\",\"ossUrl\":\"/static/Alarm/201907/Alarm_dVix8zj066KfzytmrRnUzL.jpg\",\"personImage\":\"/static/Alarm/201907/Alarm_eom0krKELs8lkiqcVgh3Ky.jpg\",\"similarity\":\"77%\"},{\"alarmDetailId\":56,\"bkname\":\"测试人员库\",\"cropImage\":\"/static/Alarm/201907/Alarm_cvyYPHhUeHG53wab6DiuQZ.jpg\",\"name\":\"\",\"origImage\":\"/static/Alarm/201907/Alarm_fQvyxlXWhR8k5kLwQh1pB2.jpg\",\"ossUrl\":\"/static/Alarm/201907/Alarm_d4cgjFoyYbjj34aNO6ax4q.jpg\",\"personImage\":\"/static/Alarm/201907/Alarm_cVpilcER6hU29J0y9itJ6g.jpg\",\"similarity\":\"77%\"},{\"alarmDetailId\":57,\"bkname\":\"测试人员库\",\"cropImage\":\"/static/Alarm/201907/Alarm_dT00YW5L84b1XZ5ZuGiWDv.jpg\",\"name\":\"\",\"origImage\":\"/static/Alarm/201907/Alarm_cxApYuoRIsAjw3QPNlglNF.jpg\",\"ossUrl\":\"/static/Alarm/201907/Alarm_dSqkJHky13SbJ4dMtfZ5RK.jpg\",\"personImage\":\"/static/Alarm/201907/Alarm_cw6sTbMt9iiguzGf9YTlok.jpg\",\"similarity\":\"77%\"}]},\"type\":0}");
        log.info("结束获取实时告警数据");
    }
}
