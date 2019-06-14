package com.secusoft.web.model;

public class DeviceBean extends Page {

    private static final long serialVersionUID = 1L;
    
    private String id;
    private String deviceId;
    private String parentId;
    private String deviceName;
    private Integer type;
    private String ip;
    private Integer port;
    private String status; //状态 ON-在线 OFF-离线
    private String longitude;//经度
    private String latitude;//纬度

    public DeviceBean() {
    }

    public DeviceBean(String id, String deviceId, String parentId, String deviceName, Integer type, String ip, Integer port, String longitude, String latitude) {
        this.id = id;
        this.deviceId = deviceId;
        this.parentId = parentId;
        this.deviceName = deviceName;
        this.type = type;
        this.ip = ip;
        this.port = port;
        this.status = status;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "DeviceBean [id=" + id + ", deviceId=" + deviceId + ", parentId=" + parentId + ", deviceName="
                + deviceName + ", type=" + type + ", ip=" + ip + ", port=" + port + ", status=" + status
                + ", longitude=" + longitude + ", latitude=" + latitude + "]";
    }
}
