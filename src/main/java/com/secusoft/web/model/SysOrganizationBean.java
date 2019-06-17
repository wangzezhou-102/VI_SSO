package com.secusoft.web.model;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

public class SysOrganizationBean {

    // 自增ID
    @TableId(value="id", type= IdType.AUTO)
    private Long id;
    
    // 组织ID
    private String orgId;
    
    // 组织名称
    private String orgName;
    
    // 上级组织ID
    private String pid;
    
    // 1-基本组织 2-业务组织
    private Integer orgType;
    
    // camera-设备  org-组织
    private Integer type;

//    private List<SysOrganizationBean> orgs;
//    
//    private List<DeviceBean> devices;
    private List<Map<String,Object>> children;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

//    public List<SysOrganizationBean> getOrgs() {
//        return orgs;
//    }
//
//    public void setOrgs(List<SysOrganizationBean> orgs) {
//        this.orgs = orgs;
//    }
//
//    public List<DeviceBean> getDevices() {
//        return devices;
//    }
//
//    public void setDevices(List<DeviceBean> devices) {
//        this.devices = devices;
//    }

    public void setChildren(List<Map<String, Object>> children) {
        this.children = children;
    }

    public List<Map<String,Object>> getChildren() {
        return children;
    }
    
    public Integer getOrgType() {
        return orgType;
    }

    public void setOrgType(Integer orgType) {
        this.orgType = orgType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SysOrganizationBean [id=" + id + ", orgId=" + orgId + ", orgName=" + orgName + ", pid=" + pid
                + ", orgType=" + orgType + ", type=" + type + ", children=" + children + "]";
    }

}
