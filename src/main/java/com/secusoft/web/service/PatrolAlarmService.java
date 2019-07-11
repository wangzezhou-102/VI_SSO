package com.secusoft.web.service;

import com.secusoft.web.model.PatrolAlarmDetailBean;
import com.secusoft.web.model.PatrolAlarmDetailRequest;
import com.secusoft.web.model.ResultVo;

/**
 * @author wangzezhou
 * @since 2019/7/11 16:01
 */
public interface PatrolAlarmService {

    /**
     * 是否关注该条告警
     * @param patrolAlarmDetailBean
     * @return
     */
    ResultVo updateFocusAlarmDetail(PatrolAlarmDetailBean patrolAlarmDetailBean);

    ResultVo getHistortyAlarmDetail(PatrolAlarmDetailRequest patrolAlarmDetailRequest);
}
