package com.secusoft.web.controller;

import com.secusoft.web.config.BkrepoConfig;
import com.secusoft.web.config.NormalConfig;
import com.secusoft.web.config.OdpsConfig;
import com.secusoft.web.model.ViRepoBean;
import com.secusoft.web.utils.OdpsUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    OdpsConfig odpsConfig;

    @Autowired
    BkrepoConfig bkrepoConfig;


    @Autowired
    NormalConfig normalConfig;

    /**
     * 烽火定时请求数据
     *
     * @throws ParseException
     * @throws InterruptedException
     */
    //0 0 */1 * * ? 每小时执行一次
    //0 0/1 * * * ? 每分钟执行一次
    @Scheduled(cron = "0 0/1 * * * ?")//0 0 */1 * * ?
    public void task() throws ParseException, InterruptedException {
        String[] bkrepoTable = NormalConfig.getBkrepoTable().split(",");
        ViRepoBean viRepoBean=null;
        for (String str : bkrepoTable) {
            OdpsUtils.table = "vi_"+str + "_linshi";
            OdpsUtils.sql = "select * from vi_" + str + ";";
            OdpsUtils.runSql();
            OdpsUtils.tunnel();
            return;
        }
//        System.out.println("启动烽火定时任务");
//        System.out.println(BkrepoTable);
//        System.out.println(OdpsUtils.getOdps());
//        System.out.println(OdpsConfig.getProject());
//        System.out.println(bkrepoConfig.getBkid());
//        System.out.println(bkrepoConfig.getMeta().getAlgorithmName());
//        System.out.println(bkrepoConfig.getMeta().getOssInfo().getAccess_id());
//        System.out.println(NormalConfig.getAddrApiService());
//        System.out.println(NormalConfig.getAddrApiShipin());
//        System.out.println(NormalConfig.getAddrApiTusou());
//        System.out.println(NormalConfig.getBkrepoTable());
        //System.out.println(new BkrepoConfig().getMeta().);
    }
}
