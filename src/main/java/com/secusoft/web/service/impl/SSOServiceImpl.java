package com.secusoft.web.service.impl;

//import com.idsmanager.dingdang.jwt.DingdangUserRetriever;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.SSORequest;
import com.secusoft.web.service.SSOService;

import java.io.IOException;

public class SSOServiceImpl implements SSOService {

    @Override
    public ResultVo SSO(SSORequest ssoRequest) throws IOException {
        String idToken = ssoRequest.getIdToken();
        //有效期
        Integer expiresIn = ssoRequest.getExpiresIn();
        //公钥
        String publicKey = ssoRequest.getPublicKey();
        //解析id_token
//        DingdangUserRetriever retriever = new DingdangUserRetriever(idToken, publicKey);
//        DingdangUserRetriever.User user = retriever.retrieve();
//        String sub = user.getSub();
//        String exp = user.getExp();
//        String iat = user.getIat();
//        if(exp.equals(iat)){//id_token 单次有效
//
//        }else{
//            long expL = Long.parseLong(exp);
//        }

//        System.out.println(sub);
        
        return ResultVo.success();
    }
}
