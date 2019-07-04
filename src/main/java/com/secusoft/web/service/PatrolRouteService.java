package com.secusoft.web.service;

import com.secusoft.web.model.PatrolRouteBean;
import com.secusoft.web.model.ResultVo;

/**
 * 巡逻路线service
 * @author Wangzezhou
 * @company 视在数科
 * @date 2019年7月2日
 */
public interface PatrolRouteService {
    //查询不同状态下的路线列表
    ResultVo selectPatrolRouteByStatus(PatrolRouteBean patrolRouteBean);
    //查询一条路线信息
    ResultVo selectPatrolRouteById(PatrolRouteBean patrolRouteBean);
    //新增路线
    ResultVo insertPatroRoute(PatrolRouteBean patrolRouteBean);
    //删除路线
    ResultVo deletePatrolRoute(PatrolRouteBean patrolRouteBean);
    //修改路线信息
    ResultVo updatePatroRoute(PatrolRouteBean patrolRouteBean);
}
