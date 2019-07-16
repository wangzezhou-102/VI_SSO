package com.secusoft.web.model;

import java.math.BigDecimal;

/**
 * 城市大脑设备信息
 * @author ChenDong
 * @company 视在数科
 * @date 2019年6月13日
 */
public class UbrDeviceBean {

    // 设备ID
    private String deviceId;
    // 实际设备ID
    private String realDeviceId;
    // 设备名
    private String name;
    // 集群编号
    private String clusterSn;
    // 天擎平台地址
    private String tqApi;
    // 行政区划
    private String civilCode;
    // 状态
    private String status;
    // 经度
    private BigDecimal longitude;
    // 纬度
    private BigDecimal latitude;
    // 类型
    private Integer ptzType;
    // 父节点
    private String parentId;
    // 设备IP
    private String ip;
    // 设备端口
    private Integer port;
    // 码流状态 0:未启动; 1:已启动
    private Integer streamState;
    // 视频播放地址
    private String playUrl;
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClusterSn() {
        return clusterSn;
    }

    public void setClusterSn(String clusterSn) {
        this.clusterSn = clusterSn;
    }

    public String getTqApi() {
        return tqApi;
    }

    public void setTqApi(String tqApi) {
        this.tqApi = tqApi;
    }

    public String getCivilCode() {
        return civilCode;
    }

    public void setCivilCode(String civilCode) {
        this.civilCode = civilCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public Integer getPtzType() {
        return ptzType;
    }

    public void setPtzType(Integer ptzType) {
        this.ptzType = ptzType;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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

    public Integer getStreamState() {
        return streamState;
    }

    public void setStreamState(Integer streamState) {
        this.streamState = streamState;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }
    
    public String getRealDeviceId() {
        return realDeviceId;
    }
    
    public void setRealDeviceId(String realDeviceId) {
        this.realDeviceId = realDeviceId;
    }
    @Override
    public String toString() {
        return "UbrDeviceBean [deviceId=" + deviceId + ", name=" + name + ", clusterSn=" + clusterSn + ", tqApi="
                + tqApi + ", civilCode=" + civilCode + ", status=" + status + ", longitude=" + longitude + ", latitude="
                + latitude + ", ptzType=" + ptzType + ", parentId=" + parentId + ", ip=" + ip + ", port=" + port
                + ", streamState=" + streamState + ", playUrl=" + playUrl + "]";
    }
    
}
