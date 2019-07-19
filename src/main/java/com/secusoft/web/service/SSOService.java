package com.secusoft.web.service;

import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.SSORequest;

import java.io.IOException;

/**
 * @author wangzezhou
 * @date 2019-07-19
 */
public interface SSOService {
    //用户登录
    ResultVo SSO(SSORequest ssoRequest) throws IOException;

}
