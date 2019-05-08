package com.secusoft.web.core.util;

import com.secusoft.web.config.properties.AppProperties;

/**
 * 验证码工具类
 */
public class KaptchaUtil {

    /**
     * 获取验证码开关
     *
     *
     * @Date 2017/5/23 22:34
     */
    public static Boolean getKaptchaOnOff() {
        return SpringContextHolder.getBean(AppProperties.class).getKaptchaOpen();
    }
}