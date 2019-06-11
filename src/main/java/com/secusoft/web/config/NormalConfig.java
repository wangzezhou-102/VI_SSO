package com.secusoft.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author chjiang
 * @since 2019/6/10 15:21
 */

@Component
@ConfigurationProperties(prefix = "Normal")
public class NormalConfig {

    private static String bkrepoTable;

    private static String addrApiService;

    private static String addrApiShipin;

    private static String addrApiTusou;

    private static String bkrepoDataTaskTime;

    public static String getBkrepoTable() {
        return bkrepoTable;
    }

    @Value("${Normal.BkrepoTable}")
    public static void setBkrepoTable(String bkrepoTable) {
        NormalConfig.bkrepoTable = bkrepoTable;
    }

    public static String getAddrApiService() {
        return addrApiService;
    }

    @Value("${Normal.addrApiService}")
    public static void setAddrApiService(String addrApiService) {
        NormalConfig.addrApiService = addrApiService;
    }

    public static String getAddrApiShipin() {
        return addrApiShipin;
    }

    @Value("${Normal.addrApiShipin}")
    public static void setAddrApiShipin(String addrApiShipin) {
        NormalConfig.addrApiShipin = addrApiShipin;
    }

    public static String getAddrApiTusou() {
        return addrApiTusou;
    }

    @Value("${Normal.addrApiTusou}")
    public static void setAddrApiTusou(String addrApiTusou) {
        NormalConfig.addrApiTusou = addrApiTusou;
    }

    public static String getBkrepoDataTaskTime() {
        return bkrepoDataTaskTime;
    }

    @Value("${Normal.BkrepoDataTaskTime}")
    public static void setBkrepoDataTaskTime(String bkrepoDataTaskTime) {
        NormalConfig.bkrepoDataTaskTime = bkrepoDataTaskTime;
    }
}
