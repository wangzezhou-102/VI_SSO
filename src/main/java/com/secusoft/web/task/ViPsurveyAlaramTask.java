package com.secusoft.web.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.secusoft.web.config.ServiceApiConfig;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.mapper.ViPsurveyAlarmDetailMapper;
import com.secusoft.web.mapper.ViPsurveyAlarmMapper;
import com.secusoft.web.model.ViPsurveyAlarmBean;
import com.secusoft.web.model.ViPsurveyAlarmDetailBean;
import com.secusoft.web.model.ViPsurveyAlaramVo;
import com.secusoft.web.serviceapi.ServiceApiClient;
import com.secusoft.web.websocket.WebSock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * 布控告警同步
 *
 * @author chjiang
 * @since 2019/6/19 10:11
 */
@Component
@Configurable
@EnableScheduling
public class ViPsurveyAlaramTask {

    @Resource
    ViPsurveyAlarmMapper viPsurveyAlarmMapper;

    @Resource
    ViPsurveyAlarmDetailMapper viPsurveyAlarmDetailMapper;

    @Autowired
    WebSock webSock;

    //@Scheduled(cron = "0 0 */1 * * ?")
    @Scheduled(cron = "30 03 17 * * ?")
    public void ViPsurveyAlaram() throws IOException {

        String responseStr = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getGetViPsurveyAlaram(), "");

        JSONObject jsonObject = (JSONObject) JSONObject.parse(responseStr);
        String code = jsonObject.getString("code");
        String data = jsonObject.getString("data");
        if (String.valueOf(BizExceptionEnum.OK.getCode()).equals(code) && (!data.isEmpty() || !"null".equals(data))) {
            List<ViPsurveyAlaramVo> viPsurveyAlaramVoLists = JSONArray.parseArray(data, ViPsurveyAlaramVo.class);
            for (ViPsurveyAlaramVo alaramVo : viPsurveyAlaramVoLists) {

                String taskId = alaramVo.getTaskId();

                ViPsurveyAlarmBean viPsurveyAlarmBean = new ViPsurveyAlarmBean();
                viPsurveyAlarmBean.setTaskId(taskId);
                ViPsurveyAlarmBean bean = viPsurveyAlarmMapper.getViPsurveyAlarmByBean(viPsurveyAlarmBean);
                //布控报警
                alaramVo.setTaskId(taskId);
                //复制对象
                BeanCopier beanCopier = BeanCopier.create(ViPsurveyAlaramVo.class, ViPsurveyAlarmBean.class, false);
                beanCopier.copy(alaramVo, viPsurveyAlarmBean, null);
                if (null == bean) {
                    viPsurveyAlarmMapper.insertViPsurveyAlarm(viPsurveyAlarmBean);
                }
                //人员报警布控图比对
                for (ViPsurveyAlarmDetailBean beans : alaramVo.getSimilar()) {
                    beans.setAlarmId(viPsurveyAlarmBean.getId());
                    beans.setTaskId(taskId);
                    beans.setAlarmId(viPsurveyAlarmBean.getId());
                    beans.setAlarmType("312312");
                    beans.setViPsurveyAlarmBean(viPsurveyAlarmBean);
                }
                viPsurveyAlarmDetailMapper.insertBatch(alaramVo.getSimilar());
            }
//            Map<String ,Object> map=new HashMap<>();
//            map.put("anbao", new Object());
//            map.put("Bktask", new Object());
//            if(detailBeanList.size()>0){
//                WebSocketMessageVO webSocketMessageVO=new WebSocketMessageVO();
//                webSocketMessageVO.setData(detailBeanList);
//                webSock.sendMessage(JSON.toJSONString(webSocketMessageVO));
//            }
        }
    }
}
