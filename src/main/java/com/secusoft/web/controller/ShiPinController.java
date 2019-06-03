package com.secusoft.web.controller;

import com.secusoft.web.core.common.GlobalApiResult;
import com.secusoft.web.shipinapi.service.CameraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(value = "*", maxAge = 3600)
@RestController
public class ShiPinController {

    @Autowired
    private CameraService CameraServiceImpl;

    @RequestMapping("/camera_list")
    public Object getALLCameraDetail() {
        return GlobalApiResult.success(CameraServiceImpl.getAllCamera());
    }
}
