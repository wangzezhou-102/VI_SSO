package com.secusoft.web.tusouapi.service;

import com.secusoft.web.model.ResultVo;
import com.secusoft.web.tusouapi.model.RecordOfflineTaskBean;
import com.secusoft.web.tusouapi.model.RecordOfflineTaskRequest;

/**
 * @author zzwang
 * @since 2019/6/24 10:53
 */
public interface RecordOfflineTaskService {
    /*
    * 创建离线视频分析任务
    * */
    ResultVo addRecordOfflineTask(RecordOfflineTaskRequest recordOfflineTaskRequest);
    /*
    *获取可执行离线视频分析任务
    * */
    ResultVo getRecordOfflineTaskProgress(RecordOfflineTaskBean recordOfflineTaskBean);
    /*
    * 修改离线视频分析任务的状态
    * */
    ResultVo enableRecordOfflineTask(RecordOfflineTaskBean recordOfflineTaskBean);
    /*
     * 创建离线视频分析任务
     * */
    void addRecordOfflineTaskReStart();
    /*
     *获取可执行离线视频分析任务
     * */
    void getRecordOfflineTaskProgressReStart();
    /*
     * 修改离线视频分析任务的状态
     * */
    void enableRecordOfflineTaskReStart();
}
