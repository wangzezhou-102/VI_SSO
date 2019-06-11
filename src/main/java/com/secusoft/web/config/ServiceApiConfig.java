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

    private String pathBkrepoMeta;

    private String pathBkrepoCreate;

    private String pathBkmemberAdd;

    private String pathBkmemberDelete;

    public String getPathBkrepoMeta() {
        return pathBkrepoMeta;
    }

    public void setPathBkrepoMeta(String pathBkrepoMeta) {
        this.pathBkrepoMeta = pathBkrepoMeta;
    }

    public String getPathBkrepoCreate() {
        return pathBkrepoCreate;
    }

    public void setPathBkrepoCreate(String pathBkrepoCreate) {
        this.pathBkrepoCreate = pathBkrepoCreate;
    }

    public String getPathBkmemberAdd() {
        return pathBkmemberAdd;
    }

    public void setPathBkmemberAdd(String pathBkmemberAdd) {
        this.pathBkmemberAdd = pathBkmemberAdd;
    }

    public String getPathBkmemberDelete() {
        return pathBkmemberDelete;
    }

    public void setPathBkmemberDelete(String pathBkmemberDelete) {
        this.pathBkmemberDelete = pathBkmemberDelete;
    }
}
