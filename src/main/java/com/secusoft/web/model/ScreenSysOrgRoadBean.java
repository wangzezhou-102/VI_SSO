package com.secusoft.web.model;

import java.io.Serializable;

/**
 * 组织分配路数情况表
 */
public class ScreenSysOrgRoadBean implements Serializable {
    private Integer id;
    private String orgCode;
    private String orgName;
    private String patentCode;
    private Integer totalRoads;
    private Integer usedRoads;
    private Integer status;

    public ScreenSysOrgRoadBean() {
    }

    public ScreenSysOrgRoadBean(Integer id, String orgCode, String orgName, String patentCode, Integer totalRoads, Integer usedRoads, Integer status) {
        this.id = id;
        this.orgCode = orgCode;
        this.orgName = orgName;
        this.patentCode = patentCode;
        this.totalRoads = totalRoads;
        this.usedRoads = usedRoads;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPatentCode() {
        return patentCode;
    }

    public void setPatentCode(String patentCode) {
        this.patentCode = patentCode;
    }

    public Integer getTotalRoads() {
        return totalRoads;
    }

    public void setTotalRoads(Integer totalRoads) {
        this.totalRoads = totalRoads;
    }

    public Integer getUsedRoads() {
        return usedRoads;
    }

    public void setUsedRoads(Integer usedRoads) {
        this.usedRoads = usedRoads;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ScreenSysOrgRoadBean{" +
                "id=" + id +
                ", orgCode='" + orgCode + '\'' +
                ", orgName='" + orgName + '\'' +
                ", patentCode='" + patentCode + '\'' +
                ", totalRoads=" + totalRoads +
                ", usedRoads=" + usedRoads +
                ", status=" + status +
                '}';
    }
}
