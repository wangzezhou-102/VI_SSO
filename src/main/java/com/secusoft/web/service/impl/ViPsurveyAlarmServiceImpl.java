package com.secusoft.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.mapper.ViPsurveyAlarmDetailMapper;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.ViPsurveyAlarmDetailBean;
import com.secusoft.web.model.ViPsurveyAlarmDetailRequest;
import com.secusoft.web.service.ViPsurveyAlarmService;
import com.secusoft.web.utils.PageReturnUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author chjiang
 * @since 2019/6/19 14:23
 */
@Service
public class ViPsurveyAlarmServiceImpl implements ViPsurveyAlarmService {

    @Resource
    ViPsurveyAlarmDetailMapper viPsurveyAlarmDetailMapper;

    /**
     * 更新告警详情关注与否
     * @param viPsurveyAlarmDetailBean
     * @return
     */
    @Override
    public ResultVo updateFocusAlarmDetail(ViPsurveyAlarmDetailBean viPsurveyAlarmDetailBean) {

        if (viPsurveyAlarmDetailBean == null) {
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        if (viPsurveyAlarmDetailBean.getId() == 0) {
            return ResultVo.failure(BizExceptionEnum.PARAM_ERROR.getCode(), BizExceptionEnum.PARAM_ERROR.getMessage());
        }

        viPsurveyAlarmDetailBean.setGmtModified(new Date());

        viPsurveyAlarmDetailMapper.updateFocusAlaramDetail(viPsurveyAlarmDetailBean);

        return ResultVo.success();
    }

    @Override
    public ResultVo getHistortyAlarmDetail(ViPsurveyAlarmDetailRequest viPsurveyAlarmDetailRequest) {

        PageHelper.startPage(viPsurveyAlarmDetailRequest.getCurrent(), viPsurveyAlarmDetailRequest.getSize());

        List<ViPsurveyAlarmDetailBean> list =
                viPsurveyAlarmDetailMapper.getAllViPsurveyAlaramDetail();

        return ResultVo.success(PageReturnUtils.getPageMap(list, viPsurveyAlarmDetailRequest.getCurrent(),
                viPsurveyAlarmDetailRequest.getSize()));
    }
}
