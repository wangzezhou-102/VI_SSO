package com.secusoft.web.service;


import com.secusoft.web.model.PatrolTaskBean;
import com.secusoft.web.model.PatrolTaskRequest;
import com.secusoft.web.model.ResultVo;

/**
 * 巡逻任务service
 * @author Wangzezhou
 * @company 视在数科
 * @date 2019年7月4日
 */
public interface PatrolTaskService {
    //新增巡逻任务
    ResultVo insertPatrolTak(PatrolTaskRequest patrolTaskRequest);
    //巡逻任务编辑
    ResultVo updatePatrolTask(PatrolTaskRequest patrolTaskRequest);
    //巡逻任务删除
    ResultVo deletePatrolTask(PatrolTaskBean patrolTaskBean);
    //巡逻任务查询
    ResultVo selectPatrolTask(PatrolTaskBean patrolTaskBean);
    //查询多条巡逻任务
    ResultVo selectPatrolTaskAll();
    //开启巡逻任务
    ResultVo startPatrolTask(PatrolTaskBean patrolTaskBean);
    //终止巡逻任务
    ResultVo stopPatrolTask(PatrolTaskBean patrolTaskBean);
    //立即执行任务
    ResultVo runningPatrolTask(PatrolTaskBean patrolTaskBean);

}
