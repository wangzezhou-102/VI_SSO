package com.secusoft.web.shipinapi.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 视频启流/停流Request参数
 * @author chjiang
 * @since 2019/6/13 13:15
 */
public class StreamRequest {

    /**
     * 识别码
     */
    private String appId;

    /**
     * 设备编号
     */
    private String deviceId;

    /**
     * 任务开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 任务结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
