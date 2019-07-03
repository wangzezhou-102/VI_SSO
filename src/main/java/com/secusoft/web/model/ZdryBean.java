package com.secusoft.web.model;

import java.util.Date;

/**
 * @author chjiang
 * @since 2019/6/24 9:17
 */
public class ZdryBean {

    private Integer id;

    private String picId;

    private String humanName;

    private byte[] pic;

    private Date updateTime;

    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public String getHumanName() {
        return humanName;
    }

    public void setHumanName(String humanName) {
        this.humanName = humanName;
    }

    public byte[] getPic() {
        return pic;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ZdryBean{" +
                "id=" + id +
                ", picId='" + picId + '\'' +
                ", humanName='" + humanName + '\'' +
                ", updateTime=" + updateTime +
                ", status='" + status + '\'' +
                '}';
    }
}
