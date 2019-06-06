package com.secusoft.web.controller;

import com.secusoft.web.model.Picture;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
public class PictureController {
    @Autowired
    private PictureService pictureService;

    /**
     * 收藏图片
     * @param picture 图片对象
     * @return
     */
    @RequestMapping("/addPicture")
    public ResponseEntity<ResultVo> addPicture(@RequestBody Picture picture){
        ResultVo resultVo = pictureService.addPicture(picture);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    /**
     * 取消收藏图片
     * @param picture 图片ID
     * @return
     */
    @RequestMapping("/removePicture")
    public ResponseEntity<ResultVo> removeFolder(@RequestBody Picture picture){
        ResultVo resultVo = pictureService.removePicture(picture.getId());
        return  new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    /**
     * 收藏图片详情
     * @param picture
     * @return
     */
    @RequestMapping("/getPictureById")
     public ResponseEntity<ResultVo> getFolder(@RequestBody Picture picture){
        ResultVo resultVo = pictureService.getPictureById(picture.getId());
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
   }

}
