package com.secusoft.web.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.secusoft.web.model.ViSurveyTaskBean;
import com.secusoft.web.model.ViSurveyTaskRequest;
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
    void delViSurveyTask(ViSurveyTaskRequest viSurveyTaskRequest);

    /**
     * 获取所有布控任务
     * @return
     */
    List<ViSurveyTaskBean> getAllViSurveyTask(ViSurveyTaskRequest viSurveyTaskRequest);

    /**
     * 正在执行的布控任务
     * @return
     */
    Integer getEnabledViSurveyTaskCount();

    /**
     * 获取启停中的任务列表
     * @param viSurveyTaskBean
     * @return
     */
    List<ViSurveyTaskBean> getTaskStartOrStopFailedList(ViSurveyTaskBean viSurveyTaskBean);

    /**
     * 统计当前时间当前账号权限下处于正在执行状态下的布控数量
     * @author hbxing
     * @company 视在数科
     * @date 2019年6月18日
     */
    Integer selectExecutingSurveyTaskNumber(@Param("userIds") List<Integer> userIds);
    /**
     * 获取某些用户下面的布控任务id
     * @author hbxing
     * @company 视在数科
     * @date 2019年6月20日
     */
    List<String> selectIdByUserIds(@Param("userIds") List<Integer> userIds);

    /**
     * 根据布控任务ID获取布控任务
     * @param viSurveyTaskRequest
     * @return
     */
    ViSurveyTaskBean selectBeanByIdOrObjectId(ViSurveyTaskRequest viSurveyTaskRequest);

    /**
     * 获取所有布控任务分页
     * @return
     */
    List<ViSurveyTaskBean> getAllViSurveyTaskByPage(ViSurveyTaskRequest viSurveyTaskRequest);

    /**
     * 通过beanId获取对象
     * @param viSurveyTaskBean
     * @return
     */
    ViSurveyTaskBean getViSurveyTaskById(ViSurveyTaskBean viSurveyTaskBean);
}
