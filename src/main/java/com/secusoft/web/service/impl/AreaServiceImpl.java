package com.secusoft.web.service.impl;

import com.secusoft.web.mapper.AreaMapper;
import com.secusoft.web.mapper.DeviceMapper;
import com.secusoft.web.model.Area;
import com.secusoft.web.model.Device;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.AreaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {
    @Resource
    private AreaMapper areaMapper;

    @Resource
    private DeviceMapper deviceMapper;

    @Override
    public ResultVo addArea(Area area, List deviceIds) {
        areaMapper.insertArea(area);
        areaMapper.insertAreaDevice(deviceIds,area.getId());
        return ResultVo.success();
    }


    @Override
    public ResultVo removeArea(Area area) {
        areaMapper.deleteAreaDeviceById(area.getId());
        areaMapper.deleteAreaById(area.getId());
        return ResultVo.success();
    }

    @Override
    public ResultVo updateAreaName(Area area) {
        areaMapper.updateAreaNameById(area);
        return ResultVo.success();
    }

    @Override
    public ResultVo updateArea(Area area, List deviceIds) {
        areaMapper.deleteAreaDeviceById(area.getId());
        areaMapper.insertAreaDevice(deviceIds,area.getId());
        return  ResultVo.success();
    }

    @Override
    public ResultVo readArea(Area area) {
        List<Device> devices = deviceMapper.selectDeviceByAreaId(area.getId());
        return ResultVo.success(devices);
    }
}
