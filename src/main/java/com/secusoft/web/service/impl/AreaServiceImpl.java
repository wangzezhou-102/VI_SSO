package com.secusoft.web.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.mapper.AreaMapper;
import com.secusoft.web.mapper.DeviceMapper;
import com.secusoft.web.model.AreaBean;
import com.secusoft.web.model.DeviceBean;
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
    public ResultVo addArea(AreaBean areaBean, List deviceIds) {
        areaMapper.insertArea(areaBean);
        areaMapper.insertAreaDevice(deviceIds, areaBean.getId());
        return ResultVo.success();
    }


    @Override
    public ResultVo removeArea(AreaBean areaBean) {
        areaMapper.deleteAreaDeviceById(areaBean.getId());
        areaMapper.deleteAreaById(areaBean.getId());
        return ResultVo.success();
    }

    @Override
    public ResultVo updateAreaName(AreaBean areaBean) {
        areaMapper.updateAreaNameById(areaBean);
        return ResultVo.success();
    }

    @Override
    public ResultVo updateArea(AreaBean areaBean, List deviceIds) {
        areaMapper.deleteAreaDeviceById(areaBean.getId());
        areaMapper.insertAreaDevice(deviceIds, areaBean.getId());
        return  ResultVo.success();
    }

    @Override
    public ResultVo readArea(AreaBean areaBean) {
        List<DeviceBean> deviceBeans = deviceMapper.selectDeviceByAreaId(areaBean.getId());
        return ResultVo.success(deviceBeans);
    }
    
    public ResultVo removeAreaByFolderId(Integer folderId) {
    	if(folderId==null) {
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
    	}
    	areaMapper.deleteAreaDeviceByFolderId(folderId);
    	AreaBean bean = new AreaBean();
    	bean.setFolderId(folderId);
    	areaMapper.deleteAreaByBean(bean);
    	return ResultVo.success();
    }
}
