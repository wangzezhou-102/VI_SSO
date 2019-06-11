package com.secusoft.web.controller;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${normal.BkrepoTable}")
    private String bkrepoTable;

    @Value("${normal.addrApiService}")
    private String addrApiService;

    @Value("${normal.addrApiShipin}")
    private String addrApiShipin;

    @Value("${normal.addrApiTusou}")
    private String addrApiTusou;

    /**
     * 烽火定时请求数据
     *
     * @throws ParseException
     * @throws InterruptedException
     */
    //0 0 */1 * * ? 每小时执行一次
    //0 0/1 * * * ? 每分钟执行一次
    @Scheduled(cron = "0 0 */1 * * ?")//0 0 */1 * * ?
    public void task() throws ParseException, InterruptedException {
//        String[] bkrepoTable = bkrepoTable.split(",");
//        ViRepoBean viRepoBean=null;
//        for (String str : bkrepoTable) {
//            OdpsUtils.table = "vi_"+str + "_linshi";
//            OdpsUtils.sql = "select * from vi_" + str + ";";
//            OdpsUtils.runSql();
//            OdpsUtils.tunnel();
//            return;
//        }
        System.out.println("启动烽火定时任务");
        System.out.println(bkrepoTable);
        System.out.println(addrApiService);
        System.out.println(addrApiShipin);
        System.out.println(addrApiTusou);
//        System.out.println(bkrepoConfig.getMeta().getAlgorithmName());
//        System.out.println(bkrepoConfig.getMeta().getOssInfo().getAccess_id());
//        System.out.println(normalConfig.getAddrApiService());
//        System.out.println(normalConfig.getAddrApiShipin());
//        System.out.println(normalConfig.getAddrApiTusou());
//
//        System.out.println(serviceApiConfig.getPathBkmemberAdd());
        //System.out.println(new BkrepoConfig().getMeta().);
    }
}
