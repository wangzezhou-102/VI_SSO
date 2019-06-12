package com.secusoft.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author chjiang
 * @since 2019/6/10 13:27
 */
@Component
@ConfigurationProperties(prefix = "Odps")
public class OdpsConfig {

    public static String accessId;

    public static String accessKey;

    public static String endPoint;

    public static String project;

    public static void setAccessId(String accessId) {
        OdpsConfig.accessId = accessId;
    }

    public static void setAccessKey(String accessKey) {
        OdpsConfig.accessKey = accessKey;
    }

    public static void setEndPoint(String endPoint) {
        OdpsConfig.endPoint = endPoint;
    }

    public static void setProject(String project) {
        OdpsConfig.project = project;
    }

    public static String getAccessId() {
        return accessId;
    }

    public static String getAccessKey() {
        return accessKey;
    }

    public static String getEndPoint() {
        return endPoint;
    }

    public static String getProject() {
        return project;
    }
}
