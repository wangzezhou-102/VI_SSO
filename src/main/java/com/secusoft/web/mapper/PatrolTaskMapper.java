package com.secusoft.web.mapper;

import com.secusoft.web.model.PatrolTaskBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 巡逻路线Mapper
 * @author Wangzezhou
 * @company 视在数科
 * @date 2019年7月3日
 */
@Mapper
public interface PatrolTaskMapper {
    //新增巡逻任务
    int insertPatrolTask(PatrolTaskBean patrolTaskBean);
    //删除巡逻任务
    int deletePatrolTask(PatrolTaskBean patrolTaskBean);
    //修改巡逻任务
    int updatePatrolTask(PatrolTaskBean patrolTaskBean);
    //根据taskId查询巡逻任务
    PatrolTaskBean selectPatrolTaskByPrimaryKey(PatrolTaskBean patrolTaskBean);
    //根据任务名称查询巡逻id
    PatrolTaskBean selectPatrolTaskByPatrolName(PatrolTaskBean patrolTaskBean);
    //查询所有非删除态巡逻任务
    List<PatrolTaskBean> selectPatrolTaskAll(PatrolTaskBean patrolTaskBean);
}
