package com.secusoft.web.controller;

import com.secusoft.web.core.common.Constants;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.core.exception.BussinessException;
import com.secusoft.web.core.util.ResponseUtil;
import com.secusoft.web.model.PatrolRouteBean;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.PatrolRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 巡逻路线
 * @author Wangzezhou
 * @company 视在数科
 * @date 2019年7月2日
 */
@RestController
@CrossOrigin(value = "*",maxAge = 3600)
public class PatrolRouteController {

    @Autowired
    private PatrolRouteService patrolRouteService;

    @PostMapping("/selectrouteall")
    public ResponseEntity selectPatrolRouteByStatus(@RequestBody PatrolRouteBean patrolRouteBean){//可选状态 删除-3
        ResultVo resultVo = patrolRouteService.selectPatrolRouteByStatus(patrolRouteBean);
        return ResponseUtil.handle(Constants.OK, resultVo);
    }

    @PostMapping("/selectroute")
    public ResponseEntity selectPatrolRouteById(@RequestBody PatrolRouteBean patrolRouteBean){//路线id Id
        ResultVo resultVo = patrolRouteService.selectPatrolRouteById(patrolRouteBean);
        return ResponseUtil.handle(Constants.OK, resultVo);
    }

    @PostMapping("/addroute")
    public ResponseEntity addPatrolRoute(@RequestBody PatrolRouteBean patrolRouteBean){ //路线名称routeName 设备列表 devices 创建者createId 组织code ordCode
        ResultVo resultVo = patrolRouteService.insertPatroRoute(patrolRouteBean);
        return ResponseUtil.handle(Constants.OK, resultVo);
    }

    @PostMapping("/delroute")
    public ResponseEntity delPatrolRoute(@RequestBody PatrolRouteBean patrolRouteBean){//路线id id
        ResultVo resultVo = patrolRouteService.deletePatrolRoute(patrolRouteBean);
        return ResponseUtil.handle(Constants.OK, resultVo);
    }

    @PostMapping("/updateroute")
    public ResponseEntity updatePatrolRoute(@RequestBody PatrolRouteBean patrolRouteBean){//设备列表 devices 路线id Id 路线名称 routeName
        ResultVo resultVo = patrolRouteService.updatePatroRoute(patrolRouteBean);
        return ResponseUtil.handle(Constants.OK, resultVo);
    }
    

}
