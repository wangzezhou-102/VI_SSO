package com.secusoft.web.task;

import com.alibaba.fastjson.JSON;
import com.secusoft.web.config.NormalConfig;
import com.secusoft.web.config.ServiceApiConfig;
import com.secusoft.web.model.ViRepoBean;
import com.secusoft.web.serviceapi.ServiceApiClient;
import com.secusoft.web.serviceapi.model.BaseResponse;
import com.secusoft.web.serviceapi.model.ZdryResponse;
import com.secusoft.web.utils.OdpsUtils;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.text.ParseException;

/**
 * 烽火数据同步Controller
 */
@Component
@Configurable
@EnableScheduling
public class BkrepoDataTask {

    /**
     * 烽火定时请求数据
     *
     * @throws ParseException
     * @throws InterruptedException
     */
    //0 0 */1 * * ? 每小时执行一次
    //0 0/1 * * * ? 每分钟执行一次
    //@Scheduled(cron = "0 0 */1 * * ?")//0 0 */1 * * ?
    public void BkrepoData() throws ParseException, InterruptedException {
        String[] bkrepoTable =null;
        if (NormalConfig.getBkrepoType() == 1) {
            bkrepoTable = NormalConfig.getBkrepoTable1().split(",");
            ViRepoBean viRepoBean = null;
            for (String str : bkrepoTable) {
                OdpsUtils.table = "vi_" + str + "_linshi";
                OdpsUtils.sql = "select * from vi_" + str + ";";
                OdpsUtils.runSql();
                OdpsUtils.tunnel();
                return;
            }
        } else if (NormalConfig.getBkrepoType() == 2) {
            BaseResponse baseResponse =null;
            bkrepoTable = NormalConfig.getBkrepoTable2().split(",");
            for (String str : bkrepoTable) {
                if("view_qgzt".equals(str)){
                    baseResponse = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getViewQgztList());
                    ZdryResponse zdryResponse = JSON.parseObject(JSON.toJSONString(baseResponse.getData()), ZdryResponse.class);

                }else if("view_sgy".equals(str)){
                    baseResponse = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getViewSgyList());
                    ZdryResponse zdryResponse = JSON.parseObject(JSON.toJSONString(baseResponse.getData()), ZdryResponse.class);

                }else if("view_lk".equals(str)){
                    baseResponse = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getViewLkList());
                    ZdryResponse zdryResponse = JSON.parseObject(JSON.toJSONString(baseResponse.getData()), ZdryResponse.class);

                }else if("view_sdts".equals(str)){
                    baseResponse = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getViewSdtsList());
                    ZdryResponse zdryResponse = JSON.parseObject(JSON.toJSONString(baseResponse.getData()), ZdryResponse.class);

                }
            }
        }
    }
}
