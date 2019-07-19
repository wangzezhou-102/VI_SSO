package com.secusoft.web.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.secusoft.web.config.NormalConfig;
import com.secusoft.web.config.ServiceApiConfig;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.core.util.SpringContextHolder;
import com.secusoft.web.mapper.SecurityTimeMapper;
import com.secusoft.web.mapper.ViTaskDeviceMapper;
import com.secusoft.web.model.SecurityTaskRequest;
import com.secusoft.web.model.ViSurveyTaskBean;
import com.secusoft.web.model.ViTaskDeviceBean;
import com.secusoft.web.serviceapi.ServiceApiClient;
import com.secusoft.web.shipinapi.model.StreamRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.TimerTask;

import static com.secusoft.web.utils.SecurityTaskUntils.securityTaskStreamBeginTime;
/**
 * 安保启动码流定时任务
 *
 */
public class SecurityVideoStreamStartTask extends TimerTask {

    private static Logger log = LoggerFactory.getLogger(SecurityVideoStreamStartTask.class);

    private SecurityTaskRequest securityTaskRequest;

    public SecurityVideoStreamStartTask(SecurityTaskRequest securityTaskRequest) {
        this.securityTaskRequest = securityTaskRequest;
    }

    private static ViTaskDeviceMapper viTaskDeviceMapper = SpringContextHolder.getBean(ViTaskDeviceMapper.class);

    private static SecurityTimeMapper securityTimeMapper = SpringContextHolder.getBean(SecurityTimeMapper.class);


    @Override
    public void run() {
        log.info("开始启流，布控任务编号：" + securityTaskRequest.getTaskId());
        if (securityTaskRequest != null && securityTaskRequest.getId() != null) {

            log.info("开始对比时间是否一致");
            if (!securityTaskStreamBeginTime(securityTimeMapper, securityTaskRequest)) {
                log.info("时间不一致，无法启流，安保任务编号：" + securityTaskRequest.getTaskId());
                return;
            }

            List<ViTaskDeviceBean> viTaskDeviceBeans = viTaskDeviceMapper.selectViTaskRepoByTaskId(securityTaskRequest.getTaskId());
            if (viTaskDeviceBeans != null) {
                for (ViTaskDeviceBean viTaskDeviceBean : viTaskDeviceBeans) {
                    //判断设备是否已启用或者状态是否为1
                    if (viTaskDeviceBean.getAction() == 2 && viTaskDeviceBean.getStatus() == 2) {
                        StreamRequest streamRequest = new StreamRequest();
                        streamRequest.setAppId(NormalConfig.getSzSecurityAppId());
                        streamRequest.setDeviceId(viTaskDeviceBean.getDeviceId());

                        String requestStr = JSON.toJSONString(streamRequest);
                        String responseStr = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getStreamStart(), requestStr);
                        JSONObject jsonObject = (JSONObject) JSONObject.parse(responseStr);
                        String code = jsonObject.getString("code");
                        String message = jsonObject.getString("message");
                        if (String.valueOf(BizExceptionEnum.OK.getCode()).equals(code)) {
                            log.info("设备号：" + viTaskDeviceBean.getDeviceId() + "，启流成功，安保任务编号：" + securityTaskRequest.getTaskId());
                            viTaskDeviceBean.setStatus(1);
                        } else {
                            log.info("设备号：" + viTaskDeviceBean.getDeviceId() + "，启流失败，原因：" + message);
                            viTaskDeviceBean.setStatus(0);
                        }
                        viTaskDeviceBean.setAction(1);
                        viTaskDeviceMapper.updateViTaskDevice(viTaskDeviceBean);

                    }
                }
            }
        }

        log.info("结束启流，安保任务编号：" + securityTaskRequest.getTaskId());
    }
}
