package com.secusoft.web.tusouapi.model;


import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 离线视频分析任务
 */
public class RecordOfflineTaskBean {

    private Integer id;

    private String taskId;

    private String deviceId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    //任务进度
    private Integer progress;
    //任务状态
    private String enable;
    //任务创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    //任务删除（取消）时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date destoryTime;

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getTaskId() { return taskId; }

    public void setTaskId(String taskId) { this.taskId = taskId; }

    public String getDeviceId() { return deviceId; }

    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }

    public Date getBeginTime() { return beginTime; }

    public void setBeginTime(Date beginTime) { this.beginTime = beginTime; }

    public Date getEndTime() { return endTime; }

    public void setEndTime(Date endTime) { this.endTime = endTime; }

    public Integer getProgress() { return progress; }

    public void setProgress(Integer progress) { this.progress = progress; }

    public String getEnable() { return enable; }

    public void setEnable(String enable) { this.enable = enable; }

    public Date getCreateTime() { return createTime; }

    public void setCreateTime(Date createTime) { this.createTime = createTime; }

    public Date getDestoryTime() { return destoryTime; }

    public void setDestoryTime(Date destoryTime) { this.destoryTime = destoryTime; }

    @Override
    public String toString() {
        return "RecordOfflineTaskBean{" +
                "id=" + id +
                ", taskId='" + taskId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", progress=" + progress +
                ", enable='" + enable + '\'' +
                ", createTime=" + createTime +
                ", destoryTime='" + destoryTime + '\'' +
                '}';
    }
}
