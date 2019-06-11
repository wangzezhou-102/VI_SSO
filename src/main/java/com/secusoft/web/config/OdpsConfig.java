package com.secusoft.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author chjiang
 * @since 2019/6/10 13:27
 */
@Component
@ConfigurationProperties(prefix = "Odps")
public class OdpsConfig {

    private static String accessId;

    private static String accessKey;

    private static String endPoint;

    private static String project;

    public static String getAccessId() {
        return accessId;
    }

    @Value("${Odps.accessId}")
    public static void setAccessId(String accessId) {
        OdpsConfig.accessId = accessId;
    }

    public static String getAccessKey() {
        return accessKey;
    }

    @Value("${Odps.accessKey}")
    public static void setAccessKey(String accessKey) {
        OdpsConfig.accessKey = accessKey;
    }

    public static String getEndPoint() {
        return endPoint;
    }

    @Value("${Odps.endPoint}")
    public static void setEndPoint(String endPoint) {
        OdpsConfig.endPoint = endPoint;
    }

    public static String getProject() {
        return project;
    }

    @Value("${Odps.project}")
    public static void setProject(String project) {
        OdpsConfig.project = project;
    }
}
