package com.secusoft.web.controller;

import com.secusoft.web.Service.ViSurveyTaskService;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.ViSurveyTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ViSurveyTaskController {

    @Autowired
    ViSurveyTaskService viSurveyTaskService;

    @RequestMapping("/insertViSurveyTask")
    public ResponseEntity<ResultVo> insertViSurveyTask(@RequestBody ViSurveyTask viSurveyTask){
        ResultVo resultVo = viSurveyTaskService.insertViSurveyTask(viSurveyTask);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    @RequestMapping("/updateViSurveyTaskSurveyStatus")
    public ResponseEntity<ResultVo> updateViSurveyTask(@RequestBody ViSurveyTask viSurveyTask){
        ResultVo resultVo = viSurveyTaskService.updateViSurveyTask(viSurveyTask);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    @RequestMapping("/delViSurveyTask")
    public ResponseEntity<ResultVo> delViSurveyTask(@RequestBody ViSurveyTask viSurveyTask){

        System.out.println("要删除的ID："+viSurveyTask.getId());
        ResultVo resultVo = viSurveyTaskService.delViSurveyTask(viSurveyTask.getId());
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }
}
