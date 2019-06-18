package com.secusoft.web.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.secusoft.web.service.DeviceService;

/**
 * 同步设备任务
 * @author ChenDong
 * @company 视在数科
 * @date 2019年6月13日
 */
@Component
public class SyncDeviceTask {

    private static final Logger log = LoggerFactory.getLogger(SyncDeviceTask.class);
    
    @Autowired
    private DeviceService deviceService;
    
    @Value("${ubr.sync.enable:false}")
    private boolean enable;
    
    @Scheduled(cron="0 0 3 * * ?")
    public void syncDevice() {
    	if(enable){
    		// 开启设备同步
    		log.info("sync device start");
    		deviceService.syncDeviceFromUbr();
    		log.info("sync device end");
    	}
    }
}
