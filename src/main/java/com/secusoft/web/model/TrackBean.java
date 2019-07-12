package com.secusoft.web.model;

import com.secusoft.web.tusouapi.model.SearchResponseData;

import java.io.Serializable;
import java.util.List;

public class TrackBean implements Serializable {
    private String id;
    private String trackName;
    private String folderId;
    private List<PictureBean> pictureBeans;
    private List<SearchResponseData> pictureList;
    private Integer idn;

    public TrackBean() {
    }

    public TrackBean(String id, String trackName, String folderId, List<PictureBean> pictureBeans, List<SearchResponseData> pictureList) {
        this.id = id;
        this.trackName = trackName;
        this.folderId = folderId;
        this.pictureBeans = pictureBeans;
        this.pictureList = pictureList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public List<PictureBean> getPictureBeans() {
        return pictureBeans;
    }

    public void setPictureBeans(List<PictureBean> pictureBeans) {
        this.pictureBeans = pictureBeans;
    }

    public List<SearchResponseData> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<SearchResponseData> pictureList) {
        this.pictureList = pictureList;
    }

	public Integer getIdn() {
		return idn;
	}

	public void setIdn(Integer idn) {
		this.idn = idn;
	}
    
}
