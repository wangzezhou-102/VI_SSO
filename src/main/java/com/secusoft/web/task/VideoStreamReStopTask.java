package com.secusoft.web.task;

import com.secusoft.web.config.BkrepoConfig;
import com.secusoft.web.config.ServiceApiConfig;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.mapper.ViTaskDeviceMapper;
import com.secusoft.web.model.ViTaskDeviceBean;
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
import java.text.ParseException;
import java.util.List;

/**
 * 烽火数据同步Controller
 */
@Component
@Configurable
@EnableScheduling
public class VideoStreamReStopTask {
    private static Logger log = LoggerFactory.getLogger(VideoStreamReStopTask.class);

    @Resource
    BkrepoConfig bkrepoConfig;

    @Resource
    ViTaskDeviceMapper viTaskDeviceMapper;

    /**
     * 设备停流重新下发定时请求
     */
    //0 0 */1 * * ? 每小时执行一次
    //0 0/1 * * * ? 每分钟执行一次
    //@Scheduled(cron = "0 0/1 * * * ?")//0 0 */1 * * ?
    public void VideoStreamReStop() throws ParseException, InterruptedException {
        ViTaskDeviceBean viTaskDeviceBean = new ViTaskDeviceBean();
        viTaskDeviceBean.setStatus(0);
        viTaskDeviceBean.setAction(1);
        List<ViTaskDeviceBean> list = viTaskDeviceMapper.getViTaskDeviceByObject(viTaskDeviceBean);
        for (ViTaskDeviceBean bean : list) {
            if (0 == bean.getStatus() && 1 == bean.getAction()) {
                BaseRequest<BKTaskDataTaskIdRequest> bkTaskDataTaskIdRequestBaseResponse = new BaseRequest<>();
                BKTaskDataTaskIdRequest bkTaskDataTaskIdRequest = new BKTaskDataTaskIdRequest();
                bkTaskDataTaskIdRequest.setTaskId(bean.getViSurveyTask().getTaskId());
                bkTaskDataTaskIdRequestBaseResponse.setData(bkTaskDataTaskIdRequest);
                BaseResponse baseResponse =
                        ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBktaskStart(),
                                bkTaskDataTaskIdRequestBaseResponse);
                String code = baseResponse.getCode();
                //判断返回值code，若开启任务成功，则更改布控任务状态为1
                if (String.valueOf(BizExceptionEnum.OK.getCode()).equals(code) ) {
                    //若成功，则状态改为开启
                    log.info("重停流成功");
                    viTaskDeviceBean.setStatus(1);
                } else {
                    log.info("重停流失败");
                    viTaskDeviceBean.setStatus(0);
                }
                viTaskDeviceMapper.updateViTaskDevice(bean);
            }
        }
    }
}
