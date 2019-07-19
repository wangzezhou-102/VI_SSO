package com.secusoft.web.model;

/**
 * 巡逻任务对应路线
 * @author Wangzezhou
 * @company 视在数科
 * @date 2019年7月3日
 */
public class PatrolTaskRouteBean {
    private Integer id;
    //巡逻任务id
    private String taskId;
    //任务路线id
    private Integer routeId;
    
    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getTaskId() { return taskId; }

    public void setTaskId(String taskId) { this.taskId = taskId; }

    public Integer getRouteId() { return routeId; }

    public void setRouteId(Integer routeId) { this.routeId = routeId; }
}
