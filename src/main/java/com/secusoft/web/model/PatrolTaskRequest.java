package com.secusoft.web.model;


import java.util.List;

public class PatrolTaskRequest {
    //任务id
    private String taskId;
    //任务名称 patrolName 创建者id create_id create_id对应的组织code'
    private String patrolName;
    //修改者id
    private Integer updateId;
    //组织code
    private String orgCode;
    //循环周期
    private Integer cycle;
    //启停状态
    private Integer enable;
    //任务状态
    private Integer status;
    //时间模板ID
    private String timeTemplateId;
    //时间段
    private List<PatrolTimeSegmentBean> patrolTimeSegments;
    //预置路线
    private List<PatrolRouteBean> patrolRoutes;
    //设备列表
    private List<DeviceBean> devices;
    //目标库
    private List<ViRepoBean> repoes;

    public String getTimeTemplateId() { return timeTemplateId; }

    public void setTimeTemplateId(String timeTemplateId) { this.timeTemplateId = timeTemplateId; }

    public Integer getStatus() { return status; }

    public void setStatus(Integer status) { this.status = status; }

    public String getTaskId() { return taskId; }

    public void setTaskId(String taskId) { this.taskId = taskId; }

    public Integer getEnable() { return enable; }

    public void setEnable(Integer enable) { this.enable = enable; }

    public String getPatrolName() { return patrolName; }

    public void setPatrolName(String patrolName) { this.patrolName = patrolName; }

    public Integer getUpdateId() { return updateId; }

    public void setUpdateId(Integer updateId) { this.updateId = updateId; }

    public String getOrgCode() { return orgCode; }

    public void setOrgCode(String orgCode) { this.orgCode = orgCode; }

    public Integer getCycle() { return cycle; }

    public void setCycle(Integer cycle) { this.cycle = cycle; }

    public List<PatrolTimeSegmentBean> getPatrolTimeSegments() { return patrolTimeSegments; }

    public void setPatrolTimeSegments(List<PatrolTimeSegmentBean> patrolTimeSegments) { this.patrolTimeSegments = patrolTimeSegments; }

    public List<PatrolRouteBean> getPatrolRoutes() { return patrolRoutes; }

    public void setPatrolRoutes(List<PatrolRouteBean> patrolRoutes) { this.patrolRoutes = patrolRoutes; }

    public List<DeviceBean> getDevices() { return devices; }

    public void setDevices(List<DeviceBean> devices) { this.devices = devices; }

    public List<ViRepoBean> getRepoes() { return repoes; }

    public void setRepoes(List<ViRepoBean> repoes) { this.repoes = repoes; }

    /**
    * 数据校验
    * */
    public boolean validateTaskAll(){
        if(patrolName == null || updateId == null || cycle == null || patrolTimeSegments == null || patrolRoutes == null|| devices == null || repoes == null){
            return false;
        }
        return true;
    }
    public boolean validateTaskIdAndEnable(){
        if(taskId == null || enable == null){
            return false;
        }
        return true;
    }
    public boolean validateOrgCode(){
        if(orgCode == null){
            return false;
        }
        return true;
    }
    public boolean validateStatus(){
        if(status == null){
            return false;
        }
        return true;
    }
    public boolean validateTimeTemplate(){
        if(timeTemplateId == null){
            return false;
        }
        return true;
    }

}
