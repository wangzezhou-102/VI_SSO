package com.secusoft.web.model;


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

    @Override
    public String toString() {
        return "ViOfflineTaskBean{" +
                "id=" + id +
                ", taskId='" + taskId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", beigntime=" + beginTime +
                ", endtime=" + endTime +
                ", status=" + progress +
                ", isDelete='" + enable + '\'' +
                '}';

    }
}
