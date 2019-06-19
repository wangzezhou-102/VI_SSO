package com.secusoft.web.task;

import com.alibaba.fastjson.JSON;
import com.secusoft.web.config.BkrepoConfig;
import com.secusoft.web.config.ServiceApiConfig;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.serviceapi.ServiceApiClient;
import com.secusoft.web.serviceapi.model.BaseResponse;
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
 * @author chjiang
 * @since 2019/6/18 15:44
 */
@Component
public class BkrepoTask implements ApplicationRunner {

    @Resource
    BkrepoConfig bkrepoConfig;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        BaseRequest baseRequest=new BaseRequest();
        baseRequest.setRequestId(bkrepoConfig.getRequestId());
        BaseResponse baseResponse = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBkrepoMeta(), baseRequest);

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
        baseResponse=new BaseResponse();
        baseResponse.setCode("SUCCESS");
        baseResponse.setMessage("");
        String code=baseResponse.getCode();
        Object data=baseResponse.getData();
        String message=baseResponse.getMessage();
        if(String.valueOf(BizExceptionEnum.OK.getCode()).equals(code)&&("null".equals(data))){
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
            baseResponse = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBkrepoCreate(), bkRepoCreateRequestBaseRequest);

            //解析json
            code=baseResponse.getCode();
            if(BizExceptionEnum.OK.getCode()==Integer.parseInt(code)){
                System.out.println("布控库创建成功");
            }
        }else{
            System.out.println(message);
        }
    }
}
