package com.secusoft.web.model;

import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@TableName("vi_basic_member")
public class ViBasicMemberBean implements Serializable {
    private Integer id;

    private String objectId;

    private Integer repoId;

    private String realObjectId;

    private String realTableName;

    private String identityId;

    private String identityName;

    private String content;

    private String imageUrl;

    private String feature;

    private String attribute;

    private Integer status;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Integer getRepoId() {
        return repoId;
    }

    public void setRepoId(Integer repoId) {
        this.repoId = repoId;
    }

    public String getRealObjectId() {
        return realObjectId;
    }

    public void setRealObjectId(String realObjectId) {
        this.realObjectId = realObjectId;
    }

    public String getRealTableName() {
        return realTableName;
    }

    public void setRealTableName(String realTableName) {
        this.realTableName = realTableName;
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public String getIdentityName() {
        return identityName;
    }

    public void setIdentityName(String identityName) {
        this.identityName = identityName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
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

    @Override
    public String toString() {
        return "ViBasicMemberBean{" +
                "id=" + id +
                ", objectId='" + objectId + '\'' +
                ", repoId=" + repoId +
                ", realObjectId='" + realObjectId + '\'' +
                ", realTableName='" + realTableName + '\'' +
                ", identityId='" + identityId + '\'' +
                ", identityName='" + identityName + '\'' +
                ", content='" + content + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", feature='" + feature + '\'' +
                ", attribute='" + attribute + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}