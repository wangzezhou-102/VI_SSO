package com.secusoft.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.secusoft.web.mapper.DeviceMapper;
import com.secusoft.web.mapper.SysOrganizationMapper;
import com.secusoft.web.model.DeviceBean;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.SysOrganizationBean;
import com.secusoft.web.service.DeviceService;

import javax.annotation.Resource;

/**
 * 设备Service
 * @author ChenDong
 * @company 视在数科
 * @date 2019年6月12日
 */
@Service
public class DeviceServiceImpl implements DeviceService {

    @Resource
    private DeviceMapper deviceMapper;
    
    @Resource
    private SysOrganizationMapper orgMapper;
    /**
     * 获取设备信息，可分页
     */
    @Override
    public ResultVo readDeviceList(DeviceBean queryBean) {
        ResultVo resultVo = new ResultVo();
        if(queryBean != null && queryBean.getPageNumber() != null && queryBean.getPageSize() != null) {
            PageHelper.startPage(queryBean.getPageNumber().intValue(), queryBean.getPageSize());
        }
        List<DeviceBean> list = deviceMapper.readDeviceList(queryBean);
        PageInfo<DeviceBean> pageInfo = new PageInfo<DeviceBean>(list);
        resultVo.setData(pageInfo.getList());
        resultVo.setTotal(pageInfo.getTotal());
        return resultVo;
    }

    /**
     * 获取设备树
     */
    @Override
    public ResultVo readDeviceTree() {
        ResultVo resultVo = new ResultVo();
        SysOrganizationBean queryBean = new SysOrganizationBean();
        List<SysOrganizationBean> list = orgMapper.readOrgDeviceTree(queryBean);
        resultVo.setData(list);
        return resultVo;
    }

}
