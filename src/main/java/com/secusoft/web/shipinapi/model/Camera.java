package com.secusoft.web.shipinapi.model;

public class Camera {

    private String cameraId;
    private String latitude;
    private String longitude;
    private int cameraType;
    private String name;

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getCameraType() {
        return cameraType;
    }

    public void setCameraType(int cameraType) {
        this.cameraType = cameraType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
