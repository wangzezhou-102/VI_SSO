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
    private String policeStationId;
    private String tableName;
    private Integer type;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;

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
