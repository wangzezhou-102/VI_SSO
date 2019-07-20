package com.secusoft.web.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ViRealtimeTraceReponse {

    /**
     * 设备编号，以数组形式
     */
    private String[] deviceId;

    /**
     * 图片base64
     */
    private String content;

    private Integer id;

    private String traceId;

    private String traceName;

    private String userId;

    private Integer action;

    private Date createTime;

    private Date modifyTime;

    private Integer validState;

    private Integer areaType;

    public Integer getAreaType() {
        return areaType;
    }

    public void setAreaType(Integer areaType) {
        this.areaType = areaType;
    }

    public String[] getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String[] deviceId) {
        this.deviceId = deviceId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getTraceName() {
        return traceName;
    }

    public void setTraceName(String traceName) {
        this.traceName = traceName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getValidState() {
        return validState;
    }

    public void setValidState(Integer validState) {
        this.validState = validState;
    }
}
