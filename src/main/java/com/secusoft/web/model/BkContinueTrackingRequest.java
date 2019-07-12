package com.secusoft.web.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class BkContinueTrackingRequest {

    /**
     * 设备编号，以数组形式
     */
    private String[] deviceId;

    /**
     * 查询开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 查询结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 图片base64
     */
    private String content;

    /**
     * from 是跳过多少个结果
     */
    private String from;

    /**
     * size是本次展示多少个结果
     */
    private String size;

    public String[] getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String[] deviceId) {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
