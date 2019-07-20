package com.secusoft.web.controller;

import com.secusoft.web.core.common.Constants;
import com.secusoft.web.core.util.ResponseUtil;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.SSORequest;
import com.secusoft.web.service.SSOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 单点登录接口
 * @author wangzezhou
 * @date 2019-07-19
 */
public class SSOController {
    @Autowired
    private SSOService ssoService;
    //登录
    @PostMapping("/jwt/sso")
    public ResponseEntity SSO(@RequestBody SSORequest ssoRequest){
        ResultVo resultVo = null;
        try{
            resultVo = ssoService.SSO(ssoRequest);
        }catch (Exception e){
            //ResponseUtil.handle(Constants.FAILED, resultVo);
        }

        return ResponseUtil.handle(Constants.OK, resultVo);
    }
}
