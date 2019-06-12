package com.secusoft.web.model;

import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.secusoft.web.core.util.StringUtils;

/**
 * 离线视频文件夹
 * @author ChenDong
 * @company 视在数科
 * @date 2019年6月6日
 */
public class OfflineFolderBean {

    // 自增ID
    @TableId(value="id", type= IdType.AUTO)
    private Long id;
    
    // 组织结构ID
    private String orgId;
    
    // 文件夹名
    private String classifyName;
    
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
    
    // 关联点位列表
    @TableField(exist = false)
    private List<OfflinePointBean> points;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
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

    public List<OfflinePointBean> getPoints() {
        return points;
    }

    public void setPoints(List<OfflinePointBean> points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "OfflineFolderBean [id=" + id + ", classifyName=" + classifyName + ", gmtCreate=" + gmtCreate
                + ", gmtModified=" + gmtModified + ", createUser=" + createUser + ", modifiedUser=" + modifiedUser
                + ", idn=" + idn + ", keyWord=" + keyWord + "]";
    }
    
    /**
     * 数据完整性验证
     * @author ChenDong
     * @date 2019年6月6日
     * @return
     */
    public boolean validate() {
        if(StringUtils.isEmpty(classifyName)||StringUtils.isEmpty(orgId)) {
            return false;
        }
        return true;
    }
}
