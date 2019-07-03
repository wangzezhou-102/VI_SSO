package com.secusoft.web.mapper;

import com.secusoft.web.tusouapi.model.RecordOfflineTaskBean;
import java.util.List;

/**
 * @author zzwang
 * @since 2019/6/24 13:11
 */
public interface RecordOfflineTaskMapper {
    /**
     * 添加离线分析任务
     */
    void addRecordOfflineTask(RecordOfflineTaskBean recordOfflineTaskBean);
    /**
     * 查询不同执行状态的任务
     */
    List<RecordOfflineTaskBean> getRecordOfflineTaskByEnable(RecordOfflineTaskBean recordOfflineTaskBean);
    /**
     * 查询任务的详细信息
     */
    RecordOfflineTaskBean getRecordOfflineTaskByTaskId(RecordOfflineTaskBean recordOfflineTaskBean);
    /**
     * 修改一条离线分析任务的状态
     */
    void updateRecordOfflineTaskEnableByTaskId(RecordOfflineTaskBean recordOfflineTaskBean);
    /**
     * 修改一条离线分析任务的进度
     */
    void updateRecordOfflineTaskProgressByTaskId(RecordOfflineTaskBean recordOfflineTaskBean);

}
