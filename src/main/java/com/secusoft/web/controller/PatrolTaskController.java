package com.secusoft.web.controller;

import com.secusoft.web.core.common.Constants;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.core.exception.BussinessException;
import com.secusoft.web.core.util.ResponseUtil;
import com.secusoft.web.model.PatrolTaskBean;
import com.secusoft.web.model.PatrolTaskRequest;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.PatrolTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 巡逻任务
 * @author Wangzezhou
 * @company 视在数科
 * @date 2019年7月3日
 */
@RestController
@CrossOrigin(value = "*",maxAge = 3600)
public class PatrolTaskController {
    @Autowired
    private PatrolTaskService patrolTaskService;

    @PostMapping("/addpatroltask")
    public ResponseEntity addPatrolTask(@RequestBody PatrolTaskRequest patrolTaskRequest){//任务名称 patrolName 创建者id create_id create_id对应的组织code'
        if(!patrolTaskRequest.validateTaskAll()){
            throw new BussinessException(BizExceptionEnum.PARAM_NULL);
        }
        if(!patrolTaskRequest.validateOrgCode()){
            throw new BussinessException(BizExceptionEnum.PARAM_NULL);
        }
        ResultVo resultVo = patrolTaskService.insertPatrolTak(patrolTaskRequest);
        return ResponseUtil.handle(Constants.OK, resultVo);
    }

    @PostMapping("/deletepatroltask")
    public ResponseEntity deletePatrolTask(@RequestBody PatrolTaskBean patrolTaskBean){
        if(!patrolTaskBean.validateTaskId()){
            throw new BussinessException(BizExceptionEnum.PARAM_NULL);
        }
        if(!patrolTaskBean.validateStatus()){
            throw new BussinessException(BizExceptionEnum.PARAM_NULL);
        }
        ResultVo resultVo = patrolTaskService.deletePatrolTask(patrolTaskBean);
        return ResponseUtil.handle(Constants.OK, resultVo);
    }

    @PostMapping("/updatepatroltask")
    public ResponseEntity updatePatrolTask(@RequestBody PatrolTaskRequest patrolTaskRequest){
            if(!patrolTaskRequest.validateTaskAll()){
                throw new BussinessException(BizExceptionEnum.PARAM_NULL);
            }
            if(!patrolTaskRequest.validateTaskIdAndEnable()){
                throw new BussinessException(BizExceptionEnum.PARAM_NULL);
            }
            if(!patrolTaskRequest.validateStatus()){
                throw new BussinessException(BizExceptionEnum.PARAM_NULL);
            }
            if(!patrolTaskRequest.validateTimeTemplate()){
                throw new BussinessException(BizExceptionEnum.PARAM_NULL);
            }
        ResultVo resultVo = patrolTaskService.updatePatrolTask(patrolTaskRequest);
        return ResponseUtil.handle(Constants.OK, resultVo);
    }

    @PostMapping("/selectpatroltask")
    public ResponseEntity selectPatrolTask(@RequestBody PatrolTaskBean patrolTaskBean){
        if(!patrolTaskBean.validateTaskId()){
            throw new BussinessException(BizExceptionEnum.PARAM_NULL);
        }
        ResultVo resultVo = patrolTaskService.selectPatrolTask(patrolTaskBean);
        return ResponseUtil.handle(Constants.OK, resultVo);
    }

    @PostMapping("/selectpatroltaskall")
    public ResponseEntity selectPatrolTaskAll(){
        ResultVo resultVo = patrolTaskService.selectPatrolTaskAll();
        return ResponseUtil.handle(Constants.OK, resultVo);
    }
    //终止任务
    @PostMapping("/stoppatroltask")
    public ResponseEntity stopPatrolTask(@RequestBody PatrolTaskBean patrolTaskBean){
        if(!patrolTaskBean.validateStatus()) {
            throw new BussinessException(BizExceptionEnum.PARAM_NULL);
        }
        if(!patrolTaskBean.validateTaskId()){
            throw new BussinessException(BizExceptionEnum.PARAM_NULL);
        }
        ResultVo resultVo = patrolTaskService.stopPatrolTask(patrolTaskBean);
        return ResponseUtil.handle(Constants.OK, resultVo);
    }
    //开启任务（对应终止任务）
    @PostMapping("/startpatroltask")
    public ResponseEntity startpatroltask(@RequestBody PatrolTaskBean patrolTaskBean){
        if(!patrolTaskBean.validateTaskId()){
            throw new BussinessException(BizExceptionEnum.PARAM_NULL);
        }
        ResultVo resultVo = patrolTaskService.startPatrolTask(patrolTaskBean);
        return ResponseUtil.handle(Constants.OK, resultVo);
    }
    //立即开启任务
    @PostMapping("/runningpatroltask")
    public ResponseEntity runningpatroltask(@RequestBody PatrolTaskBean patrolTaskBean){
        ResultVo resultVo = patrolTaskService.runningPatrolTask(patrolTaskBean);
        return ResponseUtil.handle(Constants.OK, resultVo
        );
    }

}
