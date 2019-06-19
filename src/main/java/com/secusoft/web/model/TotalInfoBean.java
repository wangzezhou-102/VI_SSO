package com.secusoft.web.model;

/**
 * 统计信息实体
 * @author huanghao
 * @date 2019-06-19
 */
public class TotalInfoBean {
    /**
     * 摄像头并发路数
     */
    private Integer deviceConcurrenceTotal;
    /**
     * 摄像头使用总路数
     */
    private Integer deviceUsedTotal;
    /**
     * 目标搜图使用次数
     */
    private Integer seachUsedTotal;
    /**
     * 布控追踪任务个数
     */
    private Integer surveyTaskTotal;
    /**
     * 安保护航任务个数
     */
    private Integer securityTaskTotal;
    /**
     * 区域巡逻执行任务个数
     */
    private Integer areaPatrollingTaskTotal;

    public TotalInfoBean() {
    }

    public TotalInfoBean(Integer deviceConcurrenceTotal, Integer deviceUsedTotal, Integer seachUsedTotal, Integer surveyTaskTotal, Integer securityTaskTotal, Integer areaPatrollingTaskTotal) {
        this.deviceConcurrenceTotal = deviceConcurrenceTotal;
        this.deviceUsedTotal = deviceUsedTotal;
        this.seachUsedTotal = seachUsedTotal;
        this.surveyTaskTotal = surveyTaskTotal;
        this.securityTaskTotal = securityTaskTotal;
        this.areaPatrollingTaskTotal = areaPatrollingTaskTotal;
    }

    public Integer getDeviceConcurrenceTotal() {
        return deviceConcurrenceTotal;
    }

    public void setDeviceConcurrenceTotal(Integer deviceConcurrenceTotal) {
        this.deviceConcurrenceTotal = deviceConcurrenceTotal;
    }

    public Integer getDeviceUsedTotal() {
        return deviceUsedTotal;
    }

    public void setDeviceUsedTotal(Integer deviceUsedTotal) {
        this.deviceUsedTotal = deviceUsedTotal;
    }

    public Integer getSeachUsedTotal() {
        return seachUsedTotal;
    }

    public void setSeachUsedTotal(Integer seachUsedTotal) {
        this.seachUsedTotal = seachUsedTotal;
    }

    public Integer getSurveyTaskTotal() {
        return surveyTaskTotal;
    }

    public void setSurveyTaskTotal(Integer surveyTaskTotal) {
        this.surveyTaskTotal = surveyTaskTotal;
    }

    public Integer getSecurityTaskTotal() {
        return securityTaskTotal;
    }

    public void setSecurityTaskTotal(Integer securityTaskTotal) {
        this.securityTaskTotal = securityTaskTotal;
    }

    public Integer getAreaPatrollingTaskTotal() {
        return areaPatrollingTaskTotal;
    }

    public void setAreaPatrollingTaskTotal(Integer areaPatrollingTaskTotal) {
        this.areaPatrollingTaskTotal = areaPatrollingTaskTotal;
    }

    @Override
    public String toString() {
        return "TotalInfoBean{" +
                "deviceConcurrenceTotal=" + deviceConcurrenceTotal +
                ", deviceUsedTotal=" + deviceUsedTotal +
                ", seachUsedTotal=" + seachUsedTotal +
                ", surveyTaskTotal=" + surveyTaskTotal +
                ", securityTaskTotal=" + securityTaskTotal +
                ", areaPatrollingTaskTotal=" + areaPatrollingTaskTotal +
                '}';
    }
}
