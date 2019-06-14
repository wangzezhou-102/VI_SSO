package com.secusoft.web.utils;

import com.alibaba.fastjson.JSON;
import com.secusoft.web.shipinapi.model.StreamRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

/**
 * 停止码流定时任务
 *
 * @author chjiang
 * @since 2019/6/12 11:27
 */
public class VideoStreamStopTask extends TimerTask {

    private String deviceId;

    public VideoStreamStopTask(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public void run() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("VideoStreamStopTask：" + df.format(new Date()));
        System.out.println(new Date());
        String[] arrDeviceId = deviceId.split(",");
        for (String str : arrDeviceId) {
            StreamRequest streamRequest = new StreamRequest();
            streamRequest.setDeviceId(str);

            String requestStr = JSON.toJSONString(streamRequest);
            //String responseStr = ShiPinClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig
            // .getStreamStop(), requestStr);
        }
    }
}
