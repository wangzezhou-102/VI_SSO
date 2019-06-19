package com.secusoft.web.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


public class ViSurveyTaskRequest implements Serializable,Cloneable {
    private static final long serialVersionUID = 1L;

    /**
     * 布控任务Id
     */
    private Integer id;

    /**
     * 布控任务名称
     */
    private String taskId;

    /**
     * 布控任务名称
     */
    private String surveyName;
    /**
     * 布控任务开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;
    /**
     * 布控任务结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    /**
     * 布控类型 1-人员 2-车辆 3-事件 4-物品
     */
    private Integer surveyType;
    /**
     * 区域或框选择 1-区域选择 2-不规则圈选 3-不规则框选
     */
    private Integer areaType;
    /**
     * 所有设备信息
     */
    private String surveyDevice;
    /**
     * 所有布控库信息
     */
    private String surveyRepo;
    /**
     * 操作人员编号
     */
    private Integer userId;

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
        this.taskId = taskId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getSurveyType() {
        return surveyType;
    }

    public void setSurveyType(Integer surveyType) {
        this.surveyType = surveyType;
    }

    public Integer getAreaType() {
        return areaType;
    }

    public void setAreaType(Integer areaType) {
        this.areaType = areaType;
    }

    public String getSurveyDevice() {
        return surveyDevice;
    }

    public void setSurveyDevice(String surveyDevice) {
        this.surveyDevice = surveyDevice;
    }

    public String getSurveyRepo() {
        return surveyRepo;
    }

    public void setSurveyRepo(String surveyRepo) {
        this.surveyRepo = surveyRepo;
    }    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
