package com.secusoft.web.model;

import java.util.List;

/**
 * 安保类型预设目标库实体
 * @author huanghao
 * @date 2019-07-04
 */
public class SecurityTaskTypeRepoBean {
    private Integer id;
    private List<Integer> repoIds;

    public SecurityTaskTypeRepoBean() {
    }

    public SecurityTaskTypeRepoBean(Integer id, List<Integer> repoIds) {
        this.id = id;
        this.repoIds = repoIds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Integer> getRepoIds() {
        return repoIds;
    }

    public void setRepoIds(List<Integer> repoIds) {
        this.repoIds = repoIds;
    }

    @Override
    public String toString() {
        return "SecurityTaskTypeRepoBean{" +
                "id=" + id +
                ", repoIds=" + repoIds +
                '}';
    }
}
