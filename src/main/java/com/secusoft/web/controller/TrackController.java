package com.secusoft.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.core.util.ResponseUtil;
import com.secusoft.web.model.PictureBean;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.TrackBean;
import com.secusoft.web.service.TrackService;
import com.secusoft.web.tusouapi.model.SearchResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 *  收藏轨迹接口
 *  @author huanghao
 */
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
public class TrackController {
    @Autowired
    private TrackService trackService;

    /**
     * 添加轨迹 不覆盖
     * @param jsonObject Track对象 以及 Picture数组
     * @return
     */
    @RequestMapping("addtrack")
    public ResponseEntity<ResultVo> addTrack(@RequestBody JSONObject jsonObject){
        TrackBean trackBean = JSON.parseObject(jsonObject.get("track").toString(), new TypeReference<TrackBean>() {
        });
        ArrayList<SearchResponseData> searchResponseDataList = JSON.parseObject(jsonObject.get("pictureList").toString(), new TypeReference<ArrayList<SearchResponseData>>() {
        });
        ArrayList<PictureBean> pictureBeanList = new ArrayList<>();
        PictureBean pictureBean = new PictureBean();
        for (SearchResponseData searchResponseData:searchResponseDataList) {
            pictureBean.setScore(searchResponseData.getScore()==null?null:searchResponseData.getScore().toString());
            pictureBean.setDeviceId(searchResponseData.getSource().getCameraId());
            pictureBean.setPictureId(searchResponseData.getId());
            pictureBean.setPictureTime(searchResponseData.getSource().getTimestamp());
            pictureBean.setOriImageSignedUrl(searchResponseData.getSource().getOriImageSigned());
            pictureBean.setOrigImageUrl(searchResponseData.getSource().getOrigImage());
            pictureBean.setCropImageSignedUrl(searchResponseData.getSource().getCropImageSigned());
            pictureBean.setCropImageUrl(searchResponseData.getSource().getCropImage());
            pictureBeanList.add(pictureBean);
        }

        ResultVo resultVo = trackService.addTrack(trackBean, pictureBeanList);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    /**
     *覆盖原来重名的轨迹
     * @param jsonObject
     * @return
     */
    @RequestMapping("coveraddtrack")
    public ResponseEntity<ResultVo> coveraddTrack(@RequestBody JSONObject jsonObject){
        TrackBean trackBean = JSON.parseObject(jsonObject.get("track").toString(), new TypeReference<TrackBean>() {
        });
        ArrayList<SearchResponseData> searchResponseDataList = JSON.parseObject(jsonObject.get("pictureList").toString(), new TypeReference<ArrayList<SearchResponseData>>() {
        });
        ArrayList<PictureBean> pictureBeanList = new ArrayList<>();
        PictureBean pictureBean = new PictureBean();
        for (SearchResponseData searchResponseData:searchResponseDataList) {
            pictureBean.setScore(searchResponseData.getScore().toString());
            pictureBean.setDeviceId(searchResponseData.getSource().getCameraId());
            pictureBean.setPictureId(searchResponseData.getId());
            pictureBean.setPictureTime(searchResponseData.getSource().getTimestamp());
            pictureBean.setOriImageSignedUrl(searchResponseData.getSource().getOriImageSigned());
            pictureBean.setOrigImageUrl(searchResponseData.getSource().getOrigImage());
            pictureBean.setCropImageSignedUrl(searchResponseData.getSource().getCropImageSigned());
            pictureBean.setCropImageUrl(searchResponseData.getSource().getCropImage());
            pictureBeanList.add(pictureBean);
        }
        ResultVo resultVo = null;
        try {
            resultVo = trackService.coveraddTrack(trackBean, pictureBeanList);

        } catch (Exception ex) {
            resultVo = ResultVo.failure(BizExceptionEnum.COLLECTION_COVERTRACK.getCode(),BizExceptionEnum.COLLECTION_COVERTRACK.getMessage());
        }
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);

    }

    /**
     * 删除轨迹
     * @param trackBean id
     * @return
     */
    @RequestMapping("removetrack")
    public ResponseEntity<ResultVo> removeTrack(@RequestBody TrackBean trackBean){
        ResultVo resultVo = trackService.removeTrack(trackBean);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    /**
     * 暂无用，启用需修改
     * 修改该轨迹下的图片
     * @param jsonObject
     * @return
     */
    @RequestMapping("updatetrack")
    public ResponseEntity<ResultVo> updateTrack(@RequestBody JSONObject jsonObject){
        TrackBean trackBean = JSON.parseObject(jsonObject.get("Track").toString(), new TypeReference<TrackBean>() {
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
    @RequestMapping("updatetrackname")
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
    
    /**
     * 删除轨迹下图片
     * @param pictureBean
     * @return
     */
    @PostMapping("removetrackpic")
    public ResponseEntity<ResultVo> removeTrackPic(@RequestBody PictureBean pictureBean){
    	ResultVo resultVo = trackService.removeTrackPic(pictureBean);
    	return ResponseUtil.handle(HttpStatus.OK, resultVo);
    }
}
