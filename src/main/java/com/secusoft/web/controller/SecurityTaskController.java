package com.secusoft.web.controller;

import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.SecurityTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 安保护航接口
 * @author huanghao
 * @date 2019-07-02
 */
@RestController
@CrossOrigin(value = "*", maxAge = 3600)
public class SecurityTaskController {
    @Autowired
    SecurityTaskService securityTaskService;

    /**
     * 安保任务类型与地点关联接口
     * @return
     */
    @PostMapping("/securitytasktype")
    public  ResponseEntity<ResultVo> securityTaskType(){
        ResultVo resultVo = securityTaskService.getSecurityTaskType();
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    /**
     * 安保任务类型与地点关联接口
     * @return
     */
    @PostMapping("/securitytypeplace")
    public  ResponseEntity<ResultVo> securityTypeplace(){
        ResultVo resultVo = securityTaskService.getSecurityTypePlace();
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }


    /**
     * 安保护航目标库接口
     * @return
     */
    @PostMapping("/listrepo")
    public ResponseEntity<ResultVo> repoList(){
        ResultVo resultVo = securityTaskService.getAllViRepo();
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }
}
