package com.secusoft.web.mapper;

import com.secusoft.web.model.ViSurveyTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ViSurveyTaskMapper {

    /**
     * 添加一条布控任务
     */
    void insertViSurveyTask(ViSurveyTask viSurveyTask);


    /**
     * 更新一条布控任务布控状态
     */
    void updateViSurveyTask(ViSurveyTask viSurveyTask);


    /**
     * 删除一条布控任务
     */
    void delViSurveyTask(@Param("id") Integer id);

    /**
     * 获取所有布控任务
     * @return
     */
    List<ViSurveyTask> getAllViSurveyTask();
}
