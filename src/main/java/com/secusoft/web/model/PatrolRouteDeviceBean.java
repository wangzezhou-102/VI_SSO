package com.secusoft.web.model;

/**
 * 巡逻路线对应设备
 * @author Wangzezhou
 * @company 视在数科
 * @date 2019年7月1日
 */
public class PatrolRouteDeviceBean {
    private Integer id;

    private Integer routeId;

    private String deviceId;
    //设备状态和路线状态保持一致（状态  0-无操作 1-使用中 2-未使用 ）
    private Integer status;

    private DeviceBean deviceBean;

    public Integer getStatus() { return status; }

    public void setStatus(Integer status) { this.status = status; }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public Integer getRouteId() { return routeId; }

    public void setRouteId(Integer routeId) { this.routeId = routeId; }

    public String getDeviceId() { return deviceId; }

    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }

    public DeviceBean getDeviceBean() { return deviceBean; }

    public void setDeviceBean(DeviceBean deviceBean) { this.deviceBean = deviceBean; }
}
