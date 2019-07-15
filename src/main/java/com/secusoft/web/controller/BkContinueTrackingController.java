package com.secusoft.web.controller;


import com.secusoft.web.model.BkContinueTrackingRequest;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.BkContinueTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 布控追踪-继续追踪
 * @author chjiang
 * @since 2019-7-11 20:45:11
 */
@RestController
public class BkContinueTrackingController {
    @Autowired
    BkContinueTrackingService bkContinueTrackingService;

    @RequestMapping("/bkcontinuetracking")
    public ResponseEntity<ResultVo> bkContinueTracking(@RequestBody BkContinueTrackingRequest bkContinueTrackingRequest){
        return new ResponseEntity<>(bkContinueTrackingService.getBkContinueTracking(bkContinueTrackingRequest), HttpStatus.OK);
    }
}
