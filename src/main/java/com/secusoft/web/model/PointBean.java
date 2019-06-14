package com.secusoft.web.model;

/**
 * 点位的实体 包含经纬度
 */
public class PointBean {
    private Double longitude;
    private Double latitude;

    public PointBean() {
    }

    public PointBean(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
