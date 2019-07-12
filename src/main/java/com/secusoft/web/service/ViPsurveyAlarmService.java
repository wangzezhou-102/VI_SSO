package com.secusoft.web.service;

import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.ViPsurveyAlarmDetailBean;
import com.secusoft.web.model.ViPsurveyAlarmDetailRequest;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;

/**
 * @author chjiang
 * @since 2019/6/19 14:23
 */
public interface ViPsurveyAlarmService {

    /**
     * 是否关注该条告警
     * @param viPsurveyAlarmDetailBean
     * @return
     */
    ResultVo updateFocusAlarmDetail(ViPsurveyAlarmDetailBean viPsurveyAlarmDetailBean);

    ResultVo getHistortyAlarmDetail(ViPsurveyAlarmDetailRequest viPsurveyAlarmDetailRequest, HttpServletRequest httpServletRequest);
}
