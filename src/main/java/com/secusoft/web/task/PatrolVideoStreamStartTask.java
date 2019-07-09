package com.secusoft.web.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.secusoft.web.config.NormalConfig;
import com.secusoft.web.config.ServiceApiConfig;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.core.util.SpringContextHolder;
import com.secusoft.web.mapper.PatrolTaskMapper;
import com.secusoft.web.mapper.ViTaskDeviceMapper;
import com.secusoft.web.model.PatrolTaskBean;
import com.secusoft.web.model.ViTaskDeviceBean;
import com.secusoft.web.serviceapi.ServiceApiClient;
import com.secusoft.web.shipinapi.model.StreamRequest;
import com.secusoft.web.utils.PatrolTaskUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;


/**
 * 启动码流定时任务
 *
 * @author Wangzezhou
 * @since 2019/07/09 17:05
 */
public class PatrolVideoStreamStartTask extends TimerTask {

    private static Logger log = LoggerFactory.getLogger(PatrolVideoStreamStartTask.class);

    private PatrolTaskBean patrolTaskBean;

    private PatrolTaskUtil patrolTaskUtil;

    public PatrolVideoStreamStartTask(PatrolTaskBean patrolTaskBean){ this.patrolTaskBean = patrolTaskBean;}
    public PatrolVideoStreamStartTask(PatrolTaskBean patrolTaskBean,PatrolTaskUtil patrolTaskUtil) {
        this.patrolTaskBean = patrolTaskBean;
        this.patrolTaskUtil = patrolTaskUtil;
    }
    private static ViTaskDeviceMapper viTaskDeviceMapper = SpringContextHolder.getBean(ViTaskDeviceMapper.class);

    private static PatrolTaskMapper patrolTaskMapper = SpringContextHolder.getBean(PatrolTaskMapper.class);

    @Override
    public void run() {
        log.info("开始启流，布控任务编号：" + patrolTaskBean.getTaskId());
        if (patrolTaskBean != null) {
            if (1 != patrolTaskBean.getEnable()) {
                log.info("无需立即执行，开始判断是否到执行时间");
                if (!patrolTaskUtil.validTaskStreamBeginTime(patrolTaskMapper, patrolTaskBean)) {
                    log.info("时间不一致，无法启流，布控任务编号：" + patrolTaskBean.getTaskId());
                    return;
                }
            }

            PatrolTaskBean patrolTaskBean = patrolTaskMapper.selectPatrolTaskByPrimaryKey(this.patrolTaskBean);
            if(patrolTaskBean != null) {
                for (ViTaskDeviceBean viTaskDeviceBean : patrolTaskBean.getViTaskDevices()) {
                    //判断设备是否已启用或者状态是否为2
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
                            log.info("设备号：" + viTaskDeviceBean.getDeviceId() + "，启流成功，布控任务编号：" + this.patrolTaskBean.getTaskId());
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

        log.info("结束停流，布控任务编号：" + patrolTaskBean.getTaskId());
    }
}
