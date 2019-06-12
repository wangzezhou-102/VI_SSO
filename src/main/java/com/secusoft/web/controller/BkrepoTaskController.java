package com.secusoft.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.secusoft.web.config.BkrepoConfig;
import com.secusoft.web.config.ServiceApiConfig;
import com.secusoft.web.serviceapi.ServiceClient;
import com.secusoft.web.tusouapi.model.BKRepoCreateRequest;
import com.secusoft.web.tusouapi.model.BKRepoMeta;
import com.secusoft.web.tusouapi.model.BaseRequest;
import com.secusoft.web.tusouapi.model.OSSInfo;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 布控库判断
 */
@Component
public class BkrepoTaskController implements ApplicationRunner {

    @Resource
    BkrepoConfig bkrepoConfig;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        String responseStr = ServiceClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBkrepoMeta(), "");

//        String responseStr="\n" +
//                "\t\"errorCode\": \"SUCCESS\",\n" +
//                "\t\"errorMsg\": \"\",\n" +
//                "\t\"data\": [{\n" +
//                "\t\t\"bkid\": \"nonvehicle6\",\n" +
//                "\t\t\"meta\": {\n" +
//                "\t\t\t\"algorithmType\": \"person\",\n" +
//                "\t\t\t\"algorithmVersion\": \"v1.0.0\",\n" +
//                "\t\t\t\"bkdesc\": \"234\",\n" +
//                "\t\t\t\"algorithmName\": \"person\"\n" +
//                "\t\t}\n" +
//                "\t}]\n" +
//                "}" ;
        responseStr="{\n" +
                "\t\"errorCode\": \"SUCCESS\",\n" +
                "\t\"errorMsg\": \"\",\n" +
                "\t\"data\": \"null\"}" ;
        JSONObject jsonObject= (JSONObject) JSONObject.parse(responseStr);
        String code=jsonObject.getString("code");
        String data=jsonObject.getString("data");
        if("1001010".equals(code)&&(data.isEmpty()||"null".equals(data))){
            //头部参数
            BaseRequest<BKRepoCreateRequest> bkRepoCreateRequestBaseRequest=new BaseRequest<>();
            bkRepoCreateRequestBaseRequest.setRequestId(bkrepoConfig.getRequestId());
            //请求data参数
            BKRepoCreateRequest bkRepoCreateRequest=new BKRepoCreateRequest();
            bkRepoCreateRequest.setBkid(bkrepoConfig.getBkid());
            //meta参数
            BKRepoMeta bkRepoMeta=new BKRepoMeta();
            bkRepoMeta.setBkdesc(bkrepoConfig.getMeta().getBkdesc());
            bkRepoMeta.setBkname(bkrepoConfig.getMeta().getBkname());
            bkRepoMeta.setAlgorithmName(bkrepoConfig.getMeta().getAlgorithmName());
            bkRepoMeta.setAlgorithmVersion(bkrepoConfig.getMeta().getAlgorithmVersion());
            bkRepoMeta.setAlgorithmType(bkrepoConfig.getMeta().getAlgorithmVersion());
            //ossInfo参数
            OSSInfo ossInfo=new OSSInfo();
            ossInfo.setOssEndpoint(bkrepoConfig.getMeta().getOssInfo().getEndpoint());
            ossInfo.setOssAccessKeyId(bkrepoConfig.getMeta().getOssInfo().getAccess_id());
            ossInfo.setOssAccessKeySecret(bkrepoConfig.getMeta().getOssInfo().getAccess_key());
            ossInfo.setOssBucket(bkrepoConfig.getMeta().getOssInfo().getBucket_name());
            bkRepoMeta.setOssInfo(ossInfo);
            bkRepoCreateRequest.setMeta(bkRepoMeta);
            bkRepoCreateRequestBaseRequest.setData(bkRepoCreateRequest);

            String requestStr = JSON.toJSONString(bkRepoCreateRequestBaseRequest);
            String responseBkrepoCreateStr = ServiceClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBkrepoCreate(), requestStr);
            //解析json
            jsonObject= (JSONObject) JSONObject.parse(responseBkrepoCreateStr);
            code=jsonObject.getString("code");
            if("1001010".equals(code)){
                System.out.println("布控库创建成功");
            }
        }else{
            String message=jsonObject.getString("message");
            System.out.println("message");
        }
    }
}
