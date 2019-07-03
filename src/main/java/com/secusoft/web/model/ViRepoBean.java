package com.secusoft.web.model;

import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@TableName("vi_repo")
public class ViRepoBean implements Serializable {

    private Integer id;
    private String bkdesc;
    private String bkname;
    private Integer bktype;
    private String bktypeValue;
    /**
     * 分局编号
     */
    private String policeStationId = "0";
    /**
     * 表名
     */
    private String tableName = "vi_private_member";
    /**
     * 0-基础库 1-自定义库
     */
    private Integer type = 1;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;

    private Integer validState;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBkdesc() {
        return bkdesc;
    }

    public void setBkdesc(String bkdesc) {
        this.bkdesc = bkdesc;
    }

    public String getBkname() {
        return bkname;
    }

    public void setBkname(String bkname) {
        this.bkname = bkname;
    }

    public Integer getBktype() {
        return bktype;
    }

    public void setBktype(Integer bktype) {
        this.bktype = bktype;
    }

    public String getBktypeValue() {
        return bktypeValue;
    }

    public void setBktypeValue(String bktypeValue) {
        this.bktypeValue = bktypeValue;
    }

    public String getPoliceStationId() {
        return policeStationId;
    }

    public void setPoliceStationId(String policeStationId) {
        this.policeStationId = policeStationId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getValidState() {
        return validState;
    }

    public void setValidState(Integer validState) {
        this.validState = validState;
    }

    @Override
    public String toString() {
        return "ViRepoBean{" +
                "id=" + id +
                ", bkdesc='" + bkdesc + '\'' +
                ", bkname='" + bkname + '\'' +
                ", bktype=" + bktype +
                ", policeStationId='" + policeStationId + '\'' +
                ", tableName='" + tableName + '\'' +
                ", type=" + type +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                '}';
    }
}
