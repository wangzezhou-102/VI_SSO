package com.secusoft.web.controller;

import com.secusoft.web.core.base.controller.BaseController;
import org.springframework.web.bind.annotation.*;



@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class TestController extends BaseController {


    @RequestMapping("/test")
    public Object test(){

        return SUCCESS_TIP;
    }

}
