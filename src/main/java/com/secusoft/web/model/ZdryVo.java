package com.secusoft.web.model;

import com.secusoft.web.utils.PageReqAbstractModel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author chjiang
 * @since 2019/6/28 15:50
 */
public class ZdryVo extends PageReqAbstractModel {

    /**
     * 是否第一次同步 1-首次 0-非首次
     */
    private Integer isFirst;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private Integer syncNum;

    private Integer startNum;

    private Integer endNum;

    private String tableName;

    public Integer getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(Integer isFirst) {
        this.isFirst = isFirst;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getSyncNum() {
        return syncNum;
    }

    public void setSyncNum(Integer syncNum) {
        this.syncNum = syncNum;
    }

    public Integer getStartNum() {
        return startNum;
    }

    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }

    public Integer getEndNum() {
        return endNum;
    }

    public void setEndNum(Integer endNum) {
        this.endNum = endNum;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
