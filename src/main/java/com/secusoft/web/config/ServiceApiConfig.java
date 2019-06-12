package com.secusoft.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author chjiang
 * @since 2019/6/11 16:23
 */
@Component
@ConfigurationProperties(prefix = "ServiceApi")
public class ServiceApiConfig {

    public static String pathBkrepoMeta;

    public static  String pathBkrepoCreate;

    public static  String pathBkmemberAdd;

    public static  String pathBkmemberDelete;

    public static String getPathBkrepoMeta() {
        return pathBkrepoMeta;
    }

    public static void setPathBkrepoMeta(String pathBkrepoMeta) {
        ServiceApiConfig.pathBkrepoMeta = pathBkrepoMeta;
    }

    public static String getPathBkrepoCreate() {
        return pathBkrepoCreate;
    }

    public static void setPathBkrepoCreate(String pathBkrepoCreate) {
        ServiceApiConfig.pathBkrepoCreate = pathBkrepoCreate;
    }

    public static String getPathBkmemberAdd() {
        return pathBkmemberAdd;
    }

    public static void setPathBkmemberAdd(String pathBkmemberAdd) {
        ServiceApiConfig.pathBkmemberAdd = pathBkmemberAdd;
    }

    public static String getPathBkmemberDelete() {
        return pathBkmemberDelete;
    }

    public static void setPathBkmemberDelete(String pathBkmemberDelete) {
        ServiceApiConfig.pathBkmemberDelete = pathBkmemberDelete;
    }
}
