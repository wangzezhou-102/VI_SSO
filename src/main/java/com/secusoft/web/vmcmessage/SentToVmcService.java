package com.secusoft.web.vmcmessage;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SentToVmcService {

    private static Logger log = LoggerFactory.getLogger(SentToVmcService.class);
    @Value("${vmc_server}")
    private String vmcUrl;
    @Value("${ubrain_web_login_session}")
    private String loginID;
    @Value("${vmc_deskey}")
    private String desKey;
    @Value("${current_monitorid}")
    private int monitorid;



    public void sendChanges(String type, String operator, String id, String comment) {

        JSONObject json = new JSONObject();
        json.put("CommandId", "10131003");
        json.put("LoginId", loginID);
        json.put("Token", System.currentTimeMillis());
        json.put("Monitor", String.valueOf(monitorid));
        json.put("Type", type);
        json.put("Operator", operator);
        json.put("ID", id);
        json.put("Comment", comment);
        String messageJsonStr = json.toJSONString();
        log.info("send:"+messageJsonStr);
        String resultStr = MyHttpClientPool.getClientConnectionPool().sendToVMC(vmcUrl,desKey,messageJsonStr);
        if (resultStr != null) {
            JSONObject jsonObject = JSONObject.parseObject(resultStr);
            String resultCode = String.valueOf(jsonObject.get("ResultCode"));
            log.info("send message result:" + resultStr);

            if (!"0".equals(resultCode)) {
                log.info("sent success,but operation failed");
            }
        } else {
            log.error("Connection to Vmc failed");
        }

    }
}
