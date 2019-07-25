package com.secusoft.web.service;

import com.alibaba.fastjson.JSONObject;
import com.secusoft.web.model.ResultVo;
import org.jose4j.lang.JoseException;

import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author wangzezhou
 * @date 2019-07-19
 */
public interface SSOService {
    //解析id_token,获取信息
    ResultVo SSO(JSONObject jsonObject, HttpSession session) throws JoseException,IOException;
    //判断用户登录
    ResultVo getUserAccessToken(JSONObject jsonObject);
   /* //获取TIP 访问令牌
    ResultVo getTipAccessToken(JSONObject jsonObject);*/
}
