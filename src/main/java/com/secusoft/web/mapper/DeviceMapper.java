package com.secusoft.web.mapper;

import com.secusoft.web.model.DeviceBean;

import java.util.List;

public interface DeviceMapper {
    DeviceBean selectDeviceById(String id);
    List<DeviceBean> selectDeviceByAreaId(String id);

}
