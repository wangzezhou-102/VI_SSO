package com.secusoft.web.controller;

import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.ViSurveyTaskRequest;
import com.secusoft.web.model.ViSurveyTaskVo;
import com.secusoft.web.service.ViSurveyTaskService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ViSurveyTaskController {

    @Autowired
    ViSurveyTaskService viSurveyTaskService;

    @PostMapping("/insertvisurveytask")
    public ResponseEntity<ResultVo> insertViSurveyTask(@RequestBody ViSurveyTaskRequest viSurveyTaskRequest) {
        ResultVo resultVo = null;
        try {
            resultVo = viSurveyTaskService.insertViSurveyTask(viSurveyTaskRequest);
        } catch (Exception ex) {
            resultVo = ResultVo.failure(BizExceptionEnum.TASK_ADD_FAIL.getCode(), BizExceptionEnum.TASK_ADD_FAIL.getMessage());
        }
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    @PostMapping("/updatevisurveytask")
    public ResponseEntity<ResultVo> updateViSurveyTask(@RequestBody ViSurveyTaskRequest viSurveyTaskRequest) {
        ResultVo resultVo = viSurveyTaskService.updateViSurveyTask(viSurveyTaskRequest);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    @PostMapping("/delvisurveytask")
    @ApiOperation("删除布控任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "布控编号", required = true , dataType="int", paramType="query"),
    })
    public ResponseEntity<ResultVo> delViSurveyTask(@RequestBody ViSurveyTaskRequest viSurveyTaskRequest) {

        ResultVo resultVo = viSurveyTaskService.delViSurveyTask(viSurveyTaskRequest);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    @PostMapping("/listvisurveytask")
    public ResponseEntity<ResultVo> listViSurveyTask(@RequestBody ViSurveyTaskVo viSurveyTaskVo) {
        return new ResponseEntity<ResultVo>(viSurveyTaskService.getAllInformation(viSurveyTaskVo), HttpStatus.OK);
    }

    @PostMapping("/startvisurveytask")
    public ResponseEntity<ResultVo> startViSurveyTask(@RequestBody ViSurveyTaskRequest viSurveyTaskRequest) {
        return new ResponseEntity<ResultVo>(viSurveyTaskService.startViSurveyTask(viSurveyTaskRequest), HttpStatus.OK);
    }

    @PostMapping("/stopvisurveytask")
    public ResponseEntity<ResultVo> stopViSurveyTask(@RequestBody ViSurveyTaskRequest viSurveyTaskRequest) {
        return new ResponseEntity<ResultVo>(viSurveyTaskService.stopViSurveyTask(viSurveyTaskRequest), HttpStatus.OK);
    }
}
