package com.secusoft.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.secusoft.web.model.AreaBean;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  区域收藏接口
 *  @author huanghao
 */
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
        AreaBean areaBean = JSON.parseObject(jsonObject.get("Area").toString(), new TypeReference<AreaBean>() {
        });
        List<String> deviceIds = JSON.parseObject(jsonObject.get("devices").toString(), new TypeReference<List<String>>() {
        });
        ResultVo resultVo = areaService.addArea(areaBean, deviceIds);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    /**
     * 取消收藏区域
     * @param areaBean 取消收藏区域的ID
     * @return
     */
    @RequestMapping("/removeArea")
    public ResponseEntity<ResultVo> removeFolder(@RequestBody AreaBean areaBean){
        ResultVo resultVo = areaService.removeArea(areaBean);
        return  new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    /**
     *更新收藏区域
     * @param jsonObject Area对象 以及 数组 设备id
     * @return
     */
    @RequestMapping("updateArea")
    public ResponseEntity<ResultVo> updateArea(@RequestBody JSONObject jsonObject){
        AreaBean areaBean = JSON.parseObject(jsonObject.get("AreaBean").toString(), new TypeReference<AreaBean>() {
        });
        List<String> deviceIds = JSON.parseObject(jsonObject.get("devices").toString(), new TypeReference<List<String>>() {
        });
        ResultVo resultVo = areaService.updateArea(areaBean, deviceIds);
        return new ResponseEntity<ResultVo>(resultVo,HttpStatus.OK);
    }

    /**
     * 更改区域名称
     * @param areaBean 区域名称 以及 区域ID
     * @return
     */
    @RequestMapping("updateAreaName")
    public ResponseEntity<ResultVo> updateAreaName(@RequestBody AreaBean areaBean){
        ResultVo resultVo = areaService.updateAreaName(areaBean);
        return new ResponseEntity<ResultVo>(resultVo,HttpStatus.OK);
    }

    /**
     * 展示该区域下的所有设备
     * @param areaBean 区域ID
     * @return
     */
    @RequestMapping("readArea")
    public ResponseEntity<ResultVo> readArea(@RequestBody AreaBean areaBean){
        ResultVo resultVo = areaService.readArea(areaBean);
        return new ResponseEntity<ResultVo>(resultVo,HttpStatus.OK);
    }
}
