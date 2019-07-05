package com.secusoft.web.model;

import java.util.List;

/**
 * 地点VO
 * @author huanghao
 * @date 2019-07-02
 */
public class SecurityPlaceBean {
    private Integer parentId;
    private List<SecurityTaskPlaceBean> list;

    public SecurityPlaceBean() {
    }

    public SecurityPlaceBean(Integer parentId, List<SecurityTaskPlaceBean> list) {
        this.parentId = parentId;
        this.list = list;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<SecurityTaskPlaceBean> getList() {
        return list;
    }

    public void setList(List<SecurityTaskPlaceBean> list) {
        this.list = list;
    }
}
