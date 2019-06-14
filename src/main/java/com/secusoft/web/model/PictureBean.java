package com.secusoft.web.model;

import java.io.Serializable;


public class PictureBean implements Serializable {
    private String id;
    private String pictureId;
    private String origImageUrl;
    private String cropImageUrl;
    private String oriImageSignedUrl;
    private String cropImageSignedUrl;
    private Long pictureTime;
    private String deviceId;
    private String folderId;
    /**
     * 区别轨迹和收藏 0：不是轨迹 1：是轨迹
     */
    private Integer picType;
    /**
     * 相似程度
     */
    private String score;

    private DeviceBean deviceBean;

    public PictureBean() {
        super();
    }

    public PictureBean(String id, String pictureId, String origImageUrl, String cropImageUrl, String oriImageSignedUrl, String cropImageSignedUrl, Long pictureTime, String deviceId, String folderId, Integer picType, String score, DeviceBean deviceBean) {
        this.id = id;
        this.pictureId = pictureId;
        this.origImageUrl = origImageUrl;
        this.cropImageUrl = cropImageUrl;
        this.oriImageSignedUrl = oriImageSignedUrl;
        this.cropImageSignedUrl = cropImageSignedUrl;
        this.pictureTime = pictureTime;
        this.deviceId = deviceId;
        this.folderId = folderId;
        this.picType = picType;
        this.score = score;
        this.deviceBean = deviceBean;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public String getOrigImageUrl() {
        return origImageUrl;
    }

    public void setOrigImageUrl(String origImageUrl) {
        this.origImageUrl = origImageUrl;
    }

    public String getCropImageUrl() {
        return cropImageUrl;
    }

    public void setCropImageUrl(String cropImageUrl) {
        this.cropImageUrl = cropImageUrl;
    }

    public String getOriImageSignedUrl() {
        return oriImageSignedUrl;
    }

    public void setOriImageSignedUrl(String oriImageSignedUrl) {
        this.oriImageSignedUrl = oriImageSignedUrl;
    }

    public String getCropImageSignedUrl() {
        return cropImageSignedUrl;
    }

    public void setCropImageSignedUrl(String cropImageSignedUrl) {
        this.cropImageSignedUrl = cropImageSignedUrl;
    }

    public Long getPictureTime() {
        return pictureTime;
    }

    public void setPictureTime(Long pictureTime) {
        this.pictureTime = pictureTime;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public Integer getPicType() {
        return picType;
    }

    public void setPicType(Integer picType) {
        this.picType = picType;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public DeviceBean getDeviceBean() {
        return deviceBean;
    }

    public void setDeviceBean(DeviceBean deviceBean) {
        this.deviceBean = deviceBean;
    }

    @Override
    public String toString() {
        return "PictureBean{" +
                "id='" + id + '\'' +
                ", origImageUrl='" + origImageUrl + '\'' +
                ", cropImageUrl='" + cropImageUrl + '\'' +
                ", oriImageSignedUrl='" + oriImageSignedUrl + '\'' +
                ", cropImageSignedUrl='" + cropImageSignedUrl + '\'' +
                ", pictureTime=" + pictureTime +
                ", deviceId='" + deviceId + '\'' +
                ", folderId='" + folderId + '\'' +
                ", picType=" + picType +
                ", score='" + score + '\'' +
                ", deviceBean=" + deviceBean +
                '}';
    }
}
