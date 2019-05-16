package com.secusoft.web.controller;

import com.alibaba.fastjson.JSON;
import com.secusoft.web.core.base.controller.BaseController;
import com.secusoft.web.tusouapi.TuSouClient;
import org.springframework.web.bind.annotation.*;



@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class TestController extends BaseController {


    @RequestMapping("/test")
    public Object test(){

        return SUCCESS_TIP;
    }

}
