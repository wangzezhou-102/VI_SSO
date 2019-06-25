package com.secusoft.web.core.Interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 拦截器的配置
 */

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private MyInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截规则
        registry.addInterceptor(interceptor).addPathPatterns("/tusou_search_sort","/testsearch")
        .excludePathPatterns("/test/no");
    }
}