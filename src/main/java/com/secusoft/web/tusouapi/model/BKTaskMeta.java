package com.secusoft.web.tusouapi.model;

import java.util.ArrayList;


/**
 * 布控任务相关信息
 */
public class BKTaskMeta {


    /**
     * [必须] 布控任务描述信息
     */
    String desc;
    /**
     * [必须] 布控库id
     */
    String bkid;
    /**
     * [必须] camera的信息。多个camera信息，组成Array数组。如：
     *  [{“cameraId”:”c001”,”threshold”:0.6},{“cameraId”:”c002”,”threshold”:0.7}]
     */
    ArrayList<BKTaskCameraInfo> cameraInfo;
    /**
     * [必须] 算法对应的输⼊通道(datahub, rocketmq)信息
     */
    BKTaskChannel input;
    /**
     * [必须] 算法对应的输出通道(datahub, rocketmq)信息
     */
    BKTaskChannel output;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getBkid() {
        return bkid;
    }

    public void setBkid(String bkid) {
        this.bkid = bkid;
    }

    public ArrayList<BKTaskCameraInfo> getCameraInfo() {
        return cameraInfo;
    }

    public void setCameraInfo(ArrayList<BKTaskCameraInfo> cameraInfo) {
        this.cameraInfo = cameraInfo;
    }

    public BKTaskChannel getInput() {
        return input;
    }

    public void setInput(BKTaskChannel input) {
        this.input = input;
    }

    public BKTaskChannel getOutput() {
        return output;
    }

    public void setOutput(BKTaskChannel output) {
        this.output = output;
    }
}