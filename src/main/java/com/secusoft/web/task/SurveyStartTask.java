package com.secusoft.web.task;

import com.secusoft.web.config.ServiceApiConfig;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.core.util.SpringContextHolder;
import com.secusoft.web.mapper.ViSurveyTaskMapper;
import com.secusoft.web.model.ViSurveyTaskBean;
import com.secusoft.web.serviceapi.ServiceApiClient;
import com.secusoft.web.serviceapi.model.BaseResponse;
import com.secusoft.web.tusouapi.model.BKTaskDataTaskIdRequest;
import com.secusoft.web.tusouapi.model.BaseRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.TimerTask;

import static com.secusoft.web.utils.ViSurveyTaskUntils.validTaskBeginTime;

/**
 * 启动布控定时任务
 *
 * @author chjiang
 * @since 2019/6/12 11:27
 */
public class SurveyStartTask extends TimerTask {
    private static Logger log = LoggerFactory.getLogger(SurveyStartTask.class);

    private ViSurveyTaskBean viSurveyTaskBean;

    public SurveyStartTask(ViSurveyTaskBean viSurveyTaskBean) {
        this.viSurveyTaskBean = viSurveyTaskBean;
    }

    private static ViSurveyTaskMapper viSurveyTaskMapper = SpringContextHolder.getBean(ViSurveyTaskMapper.class);

    @Override
    public void run() {
        log.info("开始启动布控任务，布控任务编号：" + viSurveyTaskBean.getTaskId());
        if (viSurveyTaskBean != null && viSurveyTaskBean.getId() != null) {
            if (1 != viSurveyTaskBean.getEnable()) {
                log.info("无需立即执行，开始判断是否到执行时间");
                if (!validTaskBeginTime(viSurveyTaskMapper, viSurveyTaskBean)) {
                    log.info("时间不一致，无法启动任务，布控任务编号：" + viSurveyTaskBean.getTaskId());
                    return;
                }
            }

            if (2 == viSurveyTaskBean.getEnable()) {
                BaseRequest<BKTaskDataTaskIdRequest> bkTaskDataTaskIdRequestBaseResponse = new BaseRequest<>();
                BKTaskDataTaskIdRequest bkTaskDataTaskIdRequest = new BKTaskDataTaskIdRequest();
                bkTaskDataTaskIdRequest.setTaskId(viSurveyTaskBean.getTaskId());
                bkTaskDataTaskIdRequestBaseResponse.setData(bkTaskDataTaskIdRequest);
                BaseResponse baseResponse = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBktaskStart(), bkTaskDataTaskIdRequestBaseResponse);

                String code = baseResponse.getCode();
                String message = baseResponse.getMessage();
                viSurveyTaskBean.setEnable(1);
                //判断返回值code，若开启任务成功，则更改布控任务状态为1
                if (String.valueOf(BizExceptionEnum.OK.getCode()).equals(code)) {
                    log.info("任务号：" + viSurveyTaskBean.getTaskId() + "，开启任务成功");
                    viSurveyTaskBean.setSurveyStatus(1);
                } else {
                    log.info("任务号：" + viSurveyTaskBean.getTaskId() + "，开启任务失败，原因：" + message);
                    viSurveyTaskBean.setSurveyStatus(0);
                }
                viSurveyTaskMapper.updateViSurveyTask(viSurveyTaskBean);
                if (0 == viSurveyTaskBean.getSurveyStatus()) {
                    throw new RuntimeException("任务号：" + viSurveyTaskBean.getTaskId() + "，开启任务失败，原因：" + message);
                }
            }
        }
    }
}
