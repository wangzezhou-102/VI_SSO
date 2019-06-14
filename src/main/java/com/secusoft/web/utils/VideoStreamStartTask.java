package com.secusoft.web.utils;

import com.secusoft.web.model.ViSurveyTaskBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

/**
 * 启动码流定时任务
 *
 * @author chjiang
 * @since 2019/6/12 11:27
 */
public class VideoStreamStartTask extends TimerTask {

    private ViSurveyTaskBean viSurveyTaskBean;

    public VideoStreamStartTask(ViSurveyTaskBean viSurveyTaskBean) {
        this.viSurveyTaskBean = viSurveyTaskBean;
    }

    @Override
    public void run() {
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("设备启流："+df.format(new Date()));
//        String[] arrDeviceId=viSurveyTaskBean.getSurveyDevice().split(",");
//        for (String str:arrDeviceId) {
//            StreamRequest streamRequest = new StreamRequest();
//            streamRequest.setDeviceId(str);
//            streamRequest.setStartTime(viSurveyTaskBean.getBeginTime());
//            streamRequest.setStartTime(viSurveyTaskBean.getEndTime());
//
//            String requestStr = JSON.toJSONString(streamRequest);
//            //String responseStr = ShiPinClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getStreamStart(), requestStr);
//        }
    }
}
