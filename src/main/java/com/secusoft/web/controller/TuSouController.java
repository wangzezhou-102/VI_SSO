package com.secusoft.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.shipinapi.service.CameraService;
import com.secusoft.web.tusouapi.TuSouClient;
import com.secusoft.web.tusouapi.service.TuSouSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(value = "*", maxAge = 3600)
@RestController
public class TuSouController {
    @Autowired
    TuSouSearchService tuSouSearchService;
    private static Logger log = LoggerFactory.getLogger(TuSouController.class);


    @RequestMapping("/tusou_search")
    public JSONObject search(@RequestBody Object request){

        String requestStr = JSON.toJSONString(request);
        String responseStr = TuSouClient.getClientConnectionPool().fetchByPostMethod(TuSouClient.Path_SEARCH, requestStr);

        log.info(">> "+requestStr);

        return JSON.parseObject(responseStr);
    }

    /**
     * 排序后的图搜结果
     * @return
     */
    @RequestMapping("susou_search_sort")
    public JSONObject sortSearch(@RequestBody JSONObject request){
        ResultVo resultVo = tuSouSearchService.sortsearch(request);
        ResponseEntity<ResultVo> resultVoResponseEntity = new ResponseEntity<>(resultVo, HttpStatus.OK);
        String responStr = JSON.toJSONString(resultVoResponseEntity, SerializerFeature.DisableCircularReferenceDetect);
        JSONObject object = JSON.parseObject(responStr );
        return object;
    }



}
