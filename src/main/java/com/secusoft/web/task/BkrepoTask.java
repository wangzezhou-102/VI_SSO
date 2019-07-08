package com.secusoft.web.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.secusoft.web.config.BkrepoConfig;
import com.secusoft.web.config.ServiceApiConfig;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.serviceapi.ServiceApiClient;
import com.secusoft.web.serviceapi.model.BaseResponse;
import com.secusoft.web.tusouapi.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 布控库判断
 *
 * @author chjiang
 * @since 2019/6/18 15:44
 */
@Component
public class BkrepoTask implements ApplicationRunner {
    private static Logger log = LoggerFactory.getLogger(BkrepoTask.class);

    @Resource
    BkrepoConfig bkrepoConfig;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        log.info("开始布控库创建");
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setRequestId(bkrepoConfig.getRequestId());
        BKRepoDataBkIdRequest bkRepoDataBkIdRequest = new BKRepoDataBkIdRequest();
        bkRepoDataBkIdRequest.setBkid(bkrepoConfig.getBkid());
        baseRequest.setData(bkRepoDataBkIdRequest);
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
//        BaseResponse baseResponse=new BaseResponse();
//        baseResponse.setCode("SUCCESS");
//        baseResponse.setMessage("");
        if (baseResponse == null || baseResponse.getData() == null || baseResponse.getData().equals("")) {
            log.info("请求失败或data为空");
            return;
        }
        String code = baseResponse.getCode();
        JSONArray data = (JSONArray) baseResponse.getData();
        String message = baseResponse.getMessage();

        boolean bkCreateResult = false;
        for (int i = 0; i < data.size(); i++) {
            JSONObject object = data.getJSONObject(i);
            String bkId = object.getString("bkid");
            if (bkrepoConfig.getBkid().equals(bkId)) {
                bkCreateResult = false;
            }
        }
        if (String.valueOf(BizExceptionEnum.OK.getCode()).equals(code) && bkCreateResult) {
            //头部参数
            BaseRequest<BKRepoCreateRequest> bkRepoCreateRequestBaseRequest = new BaseRequest<>();
            bkRepoCreateRequestBaseRequest.setRequestId(bkrepoConfig.getRequestId());
            //请求data参数
            BKRepoCreateRequest bkRepoCreateRequest = new BKRepoCreateRequest();
            bkRepoCreateRequest.setBkid(bkrepoConfig.getBkid());
            //meta参数
            BKRepoMeta bkRepoMeta = new BKRepoMeta();
            bkRepoMeta.setBkdesc(bkrepoConfig.getMeta().getBkdesc());
            bkRepoMeta.setBkname(bkrepoConfig.getMeta().getBkname());
            bkRepoMeta.setAlgorithmName(bkrepoConfig.getMeta().getAlgorithmName());
            bkRepoMeta.setAlgorithmVersion(bkrepoConfig.getMeta().getAlgorithmVersion());
            bkRepoMeta.setAlgorithmType(bkrepoConfig.getMeta().getAlgorithmType());
            //ossInfo参数
            OSSInfo ossInfo = new OSSInfo();
            ossInfo.setEndpoint(bkrepoConfig.getMeta().getOssInfo().getEndpoint());
            ossInfo.setAccess_id(bkrepoConfig.getMeta().getOssInfo().getAccess_id());
            ossInfo.setAccess_key(bkrepoConfig.getMeta().getOssInfo().getAccess_key());
            ossInfo.setBucket_name(bkrepoConfig.getMeta().getOssInfo().getBucket_name());
            bkRepoMeta.setOssInfo(ossInfo);
            bkRepoCreateRequest.setMeta(bkRepoMeta);
            bkRepoCreateRequestBaseRequest.setData(bkRepoCreateRequest);

            baseResponse = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBkrepoCreate(), bkRepoCreateRequestBaseRequest);

            //解析json
            code = baseResponse.getCode();
            if (String.valueOf(BizExceptionEnum.OK.getCode()).equals(code)) {
                log.info("布控库创建成功");
            }
        } else {
            log.info("布控库已创建");
        }
    }
}
