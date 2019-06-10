package com.secusoft.web.controller;

import com.secusoft.web.service.ViRepoService;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.ViRepoBean;
import com.secusoft.web.model.ViRepoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 布控库创建（包含自定义库和基础库）
 * @author chjiang
 * @since 2019/6/6 14:10
 */
@RestController
public class ViRepoController {

    @Autowired
    ViRepoService viRepoService;

    @RequestMapping("/insertvirepo")
    public ResponseEntity<ResultVo> insertViRepo(@RequestBody ViRepoBean viRepoBean){
        ResultVo resultVo = viRepoService.insertViRepo(viRepoBean);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    @RequestMapping("/updatevirepo")
    public ResponseEntity<ResultVo> updateViRepo(@RequestBody ViRepoBean viRepoBean){
        ResultVo resultVo = viRepoService.updateViRepo(viRepoBean);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    @RequestMapping("/delvirepo")
    public ResponseEntity<ResultVo> delViRepo(@RequestBody ViRepoBean viRepoBean){
        ResultVo resultVo = viRepoService.delViRepo(viRepoBean.getId());
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    @RequestMapping("/listvirepo")
    public ResponseEntity<ResultVo> listViRepo(@RequestBody ViRepoVo viRepoVo){
        return  new ResponseEntity<ResultVo>(viRepoService.getAllViRepo(viRepoVo), HttpStatus.OK);
    }
}
