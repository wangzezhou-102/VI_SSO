package com.secusoft.web.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.secusoft.web.config.ServiceApiConfig;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.core.util.SpringContextHolder;
import com.secusoft.web.mapper.ViSurveyTaskMapper;
import com.secusoft.web.mapper.ViTaskDeviceMapper;
import com.secusoft.web.model.ViSurveyTaskBean;
import com.secusoft.web.model.ViTaskDeviceBean;
import com.secusoft.web.serviceapi.ServiceApiClient;
import com.secusoft.web.shipinapi.model.StreamRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

/**
 * 启动码流定时任务
 *
 * @author chjiang
 * @since 2019/6/12 11:27
 */
public class VideoStreamStartTask extends TimerTask {

    private static Logger log = LoggerFactory.getLogger(VideoStreamStartTask.class);

    private ViSurveyTaskBean viSurveyTaskBean;

    public VideoStreamStartTask(ViSurveyTaskBean viSurveyTaskBean) {
        this.viSurveyTaskBean = viSurveyTaskBean;
    }

    private static ViTaskDeviceMapper viTaskDeviceMapper = SpringContextHolder.getBean(ViTaskDeviceMapper.class);

    private static ViSurveyTaskMapper viSurveyTaskMapper = SpringContextHolder.getBean(ViSurveyTaskMapper.class);

    @Override
    public void run() {
        for (ViTaskDeviceBean viTaskDeviceBean : viSurveyTaskBean.getViTaskDeviceList()) {
            //判断设备是否已启用或者状态是否为1
            if (viTaskDeviceBean.getAction() == 2 && viTaskDeviceBean.getStatus() == 2) {
                StreamRequest streamRequest = new StreamRequest();
                streamRequest.setDeviceId(viTaskDeviceBean.getDeviceId());

                String requestStr = JSON.toJSONString(streamRequest);
                String responseStr = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getStreamStart(), requestStr);
                JSONObject jsonObject = (JSONObject) JSONObject.parse(responseStr);
                String code = jsonObject.getString("code");
                String message = jsonObject.getString("message");
                if (String.valueOf(BizExceptionEnum.OK.getCode()).equals(code)) {
                    log.info("设备号：" + viTaskDeviceBean.getDeviceId() + "，启流成功");
                    viTaskDeviceBean.setStatus(1);
                } else {
                    log.info("设备号：" + viTaskDeviceBean.getDeviceId() + "，启流失败，原因："+message);
                    viTaskDeviceBean.setStatus(0);
                }
                viTaskDeviceBean.setAction(0);
                viTaskDeviceMapper.updateViTaskDevice(viTaskDeviceBean);
            }
        }
    }
}
