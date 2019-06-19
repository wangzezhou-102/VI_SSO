package com.secusoft.web.task;

import com.secusoft.web.config.BkrepoConfig;
import com.secusoft.web.config.ServiceApiConfig;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.mapper.ViSurveyTaskMapper;
import com.secusoft.web.model.ViSurveyTaskBean;
import com.secusoft.web.serviceapi.ServiceApiClient;
import com.secusoft.web.serviceapi.model.BaseResponse;
import com.secusoft.web.tusouapi.model.BKTaskDataTaskIdRequest;
import com.secusoft.web.tusouapi.model.BaseRequest;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 烽火数据同步Controller
 */
@Component
@Configurable
@EnableScheduling
public class SurveyReStartTask {

    @Resource
    BkrepoConfig bkrepoConfig;

    @Resource
    ViSurveyTaskMapper viSurveyTaskMapper;

    /**
     * 布控任务重新下发开始定时请求
     */
    //0 0 */1 * * ? 每小时执行一次
    //0 0/1 * * * ? 每分钟执行一次
    //@Scheduled(cron = "0 0/1 * * * ?")//0 0 */1 * * ?
    public void surveyReStart() {
        ViSurveyTaskBean viSurveyTaskBean = new ViSurveyTaskBean();
        viSurveyTaskBean.setBeginTime(new Date());
        //        List<ViSurveyTaskBean> list = viSurveyTaskMapper.getTaskStartOrStopFailedList(viSurveyTaskBean);
        List<ViSurveyTaskBean> list = new ArrayList<>();
        for (ViSurveyTaskBean bean : list) {
            if (0 == bean.getEnable()) {
                BaseRequest<BKTaskDataTaskIdRequest> bkTaskDataTaskIdRequestBaseResponse = new BaseRequest<>();
                BKTaskDataTaskIdRequest bkTaskDataTaskIdRequest = new BKTaskDataTaskIdRequest();
                bkTaskDataTaskIdRequest.setTaskId(viSurveyTaskBean.getTaskId());
                bkTaskDataTaskIdRequestBaseResponse.setData(bkTaskDataTaskIdRequest);
                BaseResponse baseResponse = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBktaskStart(),
                        bkTaskDataTaskIdRequestBaseResponse);
                String code = baseResponse.getCode();
                //判断返回值code，若开启任务成功，则更改布控任务状态为1
                if (BizExceptionEnum.OK.getCode() == Integer.parseInt(code)) {
                    //若成功，则状态改为开启
                    viSurveyTaskBean.setEnable(1);
                    viSurveyTaskMapper.updateViSurveyTask(viSurveyTaskBean);
                }
            }
        }
    }
}