package com.secusoft.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.secusoft.web.mapper.SysOperationLogMapper;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.tusouapi.TuSouClient;
import com.secusoft.web.tusouapi.service.TuSouSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@CrossOrigin(value = "*", maxAge = 3600)
@RestController
public class TuSouController {
    @Autowired
    TuSouSearchService tuSouSearchService;

    @Resource
    SysOperationLogMapper sysOperationLogMapper;
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
    @RequestMapping("/tusou_search_sort")
    public ResponseEntity<ResultVo> sortSearch(@RequestBody Object object){
        JSONObject request =(JSONObject)JSON.toJSON(object);
        ResultVo resultVo = tuSouSearchService.sortsearch(request);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 模拟数据的图搜
     * @param object
     * @return
     */
    @RequestMapping("/testsearch")
    public ResponseEntity<ResultVo> testSearch(@RequestBody Object object){
        JSONObject request =(JSONObject)JSON.toJSON(object);
        ResultVo resultVo = tuSouSearchService.testsearch(request);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 图搜缓存记录接口
     * @return
     */
    @RequestMapping("/cacheSearch")
    public ResponseEntity<ResultVo> cacheSearch(){
        ResultVo resultVo = tuSouSearchService.cacheSearch();
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }









}
