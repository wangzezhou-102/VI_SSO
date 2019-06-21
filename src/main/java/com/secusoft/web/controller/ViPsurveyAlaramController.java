package com.secusoft.web.controller;

import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.ViPsurveyAlaramBean;
import com.secusoft.web.model.ViPsurveyAlaramDetailBean;
import com.secusoft.web.model.ViPsurveyAlaramDetailRequest;
import com.secusoft.web.service.ViPsurveyAlaramService;
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
public class ViPsurveyAlaramController {

    @Autowired
    ViPsurveyAlaramService viPsurveyAlaramService;


    /**
     * 是否关注该告警
     * @param viPsurveyAlaramDetailBean
     * @return
     */
    @RequestMapping("/updatefocusalaramdetail")
    public ResponseEntity<ResultVo> updateFocusAlaramDetail(@RequestBody ViPsurveyAlaramDetailBean viPsurveyAlaramDetailBean){
        ResultVo resultVo = viPsurveyAlaramService.updateFocusAlaramDetail(viPsurveyAlaramDetailBean);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    @RequestMapping("/gethistortyalaramdetail")
    public ResponseEntity<ResultVo> getHistortyAlaramDetail(@RequestBody ViPsurveyAlaramDetailRequest viPsurveyAlaramDetailRequest){
        ResultVo resultVo = viPsurveyAlaramService.getHistortyAlaramDetail(viPsurveyAlaramDetailRequest);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }
}
