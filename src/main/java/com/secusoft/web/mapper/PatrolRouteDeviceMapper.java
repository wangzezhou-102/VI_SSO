package com.secusoft.web.mapper;

import com.secusoft.web.model.PatrolRouteDeviceBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 巡逻路线Mapper
 * @author Wangzezhou
 * @company 视在数科
 * @date 2019年7月1日
 */
@Mapper
public interface PatrolRouteDeviceMapper {
    //增加路线设备
    int insertPatrolRouteDevice(PatrolRouteDeviceBean patrolRouteDeviceBean);
    //删除路线对应设备
    int deletePatrolRouteDevice(PatrolRouteDeviceBean patrolRouteDeviceBean);
    //修改路线状态
    int updatePatrolRouteDevice(PatrolRouteDeviceBean patrolRouteDeviceBean);
    //查看路线对应设备
    List<PatrolRouteDeviceBean> selectPatrolRouteDeviceByRouteId(PatrolRouteDeviceBean patrolRouteDeviceBean);
}
