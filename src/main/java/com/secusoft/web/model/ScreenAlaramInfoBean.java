package com.secusoft.web.model;

import java.io.Serializable;

/**
 *蓝色大屏浮框 报警统计
 * @author hbxing
 * @date 2019/6/20
 */
public class ScreenAlaramInfoBean implements Serializable {
    private Integer AlarmTotal;
    private Integer SurveyAlarmTotal;
    private Integer securityAlarmTotal;
    private Integer patrolAlarmTotal;

    public ScreenAlaramInfoBean() {
    }

    public ScreenAlaramInfoBean(Integer alarmTotal, Integer surveyAlarmTotal, Integer securityAlarmTotal, Integer patrolAlarmTotal) {
        AlarmTotal = alarmTotal;
        SurveyAlarmTotal = surveyAlarmTotal;
        this.securityAlarmTotal = securityAlarmTotal;
        this.patrolAlarmTotal = patrolAlarmTotal;
    }

    public Integer getAlarmTotal() {
        return AlarmTotal;
    }

    public void setAlarmTotal(Integer alarmTotal) {
        AlarmTotal = alarmTotal;
    }

    public Integer getSurveyAlarmTotal() {
        return SurveyAlarmTotal;
    }

    public void setSurveyAlarmTotal(Integer surveyAlarmTotal) {
        SurveyAlarmTotal = surveyAlarmTotal;
    }

    public Integer getSecurityAlarmTotal() {
        return securityAlarmTotal;
    }

    public void setSecurityAlarmTotal(Integer securityAlarmTotal) {
        this.securityAlarmTotal = securityAlarmTotal;
    }

    public Integer getPatrolAlarmTotal() {
        return patrolAlarmTotal;
    }

    public void setPatrolAlarmTotal(Integer patrolAlarmTotal) {
        this.patrolAlarmTotal = patrolAlarmTotal;
    }

    @Override
    public String toString() {
        return "ScreenAlaramInfoBean{" +
                "AlarmTotal=" + AlarmTotal +
                ", SurveyAlarmTotal=" + SurveyAlarmTotal +
                ", securityAlarmTotal=" + securityAlarmTotal +
                ", patrolAlarmTotal=" + patrolAlarmTotal +
                '}';
    }
}
