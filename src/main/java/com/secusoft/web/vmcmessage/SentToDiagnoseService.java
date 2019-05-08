package com.secusoft.web.vmcmessage;

import java.nio.charset.Charset;

import com.alibaba.fastjson.JSONObject;

import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SentToDiagnoseService{

    private static Logger log = LoggerFactory.getLogger(SentToDiagnoseService.class);

    @Value("${vmc_vmcdiagnose_server}")
    private String serverUrl;

    private String serverUri = "/api/v1/sceneTask?device=%s&active=%s";     //active = add/delete/update

    public Boolean sendMessage(String device, String playAddr, String sceneUrls, int positionCount, String callbackUrl, int  interval, String active) {
        
        String postUrl = serverUrl+String.format(serverUri, device, active);
        JSONObject json = new JSONObject();
        json.put("device", device);
        json.put("playAddr", playAddr);
        json.put("sceneUrls", sceneUrls);
        json.put("positionCount", positionCount);
        json.put("callbackUrl", callbackUrl);
        json.put("interval", interval);
        String messageJsonStr = json.toJSONString();
        log.info("send:"+messageJsonStr);
        StringEntity stringEntity = new StringEntity(messageJsonStr, Charset.forName("UTF-8"));
        String resultStr = MyHttpClientPool.getClientConnectionPool().fetchByPostMethod(postUrl, stringEntity);
        if (resultStr != null) {
            JSONObject jsonObject = JSONObject.parseObject(resultStr);
            JSONObject resultObject = JSONObject.parseObject(String.valueOf(jsonObject.get("result")));
            String resultCode = String.valueOf(resultObject.get("code"));
            log.info("send message result:" + resultStr);
            if ("0".equals(resultCode)) {
                return true;
            }else{
                log.info("sent success,but operation failed");
            }
        } else {
            log.error("Connection to Vmc failed");
        }
        return false;

    }



}