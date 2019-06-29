package com.secusoft.web.tusouapi.model;

import java.util.Date;
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
    private Date beginTime;
    /**
     *视频结束时间
     */
    private Date endTime;

    public List<String> getDeviceIds() { return deviceIds; }

    public void setDeviceIds(List<String> deviceIds) { this.deviceIds = deviceIds; }

    public Date getBeginTime() { return beginTime; }

    public void setBeginTime(Date beginTime) { this.beginTime = beginTime; }

    public Date getEndTime() { return endTime; }

    public void setEndTime(Date endTime) { this.endTime = endTime; }

    public boolean validate(){
        //判断所需参数是否完成
        if(deviceIds.isEmpty() || beginTime.toString().isEmpty() || endTime.toString().isEmpty()){
           return false;
        }
        return true;
    }
}
