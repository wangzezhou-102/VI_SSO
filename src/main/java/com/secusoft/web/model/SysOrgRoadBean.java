package com.secusoft.web.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 组织分配路数情况表
 * @author chjiang
 * @company 视在数科
 * @date 2019年6月18日
 */
public class SysOrgRoadBean {

    /**
     *
     */
    private Integer id;

    /**
     * 组织编码
     */
    private String orgCode;

    /**
     * 组织名称
     */
    private String orgName;

    /**
     * 上级组织编码，若为根节点，则为root
     */
    private String parentCode;

    /**
     * 总路数
     */
    private Short totalRoads;

    /**
     * 已使用路数
     */
    private Short usedRoads;

    /**
     * 路数使用情况，默认充足 0-不足 1-充足
     */
    private Integer status;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

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
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode == null ? null : parentCode.trim();
    }

    public Short getTotalRoads() {
        return totalRoads;
    }

    public void setTotalRoads(Short totalRoads) {
        this.totalRoads = totalRoads;
    }

    public Short getUsedRoads() {
        return usedRoads;
    }

    public void setUsedRoads(Short usedRoads) {
        this.usedRoads = usedRoads;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}