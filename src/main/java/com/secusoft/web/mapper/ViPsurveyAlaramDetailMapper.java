package com.secusoft.web.mapper;

import com.secusoft.web.model.ViPsurveyAlaramDetailBean;

import java.util.List;

/**
 * @author chjiang
 * @since 2019/6/19 14:24
 */
public interface ViPsurveyAlaramDetailMapper {

    /**
     * 添加布控告警详细
     * @param viPsurveyAlaramDetailBeans
     */
    void insertBatch(List<ViPsurveyAlaramDetailBean> viPsurveyAlaramDetailBeans);

    /**
     * 是否关注该布控
     * @param viPsurveyAlaramDetailBean
     */
    void updateFocusAlaramDetail(ViPsurveyAlaramDetailBean viPsurveyAlaramDetailBean);

    /**
     * 根据id获取detail对象
     * @param viPsurveyAlaramDetailBean
     * @return
     */
    ViPsurveyAlaramDetailBean selectById(ViPsurveyAlaramDetailBean viPsurveyAlaramDetailBean);

    /**
     * 获取所有的detail
     * @return
     */
    List<ViPsurveyAlaramDetailBean> getAllViPsurveyAlaramDetail();
}
