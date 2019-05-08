package com.secusoft.web.config.web;

import javax.annotation.PostConstruct;

import com.secusoft.web.core.shiro.ShiroKit;
import com.secusoft.web.core.util.KaptchaUtil;
import com.secusoft.web.core.util.ToolUtil;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FreemarkerConfig {

    @Autowired
    protected freemarker.template.Configuration configuration;
    @Autowired
    protected org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver resolver;
    @Autowired
    protected org.springframework.web.servlet.view.InternalResourceViewResolver springResolver;

    @PostConstruct
    public void  setSharedVariable(){
        try {
            configuration.setSharedVariable("shiro",new ShiroKit());
            configuration.setSharedVariable("tool",new ToolUtil());
            configuration.setSharedVariable("kaptcha",new KaptchaUtil());
        } catch (TemplateModelException e) {
            e.printStackTrace();
        }
        resolver.setSuffix(".ftl");
        resolver.setCache(false);
        resolver.setRequestContextAttribute("request"); //为模板调用时，调用request对象的变量名</span>
        resolver.setOrder(0);
        resolver.setExposeRequestAttributes(true);
        resolver.setExposeSessionAttributes(true);
    }
}