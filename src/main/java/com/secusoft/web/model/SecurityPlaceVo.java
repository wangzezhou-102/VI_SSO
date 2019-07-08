package com.secusoft.web.model;

import java.util.List;

/**
 * 地点VO
 * @author huanghao
 * @date 2019-07-02
 */
public class SecurityPlaceVo {
    private Integer parentId;
    private List<SecurityPlaceBean> list;

    public SecurityPlaceVo() {
    }

    public SecurityPlaceVo(Integer parentId, List<SecurityPlaceBean> list) {
        this.parentId = parentId;
        this.list = list;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<SecurityPlaceBean> getList() {
        return list;
    }

    public void setList(List<SecurityPlaceBean> list) {
        this.list = list;
    }
}
