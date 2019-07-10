package com.secusoft.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.mapper.DeviceMapper;
import com.secusoft.web.mapper.ViBasicMemberMapper;
import com.secusoft.web.mapper.ViPrivateMemberMapper;
import com.secusoft.web.mapper.ViPsurveyAlarmDetailMapper;
import com.secusoft.web.model.*;
import com.secusoft.web.service.ViPsurveyAlarmService;
import com.secusoft.web.utils.PageReturnUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author chjiang
 * @since 2019/6/19 14:23
 */
@Service
public class ViPsurveyAlarmServiceImpl implements ViPsurveyAlarmService {

    @Resource
    ViPsurveyAlarmDetailMapper viPsurveyAlarmDetailMapper;

    @Resource
    ViPrivateMemberMapper viPrivateMemberMapper;

    @Resource
    ViBasicMemberMapper viBasicMemberMapper;

    @Resource
    DeviceMapper deviceMapper;

    /**
     * 更新告警详情关注与否
     *
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

        viPsurveyAlarmDetailMapper.updateFocusAlarmDetail(viPsurveyAlarmDetailBean);

        return ResultVo.success();
    }

    @Override
    public ResultVo getHistortyAlarmDetail(ViPsurveyAlarmDetailRequest viPsurveyAlarmDetailRequest) {

        PageHelper.startPage(viPsurveyAlarmDetailRequest.getCurrent(), viPsurveyAlarmDetailRequest.getSize());

        List<ViPsurveyAlarmDetailResponse> histortyAlarmDetail = viPsurveyAlarmDetailMapper.getHistortyAlarmDetail(viPsurveyAlarmDetailRequest);
        Map<String, Object> pageMap = PageReturnUtils.getPageMap(histortyAlarmDetail, viPsurveyAlarmDetailRequest.getCurrent(), viPsurveyAlarmDetailRequest.getSize());


        List<ViPsurveyAlarmDetailResponse> viPsurveyAlarmVos = JSON.parseObject(pageMap.get("records").toString(), new TypeReference<ArrayList<ViPsurveyAlarmDetailResponse>>(){});

        for (ViPsurveyAlarmDetailResponse bean : viPsurveyAlarmVos) {
            SimpleDateFormat sdfs = new SimpleDateFormat("MM/dd HH:mm:ss");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                bean.setTime(sdfs.format(sdf.parse(bean.getTime())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            DecimalFormat df = new DecimalFormat("0%");
            bean.setSimilarity(df.format(Double.valueOf(bean.getSimilarity())));
            ViPrivateMemberBean viPrivateMemberBean = new ViPrivateMemberBean();
            viPrivateMemberBean.setObjectId(bean.getObjectId());
            //判断在哪个库
            ViPrivateMemberBean viPrivateMemberByBean = viPrivateMemberMapper.getViPrivateMemberByBean(viPrivateMemberBean);
            if (null == viPrivateMemberByBean) {
                ViBasicMemberBean viBasicMemberBean = new ViBasicMemberBean();
                viBasicMemberBean.setObjectId(bean.getObjectId());
                ViBasicMemberBean basicMemberBean = viBasicMemberMapper.getViBasicMemberByObjectId(viBasicMemberBean);
                if (null != basicMemberBean) {
                    bean.setBkname(basicMemberBean.getViRepoBean().getBkname());
                }
            } else {
                bean.setBkname(viPrivateMemberByBean.getViRepoBean().getBkname());
            }

            //查找设备信息
            DeviceBean deviceBean = deviceMapper.selectDeviceByDeviceId(bean.getDeviceRoadName());
            if (null != deviceBean) {
                bean.setDeviceRoadName(deviceBean.getDeviceName());
            }
        }

        return ResultVo.success();
    }
}
