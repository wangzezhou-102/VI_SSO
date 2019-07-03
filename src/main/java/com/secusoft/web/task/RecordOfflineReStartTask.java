package com.secusoft.web.task;

import com.secusoft.web.tusouapi.service.RecordOfflineTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Configurable
//@EnableScheduling
public class RecordOfflineReStartTask {
    private static Logger logger = LoggerFactory.getLogger(RecordOfflineReStartTask.class);

    @Autowired
    RecordOfflineTaskService recordOfflineTaskService;
    @Value("${vi.service.testurl}")
    String testurl;
    /*
    * 离线任务重新下发
    * */
    //@Scheduled(cron="0 0/1 * * * ?")
    public void offlineTaskReStart(){
       logger.info("离线任务重新下发");
        recordOfflineTaskService.addRecordOfflineTaskReStart();
        recordOfflineTaskService.getRecordOfflineTaskProgressReStart();
        recordOfflineTaskService.enableRecordOfflineTaskReStart();
        logger.info("离线任务下发结束");
    }


}