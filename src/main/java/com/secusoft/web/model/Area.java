package com.secusoft.web.model;

import java.util.List;

public class Area {
    private String id;//主键
    private String areaName;//区域名称
    private Integer folderId;//文件夹关联Id
    private List<Device> devices;

    public Area() {
    }

    public Area(String id, String areaName, Integer folderId, List<Device> devices) {
        this.id = id;
        this.areaName = areaName;
        this.folderId = folderId;
        this.devices = devices;
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

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    @Override
    public String toString() {
        return "Area{" +
                "id='" + id + '\'' +
                ", areaName='" + areaName + '\'' +
                ", folderId=" + folderId +
                ", devices=" + devices +
                '}';
    }
}
