package com.secusoft.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.secusoft.web.tusouapi.TuSouClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(value = "*", maxAge = 3600)
@RestController
public class TuSouController {

    private static Logger log = LoggerFactory.getLogger(TuSouController.class);


    @RequestMapping("/tusou_search")
    public JSONObject search(@RequestBody Object request){

        String requestStr = JSON.toJSONString(request);
        String responseStr = TuSouClient.getClientConnectionPool().fetchByPostMethod(TuSouClient.Path_SEARCH, requestStr);

        log.info(">> "+requestStr);

        return JSON.parseObject(responseStr);
    }



}
