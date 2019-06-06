package com.secusoft.web.model;

import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


@TableName("vi_survey_task")
public class ViSurveyTaskBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String taskId;
    private String surveyName;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private Integer surveyType;
    private Integer surveyStatus;
    private Integer areaType;
    private String surveyDevice;
    private String topic;
    private Integer enable;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date CreateTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ModifyTime;

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

    public Integer getSurveyStatus() {
        return surveyStatus;
    }

    public void setSurveyStatus(Integer surveyStatus) {
        this.surveyStatus = surveyStatus;
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

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Date getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Date createTime) {
        CreateTime = createTime;
    }

    public Date getModifyTime() {
        return ModifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        ModifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "ViSurveyTask{" +
                "id=" + id +
                ", taskId='" + taskId + '\'' +
                ", surveyName='" + surveyName + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", surveyType=" + surveyType +
                ", surveyStatus=" + surveyStatus +
                ", areaType=" + areaType +
                ", surveyDevice='" + surveyDevice + '\'' +
                ", topic='" + topic + '\'' +
                ", enable=" + enable +
                ", CreateTime=" + CreateTime +
                ", ModifyTime=" + ModifyTime +
                '}';
    }
}
