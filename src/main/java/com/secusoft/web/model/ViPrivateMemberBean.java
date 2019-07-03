package com.secusoft.web.model;

import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@TableName("vi_private_member")
public class ViPrivateMemberBean implements Serializable {

    private Integer id;
    private String objectId;
    private String identityName;
    private String identityId;
    private String imageUrl;
    private String repoId;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;
    private Integer validState;

    private ViRepoBean viRepoBean;

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

    public String getIdentityName() {
        return identityName;
    }

    public void setIdentityName(String identityName) {
        this.identityName = identityName;
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRepoId() {
        return repoId;
    }

    public void setRepoId(String repoId) {
        this.repoId = repoId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Integer getValidState() {
        return validState;
    }

    public void setValidState(Integer validState) {
        this.validState = validState;
    }

    public ViRepoBean getViRepoBean() {
        return viRepoBean;
    }

    public void setViRepoBean(ViRepoBean viRepoBean) {
        this.viRepoBean = viRepoBean;
    }

    @Override
    public String toString() {
        return "ViPrivateMember{" +
                "id=" + id +
                ", objectId='" + objectId + '\'' +
                ", identityName='" + identityName + '\'' +
                ", identityId='" + identityId + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", repoId='" + repoId + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
