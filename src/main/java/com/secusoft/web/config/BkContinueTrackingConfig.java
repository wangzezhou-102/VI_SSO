package com.secusoft.web.config;


/**
 * 布控追踪-继续追踪天擎相关参数
 */

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author chjiang
 * @since 2019/6/10 15:21
 */
@Component
@ConfigurationProperties(prefix = "BkContinueTracking")
public class BkContinueTrackingConfig {

    private static String uid;

    private static String taskId;

    private static String type;

    public static String getUid() {
        return uid;
    }

    public static void setUid(String uid) {
        BkContinueTrackingConfig.uid = uid;
    }

    public static String getTaskId() {
        return taskId;
    }

    public static void setTaskId(String taskId) {
        BkContinueTrackingConfig.taskId = taskId;
    }

    public static String getType() {
        return type;
    }

    public static void setType(String type) {
        BkContinueTrackingConfig.type = type;
    }
}
