package com.secusoft.web.model;

import java.util.Date;
import java.util.List;

public class ViRealtimeTraceBean {
    private Integer id;

    private String traceId;

    private String traceName;

    private String userId;

    private Integer action;

    private Date createTime;

    private Date modifyTime;

    private Integer validState;

    private String content;

    private List<ViTraceDeviceBean> viTraceDeviceBeans;

    private Integer areaType;

    public Integer getAreaType() {
        return areaType;
    }

    public void setAreaType(Integer areaType) {
        this.areaType = areaType;
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
        this.traceId = traceId == null ? null : traceId.trim();
    }

    public String getTraceName() {
        return traceName;
    }

    public void setTraceName(String traceName) {
        this.traceName = traceName == null ? null : traceName.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public List<ViTraceDeviceBean> getViTraceDeviceBeans() {
        return viTraceDeviceBeans;
    }

    public void setViTraceDeviceBeans(List<ViTraceDeviceBean> viTraceDeviceBeans) {
        this.viTraceDeviceBeans = viTraceDeviceBeans;
    }
}