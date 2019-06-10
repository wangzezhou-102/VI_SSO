package com.secusoft.web.model;

import java.util.List;

public class AreaBean {
    private String id;//主键
    private String areaName;//区域名称
    private Integer folderId;//文件夹关联Id
    private List<DeviceBean> deviceBeans;

    public AreaBean() {
    }

    public AreaBean(String id, String areaName, Integer folderId, List<DeviceBean> deviceBeans) {
        this.id = id;
        this.areaName = areaName;
        this.folderId = folderId;
        this.deviceBeans = deviceBeans;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getFolderId() {
        return folderId;
    }

    public void setFolderId(Integer folderId) {
        this.folderId = folderId;
    }

    public List<DeviceBean> getDeviceBeans() {
        return deviceBeans;
    }

    public void setDeviceBeans(List<DeviceBean> deviceBeans) {
        this.deviceBeans = deviceBeans;
    }

    @Override
    public String toString() {
        return "AreaBean{" +
                "id='" + id + '\'' +
                ", areaName='" + areaName + '\'' +
                ", folderId=" + folderId +
                ", deviceBeans=" + deviceBeans +
                '}';
    }
}
