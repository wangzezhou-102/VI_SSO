package com.secusoft.web.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.secusoft.web.service.DeviceService;
import com.secusoft.web.shipinapi.ShiPinClient;

/**
 * 同步设备任务
 * @author ChenDong
 * @company 视在数科
 * @date 2019年6月13日
 */
@Component
public class SyncDeviceTask {

    @Autowired
    private DeviceService deviceService;
    
    @Scheduled(cron="0 0 3 * * ?")
    public void syncDevice() {
        System.out.println("task start");
//        ShiPinClient.getClientConnectionPool().fetchByPostMethod(url, jsonStr);
    }
}
