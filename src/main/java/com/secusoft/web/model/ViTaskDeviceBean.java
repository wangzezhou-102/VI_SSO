package com.secusoft.web.model;

/**
 * 布控任务与设备关联表
 */
public class ViTaskDeviceBean {
    private Integer id;

    private String taskId;

    private String deviceId;

    private Boolean status;

    private ViSurveyTaskBean viSurveyTask;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ViSurveyTaskBean getViSurveyTask() {
        return viSurveyTask;
    }

    public void setViSurveyTask(ViSurveyTaskBean viSurveyTask) {
        this.viSurveyTask = viSurveyTask;
    }
}