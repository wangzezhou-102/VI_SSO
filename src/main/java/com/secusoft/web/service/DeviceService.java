package com.secusoft.web.service;

import com.secusoft.web.model.DeviceBean;
import com.secusoft.web.model.ResultVo;

/**
 * 设备接口类
 * @author ChenDong
 * @company 视在数科
 * @date 2019年6月11日
 */
public interface DeviceService {


    ResultVo readDeviceList(DeviceBean paramBean);
    
    ResultVo readDeviceTree();
    
    ResultVo syncDeviceFromUbr();
    
    ResultVo syncStreamState();
}
