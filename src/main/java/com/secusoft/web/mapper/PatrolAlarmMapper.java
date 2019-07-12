package com.secusoft.web.mapper;

import com.secusoft.web.model.PatrolAlarmBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangzezhou
 * @since 2019/7/11 15:35
 */
public interface PatrolAlarmMapper {

    /**
     * 添加巡逻告警
     *
     * @param patrolAlarmBean
     */
    void insertPatrolAlarm(PatrolAlarmBean patrolAlarmBean);

    /**
     * 根据巡逻任务id获得巡逻报警数量
     */
    Integer selectIdNumberBytaskId(@Param("taskIds") List<String> taskIds);

    PatrolAlarmBean getPatrolAlarmByBean(PatrolAlarmBean patrolAlarmBean);
}
