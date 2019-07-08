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

import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import java.util.stream.Collectors;

import static com.secusoft.web.utils.ViSurveyTaskUntils.validTaskStreamBeginTime;
import static com.secusoft.web.utils.ViSurveyTaskUntils.validTaskStreamEndTime;

/**
 * 停止码流定时任务
 *
 * @author chjiang
 * @since 2019/6/12 11:27
 */
public class VideoStreamStopTask extends TimerTask {

    private static Logger log = LoggerFactory.getLogger(VideoStreamStopTask.class);

    private ViSurveyTaskBean viSurveyTaskBean;

    public VideoStreamStopTask(ViSurveyTaskBean viSurveyTaskBean) {
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
                if (!validTaskStreamEndTime(viSurveyTaskMapper, viSurveyTaskBean)) {
                    log.info("时间不一致，无法停流，布控任务编号：" + viSurveyTaskBean.getTaskId());
                    return;
                }
            }
            Date now = new Date();
            for (ViTaskDeviceBean viTaskDeviceBean : viSurveyTaskBean.getViTaskDeviceList()) {
                List<ViTaskDeviceBean> allViTaskDevice = viTaskDeviceMapper.getAllViTaskDevice(viTaskDeviceBean);
                if (allViTaskDevice.size() == 0) {
                    return;
                }
                viTaskDeviceBean = allViTaskDevice.get(0);

                ViTaskDeviceBean bean = new ViTaskDeviceBean();
                bean.setDeviceId(viTaskDeviceBean.getDeviceId());
                bean.setAction(0);
                bean.setStatus(1);
                List<ViTaskDeviceBean> viTaskDeviceList = viTaskDeviceMapper.getViTaskDeviceByObject(bean);
                //筛选还在使用的设备信息
                List<ViTaskDeviceBean> list = viTaskDeviceList.stream().
                        filter((ViTaskDeviceBean vtdb) -> vtdb.getViSurveyTask().getEndTime().compareTo(now) > 0).collect(Collectors.toList());
                //判断设备是否已启用1和状态是否为成功1
                if (viTaskDeviceBean.getAction() == 1 && viTaskDeviceBean.getStatus() == 1) {
                    StreamRequest streamRequest = new StreamRequest();
                    streamRequest.setDeviceId(viTaskDeviceBean.getDeviceId());

                    String requestStr = JSON.toJSONString(streamRequest);
                    String responseStr = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getStreamStart(), requestStr);

                    JSONObject jsonObject = (JSONObject) JSONObject.parse(responseStr);
                    String code = jsonObject.getString("code");
                    String message = jsonObject.getString("message");
                    if (String.valueOf(BizExceptionEnum.OK.getCode()).equals(code)) {
                        log.info("设备号：" + viTaskDeviceBean.getDeviceId() + "，停流成功，布控任务编号：" + viSurveyTaskBean.getTaskId());
                        viTaskDeviceBean.setStatus(1);
                    } else {
                        log.info("设备号：" + viTaskDeviceBean.getDeviceId() + "，停流失败，原因：" + message);
                        viTaskDeviceBean.setStatus(0);
                    }

                    viTaskDeviceBean.setAction(0);
                    viTaskDeviceMapper.updateViTaskDevice(viTaskDeviceBean);
                }
            }
        }
    }
}
