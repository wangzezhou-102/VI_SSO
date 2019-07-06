package com.secusoft.web.model;

import java.util.List;

/**
 * @author chjiang
 * @since 2019/7/1 16:44
 */
public class ViPsurveyAlarmVo {

    private String taskId;
    private ViPsurveyAlarmBean src;
    private List<ViPsurveyAlarmDetailBean> similar;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public ViPsurveyAlarmBean getSrc() {
        return src;
    }

    public void setSrc(ViPsurveyAlarmBean src) {
        this.src = src;
    }

    public List<ViPsurveyAlarmDetailBean> getSimilar() {
        return similar;
    }

    public void setSimilar(List<ViPsurveyAlarmDetailBean> similar) {
        this.similar = similar;
    }
}
