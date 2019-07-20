package com.secusoft.web.service;

import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.SSORequest;
import org.jose4j.lang.JoseException;

import java.io.IOException;

/**
 * @author wangzezhou
 * @date 2019-07-19
 */
public interface SSOService {
    //用户登录
    ResultVo SSO(SSORequest ssoRequest) throws JoseException,IOException;

}
