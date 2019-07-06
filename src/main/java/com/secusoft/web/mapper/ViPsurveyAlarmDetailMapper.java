package com.secusoft.web.mapper;

import com.secusoft.web.model.ViPsurveyAlarmDetailBean;
import com.secusoft.web.model.ViPsurveyAlarmDetailRequest;
import com.secusoft.web.model.ViPsurveyAlarmDetailResponse;

import java.util.List;

/**
 * @author chjiang
 * @since 2019/6/19 14:24
 */
public interface ViPsurveyAlarmDetailMapper {

    /**
     * 添加布控告警详细
     * @param viPsurveyAlarmDetailBeans
     */
    void insertBatch(List<ViPsurveyAlarmDetailBean> viPsurveyAlarmDetailBeans);

    /**
     * 是否关注该布控
     * @param viPsurveyAlarmDetailBean
     */
    void updateFocusAlarmDetail(ViPsurveyAlarmDetailBean viPsurveyAlarmDetailBean);

    /**
     * 根据id获取detail对象
     * @param viPsurveyAlarmDetailBean
     * @return
     */
    ViPsurveyAlarmDetailBean selectById(ViPsurveyAlarmDetailBean viPsurveyAlarmDetailBean);

    /**
     * 获取所有的detail
     * @return
     */
    List<ViPsurveyAlarmDetailBean> getAllViPsurveyAlarmDetail();

    /**
     * 单个插入告警详情
     * @param viPsurveyAlarmDetailBean
     */
    void insertViPsurveyAlarmDetail(ViPsurveyAlarmDetailBean viPsurveyAlarmDetailBean);



    /**
     * 获取历史告警信息
     * @param
     */
    List<ViPsurveyAlarmDetailResponse> getHistortyAlarmDetail(ViPsurveyAlarmDetailRequest viPsurveyAlarmDetailRequest);
}
