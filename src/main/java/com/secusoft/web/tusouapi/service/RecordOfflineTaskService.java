package com.secusoft.web.tusouapi.service;

import com.secusoft.web.model.RecordOfflineTaskBean;
import com.secusoft.web.model.RecordOfflineTaskRequest;
import com.secusoft.web.model.ResultVo;

/**
 * @author zzwang
 * @since 2019/6/24 10:53
 */
public interface RecordOfflineTaskService {
    /*
    * 创建离线视频分析任务
    * */
    ResultVo addOfflineTask(RecordOfflineTaskRequest recordOfflineTaskRequest);
    /*
    *获取可执行离线视频分析任务
    * */
    ResultVo getOfflineTaskProgress(RecordOfflineTaskBean recordOfflineTaskBean);
    /*
    * 修改离线视频分析任务的状态
    * */
    ResultVo enableOfflineTask(RecordOfflineTaskBean recordOfflineTaskBean);

}
