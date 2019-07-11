package com.secusoft.web.model;

import com.secusoft.web.utils.PageReqAbstractModel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class PatrolAlarmDetailRequest extends PageReqAbstractModel {

    /**
     * 巡逻任务名称
     */
    private String patrolName;

    /**
     * 布控目标名称(姓名，身份信息)
     */
    private String name;

    /**
     * 布控设备列表
     */
    private String[] deviceId;
    /**
     * 巡逻任务开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;

    /**
     * 巡逻任务结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    public String getPatrolName() { return patrolName; }

    public void setPatrolName(String patrolName) { this.patrolName = patrolName; }

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
}
