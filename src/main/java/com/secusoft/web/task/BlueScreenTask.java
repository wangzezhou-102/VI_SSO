package com.secusoft.web.task;

import com.secusoft.web.service.BlueScreenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 同步设备任务
 * @author ChenDong
 * @company 视在数科
 * @date 2019年6月13日
 */
@Component
public class BlueScreenTask {
    private static final Logger log = LoggerFactory.getLogger(SyncDeviceTask.class);
    @Autowired
    private BlueScreenService blueScreenService;
    @Scheduled(cron="0 0/1 * * * ?")
    public void syncDevice() {
        System.out.println("----------------");
        log.info("blue screen start");

        blueScreenService.updateScreenDateIndicator();

        log.info("blue screen end");
    }
}
