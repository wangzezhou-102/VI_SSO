package com.secusoft.web.model;

import com.secusoft.web.core.util.StringUtils;
import com.secusoft.web.tusouapi.model.SearchResponseData;
import com.secusoft.web.tusouapi.model.SearchSource;


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
    /**
     * 本地原图地址
     */
    private String localOriImageUrl;
    /**
     * 本地小图地址
     */
    private String localCropImageUrl;
    private DeviceBean deviceBean;

    public PictureBean() {
        super();
    }

    public PictureBean(String id, String pictureId, String origImageUrl, String cropImageUrl, String oriImageSignedUrl, String cropImageSignedUrl, Long pictureTime, String deviceId, String folderId, Integer picType, String score, String localOriImageUrl, String localCropImageUrl, DeviceBean deviceBean) {
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
        this.localOriImageUrl = localOriImageUrl;
        this.localCropImageUrl = localCropImageUrl;
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

    public String getLocalOriImageUrl() {
        return localOriImageUrl;
    }

    public void setLocalOriImageUrl(String localOriImageUrl) {
        this.localOriImageUrl = localOriImageUrl;
    }

    public String getLocalCropImageUrl() {
        return localCropImageUrl;
    }

    public void setLocalCropImageUrl(String localCropImageUrl) {
        this.localCropImageUrl = localCropImageUrl;
    }

    @Override
    public String toString() {
        return "PictureBean{" +
                "id='" + id + '\'' +
                ", pictureId='" + pictureId + '\'' +
                ", origImageUrl='" + origImageUrl + '\'' +
                ", cropImageUrl='" + cropImageUrl + '\'' +
                ", oriImageSignedUrl='" + oriImageSignedUrl + '\'' +
                ", cropImageSignedUrl='" + cropImageSignedUrl + '\'' +
                ", pictureTime=" + pictureTime +
                ", deviceId='" + deviceId + '\'' +
                ", folderId='" + folderId + '\'' +
                ", picType=" + picType +
                ", score='" + score + '\'' +
                ", localOriImageUrl='" + localOriImageUrl + '\'' +
                ", localCropImageUrl='" + localCropImageUrl + '\'' +
                ", deviceBean=" + deviceBean +
                '}';
    }
    public static SearchResponseData toSearchResponseDate(PictureBean pictureBean){
        SearchResponseData searchResponseData = new SearchResponseData();
        SearchSource searchSource = new SearchSource();
        searchResponseData.setSource(searchSource);
        if (pictureBean.getPictureId()!=null){
            searchResponseData.setId(pictureBean.getPictureId());
        }
        if(StringUtils.isNotEmpty(pictureBean.getId())){
            searchResponseData.setSzId(pictureBean.getId());
        }
        if(pictureBean.getScore()!=null){
            searchResponseData.setScore(Double.parseDouble(pictureBean.getScore()));
        }
        if(StringUtils.isNotEmpty(pictureBean.getOrigImageUrl())){
            searchResponseData.getSource().setOrigImage(pictureBean.getOrigImageUrl());
        }
        if(StringUtils.isNotEmpty(pictureBean.getCropImageUrl())){
            searchResponseData.getSource().setCropImage(pictureBean.getCropImageUrl());
        }
        if(StringUtils.isNotEmpty(pictureBean.getLocalOriImageUrl())){
            searchResponseData.getSource().setOriImageSigned(pictureBean.getLocalOriImageUrl());
        }
        if(StringUtils.isNotEmpty(pictureBean.getLocalCropImageUrl())){
            searchResponseData.getSource().setCropImageSigned(pictureBean.getLocalCropImageUrl());
        }
        if(StringUtils.isNotEmpty(pictureBean.getDeviceId())){
            searchResponseData.getSource().setCameraId(pictureBean.getDeviceId());
        }
        if(pictureBean.getPictureTime()!=null){
            searchResponseData.getSource().setTimestamp(pictureBean.getPictureTime());
        }
        return searchResponseData;
    }

    //将阿里格式转换成普通格式
    public static PictureBean toPictureBean(SearchResponseData searchResponseData){
        PictureBean pictureBean = new PictureBean();
        if(searchResponseData!=null){
            if (StringUtils.isNotEmpty(searchResponseData.getId())){
                pictureBean.setPictureId(searchResponseData.getId());
            }
            if (searchResponseData.getScore()!=null){
                pictureBean.setScore(searchResponseData.getScore().toString());
            }
            if(StringUtils.isNotEmpty(searchResponseData.getSource().getCameraId())){
                pictureBean.setDeviceId(searchResponseData.getSource().getCameraId());
            }
            if(StringUtils.isNotEmpty(searchResponseData.getSource().getOriImageSigned())){
                pictureBean.setOriImageSignedUrl(searchResponseData.getSource().getOriImageSigned());
            }
            if(StringUtils.isNotEmpty(searchResponseData.getSource().getCropImage())){
                pictureBean.setCropImageUrl(searchResponseData.getSource().getCropImage());
            }
            if(StringUtils.isNotEmpty(searchResponseData.getSource().getOrigImage())){
                pictureBean.setOrigImageUrl(searchResponseData.getSource().getOrigImage());
            }
            if(StringUtils.isNotEmpty(searchResponseData.getSource().getCropImageSigned())){
                pictureBean.setCropImageSignedUrl(searchResponseData.getSource().getCropImageSigned());
            }
            if (searchResponseData.getSource().getDeviceBean()!=null){
                pictureBean.setDeviceBean(searchResponseData.getSource().getDeviceBean());
            }
            if(searchResponseData.getSource().getTimestamp()!=null){
                pictureBean.setPictureTime(searchResponseData.getSource().getTimestamp());
            }
            return  pictureBean;
        }else {
            return pictureBean;
        }
    }
}
