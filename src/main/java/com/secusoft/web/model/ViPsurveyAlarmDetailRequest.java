package com.secusoft.web.model;

import com.secusoft.web.utils.PageReqAbstractModel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ViPsurveyAlarmDetailRequest extends PageReqAbstractModel {

    /**
     * 布控任务
     */
    private String surveyName;

    /**
     * 布控目标名称
     */
    private String name;

    /**
     * 布控设备列表
     */
    private String[] deviceId;
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
     * 处理状态 0-忽略 1-关注 2-无意义
     */
    private Integer alarmStatus;

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String[] deviceId) {
        this.deviceId = deviceId;
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

    public Integer getAlarmStatus() {
        return alarmStatus;
    }

    public void setAlarmStatus(Integer alarmStatus) {
        this.alarmStatus = alarmStatus;
    }
}
