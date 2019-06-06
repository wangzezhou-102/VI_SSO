package com.secusoft.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.secusoft.web.service.BoxSelectService;
import com.secusoft.web.core.common.GlobalApiResult;
import com.secusoft.web.model.Point;
import com.secusoft.web.model.Round;
import com.secusoft.web.shipinapi.model.Camera;
import com.secusoft.web.shipinapi.service.CameraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;

@CrossOrigin(value = "*", maxAge = 3600)
@RestController
public class BoxSelectController {
    @Autowired
    private  BoxSelectService boxSelectService;
    @Autowired
    private  CameraService CameraServiceImpl;

    /**
     * 多边形框选返回设备id
     * @param request 有顺序的各个点位坐标
     * @return
     */
    @RequestMapping("/boxselect")
    public Object boxselect(@RequestBody String request){
        ArrayList<Point> points = JSON.parseObject(request, new TypeReference<ArrayList<Point>>() {
        });
        //获取设备列表待改进
        List<Camera> cameras=JSON.parseObject(CameraServiceImpl.getAllCamera().toString(),new TypeReference<ArrayList<Camera>>(){});
        List<Camera> cameraList = new ArrayList<>();
        JSON.parseObject(CameraServiceImpl.getAllCamera().toString(),(Class<ArrayList<Camera>>) cameraList.getClass());
        return GlobalApiResult.success(boxSelectService.isPtInPoly(cameras,points));
    }

    /**
     * 圆形框选返回设备id
     * @param
     * @return
     */
    @RequestMapping("/roundboxselect")
    public Object roundboxselect(@RequestBody Round round){
        //获取设备列表待改进
        List<Camera> cameras=JSON.parseObject(CameraServiceImpl.getAllCamera().toString(),new TypeReference<ArrayList<Camera>>(){});
        return GlobalApiResult.success(boxSelectService.isPtInPoly2(cameras,round.getPoint(),round.getRadius()));
    }
}
