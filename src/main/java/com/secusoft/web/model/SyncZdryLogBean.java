package com.secusoft.web.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author chjiang
 * @since 2019/6/28 15:46
 */
public class SyncZdryLogBean {

    private Integer id;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 同步数量
     */
    private Integer syncCount;

    /**
     * 同步更改数量
     */
    private Integer syncEditCount;

    /**
     * 上次同步时间节点（以对应同步库中的最后一条数据时间戳更新）
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastSyncTime;

    /**
     * 最后结束同步时间节点
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastEndTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getSyncCount() {
        return syncCount;
    }

    public void setSyncCount(Integer syncCount) {
        this.syncCount = syncCount;
    }

    public Integer getSyncEditCount() {
        return syncEditCount;
    }

    public void setSyncEditCount(Integer syncEditCount) {
        this.syncEditCount = syncEditCount;
    }

    public Date getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(Date lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }

    public Date getLastEndTime() {
        return lastEndTime;
    }

    public void setLastEndTime(Date lastEndTime) {
        this.lastEndTime = lastEndTime;
    }
}
