package com.secusoft.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.secusoft.web.model.Picture;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.Track;
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
        Track track = JSON.parseObject(jsonObject.get("track").toString(), new TypeReference<Track>() {
        });
        ArrayList<Picture> pictureList = JSON.parseObject(jsonObject.get("pictureList").toString(), new TypeReference<ArrayList<Picture>>() {
        });
        ResultVo resultVo = trackService.addTrack(track, pictureList);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    /**
     * 删除轨迹
     * @param track id
     * @return
     */
    public ResponseEntity<ResultVo> removeTrack(@RequestBody Track track){
        ResultVo resultVo = trackService.removeTrack(track);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    /**
     * 修改该轨迹下的图片
     * @param jsonObject
     * @return
     */
    public ResponseEntity<ResultVo> updateTrack(@RequestBody JSONObject jsonObject){
        Track track = JSON.parseObject(jsonObject.get("Track").toString(), new TypeReference<Track>() {
        });
        ArrayList<Picture> pictureList = JSON.parseObject(jsonObject.get("PictureList").toString(), new TypeReference<ArrayList<Picture>>() {
        });
        ResultVo resultVo = trackService.updateTrack(track, pictureList);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    /**
     * 修改轨迹名称
     * @param track
     * @return
     */
    public ResponseEntity<ResultVo> updateTrackName(@RequestBody Track track){
        ResultVo resultVo = trackService.updateTrackName(track);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    /**
     * 展示该轨迹详情
     * @param track
     * @return
     */
    @RequestMapping("readTrack")
    public ResponseEntity<ResultVo> readTrack(@RequestBody Track track){
        ResultVo resultVo = trackService.readTrack(track);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);

    }
}
