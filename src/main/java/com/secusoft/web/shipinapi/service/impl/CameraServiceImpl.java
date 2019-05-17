package com.secusoft.web.shipinapi.service.impl;

import com.alibaba.fastjson.JSON;
import com.secusoft.web.shipinapi.ShiPinClient;
import com.secusoft.web.shipinapi.model.Camera;
import com.secusoft.web.shipinapi.service.CameraService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@CacheConfig(cacheNames = { "camera_list" })
@Service
public class CameraServiceImpl implements CameraService {
    @Override
    @Cacheable
    public List<Camera> getAllCamera() {

        String responseStr = ShiPinClient.getClientConnectionPool().fetchByPostMethod(ShiPinClient.Path_CAMERA_LIST, "");

        List<Camera> cameraList = new ArrayList<>();

        return JSON.parseObject(responseStr, (Class<ArrayList<Camera>>) cameraList.getClass());
    }
}
