package com.secusoft.web.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.secusoft.web.config.NormalConfig;
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

import static com.secusoft.web.utils.ViSurveyTaskUntils.validTaskStreamBeginTime;

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
        log.info("开始启流，布控任务编号：" + viSurveyTaskBean.getTaskId());
        if (viSurveyTaskBean != null && viSurveyTaskBean.getId() != null) {
            if (1 != viSurveyTaskBean.getEnable()) {
                log.info("无需立即执行，开始判断是否到执行时间");
                if (!validTaskStreamBeginTime(viSurveyTaskMapper, viSurveyTaskBean)) {
                    log.info("时间不一致，无法启流，布控任务编号：" + viSurveyTaskBean.getTaskId());
                    return;
                }
            }

            ViSurveyTaskBean viSurveyTask = viSurveyTaskMapper.getViSurveyTaskById(viSurveyTaskBean);
            if (viSurveyTask != null) {
                for (ViTaskDeviceBean viTaskDeviceBean : viSurveyTask.getViTaskDeviceList()) {
                    //判断设备是否已启用或者状态是否为1
                    if (viTaskDeviceBean.getAction() == 2 && viTaskDeviceBean.getStatus() == 2) {
                        StreamRequest streamRequest = new StreamRequest();
                        streamRequest.setAppId(NormalConfig.getSzBkAppId());
                        streamRequest.setDeviceId(viTaskDeviceBean.getDeviceId());

                        String requestStr = JSON.toJSONString(streamRequest);
                        String responseStr = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getStreamStart(), requestStr);
                        JSONObject jsonObject = (JSONObject) JSONObject.parse(responseStr);
                        String code = jsonObject.getString("code");
                        String message = jsonObject.getString("message");
                        if (String.valueOf(BizExceptionEnum.OK.getCode()).equals(code)) {
                            log.info("设备号：" + viTaskDeviceBean.getDeviceId() + "，启流成功，布控任务编号：" + viSurveyTaskBean.getTaskId());
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

        log.info("结束启流，布控任务编号：" + viSurveyTaskBean.getTaskId());
    }
}
