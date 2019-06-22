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

import java.util.TimerTask;

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
        if (2 == viSurveyTaskBean.getEnable()) {
            BaseRequest<BKTaskDataTaskIdRequest> bkTaskDataTaskIdRequestBaseResponse = new BaseRequest<>();
            BKTaskDataTaskIdRequest bkTaskDataTaskIdRequest = new BKTaskDataTaskIdRequest();
            bkTaskDataTaskIdRequest.setTaskId(viSurveyTaskBean.getTaskId());
            bkTaskDataTaskIdRequestBaseResponse.setData(bkTaskDataTaskIdRequest);
            BaseResponse baseResponse = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBktaskStart(),
                    bkTaskDataTaskIdRequestBaseResponse);
            String code = baseResponse.getCode();
            String message = baseResponse.getMessage();
            viSurveyTaskBean.setEnable(1);
            //判断返回值code，若开启任务成功，则更改布控任务状态为1
            if (BizExceptionEnum.OK.getCode() == Integer.parseInt(code)) {
                log.info("任务号：" + viSurveyTaskBean.getTaskId() + "，开启任务成功");
                viSurveyTaskBean.setSurveyStatus(1);
            }else{
                log.info("任务号：" + viSurveyTaskBean.getTaskId() + "，开启任务失败，原因：" + message);
                viSurveyTaskBean.setSurveyStatus(0);
            }
            viSurveyTaskMapper.updateViSurveyTask(viSurveyTaskBean);
        }
    }
}
