package com.secusoft.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author chjiang
 * @since 2019/6/10 15:21
 */

@Component
@ConfigurationProperties(prefix = "normal")
public class NormalConfig {

    @Value("${normal.BkrepoTable}")
    private String bkrepoTable;

    @Value("${normal.addrApiService}")
    private String addrApiService;

    @Value("${normal.addrApiService}")
    private String addrApiShipin;

    @Value("${normal.addrApiService}")
    private String addrApiTusou;

    @Value("${normal.addrApiService}")
    private String bkrepoDataTaskTime;

    public String getBkrepoTable() {
        return bkrepoTable;
    }

    public void setBkrepoTable(String bkrepoTable) {
        this.bkrepoTable = bkrepoTable;
    }

    public String getAddrApiService() {
        return addrApiService;
    }

    public void setAddrApiService(String addrApiService) {
        this.addrApiService = addrApiService;
    }

    public String getAddrApiShipin() {
        return addrApiShipin;
    }

    public void setAddrApiShipin(String addrApiShipin) {
        this.addrApiShipin = addrApiShipin;
    }

    public String getAddrApiTusou() {
        return addrApiTusou;
    }

    public void setAddrApiTusou(String addrApiTusou) {
        this.addrApiTusou = addrApiTusou;
    }

    public String getBkrepoDataTaskTime() {
        return bkrepoDataTaskTime;
    }

    public void setBkrepoDataTaskTime(String bkrepoDataTaskTime) {
        this.bkrepoDataTaskTime = bkrepoDataTaskTime;
    }
}
