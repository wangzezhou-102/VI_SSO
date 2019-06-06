package com.secusoft.web.controller;

import com.secusoft.web.core.base.controller.BaseController;
import com.secusoft.web.mapper.PictureMapper;
import com.secusoft.web.model.Picture;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class TestController extends BaseController {


    @RequestMapping("/test")
    public Object test(){

        return SUCCESS_TIP;
    }

}
