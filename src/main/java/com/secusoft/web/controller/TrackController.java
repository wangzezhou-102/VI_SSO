package com.secusoft.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.secusoft.web.model.PictureBean;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.TrackBean;
import com.secusoft.web.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@CrossOrigin(value = "*", maxAge = 3600)
@RestController
public class TrackController {
    @Autowired
    private TrackService trackService;

    /**
     * 添加轨迹
     * @param jsonObject Track对象 以及 Picture数组
     * @return
     */
    @RequestMapping("addtrack")
    public ResponseEntity<ResultVo> addTrack(@RequestBody JSONObject jsonObject){
        TrackBean trackBean = JSON.parseObject(jsonObject.get("trackBean").toString(), new TypeReference<TrackBean>() {
        });
        ArrayList<PictureBean> pictureBeanList = JSON.parseObject(jsonObject.get("pictureBeanList").toString(), new TypeReference<ArrayList<PictureBean>>() {
        });
        ResultVo resultVo = trackService.addTrack(trackBean, pictureBeanList);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    /**
     * 删除轨迹
     * @param trackBean id
     * @return
     */
    public ResponseEntity<ResultVo> removeTrack(@RequestBody TrackBean trackBean){
        ResultVo resultVo = trackService.removeTrack(trackBean);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    /**
     * 修改该轨迹下的图片
     * @param jsonObject
     * @return
     */
    public ResponseEntity<ResultVo> updateTrack(@RequestBody JSONObject jsonObject){
        TrackBean trackBean = JSON.parseObject(jsonObject.get("TrackBean").toString(), new TypeReference<TrackBean>() {
        });
        ArrayList<PictureBean> pictureBeanList = JSON.parseObject(jsonObject.get("PictureList").toString(), new TypeReference<ArrayList<PictureBean>>() {
        });
        ResultVo resultVo = trackService.updateTrack(trackBean, pictureBeanList);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    /**
     * 修改轨迹名称
     * @param trackBean
     * @return
     */
    public ResponseEntity<ResultVo> updateTrackName(@RequestBody TrackBean trackBean){
        ResultVo resultVo = trackService.updateTrackName(trackBean);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    /**
     * 展示该轨迹详情
     * @param trackBean
     * @return
     */
    @RequestMapping("readTrack")
    public ResponseEntity<ResultVo> readTrack(@RequestBody TrackBean trackBean){
        ResultVo resultVo = trackService.readTrack(trackBean);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);

    }
}
