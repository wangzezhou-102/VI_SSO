package com.secusoft.web.model;

import java.io.Serializable;
import java.util.List;

/**
 * 安保任务实体
 * @author huanghao
 * @date 2019-07-03
 */
public class SecurityTaskBean implements Serializable {
    private static final long serialVersionUID = 1L;

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
     * 安保状态（对接阿里 默认失败）
     */
    private Integer securityStatus;
    /**
     * output主题信息，topic+task_id
     */
    private String topic;
    /**
     * 操作人员编号
     */
    private String userId;
    private String orgCode;
    /**
     * 任务状态
     */
    private Integer isfinish;
    /**
     * 是否有效 0-无效（即删除） 1-有效（默认有效）
     */
    private Integer validState;
    private List<ViTaskRepoBean> repoids;

    public SecurityTaskBean() {
    }

    public SecurityTaskBean(Integer id, String taskId, String securityName, Integer securityType, List<CodeplacesBean> codeplacesBeans, List<SecurityTimeBean> timeStamps, String exposition, Integer enable, Integer securityStatus, String topic, String userId, String orgCode, Integer isfinish, Integer validState, List<ViTaskRepoBean> repoids) {
        this.id = id;
        this.taskId = taskId;
        this.securityName = securityName;
        this.securityType = securityType;
        this.codeplacesBeans = codeplacesBeans;
        this.timeStamps = timeStamps;
        this.exposition = exposition;
        this.enable = enable;
        this.securityStatus = securityStatus;
        this.topic = topic;
        this.userId = userId;
        this.orgCode = orgCode;
        this.isfinish = isfinish;
        this.validState = validState;
        this.repoids = repoids;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public Integer getSecurityStatus() {
        return securityStatus;
    }

    public void setSecurityStatus(Integer securityStatus) {
        this.securityStatus = securityStatus;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
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

    public Integer getIsfinish() {
        return isfinish;
    }

    public void setIsfinish(Integer isfinish) {
        this.isfinish = isfinish;
    }

    public Integer getValidState() {
        return validState;
    }

    public void setValidState(Integer validState) {
        this.validState = validState;
    }

    public List<ViTaskRepoBean> getRepoids() {
        return repoids;
    }

    public void setRepoids(List<ViTaskRepoBean> repoids) {
        this.repoids = repoids;
    }

    @Override
    public String toString() {
        return "SecurityTaskBean{" +
                "id=" + id +
                ", taskId='" + taskId + '\'' +
                ", securityName='" + securityName + '\'' +
                ", securityType=" + securityType +
                ", codeplacesBeans=" + codeplacesBeans +
                ", timeStamps=" + timeStamps +
                ", exposition='" + exposition + '\'' +
                ", enable=" + enable +
                ", securityStatus=" + securityStatus +
                ", topic='" + topic + '\'' +
                ", userId='" + userId + '\'' +
                ", orgCode='" + orgCode + '\'' +
                ", isfinish=" + isfinish +
                ", validState=" + validState +
                ", repoids=" + repoids +
                '}';
    }
}
