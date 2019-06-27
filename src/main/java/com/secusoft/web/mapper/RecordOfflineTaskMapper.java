package com.secusoft.web.mapper;

import com.secusoft.web.model.RecordOfflineTaskBean;
import java.util.List;

/**
 * @author zzwang
 * @since 2019/6/24 13:11
 */
public interface RecordOfflineTaskMapper {
    /**
     * 添加离线分析任务
     */
    void addOfflineTask(RecordOfflineTaskBean recordOfflineTaskBean);
    /**
     * 查询所有执行状态的任务
     */
    List<RecordOfflineTaskBean> getOfflineTaskProgress(RecordOfflineTaskBean recordOfflineTaskBean);
    /**
     * 修改一条离线分析任务的状态
     */
    void updateOfflineTaskEnableById(RecordOfflineTaskBean recordOfflineTaskBean);
    /**
     * 修改一条离线分析任务的进度
     */
    void updateOfflineTaskProgressById(RecordOfflineTaskBean recordOfflineTaskBean);
}
