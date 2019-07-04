package com.secusoft.web.model;

import java.util.Date;

/**
 * @author chjiang
 * @since 2019/7/3 19:57
 */
public class ViPsurveyAlarmDetailResponse {

    /**
     * 告警详情编号
     */
    private Integer alarmDetailId;

    /**
     * 图片地址
     */
    private String ossUrl;

    /**
     * 原图oss地址
     */
    private String origImage;

    /**
     * 抠图oss地址，这里是人脸抠图
     */
    private String cropImage;

    /**
     * 人体抠图oss地址
     */
    private String personImage;

    /**
     * 相似度
     */
    private Double similarity;

    /**
     * 对象名称
     */
    private String name;

    /**
     * 对象身份证
     */
    private String picid;

    /**
     * 入库时间
     */
    private Date time;

    /**
     * 对象id
     */
    private String objectId;

    /**
     * 处理状态 0-忽略 1-关注 2-无操作
     */
    private Integer alarmStatus=2;

    /**
     * 所在库名称
     */
    private String bkname;

    public Integer getAlarmDetailId() {
        return alarmDetailId;
    }

    public void setAlarmDetailId(Integer alarmDetailId) {
        this.alarmDetailId = alarmDetailId;
    }

    public String getOssUrl() {
        return ossUrl;
    }

    public void setOssUrl(String ossUrl) {
        this.ossUrl = ossUrl;
    }

    public String getOrigImage() {
        return origImage;
    }

    public void setOrigImage(String origImage) {
        this.origImage = origImage;
    }

    public String getCropImage() {
        return cropImage;
    }

    public void setCropImage(String cropImage) {
        this.cropImage = cropImage;
    }

    public String getPersonImage() {
        return personImage;
    }

    public void setPersonImage(String personImage) {
        this.personImage = personImage;
    }

    public Double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(Double similarity) {
        this.similarity = similarity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicid() {
        return picid;
    }

    public void setPicid(String picid) {
        this.picid = picid;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Integer getAlarmStatus() {
        return alarmStatus;
    }

    public void setAlarmStatus(Integer alarmStatus) {
        this.alarmStatus = alarmStatus;
    }

    public String getBkname() {
        return bkname;
    }

    public void setBkname(String bkname) {
        this.bkname = bkname;
    }
}
