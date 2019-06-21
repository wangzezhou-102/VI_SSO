package com.secusoft.web.model;

import java.io.Serializable;

/**
 *蓝色大屏浮框 功能模块统计
 * @author hbxing
 * @date 2019/6/20
 */
public class ScreenFunctionUserInfoBean implements Serializable {
    /**
     *目标搜图使用次数
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
     * 结伴同行使用次数
     */
    private Integer peerGroupTotal;
    /**
     * 区域巡逻执行任务个数
     */
    private Integer areaPatrollingTaskTotal;


    public ScreenFunctionUserInfoBean() {
    }

    public ScreenFunctionUserInfoBean(Integer seachUsedTotal, Integer surveyTaskTotal, Integer securityTaskTotal, Integer peerGroupTotal, Integer areaPatrollingTaskTotal) {
        this.seachUsedTotal = seachUsedTotal;
        this.surveyTaskTotal = surveyTaskTotal;
        this.securityTaskTotal = securityTaskTotal;
        this.peerGroupTotal = peerGroupTotal;
        this.areaPatrollingTaskTotal = areaPatrollingTaskTotal;
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

    public Integer getPeerGroupTotal() {
        return peerGroupTotal;
    }

    public void setPeerGroupTotal(Integer peerGroupTotal) {
        this.peerGroupTotal = peerGroupTotal;
    }

    public Integer getAreaPatrollingTaskTotal() {
        return areaPatrollingTaskTotal;
    }

    public void setAreaPatrollingTaskTotal(Integer areaPatrollingTaskTotal) {
        this.areaPatrollingTaskTotal = areaPatrollingTaskTotal;
    }

    @Override
    public String toString() {
        return "ScreenFunctionUserInfoBean{" +
                "seachUsedTotal=" + seachUsedTotal +
                ", surveyTaskTotal=" + surveyTaskTotal +
                ", securityTaskTotal=" + securityTaskTotal +
                ", peerGroupTotal=" + peerGroupTotal +
                ", areaPatrollingTaskTotal=" + areaPatrollingTaskTotal +
                '}';
    }
}
