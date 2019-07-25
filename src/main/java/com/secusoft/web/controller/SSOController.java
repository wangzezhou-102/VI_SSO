package com.secusoft.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.secusoft.web.core.common.Constants;
import com.secusoft.web.core.util.ResponseUtil;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.SSOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;

/**
 * 4A登录接口
 * @author wangzezhou
 * @date 2019-07-19
 */
public class SSOController {
    @Autowired
    private SSOService ssoService;
    //获取id_token(解析用户信息)
    @PostMapping("/jwt/sso")
    public ResponseEntity SSO(@RequestBody JSONObject jsonObject, HttpSession session){
        ResultVo resultVo = null;
        try{
            resultVo = ssoService.SSO(jsonObject,session);
        }catch (Exception e){
            //ResponseUtil.handle(Constants.FAILED, resultVo);
        }
        return ResponseUtil.handle(Constants.OK, resultVo);
    }
    //获取user_access_token
    @PostMapping("/jwt/getuseraccesstoken")
    public ResponseEntity getUserAccessToken(@RequestBody JSONObject jsonObject){
        ResultVo resutlVo = ssoService.getUserAccessToken(jsonObject);
        return ResponseUtil.handle(Constants.OK, resutlVo);
    }
    //
    @PostMapping("/gettipaccesstoken")
    public ResponseEntity getTipAccessToken(@RequestBody JSONObject jsonObject){

        return ResponseUtil.handle(Constants.OK, null);
    }
    //


}
