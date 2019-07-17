package com.secusoft.web.task;

import com.secusoft.web.config.ServiceApiConfig;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.core.util.SpringContextHolder;
import com.secusoft.web.mapper.PatrolTaskMapper;
import com.secusoft.web.model.PatrolTaskBean;
import com.secusoft.web.serviceapi.ServiceApiClient;
import com.secusoft.web.serviceapi.model.BaseResponse;
import com.secusoft.web.tusouapi.model.BKTaskDataTaskIdRequest;
import com.secusoft.web.tusouapi.model.BaseRequest;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 启动巡逻定时任务
 *
 * @author Wangzezhou
 * @since 2019/07/09 16:30
 */
public class PatrolStartTask implements Job {
    private static Logger log = LoggerFactory.getLogger(SurveyStartTask.class);

    private PatrolTaskBean patrolTaskBean;

    public PatrolStartTask(){}

    public PatrolStartTask(PatrolTaskBean patrolTaskBean) {
        this.patrolTaskBean = patrolTaskBean;
    }

    private static PatrolTaskMapper patrolTaskMapper = SpringContextHolder.getBean(PatrolTaskMapper.class);
    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        log.info("开启巡逻任务，任务编号：" + patrolTaskBean.getTaskId());
        JobDataMap patrolMap = jec.getJobDetail().getJobDataMap();
        patrolTaskBean = (PatrolTaskBean) patrolMap.get("paramName");
        if (patrolTaskBean != null ) {
            /*if (1 != patrolTaskBean.getEnable()) {
                log.info("无需立即执行，开始判断是否到执行时间");
                PatrolTaskUtil patrolTaskUtil = new PatrolTaskUtil();
                if (!patrolTaskUtil.validTaskBeginTime(patrolTaskMapper, patrolTaskBean)) {
                    log.info("时间不一致，无法启动任务，布控任务编号：" + patrolTaskBean.getTaskId());
                    return;
                }
            }*/
            PatrolTaskBean patrolTaskBean = patrolTaskMapper.selectPatrolTaskByPrimaryKey(this.patrolTaskBean);
            if (patrolTaskBean != null && 2 == patrolTaskBean.getEnable()) {
                BaseRequest<BKTaskDataTaskIdRequest> bkTaskDataTaskIdRequestBaseResponse = new BaseRequest<>();
                BKTaskDataTaskIdRequest bkTaskDataTaskIdRequest = new BKTaskDataTaskIdRequest();
                bkTaskDataTaskIdRequest.setTaskId(patrolTaskBean.getTaskId());
                bkTaskDataTaskIdRequestBaseResponse.setData(bkTaskDataTaskIdRequest);
                BaseResponse baseResponse = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBktaskStart(), bkTaskDataTaskIdRequestBaseResponse);

                String code = baseResponse.getCode();
                String message = baseResponse.getMessage();
                patrolTaskBean.setEnable(2);
                //判断返回值code，若开启任务成功，则更改布控任务状态为2
                if (String.valueOf(BizExceptionEnum.OK.getCode()).equals(code)) {
                    log.info("任务号：" + patrolTaskBean.getTaskId() + "，开启任务成功");
                    patrolTaskBean.setStatus(2);
                } else {
                    log.info("任务号：" + patrolTaskBean.getTaskId() + "，开启任务失败，原因：" + message);
                    patrolTaskBean.setStatus(1);
                }
                patrolTaskMapper.updatePatrolTask(patrolTaskBean);
                if (0 == patrolTaskBean.getStatus()) {
                    throw new RuntimeException("任务号：" + patrolTaskBean.getTaskId() + "，开启任务失败，原因：" + message);
                }
            }
        }
    }
}
