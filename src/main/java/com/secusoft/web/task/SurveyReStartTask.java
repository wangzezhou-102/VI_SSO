package com.secusoft.web.task;

import com.secusoft.web.config.ServiceApiConfig;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.mapper.ViSurveyTaskMapper;
import com.secusoft.web.model.ViSurveyTaskBean;
import com.secusoft.web.serviceapi.ServiceApiClient;
import com.secusoft.web.serviceapi.model.BaseResponse;
import com.secusoft.web.tusouapi.model.BKTaskDataTaskIdRequest;
import com.secusoft.web.tusouapi.model.BaseRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 烽火数据同步Controller
 */
@Component
@Configurable
@EnableScheduling
public class SurveyReStartTask {
    private static Logger log = LoggerFactory.getLogger(SurveyReStartTask.class);

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
        viSurveyTaskBean.setSurveyStatus(0);
        viSurveyTaskBean.setEnable(1);
        List<ViSurveyTaskBean> list = viSurveyTaskMapper.getTaskStartOrStopFailedList(viSurveyTaskBean);
//        List<ViSurveyTaskBean> list = new ArrayList<>();
        for (ViSurveyTaskBean bean : list) {
            if (1 == bean.getEnable() && 0 == bean.getSurveyStatus()) {
                BaseRequest<BKTaskDataTaskIdRequest> bkTaskDataTaskIdRequestBaseResponse = new BaseRequest<>();
                BKTaskDataTaskIdRequest bkTaskDataTaskIdRequest = new BKTaskDataTaskIdRequest();
                bkTaskDataTaskIdRequest.setTaskId(viSurveyTaskBean.getTaskId());
                bkTaskDataTaskIdRequestBaseResponse.setData(bkTaskDataTaskIdRequest);
                BaseResponse baseResponse =
                        ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBktaskStart(), bkTaskDataTaskIdRequestBaseResponse);
                String code = baseResponse.getCode();
                //判断返回值code，若开启任务成功，则更改布控任务状态为1
                if (BizExceptionEnum.OK.getCode() == Integer.parseInt(code)) {
                    log.info("任务号：" + viSurveyTaskBean.getTaskId() + "，重开启任务成功");
                    viSurveyTaskBean.setSurveyStatus(1);
                }else{
                    log.info("任务号：" + viSurveyTaskBean.getTaskId() + "，重开启任务失败");
                    viSurveyTaskBean.setSurveyStatus(0);
                }
                viSurveyTaskMapper.updateViSurveyTask(bean);
            }
        }
    }
}
