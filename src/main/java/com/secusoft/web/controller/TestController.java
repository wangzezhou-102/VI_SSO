package com.secusoft.web.controller;

import com.secusoft.web.core.base.controller.BaseController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;


@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class TestController extends BaseController {


    @RequestMapping("/test")
    @ResponseBody
    public Object test(){

        return new HashMap<String,String>().put("hello","test");
    }


    @RequestMapping("/index")
    @ResponseBody
    public Object index(){

        return "INDEX";
    }

}
