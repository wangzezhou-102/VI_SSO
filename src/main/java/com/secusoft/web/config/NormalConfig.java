package com.secusoft.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author chjiang
 * @since 2019/6/10 15:21
 */
@Component
@ConfigurationProperties(prefix = "normal")
public class NormalConfig {

    public static String bkrepoTable;

    public static String addrApiService;

    public static String addrApiShipin;

    public static String addrApiTusou;

    public static String bkrepoDataTaskTime;

    public static String getBkrepoTable() {
        return bkrepoTable;
    }

    public static void setBkrepoTable(String bkrepoTable) {
        NormalConfig.bkrepoTable = bkrepoTable;
    }

    public static String getAddrApiService() {
        return addrApiService;
    }

    public static void setAddrApiService(String addrApiService) {
        NormalConfig.addrApiService = addrApiService;
    }

    public static String getAddrApiShipin() {
        return addrApiShipin;
    }

    public static void setAddrApiShipin(String addrApiShipin) {
        NormalConfig.addrApiShipin = addrApiShipin;
    }

    public static String getAddrApiTusou() {
        return addrApiTusou;
    }

    public static void setAddrApiTusou(String addrApiTusou) {
        NormalConfig.addrApiTusou = addrApiTusou;
    }

    public static String getBkrepoDataTaskTime() {
        return bkrepoDataTaskTime;
    }

    public static void setBkrepoDataTaskTime(String bkrepoDataTaskTime) {
        NormalConfig.bkrepoDataTaskTime = bkrepoDataTaskTime;
    }
}
