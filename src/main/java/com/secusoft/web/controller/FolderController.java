package com.secusoft.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.secusoft.web.model.FolderBean;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  收藏文件夹相关接口
 *  @author huanghao
 */
@RestController
@CrossOrigin(value = "*", maxAge = 3600)
public class FolderController {
    @Autowired
    FolderService folderService;

    /**
     * 增加文件夹
     * @param folderBean folderName
     * @return
     */
    @RequestMapping("/addFolder")
    public ResponseEntity<ResultVo> addFolder(@RequestBody FolderBean folderBean){
        ResultVo resultVo = folderService.addFolder(folderBean);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    /**
     * 删除文件夹
     * @param folderBean id
     * @return
     */
    @RequestMapping("/removeFolder")
    public ResponseEntity<ResultVo> removeFolder(@RequestBody FolderBean folderBean){
        ResultVo resultVo = folderService.removeFolder(folderBean.getId());
        return  new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    /**
     * 修改文件夹状态
     * @param folderBean id
     * @return
     */
    @RequestMapping("setFolderStatus")
    public ResponseEntity<ResultVo> setFolderStatus(@RequestBody FolderBean folderBean){
        ResultVo resultVo = folderService.setFolderStatus(folderBean);
        return  new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    /**
     * 修改文件夹名称
     * @param folderBean id folderName
     * @return
     */
    @RequestMapping("setFolderName")
    public ResponseEntity<ResultVo> setFolderName(@RequestBody FolderBean folderBean){
        ResultVo resultVo = folderService.setFolderName(folderBean);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    /**
     * 根据状态展示相应文件夹
     * @param jsonObject status
     * @return
     */
    @RequestMapping("getFolderByStatus")
    public ResponseEntity<ResultVo> getFolder(@RequestBody JSONObject jsonObject){
        Integer i = Integer.parseInt(jsonObject.get("status").toString());
        ResultVo resultVo = folderService.getFolderByStatus(i);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    @RequestMapping("getFolderByName")
    public ResponseEntity<ResultVo> getFolderByName(@RequestBody JSONObject jsonObject){
        String name = String.valueOf(jsonObject.get("name").toString());
        System.out.println(name);
        ResultVo resultVo = folderService.getFolderByName(name);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    /**
     * 展示所有的文件夹
     * @return
     */
    @RequestMapping("readAllFolder")
    public ResponseEntity<ResultVo> readAllFolder(){
        ResultVo resultVo = folderService.getAllFolder();
        return new ResponseEntity<ResultVo>(resultVo,HttpStatus.OK);
    }

    /**
     * 展示文件夹详情
     * @return
     */
   @RequestMapping("readFolder")
    public JSONObject readFolder(@RequestBody FolderBean folderBean){
       ResultVo resultVo = folderService.getFolder(folderBean.getId());
       ResponseEntity<ResultVo> resultVoResponseEntity = new ResponseEntity<>(resultVo, HttpStatus.OK);
       String folder = JSON.toJSONString(resultVoResponseEntity, SerializerFeature.DisableCircularReferenceDetect);
       JSONObject object = JSON.parseObject(folder);
       return object;
   }
}
