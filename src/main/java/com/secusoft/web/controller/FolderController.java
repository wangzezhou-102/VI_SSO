package com.secusoft.web.controller;

import com.alibaba.fastjson.JSONObject;
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


@RestController
@CrossOrigin(value = "*", maxAge = 3600)
public class FolderController {
    @Autowired
    FolderService folderService;

    @RequestMapping("/addFolder")
    public ResponseEntity<ResultVo> addFolder(@RequestBody FolderBean folderBean){
        ResultVo resultVo = folderService.addFolder(folderBean);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    @RequestMapping("/removeFolder")
    public ResponseEntity<ResultVo> removeFolder(@RequestBody FolderBean folderBean){
        ResultVo resultVo = folderService.removeFolder(folderBean.getId());
        return  new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    @RequestMapping("setFolderStatus")
    public ResponseEntity<ResultVo> setFolderStatus(@RequestBody FolderBean folderBean){
        ResultVo resultVo = folderService.setFolderStatus(folderBean);
        return  new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    @RequestMapping("setFolderName")
    public ResponseEntity<ResultVo> setFolderName(@RequestBody FolderBean folderBean){
        ResultVo resultVo = folderService.setFolderName(folderBean);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }


    @RequestMapping("getFolderByStatus")
    public ResponseEntity<ResultVo> getFolder(@RequestBody JSONObject jsonObject){
        Integer i = Integer.parseInt(jsonObject.get("status").toString());
        ResultVo resultVo = folderService.getFolderByStatus(i);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    //展示所有的文件夹  在添加的时候可以选择？
}
