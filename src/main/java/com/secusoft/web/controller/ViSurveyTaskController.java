package com.secusoft.web.controller;

import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.ViSurveyTaskRequest;
import com.secusoft.web.model.ViSurveyTaskVo;
import com.secusoft.web.service.ViSurveyTaskService;
import io.swagger.annotations.Api;
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
@Api(value="CarSurvey-Controller" , description="布控相关接口")
public class ViSurveyTaskController {

    @Autowired
    ViSurveyTaskService viSurveyTaskService;

    @PostMapping("/insertvisurveytask")
    @ApiOperation("新增布控任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "surveyName", value = "布控任务名称", required = true , dataType="String", paramType="query"),
            @ApiImplicitParam(name = "beginTime", value = "布控开始时间", required = true, dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "布控结束时间", required = true, dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "surveyType", value = "布控类型 1-人员 2-车辆 3-事件 4-物品", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "areaType", value = "区域或框选择 1-区域选择 2-不规则圈选 3-不规则框选", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "surveyDevice", value = "设备，以逗号间隔", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "surveyRepo", value = "关联布控库，以逗号间隔", required = true, dataType = "String", paramType = "query")
    })
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
    @ApiOperation("更新布控任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "布控编号", required = true , dataType="int", paramType="query"),
            @ApiImplicitParam(name = "surveyName", value = "布控任务名称", required = false, dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "布控开始时间", required = false, dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "布控结束时间", required = false, dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "surveyDevice", value = "设备，以逗号间隔", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "surveyRepo", value = "关联布控库，以逗号间隔", required = false, dataType = "String", paramType = "query")
    })
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
    @ApiOperation("开始布控任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "布控编号", required = true , dataType="int", paramType="query"),
    })
    public ResponseEntity<ResultVo> startViSurveyTask(@RequestBody ViSurveyTaskRequest viSurveyTaskRequest) {
        return new ResponseEntity<ResultVo>(viSurveyTaskService.startViSurveyTask(viSurveyTaskRequest), HttpStatus.OK);
    }

    @PostMapping("/stopvisurveytask")
    @ApiOperation("停止布控任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "布控编号", required = true , dataType="int", paramType="query"),
    })
    public ResponseEntity<ResultVo> stopViSurveyTask(@RequestBody ViSurveyTaskRequest viSurveyTaskRequest) {
        return new ResponseEntity<ResultVo>(viSurveyTaskService.stopViSurveyTask(viSurveyTaskRequest), HttpStatus.OK);
    }
}
