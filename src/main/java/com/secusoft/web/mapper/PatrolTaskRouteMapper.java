package com.secusoft.web.mapper;

import com.secusoft.web.model.PatrolTaskRouteBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 巡逻任务关联路线Mapper
 * @author Wangzezhou
 * @company 视在数科
 * @date 2019年7月3日
 */
@Mapper
public interface PatrolTaskRouteMapper {
    //新增路线
    int insertPatrolTaskRoute(PatrolTaskRouteBean patrolTaskRouteBean);
    //删除路线
    int deletePatrolTaskRoute(PatrolTaskRouteBean patrolTaskRouteBean);
    //修改任务对应路线
    int updatePatrolTaskRoute(PatrolTaskRouteBean patrolTaskRouteBean);
    //查询路线
    List<PatrolTaskRouteBean> selectPatroltTaskRoute(PatrolTaskRouteBean patrolTaskRouteBean);
    //查询所有路线
    List<PatrolTaskRouteBean> selectPatrolTaskRouteAll(PatrolTaskRouteBean patrolTaskRouteBean);
}
