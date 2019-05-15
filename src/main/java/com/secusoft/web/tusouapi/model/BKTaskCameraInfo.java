package com.secusoft.web.tusouapi.model;

public class BKTaskCameraInfo{

    /**
     * 摄像头ID
     */
    private String cameraId;
    /**
     * 对应摄像头的布控结果过滤值, 0-1之间
     */
    private Double threshold;

    /**
     * @return the cameraId
     */
    public String getCameraId() {
        return cameraId;
    }

    /**
     * @param cameraId the cameraId to set
     */
    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    /**
     * @return the threshold
     */
    public Double getThreshold() {
        return threshold;
    }

    /**
     * @param threshold the threshold to set
     */
    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }

    
}