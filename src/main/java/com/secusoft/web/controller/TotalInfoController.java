package com.secusoft.web.controller;

import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.TotalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 信息统计接口
 * @author huanghao
 * @date 2019-06-19
 */
@RestController
@CrossOrigin(value = "*", maxAge = 3600)
public class TotalInfoController {

    @Autowired
    TotalInfoService totalInfoService;

    /**
     * 信息统计接口详情
     * @return
     */
    @RequestMapping("totalInfo")
    public ResponseEntity<ResultVo> readFolder(){
        ResultVo resultVo = totalInfoService.getTotalInfo();
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }
}
