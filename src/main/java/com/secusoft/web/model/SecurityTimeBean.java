package com.secusoft.web.model;

import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 安保任务对应的时间
 * @author huanghao
 * @date 2019-07-08
 */
@TableName("vi_security_time")
public class SecurityTimeBean {
    private Integer id;//主键ID
    private String taskId;//所属任务id
    private Long beginTime;//开始时间（时间戳）
    private Long endTime;//结束时间（时间戳）
    private Integer timeStatus;//当前时间段状态 0-关闭 1-开启
    public SecurityTimeBean() {
        super();
    }

    public SecurityTimeBean(Integer id, String taskId, Long beginTime, Long endTime, Integer timeStatus) {
        this.id = id;
        this.taskId = taskId;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.timeStatus = timeStatus;
    }

    public Integer getId() {
        return this.id;
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

    public Long getBeginTime() {
        return this.beginTime;
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    public Long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Integer getTimeStatus() {
        return this.timeStatus;
    }

    public void setTimeStatus(Integer timeStatus) {
        this.timeStatus = timeStatus;
    }
}
