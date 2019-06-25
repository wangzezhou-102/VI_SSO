package com.secusoft.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.mapper.ViPsurveyAlaramDetailMapper;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.ViPsurveyAlaramDetailBean;
import com.secusoft.web.model.ViPsurveyAlaramDetailRequest;
import com.secusoft.web.service.ViPsurveyAlaramService;
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
public class ViPsurveyAlaramServiceImpl implements ViPsurveyAlaramService {

    @Resource
    ViPsurveyAlaramDetailMapper viPsurveyAlaramDetailMapper;

    /**
     * 更新告警详情关注与否
     * @param viPsurveyAlaramDetailBean
     * @return
     */
    @Override
    public ResultVo updateFocusAlaramDetail(ViPsurveyAlaramDetailBean viPsurveyAlaramDetailBean) {

        if (viPsurveyAlaramDetailBean == null) {
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        if (viPsurveyAlaramDetailBean.getId() == 0) {
            return ResultVo.failure(BizExceptionEnum.PARAM_ERROR.getCode(), BizExceptionEnum.PARAM_ERROR.getMessage());
        }
        ViPsurveyAlaramDetailBean bean=viPsurveyAlaramDetailMapper.selectById(viPsurveyAlaramDetailBean);
        bean.setAlarmStatus(viPsurveyAlaramDetailBean.getAlarmStatus());
        bean.setGmtModified(new Date());

        viPsurveyAlaramDetailMapper.updateFocusAlaramDetail(bean);

        return ResultVo.success();
    }

    @Override
    public ResultVo getHistortyAlaramDetail(ViPsurveyAlaramDetailRequest viPsurveyAlaramDetailRequest) {

        PageHelper.startPage(viPsurveyAlaramDetailRequest.getCurrent(), viPsurveyAlaramDetailRequest.getSize());

        List<ViPsurveyAlaramDetailBean> list =
                viPsurveyAlaramDetailMapper.getAllViPsurveyAlaramDetail();

        return ResultVo.success(PageReturnUtils.getPageMap(list, viPsurveyAlaramDetailRequest.getCurrent(),
                viPsurveyAlaramDetailRequest.getSize()));
    }
}
