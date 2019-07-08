package com.secusoft.web.model;

/**
 * 安保地点实体
 * @author huanghao
 * @date 2019-07-02
 */
public class SecurityPlaceBean {
    private Integer id;
    private Integer typeId;
    private String placeName;
    private String longitude;
    private String latitude;
    private String radius;

    public SecurityPlaceBean() {
    }

    public SecurityPlaceBean(Integer id, Integer typeId, String placeName, String longitude, String latitude, String radius) {
        this.id = id;
        this.typeId = typeId;
        this.placeName = placeName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.radius = radius;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "SecurityPlaceBean{" +
                "id=" + id +
                ", typeId=" + typeId +
                ", placeName='" + placeName + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", radius='" + radius + '\'' +
                '}';
    }
}
