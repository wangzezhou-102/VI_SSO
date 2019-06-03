package com.secusoft.web.controller;


import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * 定时任务controller
 */
@Component
@Configurable
@EnableScheduling
public class FireTaskController {

    //0 0 */1 * * ? 每小时执行一次
    //0 0/1 * * * ? 每分钟执行一次
//    @Scheduled(cron = "0 0 */1 * * ?")//0 0 */1 * * ?
//    public void task() throws ParseException, InterruptedException {
//
//    }
}
