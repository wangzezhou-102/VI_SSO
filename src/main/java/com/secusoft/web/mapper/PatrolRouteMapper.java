package com.secusoft.web.mapper;

import com.secusoft.web.model.PatrolRouteBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 巡逻路线Mapper
 * @author Wangzezhou
 * @company 视在数科
 * @date 2019年7月1日
 */
@Mapper
public interface PatrolRouteMapper {
    //增加路线
    int insertPatrolRoute(PatrolRouteBean patrolRouteBean);
    //删除路线
    int deletePatrolRouteByPrimaryKey(PatrolRouteBean patrolRouteBean);
    //修改路线信息
    int updatePatrolRouteByPrimaryKey(PatrolRouteBean patrolRouteBean);
    //查看路线
    PatrolRouteBean selectPatrolRouteByPrimaryKey(@Param("Id")Integer Id);
    //查看路线id
    PatrolRouteBean selectPatrolRouteByRouteName(PatrolRouteBean patrolRouteBean);
    //查看所有路线
    List<PatrolRouteBean> selectPatrolRouteAll();
}
