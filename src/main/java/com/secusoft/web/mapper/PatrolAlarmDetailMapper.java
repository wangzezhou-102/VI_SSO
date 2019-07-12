package com.secusoft.web.mapper;

import com.secusoft.web.model.PatrolAlarmDetailBean;
import com.secusoft.web.model.PatrolAlarmDetailRequest;
import com.secusoft.web.model.PatrolAlarmDetailResponse;

import java.util.List;

/**
 * @author wangzezhou
 * @since 2019/7/11 15:36
 */
public interface PatrolAlarmDetailMapper {
    /**
     * 添加巡逻告警详细
     * @param patrolAlarmDetailBeans
     */
    void insertBatch(List<PatrolAlarmDetailBean> patrolAlarmDetailBeans);
    /**
     * 是否关注该布控
     * @param patrolAlarmDetailBean
     */
    void updateFocusAlarmDetail(PatrolAlarmDetailBean patrolAlarmDetailBean);
    /**
     * 根据id获取detail对象
     * @param patrolAlarmDetailBean
     * @return
     */
    PatrolAlarmDetailBean selectById(PatrolAlarmDetailBean patrolAlarmDetailBean);
    /**
     * 获取所有的detail
     * @return
     */
    List<PatrolAlarmDetailBean> getAllPatrolAlarmDetail();
    /**
     * 单个插入告警详情
     * @param patrolAlarmDetailBean
     */
    void insertPatrolAlarmDetail(PatrolAlarmDetailBean patrolAlarmDetailBean);
    /**
     * 获取历史告警信息
     * @param
     */
    List<PatrolAlarmDetailResponse> getHistortyAlarmDetail(PatrolAlarmDetailRequest patrolAlarmDetailRequest);
}
