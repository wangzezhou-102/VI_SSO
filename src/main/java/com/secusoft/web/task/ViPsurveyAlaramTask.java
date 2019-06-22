package com.secusoft.web.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.secusoft.web.config.ServiceApiConfig;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.mapper.ViPsurveyAlaramDetailMapper;
import com.secusoft.web.mapper.ViPsurveyAlaramMapper;
import com.secusoft.web.model.ViPsurveyAlaramBean;
import com.secusoft.web.model.ViPsurveyAlaramDetailBean;
import com.secusoft.web.serviceapi.ServiceApiClient;
import com.secusoft.web.websocket.WebSock;
import com.secusoft.web.websocket.WebSocketMessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * 布控告警同步
 * @author chjiang
 * @since 2019/6/19 10:11
 */
@Component
@Configurable
@EnableScheduling
public class ViPsurveyAlaramTask {

    @Resource
    ViPsurveyAlaramMapper viPsurveyAlaramMapper;

    @Resource
    ViPsurveyAlaramDetailMapper viPsurveyAlaramDetailMapper;

    @Autowired
    WebSock webSock;

    //@Scheduled(cron = "0 0 */1 * * ?")
    //@Scheduled(cron="0/20 * * * * ?")
    public void ViPsurveyAlaram() throws IOException {

        String responseStr = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getGetViPsurveyAlaram(), "");

        JSONObject jsonObject= (JSONObject) JSONObject.parse(responseStr);
        String code=jsonObject.getString("code");
        String data=jsonObject.getString("data");
        if(String.valueOf(BizExceptionEnum.OK.getCode()).equals(code)&&(!data.isEmpty()||!"null".equals(data))) {
            JSONArray jsonArrayData = (JSONArray) JSONArray.parse(data);
            JSONObject jsonData = (JSONObject) JSONObject.parse(jsonArrayData.getString(0));
            String taskId = jsonData.getString("taskId");

            //布控报警
            String src = jsonData.getString("src");
            ViPsurveyAlaramBean viPsurveyAlaramBean = (ViPsurveyAlaramBean) JSONObject.parseObject(src, ViPsurveyAlaramBean.class);
            viPsurveyAlaramBean.setTaskId(taskId);
            viPsurveyAlaramMapper.insertViPsurveyAlaram(viPsurveyAlaramBean);
            //人员报警布控图比对
            String similar = jsonData.getString("similar");
            List<ViPsurveyAlaramDetailBean> detailBeanList = (List<ViPsurveyAlaramDetailBean>) JSONObject.parseArray(similar, ViPsurveyAlaramDetailBean.class);
            for (ViPsurveyAlaramDetailBean bean: detailBeanList) {
                bean.setAlarmId(viPsurveyAlaramBean.getId());
                bean.setTaskId(taskId);
                bean.setAlarmId(viPsurveyAlaramBean.getId());
                bean.setAlarmType("312312");
                bean.setViPsurveyAlaramBean(viPsurveyAlaramBean);
            }
            viPsurveyAlaramDetailMapper.insertBatch(detailBeanList);

            if(detailBeanList.size()>0){
                WebSocketMessageVO webSocketMessageVO=new WebSocketMessageVO();
                webSocketMessageVO.setData(detailBeanList);

                webSock.sendMessage(JSON.toJSONString(webSocketMessageVO));
            }
        }
    }
}
