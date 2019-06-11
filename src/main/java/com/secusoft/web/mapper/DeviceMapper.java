package com.secusoft.web.mapper;

import com.secusoft.web.model.DeviceBean;

import java.util.List;

public interface DeviceMapper {
    List<DeviceBean> selectDeviceByPid(String id);
    DeviceBean selectDeviceById(String id);
    List<DeviceBean> selectDeviceByAreaId(String id);

}
