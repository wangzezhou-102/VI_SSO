package com.secusoft.web.model;

import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.toolkit.StringUtils;

/**
 * 离线点位
 * @author ChenDong
 * @company 视在数科
 * @date 2019年6月6日
 */
public class OfflinePointBean {

    // 自增ID
    @TableId(value="id", type= IdType.AUTO)
    private Long id;
    
    // 点位名
    private String pointName;
    
    // 离线文件夹ID
    private Long folderId;
    
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
    private Long idn;
    
    // 非数据库字段，关键字用于模糊查询
    @TableField(exist = false)
    private String keyWord;

    // 关联视频列表
    @TableField(exist = false)
    private List<OfflineVideoBean> videos;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
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

    public List<OfflineVideoBean> getVideos() {
        return videos;
    }

    public void setVideos(List<OfflineVideoBean> videos) {
        this.videos = videos;
    }

    @Override
    public String toString() {
        return "OfflinePointBean [id=" + id + ", pointName=" + pointName + ", folderId=" + folderId + ", gmtCreate="
                + gmtCreate + ", gmtModified=" + gmtModified + ", createUser=" + createUser + ", modifiedUser="
                + modifiedUser + ", idn=" + idn + ", keyWord=" + keyWord + "]";
    }

    /**
     * 数据校验
     * @author ChenDong
     * @date 2019年6月6日
     * @return
     */
    public boolean validate() {
        if(StringUtils.isEmpty(pointName)||folderId == null) {
            return false;
        }
        return true;
    }
}