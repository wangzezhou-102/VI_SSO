package com.secusoft.web.controller;

import com.secusoft.web.core.base.controller.BaseController;
import com.secusoft.web.persistence.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class TestController extends BaseController {


    @Autowired
    UserMapper userMapper;

    @RequestMapping("/test")
    public Object test(){


        return userMapper.selectList(null);
    }


}
