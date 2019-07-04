package com.secusoft.web.model;


import java.util.Date;

/**
 * 时间模板时间段
 * @author Wangzezhou
 * @company 视在数科
 * @date 2019年7月3日
 */
public class PatrolTimeSegmentBean {
    //主键id
    private Integer timeSegmentId;
    //时间模板id
    private String timeTemplateId;
    //时间段开始时间
    private Date beginTime;
    //时间段结束时间
    private Date endTime;
    //星期
    private Integer week;

    public Integer getTimeSegmentId() { return timeSegmentId; }

    public void setTimeSegmentId(Integer timeSegmentId) { this.timeSegmentId = timeSegmentId; }

    public String getTimeTemplateId() { return timeTemplateId; }

    public void setTimeTemplateId(String timeTemplateId) { this.timeTemplateId = timeTemplateId; }

    public Date getBeginTime() { return beginTime; }

    public void setBeginTime(Date beginTime) { this.beginTime = beginTime; }

    public Date getEndTime() { return endTime; }

    public void setEndTime(Date endTime) { this.endTime = endTime; }

    public Integer getWeek() { return week; }

    public void setWeek(Integer week) { this.week = week; }
}
