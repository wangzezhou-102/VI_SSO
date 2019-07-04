package com.secusoft.web.mapper;

import com.secusoft.web.model.PatrolTaskReportBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 巡逻报告Mapper
 * @author Wangzezhou
 * @company 视在数科
 * @date 2019年7月1日
 */
@Mapper
public interface PatrolTaskReportMapper {
    //查询报告
    PatrolTaskReportBean selectPatrolTaskReport(PatrolTaskReportBean patrolTaskReportBean);
    //查询所有报告
    List<PatrolTaskReportBean> selectPatrolTaskReportAll(PatrolTaskReportBean patrolTaskReportBean);
    //删除报告
    int deletePatrolTaskReport(PatrolTaskReportBean patrolTaskReportBean);
    //增加报告
    int insertPatrolTaskReport(PatrolTaskReportBean patrolTaskReportBean);
    //修改报告
    int updatePatrolTaskReport(PatrolTaskReportBean patrolTaskReportBean);
}
