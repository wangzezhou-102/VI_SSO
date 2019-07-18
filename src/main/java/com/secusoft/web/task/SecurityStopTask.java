package com.secusoft.web.task;

import com.secusoft.web.config.ServiceApiConfig;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.core.util.SpringContextHolder;
import com.secusoft.web.mapper.SecurityTaskMapper;
import com.secusoft.web.mapper.SecurityTimeMapper;
import com.secusoft.web.model.SecurityTaskBean;
import com.secusoft.web.model.SecurityTaskRequest;
import com.secusoft.web.serviceapi.ServiceApiClient;
import com.secusoft.web.serviceapi.model.BaseResponse;
import com.secusoft.web.tusouapi.model.BKTaskDataTaskIdRequest;
import com.secusoft.web.tusouapi.model.BaseRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

import static com.secusoft.web.utils.SecurityTaskUntils.securityTaskEndTime;

/**
 * 停止布控定时任务
 *
 */
public class SecurityStopTask extends TimerTask {
    private static Logger log = LoggerFactory.getLogger(SecurityStopTask.class);

    private SecurityTaskRequest securityTaskRequest;

    public SecurityStopTask(SecurityTaskRequest securityTaskRequest) {
        this.securityTaskRequest = securityTaskRequest;
    }

    private static SecurityTaskMapper securityTaskMapper = SpringContextHolder.getBean(SecurityTaskMapper.class);

    private static SecurityTimeMapper securityTimeMapper = SpringContextHolder.getBean(SecurityTimeMapper.class);

    @Override
    public void run() {
        log.info("开始停止安保任务，布控任务编号：" + securityTaskRequest.getTaskId());
        if (securityTaskRequest != null && securityTaskRequest.getId() != null) {
            log.info("开始对比时间是否一致");
            if (!securityTaskEndTime(securityTimeMapper, securityTaskRequest)) {
                log.info("时间不一致，无法启流，安保任务编号：" + securityTaskRequest.getTaskId());
                return;
            }
            if (securityTaskRequest != null && 1 == securityTaskRequest.getEnable()) {
                BaseRequest<BKTaskDataTaskIdRequest> bkTaskDataTaskIdRequestBaseResponse = new BaseRequest<>();
                BKTaskDataTaskIdRequest bkTaskDataTaskIdRequest = new BKTaskDataTaskIdRequest();
                bkTaskDataTaskIdRequest.setTaskId(securityTaskRequest.getTaskId());
                bkTaskDataTaskIdRequestBaseResponse.setData(bkTaskDataTaskIdRequest);
                BaseResponse baseResponse = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBktaskStop(), bkTaskDataTaskIdRequestBaseResponse);
                String code = baseResponse.getCode();
                String message = baseResponse.getMessage();
                securityTaskRequest.setEnable(0);
                //判断返回值code，若开启任务成功，则更改布控任务状态为1
                if (String.valueOf(BizExceptionEnum.OK.getCode()).equals(code)) {
                    log.info("任务号：" + securityTaskRequest.getTaskId() + "，结束任务成功，安保任务编号：" + securityTaskRequest.getTaskId());
                    securityTaskRequest.setSecurityStatus(1);
                } else {
                    log.info("任务号：" + securityTaskRequest.getTaskId() + "，结束任务失败，原因：" + message);
                    securityTaskRequest.setSecurityStatus(0);
                }
                SecurityTaskBean securityTaskBean = new SecurityTaskBean();
                securityTaskBean.setTaskId(securityTaskRequest.getTaskId());
                securityTaskBean.setSecurityStatus(securityTaskRequest.getSecurityStatus());
                securityTaskBean.setEnable(securityTaskRequest.getEnable());
                securityTaskMapper.updateSecurityTask(securityTaskBean);
                if (0 == securityTaskRequest.getSecurityStatus()) {
                    throw new RuntimeException("任务号：" + securityTaskRequest.getTaskId() + "，结束任务失败，原因：" + message);
                }
            }
        }
    }
}
