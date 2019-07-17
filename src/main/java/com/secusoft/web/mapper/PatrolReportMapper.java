package com.secusoft.web.mapper;

import com.secusoft.web.model.PatrolReportBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 巡逻报告Mapper
 * @author Wangzezhou
 * @company 视在数科
 * @date 2019年7月1日
 */
@Mapper
public interface PatrolReportMapper {
    //查询巡逻任务报告
    PatrolReportBean selectPatrolReport(@Param("taskId") String taskId);
    //修改巡逻任务报告
    int updatePatrolTaskReport(PatrolReportBean patrolReportBean);
    //添加巡逻任务报告
    int insertPatrolTaskReport(PatrolReportBean patrolReportBean);
}
