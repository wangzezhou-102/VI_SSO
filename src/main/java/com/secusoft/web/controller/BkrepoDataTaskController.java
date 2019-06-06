package com.secusoft.web.controller;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;

/**
 * 烽火数据同步Controller
 */
@Component
@Configurable
@EnableScheduling
public class BkrepoDataTaskController {

        /**
     * 烽火定时请求数据
     * @throws ParseException
     * @throws InterruptedException
     */
    //0 0 */1 * * ? 每小时执行一次
    //0 0/1 * * * ? 每分钟执行一次
    @Scheduled(cron = "0 0 */2 * * ?")//0 0 */1 * * ?
    public void task() throws ParseException, InterruptedException {
        System.out.println("启动烽火定时任务");
    }
}
