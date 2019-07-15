package com.secusoft.web.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 巡逻任务报告
 * @author Wangzezhou
 * @company 视在数科
 * @date 2019年7月12日
 */
public class PatrolReportBean {
    //巡逻报告id
    private Integer reportId;
    //报告名称
    private String reportName;
    //报告生成日期
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reportDate;
    //报告内容
    private String reportContent;
    //巡逻任务id
    private String taskId;
    //巡逻任务
    private PatrolTaskBean patrolTaskBean;

    public Integer getReportId() { return reportId; }

    public void setReportId(Integer reportId) { this.reportId = reportId; }

    public String getReportName() { return reportName; }

    public void setReportName(String reportName) { this.reportName = reportName; }

    public Date getReportDate() { return reportDate; }

    public void setReportDate(Date reportDate) { this.reportDate = reportDate; }

    public String getReportContent() { return reportContent; }

    public void setReportContent(String reportContent) { this.reportContent = reportContent; }

    public String getTaskId() { return taskId; }

    public void setTaskId(String taskId) { this.taskId = taskId; }

    public PatrolTaskBean getPatrolTaskBean() { return patrolTaskBean; }

    public void setPatrolTaskBean(PatrolTaskBean patrolTaskBean) { this.patrolTaskBean = patrolTaskBean; }
}
