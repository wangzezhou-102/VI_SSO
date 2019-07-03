package com.secusoft.web.controller;

import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.ViPsurveyAlarmDetailBean;
import com.secusoft.web.model.ViPsurveyAlarmDetailRequest;
import com.secusoft.web.service.ViPsurveyAlarmService;
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
public class ViPsurveyAlarmController {

    @Autowired
    ViPsurveyAlarmService viPsurveyAlarmService;


    /**
     * 是否关注该告警
     * @param viPsurveyAlarmDetailBean
     * @return
     */
    @RequestMapping("/focusalarmdetail")
    public ResponseEntity<ResultVo> updateFocusAlarmDetail(@RequestBody ViPsurveyAlarmDetailBean viPsurveyAlarmDetailBean){
        ResultVo resultVo = viPsurveyAlarmService.updateFocusAlarmDetail(viPsurveyAlarmDetailBean);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    @RequestMapping("/gethistortyalarmdetail")
    public ResponseEntity<ResultVo> getHistortyAlarmDetail(@RequestBody ViPsurveyAlarmDetailRequest viPsurveyAlarmDetailRequest){
        ResultVo resultVo = viPsurveyAlarmService.getHistortyAlarmDetail(viPsurveyAlarmDetailRequest);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }
}
