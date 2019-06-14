package com.secusoft.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.secusoft.web.config.UbregionApiConfig;
import com.secusoft.web.mapper.DeviceMapper;
import com.secusoft.web.mapper.SysOrganizationMapper;
import com.secusoft.web.model.DeviceBean;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.SysOrganizationBean;
import com.secusoft.web.service.DeviceService;

/**
 * 设备Service
 * @author ChenDong
 * @company 视在数科
 * @date 2019年6月12日
 */
@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceMapper deviceMapper;
    
    @Autowired
    private SysOrganizationMapper orgMapper;

    @Autowired
    private UbregionApiConfig ubregionApiConfig;
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

    @Override
    public ResultVo readDeviceFromBase() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 从城市大脑获取
     */
//    @Override
//    public ResultVo readDeviceFromBase() {
//        Map<String,Object> paramMap = new HashMap<String,Object>();
//        int pageNum = 1;
//        int size = 80;
//        paramMap.put("offset", pageNum);
//        paramMap.put("size", size);
//        String resultStr = ShiPinClient.getClientConnectionPool()
//                .fetchByPostMethod(ubregionApiConfig.getAccessDeviceList(), paramMap);
//        BaseResponse response = JSON.parseObject(resultStr, BaseResponse.class);
//        if(Constants.SUCCESS.equals(response.getErrorCode())) {
//            long totalCount = response.getTotalCount();
//            if(totalCount>size) {
//                // 不止一次查询
//            }else {
//                // 查完毕
//
//            }
//        }
//        return null;
//    }
//
//    public List<DeviceBean> readAllDevices(List<>) {
//
//    }
}
