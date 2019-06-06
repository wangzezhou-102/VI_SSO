package com.secusoft.web;

import com.secusoft.web.serviceapi.ServiceClient;
import com.secusoft.web.shipinapi.ShiPinClient;
import com.secusoft.web.tusouapi.TuSouClient;
import com.secusoft.web.utils.OdpsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.*;
import java.util.Properties;

/**
 * SpringBoot方式启动类
 *
 *
 * @Date 2017/5/21 12:06
 */
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
                TuSouClient.tuSouEndpoint =  properties.getProperty("tusou.api.addr");
                TuSouClient.tuSouRequestId= properties.getProperty("bkrepo.requestId");
                TuSouClient.tuSouBkid= properties.getProperty("bkrepo.bkid");
                TuSouClient.tuSouBkdesc= properties.getProperty("bkrepo.meta.bkdesc");
                TuSouClient.tuSouBkname= properties.getProperty("bkrepo.meta.bkname");
                TuSouClient.tuSouAlgorithmName= properties.getProperty("bkrepo.meta.algorithmName");
                TuSouClient.tuSouAlgorithmVersion= properties.getProperty("bkrepo.meta.algorithmVersion");
                TuSouClient.tuSouAlgorithmType= properties.getProperty("bkrepo.meta.algorithmType");
                TuSouClient.tuSouOssEndpoint= properties.getProperty("bkrepo.meta.ossInfo.endpoint");
                TuSouClient.tuSouOssAccess_id= properties.getProperty("bkrepo.meta.ossInfo.access_id");
                TuSouClient.tuSouOssAccess_key= properties.getProperty("bkrepo.meta.ossInfo.access_key");
                TuSouClient.tuSouOssBucket_name= properties.getProperty("bkrepo.meta.ossInfo.bucket_name");
                ShiPinClient.shiPinEndpoint = properties.getProperty("shipin.api.addr");
                ServiceClient.ServiceEndpoint=properties.getProperty("service.api.addr");
                OdpsUtils.accessId=properties.getProperty("Odps.accessId");
                OdpsUtils.accessKey=properties.getProperty("Odps.accessKey");
                OdpsUtils.endPoint=properties.getProperty("Odps.endPoint");
                OdpsUtils.project=properties.getProperty("Odps.project");
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
