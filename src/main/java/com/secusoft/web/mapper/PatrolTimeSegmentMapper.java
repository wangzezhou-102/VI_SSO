package com.secusoft.web.mapper;

import com.secusoft.web.model.PatrolTimeSegmentBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 巡逻任务时间段Mapper
 * @author Wangzezhou
 * @company 视在数科
 * @date 2019年7月3日
 */
@Mapper
public interface PatrolTimeSegmentMapper {
    //新增时间段
    int insertPatrolTimeSegment(PatrolTimeSegmentBean patrolTimeSegmentBean);
    //删除时间段
    int deletePatrolTimeSegment(PatrolTimeSegmentBean patrolTimeSegmentBean);
    //修改时间模板关联时间段信息
    int updatePatrolTimeSegment(PatrolTimeSegmentBean patrolTimeSegmentBean);
    //查询时间模板关联时间段信息
    List<PatrolTimeSegmentBean> selectPatroltTimeSegment(PatrolTimeSegmentBean patrolTimeSegmentBean);
}
