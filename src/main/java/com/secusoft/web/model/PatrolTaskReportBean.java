package com.secusoft.web.model;

import java.util.Date;
/**
 * 巡逻任务报告
 * @author Wangzezhou
 * @company 视在数科
 * @date 2019年7月3日
 */
public class PatrolTaskReportBean {
    //id
    private Integer reportId;
    //巡逻任务id
    private String taskId;
    //巡逻任务
    private PatrolTaskBean patrolTaskBean;
    //报告名称
    private String reportName;
    //报告时间
    private Date reportDate;
    //报告内容
    private String reportContent;

    public PatrolTaskBean getPatrolTaskBean() { return patrolTaskBean; }

    public void setPatrolTaskBean(PatrolTaskBean patrolTaskBean) { this.patrolTaskBean = patrolTaskBean; }

    public Integer getReportId() { return reportId; }

    public void setReportId(Integer reportId) { this.reportId = reportId; }

    public String getTaskId() { return taskId; }

    public void setTaskId(String taskId) { this.taskId = taskId; }

    public String getReportName() { return reportName; }

    public void setReportName(String reportName) { this.reportName = reportName; }

    public Date getReportDate() { return reportDate; }

    public void setReportDate(Date reportDate) { this.reportDate = reportDate; }

    public String getReportContent() { return reportContent; }

    public void setReportContent(String reportContent) { this.reportContent = reportContent; }
}
