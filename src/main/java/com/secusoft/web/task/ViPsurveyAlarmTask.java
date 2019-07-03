package com.secusoft.web.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.secusoft.web.config.ServiceApiConfig;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.core.util.UploadUtil;
import com.secusoft.web.mapper.ViBasicMemberMapper;
import com.secusoft.web.mapper.ViPrivateMemberMapper;
import com.secusoft.web.mapper.ViPsurveyAlarmDetailMapper;
import com.secusoft.web.mapper.ViPsurveyAlarmMapper;
import com.secusoft.web.model.*;
import com.secusoft.web.serviceapi.ServiceApiClient;
import com.secusoft.web.websocket.WebSock;
import com.secusoft.web.websocket.WebSocketMessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 布控告警同步
 *
 * @author chjiang
 * @since 2019/6/19 10:11
 */
@Component
@Configurable
@EnableScheduling
public class ViPsurveyAlarmTask {

    @Resource
    ViPsurveyAlarmMapper viPsurveyAlarmMapper;

    @Resource
    ViPsurveyAlarmDetailMapper viPsurveyAlarmDetailMapper;

    @Resource
    ViPrivateMemberMapper viPrivateMemberMapper;

    @Resource
    ViBasicMemberMapper viBasicMemberMapper;

    @Autowired
    WebSock webSock;

    //@Scheduled(cron = "0 0 */1 * * ?")
    @Scheduled(cron = "30 13 19 * * ?")
    public void ViPsurveyAlaram() throws IOException {

        String responseStr = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getGetViPsurveyAlaram(), "");

        JSONObject jsonObject = (JSONObject) JSONObject.parse(responseStr);
        String code = jsonObject.getString("code");
        String data = jsonObject.getString("data");
        List<ViPsurveyAlarmDetailResponse> detailResponses = new ArrayList<>();
        if (String.valueOf(BizExceptionEnum.OK.getCode()).equals(code) && (!data.isEmpty() || !"null".equals(data))) {
            List<ViPsurveyAlaramVo> viPsurveyAlaramVoLists = JSONArray.parseArray(data, ViPsurveyAlaramVo.class);
            for (ViPsurveyAlaramVo alaramVo : viPsurveyAlaramVoLists) {

                ViPsurveyAlarmBean viPsurveyAlarmBean = new ViPsurveyAlarmBean();
                viPsurveyAlarmBean.setTaskId(alaramVo.getTaskId());
                ViPsurveyAlarmBean bean = viPsurveyAlarmMapper.getViPsurveyAlarmByBean(viPsurveyAlarmBean);
                //布控报警
                if (null == bean) {
                    //复制对象
                    BeanCopier beanCopier = BeanCopier.create(ViPsurveyAlarmBean.class, ViPsurveyAlarmBean.class, false);
                    beanCopier.copy(alaramVo.getSrc(), viPsurveyAlarmBean, null);
                    viPsurveyAlarmBean.setCropImage(UploadUtil.downLoadFromBase64(viPsurveyAlarmBean.getCropImage(), "Alarm"));
                    viPsurveyAlarmBean.setOrigImage(UploadUtil.downLoadFromBase64(viPsurveyAlarmBean.getOrigImage(), "Alarm"));
                    viPsurveyAlarmBean.setPersonImage(UploadUtil.downLoadFromBase64(viPsurveyAlarmBean.getPersonImage(), "Alarm"));
                    viPsurveyAlarmMapper.insertViPsurveyAlarm(viPsurveyAlarmBean);
                } else {
                    viPsurveyAlarmBean = bean;
                }
                //人员报警布控图比对
                for (ViPsurveyAlarmDetailBean beans : alaramVo.getSimilar()) {
                    beans.setAlarmId(viPsurveyAlarmBean.getId());
                    beans.setTaskId(alaramVo.getTaskId());
                    beans.setAlarmType("312312");
                    beans.setViPsurveyAlarmBean(viPsurveyAlarmBean);
                    beans.setOssUrl(UploadUtil.downLoadFromBase64(beans.getOssUrl(), "Alarm"));

                    ViPsurveyAlarmDetailResponse viPsurveyAlarmDetailResponse = new ViPsurveyAlarmDetailResponse();
                    viPsurveyAlarmDetailResponse.setName(beans.getName());
                    viPsurveyAlarmDetailResponse.setOssUrl(beans.getOssUrl());
                    viPsurveyAlarmDetailResponse.setCropImage(viPsurveyAlarmBean.getCropImage());
                    viPsurveyAlarmDetailResponse.setOrigImage(viPsurveyAlarmBean.getOrigImage());
                    viPsurveyAlarmDetailResponse.setPersonImage(viPsurveyAlarmBean.getPersonImage());

                    ViPrivateMemberBean viPrivateMemberBean = new ViPrivateMemberBean();
                    viPrivateMemberBean.setObjectId(viPsurveyAlarmBean.getObjId());
                    //判断在哪个库
                    ViPrivateMemberBean viPrivateMemberByBean = viPrivateMemberMapper.getViPrivateMemberByBean(viPrivateMemberBean);
                    if (null == viPrivateMemberByBean) {
                        ViBasicMemberBean viBasicMemberBean = new ViBasicMemberBean();
                        viBasicMemberBean.setObjectId(viPsurveyAlarmBean.getObjId());
                        ViBasicMemberBean basicMemberBean = viBasicMemberMapper.getViBasicMemberByObjectId(viBasicMemberBean);
                        viPsurveyAlarmDetailResponse.setBkname(basicMemberBean.getViRepoBean().getBkname());
                    } else {
                        viPsurveyAlarmDetailResponse.setBkname(viPrivateMemberByBean.getViRepoBean().getBkname());
                    }
                    detailResponses.add(viPsurveyAlarmDetailResponse);
                }
                viPsurveyAlarmDetailMapper.insertBatch(alaramVo.getSimilar());
            }
            Map<String, Object> map = new HashMap<>();
            map.put("psurveAlarm", detailResponses);

            WebSocketMessageVO webSocketMessageVO = new WebSocketMessageVO();
            webSocketMessageVO.setData(map);
            webSock.sendMessage(JSON.toJSONString(webSocketMessageVO));

        }
    }
}
