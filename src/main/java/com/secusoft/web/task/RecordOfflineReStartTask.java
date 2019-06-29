package com.secusoft.web.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.secusoft.web.mapper.RecordOfflineTaskMapper;
import com.secusoft.web.model.RecordOfflineTaskBean;
import com.secusoft.web.utils.MyHttpClientPool;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Configurable
//@EnableScheduling
public class RecordOfflineReStartTask {
    private static Logger log = LoggerFactory.getLogger(RecordOfflineReStartTask.class);
    @Resource
    RecordOfflineTaskMapper recordOfflineTaskMapper;
    @Resource
    MyHttpClientPool myHttpClientPool;
    @Value("${vi.service.testurl}")
    String testurl;
    /*
    * 离线任务重新下发
    * */
    @Scheduled(cron="0 0/1 * * * ?")
    public void offlineTaskReStart(){
        RecordOfflineTaskBean recordOfflineTaskBean = new RecordOfflineTaskBean();
        recordOfflineTaskBean.setEnable("2");
        List<RecordOfflineTaskBean> offlineTaskByEnable = recordOfflineTaskMapper.getOfflineTaskByEnable(recordOfflineTaskBean);

        for(RecordOfflineTaskBean recordOfflineTaskBean1:offlineTaskByEnable){
            if(recordOfflineTaskBean1.getEnable().equals("2")){
                Map<String,String> map = new HashMap<>();
                map.put("taskId",recordOfflineTaskBean1.getTaskId());
                map.put("deviceId",recordOfflineTaskBean1.getDeviceId());
                StringEntity paramsEntity = new StringEntity(JSON.toJSONString(map),"UTF-8");
                String fetchByPostMethod = myHttpClientPool.fetchByPostMethod(testurl+"/Delete", paramsEntity);

                JSONObject jsonObject = JSONObject.parseObject(fetchByPostMethod);

                JSONObject data = jsonObject.getJSONObject("data");

                if(data != null){
                    String errorCode = data.getString("errorCode");
                    if("SUCCESS".equals(errorCode)){
                        log.info("重新下发成功");
                        recordOfflineTaskBean1.setEnable("0");
                        recordOfflineTaskMapper.updateOfflineTaskEnableByTaskId(recordOfflineTaskBean1);
                    }else{
                        log.info("重新下发失败");
                    }
                }
            }
        }
    }

}
