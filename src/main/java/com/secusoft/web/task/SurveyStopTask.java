package com.secusoft.web.task;

import com.secusoft.web.config.ServiceApiConfig;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.core.util.SpringContextHolder;
import com.secusoft.web.mapper.ViSurveyTaskMapper;
import com.secusoft.web.model.ViSurveyTaskBean;
import com.secusoft.web.model.ViSurveyTaskRequest;
import com.secusoft.web.serviceapi.ServiceApiClient;
import com.secusoft.web.serviceapi.model.BaseResponse;
import com.secusoft.web.tusouapi.model.BKTaskDataTaskIdRequest;
import com.secusoft.web.tusouapi.model.BaseRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.TimerTask;

import static com.secusoft.web.utils.ViSurveyTaskUntils.validTaskBeginTime;
import static com.secusoft.web.utils.ViSurveyTaskUntils.validTaskEndTime;

/**
 * 停止布控定时任务
 *
 * @author chjiang
 * @since 2019/6/12 11:27
 */
public class SurveyStopTask extends TimerTask {
    private static Logger log = LoggerFactory.getLogger(SurveyStopTask.class);

    private ViSurveyTaskBean viSurveyTaskBean;

    public SurveyStopTask(ViSurveyTaskBean viSurveyTaskBean) {
        this.viSurveyTaskBean = viSurveyTaskBean;
    }

    private static ViSurveyTaskMapper viSurveyTaskMapper = SpringContextHolder.getBean(ViSurveyTaskMapper.class);

    @Override
    public void run() {
        log.info("开始停止布控任务，布控任务编号：" + viSurveyTaskBean.getTaskId());
        if (viSurveyTaskBean != null && viSurveyTaskBean.getId() != null) {
            if (1 != viSurveyTaskBean.getEnable()) {
                log.info("无需立即执行，开始判断是否到执行时间");
                if (!validTaskEndTime(viSurveyTaskMapper, viSurveyTaskBean)) {
                    log.info("时间不一致，无法停止任务，布控任务编号：" + viSurveyTaskBean.getTaskId());
                    return;
                }
            }
            ViSurveyTaskRequest viSurveyTaskRequest = new ViSurveyTaskRequest();
            viSurveyTaskRequest.setId(viSurveyTaskBean.getId());
            viSurveyTaskRequest.setTaskId(viSurveyTaskBean.getTaskId());
            ViSurveyTaskBean bean = viSurveyTaskMapper.getViSurveyTaskById(viSurveyTaskBean);
            if (bean == null) {
                return;
            }

            if (1 == bean.getEnable()) {
                BaseRequest<BKTaskDataTaskIdRequest> bkTaskDataTaskIdRequestBaseResponse = new BaseRequest<>();
                BKTaskDataTaskIdRequest bkTaskDataTaskIdRequest = new BKTaskDataTaskIdRequest();
                bkTaskDataTaskIdRequest.setTaskId(bean.getTaskId());
                bkTaskDataTaskIdRequestBaseResponse.setData(bkTaskDataTaskIdRequest);
                BaseResponse baseResponse = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBktaskStop(), bkTaskDataTaskIdRequestBaseResponse);
                String code = baseResponse.getCode();
                String message = baseResponse.getMessage();
                bean.setEnable(0);
                //判断返回值code，若开启任务成功，则更改布控任务状态为1
                if (String.valueOf(BizExceptionEnum.OK.getCode()).equals(code)) {
                    log.info("任务号：" + bean.getTaskId() + "，结束任务成功，布控任务编号：" + viSurveyTaskBean.getTaskId());
                    bean.setSurveyStatus(1);
                } else {
                    log.info("任务号：" + bean.getTaskId() + "，结束任务失败，原因：" + message);
                    bean.setSurveyStatus(0);
                }
                viSurveyTaskMapper.updateViSurveyTask(bean);
                if (0 == viSurveyTaskBean.getSurveyStatus()) {
                    throw new RuntimeException("任务号：" + viSurveyTaskBean.getTaskId() + "，结束任务失败，原因：" + message);
                }
            }
        }
    }
}
