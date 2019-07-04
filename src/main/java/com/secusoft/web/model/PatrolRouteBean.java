package com.secusoft.web.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 巡逻路线
 * @author Wangzezhou
 * @company 视在数科
 * @date 2019年7月1日
 */
public class PatrolRouteBean {
    //路线id
    private Integer Id;
    //路线名称
    private String routeName;
    //状态 0-无操作 1-使用中 2-未使用 3-已删除
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
    //create_id对应的组织code
    private String orgCode;
    //对应设备
    private List<DeviceBean> devices;

    public String getOrgCode() { return orgCode; }

    public void setOrgCode(String orgCode) { this.orgCode = orgCode; }

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

    @Override
    public String toString() {
        return "PatrolRouteBean{" +
                "Id=" + Id +
                ", routeName='" + routeName + '\'' +
                ", status=" + status +
                ", createId=" + createId +
                ", createTime=" + createTime +
                ", updateId=" + updateId +
                ", updateTime=" + updateTime +
                ", devices=" + devices +
                '}';
    }

    public boolean validateStatus(){
        //此处参数必须不为3 且不为空
        if(status == null || status != 3 ){
            return false;
        }
        return true;
    }
    public boolean validateRouteNameAndDevices(){
        if(routeName == null || devices == null || createId == null){
            return false;
        }
        return true;
    }
    public boolean validateId(){
        if(Id == null){
            return false;
        }
        return true;
    }
    public boolean validateIdAndDevices(){
        if(Id == null || devices == null){
            return false;
        }
        return true;
    }

}
