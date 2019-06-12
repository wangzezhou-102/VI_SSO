package com.secusoft.web.model;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 离线视频
 * @author ChenDong
 * @company 视在数科
 * @date 2019年6月6日
 */
public class OfflineVideoBean {

    // 自增ID
    @TableId(value="id", type= IdType.AUTO)
    private Long id;
    
    // 视频名称
    private String name;
    
    // 关联点位
    private String cameraId;
   
    // 开始时间
    private LocalDateTime startTime;
    
    // 视频地址
    private String bucket;
    
    // 创建时间
    private LocalDateTime gmtCreate;
    
    // 更新时间
    private LocalDateTime gmtModified;
    
    // 创建用户
    private Long createUser;
    
    // 更新用户
    private Long modifiedUser;
    
    // 非数据库字段，用于更新时进行一些不可重复的字段判断
    @TableField(exist = false)
    @JsonInclude(value=Include.NON_EMPTY)
    private Long idn;
    
    // 非数据库字段，关键字用于模糊查询
    @TableField(exist = false)
    @JsonInclude(value=Include.NON_EMPTY)
    private String keyWord;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Long getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(Long modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public Long getIdn() {
        return idn;
    }

    public void setIdn(Long idn) {
        this.idn = idn;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    @Override
    public String toString() {
        return "OfflineVideoBean [id=" + id + ", name=" + name + ", cameraId=" + cameraId + ", startTime=" + startTime
                + ", bucket=" + bucket + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified + ", createUser="
                + createUser + ", modifiedUser=" + modifiedUser + ", idn=" + idn + ", keyWord=" + keyWord + "]";
    }

    /**
     * 数据校验
     * @author ChenDong
     * @date 2019年6月6日
     * @return
     */
    public boolean validate() {
        if(StringUtils.isEmpty(name)||StringUtils.isEmpty(cameraId)) {
            return false;
        }
        return true;
    }
}
