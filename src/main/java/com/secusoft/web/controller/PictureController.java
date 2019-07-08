package com.secusoft.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.secusoft.web.model.PictureBean;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.TrackBean;
import com.secusoft.web.service.PictureService;
import com.secusoft.web.tusouapi.model.SearchResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 *  收藏图片接口
 *  @author huanghao
 */
@RestController
@CrossOrigin(value = "*", maxAge = 3600)
public class PictureController {
    @Autowired
    private PictureService pictureService;

    /**
     * 收藏图片
     * @param jsonObject 图片对象
     * @return
     */
    @RequestMapping("/addPicture")
    public ResponseEntity<ResultVo> addPicture(@RequestBody JSONObject jsonObject){
        String folderId = jsonObject.get("folderId").toString();
        SearchResponseData searchResponseData = JSON.parseObject(jsonObject.get("picture").toString(), new TypeReference<SearchResponseData>() {
        });
        PictureBean pictureBean = PictureBean.toPictureBean(searchResponseData);
        pictureBean.setFolderId(folderId);
        ResultVo resultVo = pictureService.addPicture(pictureBean);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    /**
     * 取消收藏图片
     * @param pictureBean 图片ID
     * @return
     */
    @RequestMapping("/removePicture")
    public ResponseEntity<ResultVo> removeFolder(@RequestBody PictureBean pictureBean){
        ResultVo resultVo = pictureService.removePicture(pictureBean.getId());
        return  new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    /**
     * 收藏图片详情
     * @param pictureBean
     * @return
     */
    @RequestMapping("/getPictureById")
     public ResponseEntity<ResultVo> getFolder(@RequestBody PictureBean pictureBean){
        ResultVo resultVo = pictureService.getPictureById(pictureBean.getId());
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
   }

}
