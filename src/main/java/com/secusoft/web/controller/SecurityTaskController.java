package com.secusoft.web.controller;

import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.SecurityTaskTypeRepoBean;
import com.secusoft.web.service.SecurityTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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
     * 安保任务类型与地点关联接口（嵌套展示）
     * @return
     */
    @PostMapping("/securitytasktype")
    public  ResponseEntity<ResultVo> securityTaskType(){
        ResultVo resultVo = securityTaskService.getSecurityTaskType();
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    /**
     * 安保任务类型与地点关联接口（分层展示）
     * @return
     */
    @PostMapping("/securitytypeplace")
    public  ResponseEntity<ResultVo> securityTypeplace(){
        ResultVo resultVo = securityTaskService.getSecurityTypePlace();
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }


    /**
     * 安保护航目标库下拉列表接口
     * @return
     */
    @PostMapping("/listrepo")
    public ResponseEntity<ResultVo> repoList(){
        ResultVo resultVo = securityTaskService.getAllViRepo();
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    /**
     * 安保护航类型目标库预设查询接口
     * @return
     */
    @PostMapping("/readRepo")
    public ResponseEntity<ResultVo> readRepo(){
        ResultVo resultVo = securityTaskService.getSecurityTaskTypeRepo();
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    /**
     * 安保护航类型目标库预设修改接口
     * @return
     */
    @PostMapping("/setRepo")
    public ResponseEntity<ResultVo> updateRepo(@RequestBody List<SecurityTaskTypeRepoBean> securityTaskTypeRepoBeans){
        ResultVo resultVo = securityTaskService.setSecurityTaskTypeRepo(securityTaskTypeRepoBeans);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

}
