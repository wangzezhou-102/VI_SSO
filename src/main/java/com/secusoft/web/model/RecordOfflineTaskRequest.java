package com.secusoft.web.model;

import java.util.List;

/**
 * 离线任务请求
 * @author zzwang
 * @company 视在数科
 * @date 2019年6月25日
 */
public class RecordOfflineTaskRequest {
    /**
     *设备列表
     */
    private List<String> deviceIds;
    /**
     *视频开始时间
     */
    private String beginTime;
    /**
     *视频结束时间
     */
    private String endTime;

    public List<String> getDeviceIds() { return deviceIds; }

    public void setDeviceIds(List<String> deviceIds) { this.deviceIds = deviceIds; }

    public String getBeginTime() { return beginTime; }

    public void setBeginTime(String beginTime) { this.beginTime = beginTime; }

    public String getEndTime() { return endTime; }

    public void setEndTime(String endTime) { this.endTime = endTime; }

    public boolean validate(){
        //判断所需参数是否完成
        if(deviceIds.isEmpty() || beginTime.isEmpty() || endTime.isEmpty()){
           return false;
        }
        return true;
    }
}
