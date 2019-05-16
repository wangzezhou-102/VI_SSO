package com.secusoft.web.controller;

import com.alibaba.fastjson.JSON;
import com.secusoft.web.tusouapi.TuSouClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@RequestMapping("/tusou")
public class TuSouSearchController {

    private static Logger log = LoggerFactory.getLogger(TuSouSearchController.class);

    @RequestMapping("/search")
    public Object search(@RequestBody Object request){

        String requestStr = JSON.toJSONString(request);
        String responseStr = TuSouClient.getClientConnectionPool().fetchByPostMethod(TuSouClient.Path_SEARCH, requestStr);

        log.info(">> "+requestStr);

        return JSON.parseObject(responseStr);
    }
}
