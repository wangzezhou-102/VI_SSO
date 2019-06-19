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

import java.util.TimerTask;

/**
 * 停止布控定时任务
 *
 * @author chjiang
 * @since 2019/6/12 11:27
 */
public class SurveyStopTask extends TimerTask {

    private ViSurveyTaskBean viSurveyTaskBean;

    public SurveyStopTask(ViSurveyTaskBean viSurveyTaskBean) {
        this.viSurveyTaskBean = viSurveyTaskBean;
    }

    private static ViSurveyTaskMapper viSurveyTaskMapper = SpringContextHolder.getBean(ViSurveyTaskMapper.class);

    @Override
    public void run() {
        if (1==viSurveyTaskBean.getEnable()) {
            BaseRequest<BKTaskDataTaskIdRequest> bkTaskDataTaskIdRequestBaseResponse = new BaseRequest<>();
            BKTaskDataTaskIdRequest bkTaskDataTaskIdRequest = new BKTaskDataTaskIdRequest();
            bkTaskDataTaskIdRequest.setTaskId(viSurveyTaskBean.getTaskId());
            bkTaskDataTaskIdRequestBaseResponse.setData(bkTaskDataTaskIdRequest);
            BaseResponse baseResponse = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBktaskStop(),
                    bkTaskDataTaskIdRequestBaseResponse);
            String code = baseResponse.getCode();
            //判断返回值code，若关闭任务成功，则更改布控任务状态为1
            if (BizExceptionEnum.OK.getCode() == Integer.parseInt(code)) {
                //若成功，则关闭
                viSurveyTaskBean.setEnable(2);
                viSurveyTaskMapper.updateViSurveyTask(viSurveyTaskBean);
            }
        }
    }
}
