package com.secusoft.web.utils;

import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.core.util.SpringContextHolder;
import com.secusoft.web.mapper.ViSurveyTaskMapper;
import com.secusoft.web.model.ViSurveyTaskBean;
import com.secusoft.web.tusouapi.model.BKTaskDataTaskIdRequest;
import com.secusoft.web.tusouapi.model.BaseRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
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
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("SurveyStopTask：" + df.format(new Date()));
        System.out.println(new Date());
        if (viSurveyTaskBean.getEndTime().compareTo(new Date()) <= 0) {
            BaseRequest<BKTaskDataTaskIdRequest> bkTaskDataTaskIdRequestBaseResponse = new BaseRequest<>();
            BKTaskDataTaskIdRequest bkTaskDataTaskIdRequest = new BKTaskDataTaskIdRequest();
            bkTaskDataTaskIdRequest.setTaskId(viSurveyTaskBean.getTaskId());
            bkTaskDataTaskIdRequestBaseResponse.setData(bkTaskDataTaskIdRequest);
//            BaseResponse baseResponse = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBktaskStop(),
//                    bkTaskDataTaskIdRequestBaseResponse);
//            String code = baseResponse.getCode();
            //判断返回值code，若关闭任务成功，则更改布控任务状态为1
            if (BizExceptionEnum.OK.getCode() == Integer.parseInt("1001010")) {
                viSurveyTaskBean.setEnable(0);
                viSurveyTaskMapper.updateViSurveyTask(viSurveyTaskBean);
            }
        }
        System.gc();
        System.out.println("你指定" + df.format(new Date()) + "执行已经触发！");
    }
}
