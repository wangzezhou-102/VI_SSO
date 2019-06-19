package com.secusoft.web.model;

import java.util.Date;

/**
 * 布控报警表
 */
public class ViPsurveyAlaramBean {
    private Integer id;

    private String origImage;

    private String cropImage;

    private String personImage;

    private Date entryTime;

    private Date leaveTime;

    private String feature;

    private String cameraId;

    private String objTop;

    private String objBottom;

    private String objLeft;

    private String objRight;

    private String objId;

    private String objUuid;

    private Long timestamp;

    private String taskId;

    private Integer status;

    private Date gmtCreate;

    private Date gmtModified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrigImage() {
        return origImage;
    }

    public void setOrigImage(String origImage) {
        this.origImage = origImage == null ? null : origImage.trim();
    }

    public String getCropImage() {
        return cropImage;
    }

    public void setCropImage(String cropImage) {
        this.cropImage = cropImage == null ? null : cropImage.trim();
    }

    public String getPersonImage() {
        return personImage;
    }

    public void setPersonImage(String personImage) {
        this.personImage = personImage == null ? null : personImage.trim();
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public Date getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Date leaveTime) {
        this.leaveTime = leaveTime;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature == null ? null : feature.trim();
    }

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId == null ? null : cameraId.trim();
    }

    public String getObjTop() {
        return objTop;
    }

    public void setObjTop(String objTop) {
        this.objTop = objTop == null ? null : objTop.trim();
    }

    public String getObjBottom() {
        return objBottom;
    }

    public void setObjBottom(String objBottom) {
        this.objBottom = objBottom == null ? null : objBottom.trim();
    }

    public String getObjLeft() {
        return objLeft;
    }

    public void setObjLeft(String objLeft) {
        this.objLeft = objLeft == null ? null : objLeft.trim();
    }

    public String getObjRight() {
        return objRight;
    }

    public void setObjRight(String objRight) {
        this.objRight = objRight == null ? null : objRight.trim();
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId == null ? null : objId.trim();
    }

    public String getObjUuid() {
        return objUuid;
    }

    public void setObjUuid(String objUuid) {
        this.objUuid = objUuid == null ? null : objUuid.trim();
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}