package com.secusoft.web.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.secusoft.web.model.DeviceBean;

import java.util.List;

public interface DeviceMapper extends BaseMapper<DeviceBean> {
    List<DeviceBean> selectDeviceByPid(String id);
    DeviceBean selectDeviceById(String id);
    List<DeviceBean> selectDeviceByAreaId(String id);

    List<DeviceBean> readDeviceList(DeviceBean queryBean);

}
