package com.secusoft.web.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.secusoft.web.model.ViSurveyTaskBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ViSurveyTaskMapper extends BaseMapper<ViSurveyTaskBean> {

    /**
     * 添加一条布控任务
     */
    void insertViSurveyTask(ViSurveyTaskBean viSurveyTaskBean);


    /**
     * 更新一条布控任务布控状态
     */
    void updateViSurveyTask(ViSurveyTaskBean viSurveyTaskBean);


    /**
     * 删除一条布控任务
     */
    void delViSurveyTask(@Param("id") Integer id);

    /**
     * 获取所有布控任务
     * @return
     */
    List<ViSurveyTaskBean> getAllViSurveyTask();
}
