package com.secusoft.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.RoundBean;
import com.secusoft.web.service.BoxSelectService;
import com.secusoft.web.model.PointBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;

@CrossOrigin(value = "*", maxAge = 3600)
@RestController
public class BoxSelectController {
    @Autowired
    private  BoxSelectService boxSelectService;

    /**
     * 多边形框选返回设备id
     * @param request 有顺序的各个点位坐标
     * @return
     */
    @RequestMapping("/boxselect")
    public ResponseEntity<ResultVo> boxselect(@RequestBody String request){
        ArrayList<PointBean> pointBeans = JSON.parseObject(request, new TypeReference<ArrayList<PointBean>>() {
        });
        ResultVo resultVo = boxSelectService.isPtInPoly(pointBeans);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    /**
     * 圆形框选返回设备id
     * @param
     * @return
     */
    @RequestMapping("/roundboxselect")
    public ResponseEntity<ResultVo> roundboxselect(@RequestBody RoundBean roundBean){
        ResultVo resultVo = boxSelectService.isPtInPoly2(roundBean.getPointBean(), roundBean.getRadius());
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }
}
