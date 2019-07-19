package com.secusoft.web.controller;


import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.model.*;
import com.secusoft.web.service.ViRealtimeTraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ViRealtimeTraceController {

    @Autowired
    ViRealtimeTraceService viRealtimeTraceService;

    @PostMapping("/insertvirealtimetrace")
    public ResponseEntity<ResultVo> insertViRealtimeTrace(@RequestBody ViRealtimeTraceRequest viRealtimeTraceRequest) {
        return new ResponseEntity<ResultVo>( viRealtimeTraceService.insertViRealtimeTrace(viRealtimeTraceRequest), HttpStatus.OK);
    }

    @PostMapping("/updatevirealtimetrace")
    public ResponseEntity<ResultVo> updateViRealtimeTrace(@RequestBody ViRealtimeTraceRequest viRealtimeTraceRequest) {
        return new ResponseEntity<ResultVo>(viRealtimeTraceService.updateViRealtimeTrace(viRealtimeTraceRequest), HttpStatus.OK);
    }

    @PostMapping("/delvirealtimetrace")
    public ResponseEntity<ResultVo> delViRealtimeTrace(@RequestBody ViRealtimeTraceRequest viRealtimeTraceRequest) {
        return new ResponseEntity<ResultVo>(viRealtimeTraceService.delViRealtimeTrace(viRealtimeTraceRequest), HttpStatus.OK);
    }

    @PostMapping("/listvirealtimetrace")
    public ResponseEntity<ResultVo> listViSurveyTask(@RequestBody ViRealtimeTraceVo viRealtimeTraceVo) {
        return new ResponseEntity<ResultVo>(viRealtimeTraceService.getAllViRealtimeTrace(viRealtimeTraceVo), HttpStatus.OK);
    }

    @PostMapping("/virealtimetracesearch")
    public ResponseEntity<ResultVo> viRealtimeTraceSearch(@RequestBody ViRealtimeTraceRequest request) {
        return new ResponseEntity<ResultVo>(viRealtimeTraceService.viRealtimeTraceSearch(request), HttpStatus.OK);
    }
}
