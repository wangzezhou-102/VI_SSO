package com.secusoft.web.mapper;

import com.secusoft.web.model.ViPsurveyAlaramBean;

/**
 * @author chjiang
 * @since 2019/6/19 14:24
 */
public interface ViPsurveyAlaramMapper {

    /**
     * 添加布控告警
     * @param viPsurveyAlaramBean
     */
    void insertViPsurveyAlaram(ViPsurveyAlaramBean viPsurveyAlaramBean);
}
