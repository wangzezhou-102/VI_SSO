package com.secusoft.web.mapper;

import com.secusoft.web.model.Device;

import java.util.List;

public interface DeviceMapper {
    Device selectDeviceById(String id);
    List<Device> selectDeviceByAreaId(String id);

}
