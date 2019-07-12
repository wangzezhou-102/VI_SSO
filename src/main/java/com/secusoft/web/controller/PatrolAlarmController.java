package com.secusoft.web.controller;

import com.secusoft.web.model.PatrolAlarmDetailBean;
import com.secusoft.web.model.PatrolAlarmDetailRequest;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.PatrolAlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 告警处理
 */
@RestController
public class PatrolAlarmController {
    @Autowired
    PatrolAlarmService patrolAlarmService;
    /**
     * 是否关注该告警
     * @param patrolAlarmDetailBean
     * @return
     */
    @RequestMapping("/focusalarmdetail")
    public ResponseEntity<ResultVo> updateFocusAlarmDetail(@RequestBody PatrolAlarmDetailBean patrolAlarmDetailBean){
        ResultVo resultVo = patrolAlarmService.updateFocusAlarmDetail(patrolAlarmDetailBean);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    /**
     * 历史告警
     * @param patrolAlarmDetailRequest
     * @return
     */
    @RequestMapping("/gethistoryalarmdetail")
    public ResponseEntity<ResultVo> getHistoryAlarmDetail(@RequestBody PatrolAlarmDetailRequest patrolAlarmDetailRequest){
        ResultVo resultVo = patrolAlarmService.getHistortyAlarmDetail(patrolAlarmDetailRequest);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }
}
