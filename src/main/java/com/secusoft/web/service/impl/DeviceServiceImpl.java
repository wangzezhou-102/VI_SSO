package com.secusoft.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.secusoft.web.config.UbregionApiConfig;
import com.secusoft.web.core.common.Constants;
import com.secusoft.web.core.support.BeanKit;
import com.secusoft.web.mapper.DeviceMapper;
import com.secusoft.web.mapper.SysOrganizationMapper;
import com.secusoft.web.model.BaseResponse;
import com.secusoft.web.model.DeviceBean;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.SysOrganizationBean;
import com.secusoft.web.model.UbrDeviceBean;
import com.secusoft.web.service.DeviceService;
import com.secusoft.web.shipinapi.ShiPinClient;

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

    /**
     * 同步城市大脑数据到本地device表
     */
    @Override
    public ResultVo syncDeviceFromUbr() {
        int pageNum = 1;
        int pageSize = 100;
        boolean flag = true;
        boolean deleted = false;
        while(flag) {
            Map<String,Object> resultMap = readUbrDeviceByPage(pageNum, pageSize);
            if((boolean) resultMap.get("hasNext")) {
                pageNum++;
            } else {
                flag = false;
            }
            List<UbrDeviceBean> ubrDevices = (List<UbrDeviceBean>) resultMap.get("data");
            List<DeviceBean> devices = transUbrToDevice(ubrDevices);
            if(!deleted) {
                deviceMapper.deleteDeviceTable();
                deleted = true;
            }
            deviceMapper.insetBatchDevice(devices);
        }
        deviceMapper.deleteOrgDevice();
        return ResultVo.success();
    }

    public Map<String,Object> readUbrDeviceByPage(int pageNum, int pageSize) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("hasNext", false);
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("offset", pageNum);
        paramMap.put("size", pageSize);
        String resultStr = ShiPinClient.getClientConnectionPool()
                .fetchByPostMethod(ubregionApiConfig.getDeviceDetail(), paramMap);

        BaseResponse deviceRes = JSON.parseObject(resultStr, BaseResponse.class);
        if(deviceRes != null && Constants.SUCCESS.equals(deviceRes.getErrorCode())) {
            List<UbrDeviceBean> ubrDevices = JSON.parseArray(JSON.parseObject(resultStr).getString("data"), UbrDeviceBean.class);;
            long totalCount = deviceRes.getTotalCount();
            if(totalCount > (pageNum * pageSize)) {
                resultMap.put("hasNext", true);
            }
            resultMap.put("data", ubrDevices);
        } else {
            resultMap.put("data", null);
        }
        return resultMap;
    }
    
    /**
     * 数据转换 城市大脑设备信息与本地设备转换
     * @author ChenDong
     * @date 2019年6月14日
     * @param list
     * @return
     */
    public List<DeviceBean> transUbrToDevice(List<UbrDeviceBean> list) {
        List<DeviceBean> resultList = new ArrayList<DeviceBean>();
        String[] ignoreProperties = new String[] {"longitude","latitude","ptzType"};
        list.forEach(obj -> {
            DeviceBean bean = new DeviceBean();
            BeanKit.copyProperties(obj, bean, ignoreProperties);
            bean.setLongitude(obj.getLongitude()==null?null:obj.getLongitude().stripTrailingZeros().toPlainString());
            bean.setDeviceName(obj.getName());
            bean.setLatitude(obj.getLatitude()==null?null:obj.getLatitude().stripTrailingZeros().toPlainString());
            bean.setType(obj.getPtzType());
            resultList.add(bean);
        });
        return resultList;
    }
}
