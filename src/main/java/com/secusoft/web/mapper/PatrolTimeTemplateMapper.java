package com.secusoft.web.mapper;

import com.secusoft.web.model.PatrolTimeTemplateBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 巡逻任务时间模板Mapper
 * @author Wangzezhou
 * @company 视在数科
 * @date 2019年7月3日
 */
@Mapper
public interface PatrolTimeTemplateMapper {
    //新增时间模板
    int insertPatrolTimeTemplate(PatrolTimeTemplateBean patrolTimeTemplateBean);
    //删除时间模板
    int deletePatrolTimeTemplate(PatrolTimeTemplateBean patrolTimeTemplateBean);
    //修改任务对应时间模板
    int updatePatrolTimeTemplate(PatrolTimeTemplateBean patrolTimeTemplateBean);
    //查询时间模板信息
    PatrolTimeTemplateBean selectPatroltTimeTemplate(PatrolTimeTemplateBean patrolTimeTemplateBean);
    //查询所有时间模板
    List<PatrolTimeTemplateBean> selectPatrolTimeTemplateAll(PatrolTimeTemplateBean patrolTimeTemplateBean);
}
