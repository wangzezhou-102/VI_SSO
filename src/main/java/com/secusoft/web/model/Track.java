package com.secusoft.web.model;

import java.io.Serializable;
import java.util.List;

public class Track implements Serializable {
    private String id;
    private String trackName;
    private String folderId;
    private List<Picture> pictures;

    public Track() {
    }

    public Track(String id, String trackName, String folderId, List<Picture> pictures) {
        this.id = id;
        this.trackName = trackName;
        this.folderId = folderId;
        this.pictures = pictures;
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

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }
}
