package com.secusoft.web.service;

import com.secusoft.web.model.ViTaskDeviceBean;

/**
 * 布控任务设备接口
 *
 * @author chjiang
 * @since 2019/6/14 16:44
 */
public interface ViTaskDeviceService {

    /**
     * 根据相关条件获取所关联的设备信息
     * @param viTaskDeviceBean
     * @return
     */
    ViTaskDeviceBean getViTaskDeviceBeanByObject(ViTaskDeviceBean viTaskDeviceBean);
}
