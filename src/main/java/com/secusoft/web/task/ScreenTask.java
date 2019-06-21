package com.secusoft.web.task;

import com.secusoft.web.service.ScreenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 同步设备任务
 * @author hbxing
 * @company 视在数科
 * @date 2019年6月19日
 */
@Component
public class ScreenTask {
    private static final Logger log = LoggerFactory.getLogger(SyncDeviceTask.class);
    @Autowired
    private ScreenService blueScreenService;
    //@Scheduled(cron="0 0/1 * * * ?")
    public void syncDevice() {
        log.info("blue screen start");
        blueScreenService.updateBlueScreenFloatingFrame();
        blueScreenService.updateScreenDateIndicator();
        blueScreenService.readVideoApplication();
        log.info("blue screen end");
    }
}
