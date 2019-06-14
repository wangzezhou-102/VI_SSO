package com.secusoft.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 对接城市大脑openAPI配置
 * @author ChenDong
 * @company 视在数科
 * @date 2019年6月13日
 */
@ConfigurationProperties(prefix="ubregion.openapi")
@Configuration
public class UbregionApiConfig {

    // 获取已接入设备列表
    private String accessDeviceList;
    // 获取集群列表
    private String clusterList;
    // 批量获取设备详情
    private String deviceDetail;
    
    public String getAccessDeviceList() {
        return accessDeviceList;
    }
    public void setAccessDeviceList(String accessDeviceList) {
        this.accessDeviceList = accessDeviceList;
    }
    public String getClusterList() {
        return clusterList;
    }
    public void setClusterList(String clusterList) {
        this.clusterList = clusterList;
    }
    public String getDeviceDetail() {
        return deviceDetail;
    }
    public void setDeviceDetail(String deviceDetail) {
        this.deviceDetail = deviceDetail;
    }
    
}
