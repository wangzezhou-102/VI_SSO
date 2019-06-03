package com.secusoft.web.mapper;

import com.secusoft.web.model.ViSurveyTask;
import org.apache.ibatis.annotations.Param;

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
}
