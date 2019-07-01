package com.secusoft.web.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 巡逻路线
 * @author zzWang
 * @company 视在数科
 * @date 2019年7月1日
 */
public class PatrolRouteBean {
    //路线id
    private Integer Id;
    //路线名称
    private String routeName;
    //状态 0-未使用 1-使用中
    private Integer status;
    //创建者id
    private Integer createId;
    //创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    //更新者id
    private Integer updateId;
    //更新时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    //对应设备
    private List<DeviceBean> devices;

    public List<DeviceBean> getDevices() { return devices; }

    public void setDevices(List<DeviceBean> devices) { this.devices = devices; }

    public Integer getId() { return Id; }

    public void setId(Integer id) { Id = id; }

    public String getRouteName() { return routeName; }

    public void setRouteName(String routeName) { this.routeName = routeName; }

    public Integer getStatus() { return status; }

    public void setStatus(Integer status) { this.status = status; }

    public Integer getCreateId() { return createId; }

    public void setCreateId(Integer createId) { this.createId = createId; }

    public Date getCreateTime() { return createTime; }

    public void setCreateTime(Date createTime) { this.createTime = createTime; }

    public Integer getUpdateId() { return updateId; }

    public void setUpdateId(Integer updateId) { this.updateId = updateId; }

    public Date getUpdateTime() { return updateTime; }

    public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }
}
