package com.secusoft.web.model;

import java.util.List;

/**
 * @author wangzezhou
 * @since 2019/7/11 16:44
 */
public class PatrolAlarmVo {

    private String taskId;
    private PatrolAlarmBean src;
    private List<PatrolAlarmDetailBean> similar;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public PatrolAlarmBean getSrc() { return src; }

    public void setSrc(PatrolAlarmBean src) { this.src = src; }

    public List<PatrolAlarmDetailBean> getSimilar() { return similar; }

    public void setSimilar(List<PatrolAlarmDetailBean> similar) { this.similar = similar; }
}
