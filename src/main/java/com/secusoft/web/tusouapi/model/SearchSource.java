package com.secusoft.web.tusouapi.model;

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
    private Integer hair;
    private Integer objLeft;
    private Integer lower_type;
    private String  objType;
    private Integer objTop;
    private String  objUUId;
    private Long  timestamp;
    private Integer leaveTime;
    private Integer upper_type;
    private Integer objBottom;
    private Integer lower_color;
    private Double upper_colorScore;
    private Integer sex;
    private Double lower_typeScore;
    private Long   entryTime;
    private Double lower_colorScore;
    private String cameraId;
    private String objId;
    private Integer objRight;
    private Integer upper_color;
    private String cropImageSigned;

    public SearchSource() {
    }

    public SearchSource(String origImage, String oriImageSigned, Double hairScore, String cropImage, Double sexScore, Double upper_typeScore, Integer hair, Integer objLeft, Integer lower_type, String objType, Integer objTop, String objUUId, Long timestamp, Integer leaveTime, Integer upper_type, Integer objBottom, Integer lower_color, Double upper_colorScore, Integer sex, Double lower_typeScore, Long entryTime, Double lower_colorScore, String cameraId, String objId, Integer objRight, Integer upper_color, String cropImageSigned) {
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

    public Integer getHair() {
        return hair;
    }

    public void setHair(Integer hair) {
        this.hair = hair;
    }

    public Integer getObjLeft() {
        return objLeft;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public void setObjLeft(Integer objLeft) {
        this.objLeft = objLeft;
    }

    public Integer getLower_type() {
        return lower_type;
    }

    public void setLower_type(Integer lower_type) {
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




    public Integer getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Integer leaveTime) {
        this.leaveTime = leaveTime;
    }

    public Integer getUpper_type() {
        return upper_type;
    }

    public void setUpper_type(Integer upper_type) {
        this.upper_type = upper_type;
    }

    public Integer getObjBottom() {
        return objBottom;
    }

    public void setObjBottom(Integer objBottom) {
        this.objBottom = objBottom;
    }

    public Integer getLower_color() {
        return lower_color;
    }

    public void setLower_color(Integer lower_color) {
        this.lower_color = lower_color;
    }

    public Double getUpper_colorScore() {
        return upper_colorScore;
    }

    public void setUpper_colorScore(Double upper_colorScore) {
        this.upper_colorScore = upper_colorScore;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
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

    public Integer getUpper_color() {
        return upper_color;
    }

    public void setUpper_color(Integer upper_color) {
        this.upper_color = upper_color;
    }

    public String getCropImageSigned() {
        return cropImageSigned;
    }

    public void setCropImageSigned(String cropImageSigned) {
        this.cropImageSigned = cropImageSigned;
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
                ", hair=" + hair +
                ", objLeft=" + objLeft +
                ", lower_type=" + lower_type +
                ", objType='" + objType + '\'' +
                ", objTop=" + objTop +
                ", objUUId='" + objUUId + '\'' +
                ", timestamp=" + timestamp +
                ", leaveTime=" + leaveTime +
                ", upper_type=" + upper_type +
                ", objBottom=" + objBottom +
                ", lower_color=" + lower_color +
                ", upper_colorScore=" + upper_colorScore +
                ", sex=" + sex +
                ", lower_typeScore=" + lower_typeScore +
                ", entryTime=" + entryTime +
                ", lower_colorScore=" + lower_colorScore +
                ", cameraId='" + cameraId + '\'' +
                ", objId='" + objId + '\'' +
                ", objRight=" + objRight +
                ", upper_color=" + upper_color +
                ", cropImageSigned='" + cropImageSigned + '\'' +
                '}';
    }
}
