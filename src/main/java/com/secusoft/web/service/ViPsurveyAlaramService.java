package com.secusoft.web.service;

import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.ViPsurveyAlaramDetailBean;
import com.secusoft.web.model.ViPsurveyAlaramDetailRequest;

/**
 * @author chjiang
 * @since 2019/6/19 14:23
 */
public interface ViPsurveyAlaramService {

    /**
     * 是否关注该条告警
     * @param viPsurveyAlaramDetailBean
     * @return
     */
    ResultVo updateFocusAlaramDetail(ViPsurveyAlaramDetailBean viPsurveyAlaramDetailBean);

    ResultVo getHistortyAlaramDetail(ViPsurveyAlaramDetailRequest viPsurveyAlaramDetailRequest);
}
