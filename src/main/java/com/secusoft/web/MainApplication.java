package com.secusoft.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.*;
import java.util.Properties;

/**
 * SpringBoot方式启动类
 *
 *
 * @Date 2017/5/21 12:06
 */
@EnableTransactionManagement
@SpringBootApplication
public class MainApplication extends WebMvcConfigurerAdapter{

    protected final static Logger logger = LoggerFactory.getLogger(MainApplication.class);


    public static void main(String[] args) {
        Properties properties = new Properties();
        String configFilePath = System.getProperty("user.dir")+ File.separator+"config"+File.separator+"config.properties";
        File configFile = new File(configFilePath);

        if(configFile.exists()){
            try {
                InputStream in = new BufferedInputStream(new FileInputStream(configFile));
                properties.load(in);
                SpringApplication app = new SpringApplication(MainApplication.class);
                app.setDefaultProperties(properties);
                logger.info("读取config下config.properties配置成功!");
                app.run(args);
            } catch (IOException e) {
                logger.error("读取配置文件出错!");
                e.printStackTrace();
            }
        }else {
            logger.warn("未在config目录下找到config.properties配置文件,使用内置默认配置文件!");
            SpringApplication.run(MainApplication.class, args);
        }

        logger.info("Application is success!");
        System.out.println("");
    }
}
