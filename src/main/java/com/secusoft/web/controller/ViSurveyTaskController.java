package com.secusoft.web.controller;

import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.ViSurveyTaskBean;
import com.secusoft.web.model.ViSurveyTaskRequest;
import com.secusoft.web.model.ViSurveyTaskVo;
import com.secusoft.web.service.ViSurveyTaskService;
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

    @RequestMapping("/insertvisurveytask")
    public ResponseEntity<ResultVo> insertViSurveyTask(@RequestBody ViSurveyTaskRequest viSurveyTaskRequest) {
        ResultVo resultVo = null;
        try {
            resultVo = viSurveyTaskService.insertViSurveyTask(viSurveyTaskRequest);
        } catch (Exception ex) {
            resultVo = ResultVo.failure(BizExceptionEnum.TASK_ADD_FAIL.getCode(),
                    BizExceptionEnum.TASK_ADD_FAIL.getMessage());
        }
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    @RequestMapping("/updatevisurveytask")
    public ResponseEntity<ResultVo> updateViSurveyTask(@RequestBody ViSurveyTaskBean viSurveyTaskBean) {
        ResultVo resultVo = viSurveyTaskService.updateViSurveyTask(viSurveyTaskBean);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    @RequestMapping("/delvisurveytask")
    public ResponseEntity<ResultVo> delViSurveyTask(@RequestBody ViSurveyTaskBean viSurveyTaskBean) {

        ResultVo resultVo = viSurveyTaskService.delViSurveyTask(viSurveyTaskBean.getId());
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    @RequestMapping("/listvisurveytask")
    public ResponseEntity<ResultVo> listViSurveyTask(@RequestBody ViSurveyTaskVo viSurveyTaskVo) {
        return new ResponseEntity<ResultVo>(viSurveyTaskService.getAllInformation(viSurveyTaskVo), HttpStatus.OK);
    }
}
