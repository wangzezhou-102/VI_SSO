package com.secusoft.web.task;

import com.secusoft.web.core.util.SpringContextHolder;
import com.secusoft.web.mapper.PatrolTaskMapper;
import com.secusoft.web.mapper.ViTaskDeviceMapper;
import com.secusoft.web.model.PatrolTaskBean;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 启动码流定时任务
 *
 * @author Wangzezhou
 * @since 2019/07/09 17:05
 */
public class PatrolVideoStreamStartTask implements Job {

    private static Logger log = LoggerFactory.getLogger(PatrolVideoStreamStartTask.class);

    private PatrolTaskBean patrolTaskBean;

    public PatrolVideoStreamStartTask(){}

    public PatrolVideoStreamStartTask(PatrolTaskBean patrolTaskBean){ this.patrolTaskBean = patrolTaskBean;}

    private static ViTaskDeviceMapper viTaskDeviceMapper = SpringContextHolder.getBean(ViTaskDeviceMapper.class);

    private static PatrolTaskMapper patrolTaskMapper = SpringContextHolder.getBean(PatrolTaskMapper.class);

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        JobDataMap patrolMap = jec.getJobDetail().getJobDataMap();
        patrolTaskBean = (PatrolTaskBean) patrolMap.get("paramName");
        log.info("开始启流，巡逻任务编号：" + patrolTaskBean.getTaskId());
        /*if (patrolTaskBean != null) {
           *//* if (1 != patrolTaskBean.getEnable()) {
                log.info("无需立即执行，开始判断是否到执行时间");
                PatrolTaskUtil patrolTaskUtil = new PatrolTaskUtil();
                if (!patrolTaskUtil.validTaskStreamBeginTime(patrolTaskMapper, patrolTaskBean)) {
                    log.info("时间不一致，无法启流，布控任务编号：" + patrolTaskBean.getTaskId());
                    return;
                }
            }*//*
            PatrolTaskBean patrolTaskBean = patrolTaskMapper.selectPatrolTaskByPrimaryKey(this.patrolTaskBean);
            if(patrolTaskBean != null) {
                for (ViTaskDeviceBean viTaskDeviceBean : patrolTaskBean.getViTaskDevices()) {
                    //判断设备是否已启用或者状态是否为2
                    if (viTaskDeviceBean.getAction() == 2 && viTaskDeviceBean.getStatus() == 2) {
                        StreamRequest streamRequest = new StreamRequest();
                        streamRequest.setAppId(NormalConfig.getSzPatrolAppId());
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
        }*/
        log.info("结束停流，布控任务编号：" + patrolTaskBean.getTaskId());
    }
}
