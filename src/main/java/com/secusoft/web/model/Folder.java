package com.secusoft.web.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * 收藏文件夹实体
 */
public class Folder implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String folderName;
    //文件夹状态 ：0未结案  1结案
    private int status;

    //收藏图片集合
    private List imageSearchList;
    //收藏轨迹集合
    private List trackList;
    //收藏区域集合
    private List deviceArea;


    public Folder() {
    }

    public Folder(String id, String folderName, int status, List imageSearchList, List trackList, List deviceArea) {
        this.id = id;
        this.folderName = folderName;
        this.status = status;
        this.imageSearchList = imageSearchList;
        this.trackList = trackList;
        this.deviceArea = deviceArea;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List getImageSearchList() {
        return imageSearchList;
    }

    public void setImageSearchList(List imageSearchList) {
        this.imageSearchList = imageSearchList;
    }

    public List getTrackList() {
        return trackList;
    }

    public void setTrackList(List trackList) {
        this.trackList = trackList;
    }

    public List getDeviceArea() {
        return deviceArea;
    }

    public void setDeviceArea(List deviceArea) {
        this.deviceArea = deviceArea;
    }

    @Override
    public String toString() {
        return "Folder{" +
                "id='" + id + '\'' +
                ", folderName='" + folderName + '\'' +
                ", status=" + status +
                ", imageSearchList=" + imageSearchList +
                ", trackList=" + trackList +
                ", deviceArea=" + deviceArea +
                '}';
    }
}
