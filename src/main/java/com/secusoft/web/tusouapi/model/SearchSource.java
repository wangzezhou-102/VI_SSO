package com.secusoft.web.tusouapi.model;

import com.secusoft.web.model.DeviceBean;

/**
 * @author huanghao
 * @date 2019-06-12
 */
public class SearchSource {
    private String  origImage;
    private String  oriImageSigned;
    private Double  hairScore;
    private String  cropImage;
    private Double  sexScore;
    private Double  upper_typeScore;
    private String  hair;
    private Integer objLeft;
    private String lower_type;
    private String  objType;
    private Integer objTop;
    private String  objUUId;
    private Long  timestamp;
    private Integer leaveTime;
    private String upper_type;
    private Integer objBottom;
    private String lower_color;
    private Double upper_colorScore;
    private String sex;
    private Double lower_typeScore;
    private Long   entryTime;
    private Double lower_colorScore;
    private String cameraId;
    private String objId;
    private Integer objRight;
    private String upper_color;
    private String cropImageSigned;
    private String year;
    private String hat_type;
    private String hat_color;
    private String bag_type;
    private String bag_color;

    private DeviceBean deviceBean;

    public SearchSource() {
    }

    public SearchSource(String origImage, String oriImageSigned, Double hairScore, String cropImage, Double sexScore, Double upper_typeScore, String hair, Integer objLeft, String lower_type, String objType, Integer objTop, String objUUId, Long timestamp, Integer leaveTime, String upper_type, Integer objBottom, String lower_color, Double upper_colorScore, String sex, Double lower_typeScore, Long entryTime, Double lower_colorScore, String cameraId, String objId, Integer objRight, String upper_color, String cropImageSigned, String year, String hat_type, String hat_color, String bag_type, String bag_color, DeviceBean deviceBean) {
        this.origImage = origImage;
        this.oriImageSigned = oriImageSigned;
        this.hairScore = hairScore;
        this.cropImage = cropImage;
        this.sexScore = sexScore;
        this.upper_typeScore = upper_typeScore;
        this.hair = hair;
        this.objLeft = objLeft;
        this.lower_type = lower_type;
        this.objType = objType;
        this.objTop = objTop;
        this.objUUId = objUUId;
        this.timestamp = timestamp;
        this.leaveTime = leaveTime;
        this.upper_type = upper_type;
        this.objBottom = objBottom;
        this.lower_color = lower_color;
        this.upper_colorScore = upper_colorScore;
        this.sex = sex;
        this.lower_typeScore = lower_typeScore;
        this.entryTime = entryTime;
        this.lower_colorScore = lower_colorScore;
        this.cameraId = cameraId;
        this.objId = objId;
        this.objRight = objRight;
        this.upper_color = upper_color;
        this.cropImageSigned = cropImageSigned;
        this.year = year;
        this.hat_type = hat_type;
        this.hat_color = hat_color;
        this.bag_type = bag_type;
        this.bag_color = bag_color;
        this.deviceBean = deviceBean;
    }

    public String getOrigImage() {
        return origImage;
    }

    public void setOrigImage(String origImage) {
        this.origImage = origImage;
    }

    public String getOriImageSigned() {
        return oriImageSigned;
    }

    public void setOriImageSigned(String oriImageSigned) {
        this.oriImageSigned = oriImageSigned;
    }

    public Double getHairScore() {
        return hairScore;
    }

    public void setHairScore(Double hairScore) {
        this.hairScore = hairScore;
    }

    public String getCropImage() {
        return cropImage;
    }

    public void setCropImage(String cropImage) {
        this.cropImage = cropImage;
    }

    public Double getSexScore() {
        return sexScore;
    }

    public void setSexScore(Double sexScore) {
        this.sexScore = sexScore;
    }

    public Double getUpper_typeScore() {
        return upper_typeScore;
    }

    public void setUpper_typeScore(Double upper_typeScore) {
        this.upper_typeScore = upper_typeScore;
    }

    public String getHair() {
        return hair;
    }

    public void setHair(String hair) {
        this.hair = hair;
    }

    public Integer getObjLeft() {
        return objLeft;
    }

    public void setObjLeft(Integer objLeft) {
        this.objLeft = objLeft;
    }

    public String getLower_type() {
        return lower_type;
    }

    public void setLower_type(String lower_type) {
        this.lower_type = lower_type;
    }

    public String getObjType() {
        return objType;
    }

    public void setObjType(String objType) {
        this.objType = objType;
    }

    public Integer getObjTop() {
        return objTop;
    }

    public void setObjTop(Integer objTop) {
        this.objTop = objTop;
    }

    public String getObjUUId() {
        return objUUId;
    }

    public void setObjUUId(String objUUId) {
        this.objUUId = objUUId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Integer leaveTime) {
        this.leaveTime = leaveTime;
    }

    public String getUpper_type() {
        return upper_type;
    }

    public void setUpper_type(String upper_type) {
        this.upper_type = upper_type;
    }

    public Integer getObjBottom() {
        return objBottom;
    }

    public void setObjBottom(Integer objBottom) {
        this.objBottom = objBottom;
    }

    public String getLower_color() {
        return lower_color;
    }

    public void setLower_color(String lower_color) {
        this.lower_color = lower_color;
    }

    public Double getUpper_colorScore() {
        return upper_colorScore;
    }

    public void setUpper_colorScore(Double upper_colorScore) {
        this.upper_colorScore = upper_colorScore;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Double getLower_typeScore() {
        return lower_typeScore;
    }

    public void setLower_typeScore(Double lower_typeScore) {
        this.lower_typeScore = lower_typeScore;
    }

    public Long getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Long entryTime) {
        this.entryTime = entryTime;
    }

    public Double getLower_colorScore() {
        return lower_colorScore;
    }

    public void setLower_colorScore(Double lower_colorScore) {
        this.lower_colorScore = lower_colorScore;
    }

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public Integer getObjRight() {
        return objRight;
    }

    public void setObjRight(Integer objRight) {
        this.objRight = objRight;
    }

    public String getUpper_color() {
        return upper_color;
    }

    public void setUpper_color(String upper_color) {
        this.upper_color = upper_color;
    }

    public String getCropImageSigned() {
        return cropImageSigned;
    }

    public void setCropImageSigned(String cropImageSigned) {
        this.cropImageSigned = cropImageSigned;
    }

    public DeviceBean getDeviceBean() {
        return deviceBean;
    }

    public void setDeviceBean(DeviceBean deviceBean) {
        this.deviceBean = deviceBean;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getHat_type() {
        return hat_type;
    }

    public void setHat_type(String hat_type) {
        this.hat_type = hat_type;
    }

    public String getHat_color() {
        return hat_color;
    }

    public void setHat_color(String hat_color) {
        this.hat_color = hat_color;
    }

    public String getBag_type() {
        return bag_type;
    }

    public void setBag_type(String bag_type) {
        this.bag_type = bag_type;
    }

    public String getBag_color() {
        return bag_color;
    }

    public void setBag_color(String bag_color) {
        this.bag_color = bag_color;
    }

    @Override
    public String toString() {
        return "SearchSource{" +
                "origImage='" + origImage + '\'' +
                ", oriImageSigned='" + oriImageSigned + '\'' +
                ", hairScore=" + hairScore +
                ", cropImage='" + cropImage + '\'' +
                ", sexScore=" + sexScore +
                ", upper_typeScore=" + upper_typeScore +
                ", hair='" + hair + '\'' +
                ", objLeft=" + objLeft +
                ", lower_type='" + lower_type + '\'' +
                ", objType='" + objType + '\'' +
                ", objTop=" + objTop +
                ", objUUId='" + objUUId + '\'' +
                ", timestamp=" + timestamp +
                ", leaveTime=" + leaveTime +
                ", upper_type='" + upper_type + '\'' +
                ", objBottom=" + objBottom +
                ", lower_color='" + lower_color + '\'' +
                ", upper_colorScore=" + upper_colorScore +
                ", sex='" + sex + '\'' +
                ", lower_typeScore=" + lower_typeScore +
                ", entryTime=" + entryTime +
                ", lower_colorScore=" + lower_colorScore +
                ", cameraId='" + cameraId + '\'' +
                ", objId='" + objId + '\'' +
                ", objRight=" + objRight +
                ", upper_color='" + upper_color + '\'' +
                ", cropImageSigned='" + cropImageSigned + '\'' +
                ", year='" + year + '\'' +
                ", hat_type='" + hat_type + '\'' +
                ", hat_color='" + hat_color + '\'' +
                ", bag_type='" + bag_type + '\'' +
                ", bag_color='" + bag_color + '\'' +
                ", deviceBean=" + deviceBean +
                '}';
    }
}
