package com.secusoft.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.secusoft.web.core.common.Constants;
import com.secusoft.web.core.util.ResponseUtil;
import com.secusoft.web.model.DeviceBean;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.DeviceService;

/**
 * 设备接口类
 * @author ChenDong
 * @company 视在数科
 * @date 2019年6月11日
 */
@RestController
@CrossOrigin(value = "*", maxAge = 3600)
public class DeviceController {

    @Autowired
    private DeviceService deviceService;
    
    /**
     * 查询设备列表
     * @author ChenDong
     * @date 2019年6月11日
     * @param queryBean
     * @return
     */
    @PostMapping("/readdevicelist")
    public ResponseEntity<ResultVo> readDeviceList(@RequestBody DeviceBean queryBean) {
        ResultVo result = deviceService.readDeviceList(queryBean);
        return ResponseUtil.handle(Constants.SUCCESS, result);
    }
    
    /**
     * 查询设备树信息
     * @author ChenDong
     * @date 2019年6月12日
     * @param queryBean
     * @return
     */
    @PostMapping("/readdevicetree")
    public ResponseEntity<ResultVo> readDeviceTree(@RequestBody DeviceBean queryBean) {
        ResultVo result = deviceService.readDeviceTree();
        return ResponseUtil.handle(Constants.SUCCESS, result);
    }
    
}
