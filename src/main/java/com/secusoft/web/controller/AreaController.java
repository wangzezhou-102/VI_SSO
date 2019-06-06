package com.secusoft.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.secusoft.web.model.Area;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
public class AreaController {
    @Autowired
    private AreaService areaService;

    /**
     * 收藏区域
     * @param jsonObject Area对象 以及 该区域下的设备ID数组
     * @return
     */
    @RequestMapping("/addArea")
    public ResponseEntity<ResultVo> addArea(@RequestBody JSONObject jsonObject){
        Area area = JSON.parseObject(jsonObject.get("Area").toString(), new TypeReference<Area>() {
        });
        List<String> deviceIds = JSON.parseObject(jsonObject.get("devices").toString(), new TypeReference<List<String>>() {
        });
        ResultVo resultVo = areaService.addArea(area, deviceIds);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    /**
     * 取消收藏区域
     * @param area 取消收藏区域的ID
     * @return
     */
    @RequestMapping("/removeArea")
    public ResponseEntity<ResultVo> removeFolder(@RequestBody Area area){
        ResultVo resultVo = areaService.removeArea(area);
        return  new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    /**
     *更新收藏区域
     * @param jsonObject Area对象 以及 数组 设备id
     * @return
     */
    @RequestMapping("updateArea")
    public ResponseEntity<ResultVo> updateArea(@RequestBody JSONObject jsonObject){
        Area area = JSON.parseObject(jsonObject.get("Area").toString(), new TypeReference<Area>() {
        });
        List<String> deviceIds = JSON.parseObject(jsonObject.get("devices").toString(), new TypeReference<List<String>>() {
        });
        ResultVo resultVo = areaService.updateArea(area, deviceIds);
        return new ResponseEntity<ResultVo>(resultVo,HttpStatus.OK);
    }

    /**
     * 更改区域名称
     * @param area 区域名称 以及 区域ID
     * @return
     */
    @RequestMapping
    public ResponseEntity<ResultVo> updateAreaName(@RequestBody Area area){
        ResultVo resultVo = areaService.updateAreaName(area);
        return new ResponseEntity<ResultVo>(resultVo,HttpStatus.OK);
    }

    /**
     * 展示该区域下的所有设备
     * @param area 区域ID
     * @return
     */
    @RequestMapping("readArea")
    public ResponseEntity<ResultVo> readArea(@RequestBody Area area){
        ResultVo resultVo = areaService.readArea(area);
        return new ResponseEntity<ResultVo>(resultVo,HttpStatus.OK);
    }
}
