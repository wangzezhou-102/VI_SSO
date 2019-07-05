package com.secusoft.web.model;

/**
 * @author huanghao
 * @date 2019-07-03
 */
public class SecurityTimeStamp {
    private Integer id;
    private Integer taskId;
    private Long beginTime;
    private Long endTime;

    public SecurityTimeStamp() {
    }

    public SecurityTimeStamp(Integer id, Integer taskId, Long beginTime, Long endTime) {
        this.id = id;
        this.taskId = taskId;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}
