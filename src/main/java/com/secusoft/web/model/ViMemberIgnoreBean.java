package com.secusoft.web.model;

import java.util.Date;

/**
 * 布控目标无视表（基础库）
 */
public class ViMemberIgnoreBean {
    private String realObjectId;

    private String realTableName;

    private String repoId;

    private Integer type;

    private Date createTime;

    private Date modifyTime;

    public String getRealObjectId() {
        return realObjectId;
    }

    public void setRealObjectId(String realObjectId) {
        this.realObjectId = realObjectId == null ? null : realObjectId.trim();
    }

    public String getRealTableName() {
        return realTableName;
    }

    public void setRealTableName(String realTableName) {
        this.realTableName = realTableName == null ? null : realTableName.trim();
    }

    public String getRepoId() {
        return repoId;
    }

    public void setRepoId(String repoId) {
        this.repoId = repoId == null ? null : repoId.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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