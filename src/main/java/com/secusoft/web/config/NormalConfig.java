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

    public static String addrApiService;

    public static String addrApiShipin;

    public static String addrApiBk;

    public static String addrApiTusou;

    public static String bkrepoDataTaskTime;

    public static String streamMinute;

    /**
     * 基础库数据源  1-烽火 2-视频专网orcal
     */
    public static Integer bkrepoType;

    /**
     * 基础库数据源烽火表
     */
    public static String bkrepoTable1;

    /**
     * 基础库数据源视频专网orcal表
     */
    public static String bkrepoTable2;

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

    public static String getAddrApiBk() {
        return addrApiBk;
    }

    public static void setAddrApiBk(String addrApiBk) {
        NormalConfig.addrApiBk = addrApiBk;
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

    public static String getStreamMinute() {
        return streamMinute;
    }

    public static void setStreamMinute(String streamMinute) {
        NormalConfig.streamMinute = streamMinute;
    }

    public static Integer getBkrepoType() {
        return bkrepoType;
    }

    public static void setBkrepoType(Integer bkrepoType) {
        NormalConfig.bkrepoType = bkrepoType;
    }

    public static String getBkrepoTable1() {
        return bkrepoTable1;
    }

    public static void setBkrepoTable1(String bkrepoTable1) {
        NormalConfig.bkrepoTable1 = bkrepoTable1;
    }

    public static String getBkrepoTable2() {
        return bkrepoTable2;
    }

    public static void setBkrepoTable2(String bkrepoTable2) {
        NormalConfig.bkrepoTable2 = bkrepoTable2;
    }
}
