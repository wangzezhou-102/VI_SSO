package com.secusoft.web.model;

import java.util.List;

/**
 * 安保任务类型实体 包含类型下的具体地点
 * @author huanghao
 * @date 2019-07-02
 */
public class SecurityTaskTypeBean {
    private Integer id;
    private String typeName;
    private List<SecurityTaskTypeBean> subsetType;
    private List<SecurityTaskPlaceBean> securityTaskPlaces;

    public SecurityTaskTypeBean() {
    }

    public SecurityTaskTypeBean(Integer id, String typeName, List<SecurityTaskTypeBean> subsetType) {
        this.id = id;
        this.typeName = typeName;
        this.subsetType = subsetType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<SecurityTaskTypeBean> getSubsetType() {
        return subsetType;
    }

    public void setSubsetType(List<SecurityTaskTypeBean> subsetType) {
        this.subsetType = subsetType;
    }

    public List<SecurityTaskPlaceBean> getSecurityTaskPlaces() {
        return securityTaskPlaces;
    }

    public void setSecurityTaskPlaces(List<SecurityTaskPlaceBean> securityTaskPlaces) {
        this.securityTaskPlaces = securityTaskPlaces;
    }
}
