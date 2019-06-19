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
}
