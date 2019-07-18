package com.secusoft.web.model;

import java.util.List;

/**
 * @author huanghao
 * @date 2019-07-06
 */
public class SecurityTaskRequest {
    /**
     * 安保任务id
     */
    private Integer id;
    private String taskId;
    /**
     *  安保任务名称
     */
    private String securityName;
    /**
     * 安保任务类型
     */
    private Integer securityType;
    /**
     * 安保任务地点
     */
    private List<CodeplacesBean> codeplacesBeans;
    /**
     * 安保任务时间
     */
    private List<SecurityTimeBean> timeStamps;
    /**
     * 安保任务说明
     */
    private String exposition;
    /**
     * 是否开启 0-结束 1-开启
     */
    private Integer enable;
    /**
     * 操作人员编号
     */
    private String userId;
    private String orgCode;
    private List<Integer> repoids;
    /**
     * 任务状态
     */
    private Integer isfinish;
    private List<String> cameraId;
    /**
     * 开始时间结束时间
     */
    private Long beginTime;
    private Long endTime;
    private Integer securityStatus;
    public SecurityTaskRequest() {
    }

    public SecurityTaskRequest(Integer id, String taskId, String securityName, Integer securityType, List<CodeplacesBean> codeplacesBeans, List<SecurityTimeBean> timeStamps, String exposition, Integer enable, String userId, String orgCode, List<Integer> repoids, Integer isfinish, List<String> cameraId, Long beginTime, Long endTime, Integer securityStatus) {
        this.id = id;
        this.taskId = taskId;
        this.securityName = securityName;
        this.securityType = securityType;
        this.codeplacesBeans = codeplacesBeans;
        this.timeStamps = timeStamps;
        this.exposition = exposition;
        this.enable = enable;
        this.userId = userId;
        this.orgCode = orgCode;
        this.repoids = repoids;
        this.isfinish = isfinish;
        this.cameraId = cameraId;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.securityStatus = securityStatus;
    }

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

    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public Integer getSecurityType() {
        return securityType;
    }

    public void setSecurityType(Integer securityType) {
        this.securityType = securityType;
    }

    public List<CodeplacesBean> getCodeplacesBeans() {
        return codeplacesBeans;
    }

    public void setCodeplacesBeans(List<CodeplacesBean> codeplacesBeans) {
        this.codeplacesBeans = codeplacesBeans;
    }

    public List<SecurityTimeBean> getTimeStamps() {
        return timeStamps;
    }

    public void setTimeStamps(List<SecurityTimeBean> timeStamps) {
        this.timeStamps = timeStamps;
    }

    public String getExposition() {
        return exposition;
    }

    public void setExposition(String exposition) {
        this.exposition = exposition;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public List<Integer> getRepoids() {
        return repoids;
    }

    public void setRepoids(List<Integer> repoids) {
        this.repoids = repoids;
    }

    public Integer getIsfinish() {
        return isfinish;
    }

    public void setIsfinish(Integer isfinish) {
        this.isfinish = isfinish;
    }

    public List<String> getCameraId() {
        return cameraId;
    }

    public void setCameraId(List<String> cameraId) {
        this.cameraId = cameraId;
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

    public Integer getSecurityStatus() {
        return securityStatus;
    }

    public void setSecurityStatus(Integer securityStatus) {
        this.securityStatus = securityStatus;
    }

    @Override
    public String toString() {
        return "SecurityTaskRequest{" +
                "id=" + id +
                ", taskId='" + taskId + '\'' +
                ", securityName='" + securityName + '\'' +
                ", securityType=" + securityType +
                ", codeplacesBeans=" + codeplacesBeans +
                ", timeStamps=" + timeStamps +
                ", exposition='" + exposition + '\'' +
                ", enable=" + enable +
                ", userId='" + userId + '\'' +
                ", orgCode='" + orgCode + '\'' +
                ", repoids=" + repoids +
                ", isfinish=" + isfinish +
                ", cameraId=" + cameraId +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", securityStatus=" + securityStatus +
                '}';
    }
}
