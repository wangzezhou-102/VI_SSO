package com.secusoft.web.controller;

import com.secusoft.web.core.base.controller.BaseController;
import com.secusoft.web.mapper.ViGazhkSjzyzhQgpqryMapper;
import com.secusoft.web.model.gazhk.ViGazhkSjzyzhQgpqryBean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class TestController extends BaseController {

    @Resource
    ViGazhkSjzyzhQgpqryMapper viGazhkSjzyzhQgpqryMapper;

    @RequestMapping("/test")
    public Object test(){

        ViGazhkSjzyzhQgpqryBean viGazhkSjzyzhQgpqryBean=new ViGazhkSjzyzhQgpqryBean();
        viGazhkSjzyzhQgpqryBean.setJg("浙江");
        viGazhkSjzyzhQgpqryBean.setSfzh("330132132131");
        viGazhkSjzyzhQgpqryBean.setXb("男");
        viGazhkSjzyzhQgpqryBean.setXm("王五");
        viGazhkSjzyzhQgpqryBean.setObjectId("1");
        viGazhkSjzyzhQgpqryBean.setXh(1);
        viGazhkSjzyzhQgpqryMapper.addViGazhkSjzyzhQgpqry(viGazhkSjzyzhQgpqryBean);
        return null;
    }

}
