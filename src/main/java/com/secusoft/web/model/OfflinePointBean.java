package com.secusoft.web.model;

import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
    
    //设备ID
    private String camera_id;
    
    // 点位名
    private String name;
    
    // 离线文件夹ID
    private String classifyId;
    
    //组织结构ID
    private String orgId;
    
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

    // 关联视频列表
    @TableField(exist = false)
    private List<OfflineVideoBean> videos;
   

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getCamera_id() {
        return camera_id;
    }


    public void setCamera_id(String camera_id) {
        this.camera_id = camera_id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getClassifyId() {
        return classifyId;
    }


    public void setClassifyId(String classifyId) {
        this.classifyId = classifyId;
    }


    public String getOrgId() {
        return orgId;
    }


    public void setOrgId(String orgId) {
        this.orgId = orgId;
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
        return "OfflinePointBean [id=" + id + ", camera_id=" + camera_id + ", name=" + name + ", classifyId="
                + classifyId + ", orgId=" + orgId + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified
                + ", createUser=" + createUser + ", modifiedUser=" + modifiedUser + ", idn=" + idn + ", keyWord="
                + keyWord + ", videos=" + videos + "]";
    }


    /**
     * 数据校验
     * @author ChenDong
     * @date 2019年6月6日
     * @return
     */
    public boolean validate() {
        if(StringUtils.isEmpty(name)||StringUtils.isEmpty(classifyId)) {
            return false;
        }
        return true;
    }
}