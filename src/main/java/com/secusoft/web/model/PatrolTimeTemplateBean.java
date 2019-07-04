package com.secusoft.web.model;

import java.util.List;

/**
 * 巡逻任务时间模板
 * @author Wangzezhou
    * @company 视在数科
    * @date 2019年7月3日
 * */
public class PatrolTimeTemplateBean {
    //时间模板id
    private String timeTemplateId;
    //循环周期 0-日 1-周
    private Integer cycle;
    //时间模板名称
    private String timeplateName;
    //时间模板列表
    private List<PatrolTimeSegmentBean> patrolTimeSegmentBeans;

    public String getTimeTemplateId() { return timeTemplateId; }

    public void setTimeTemplateId(String timeTemplateId) { this.timeTemplateId = timeTemplateId; }

    public Integer getCycle() { return cycle; }

    public void setCycle(Integer cycle) { this.cycle = cycle; }

    public String getTimeplateName() { return timeplateName; }

    public void setTimeplateName(String timeplateName) { this.timeplateName = timeplateName; }

    public List<PatrolTimeSegmentBean> getPatrolTimeSegmentBeans() { return patrolTimeSegmentBeans; }

    public void setPatrolTimeSegmentBeans(List<PatrolTimeSegmentBean> patrolTimeSegmentBeans) { this.patrolTimeSegmentBeans = patrolTimeSegmentBeans; }
}
