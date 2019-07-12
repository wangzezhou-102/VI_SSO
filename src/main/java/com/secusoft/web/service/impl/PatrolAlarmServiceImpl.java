package com.secusoft.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.mapper.*;
import com.secusoft.web.model.*;
import com.secusoft.web.service.PatrolAlarmService;
import com.secusoft.web.utils.PageReturnUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author wangzezhou
 * @since 2019/7/11 16:02
 */
@Service
public class PatrolAlarmServiceImpl implements PatrolAlarmService {

    @Resource
    PatrolAlarmDetailMapper patrolAlarmDetailMapper;

    @Resource
    ViPrivateMemberMapper viPrivateMemberMapper;

    @Resource
    ViBasicMemberMapper viBasicMemberMapper;

    @Resource
    DeviceMapper deviceMapper;

    /**
     * 更新告警详情关注与否
     * @param patrolAlarmDetailBean
     * @return
     */
    @Override
    public ResultVo updateFocusAlarmDetail(PatrolAlarmDetailBean patrolAlarmDetailBean) {

        if (patrolAlarmDetailBean == null) {
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        if (patrolAlarmDetailBean.getId() == 0) {
            return ResultVo.failure(BizExceptionEnum.PARAM_ERROR.getCode(), BizExceptionEnum.PARAM_ERROR.getMessage());
        }

        patrolAlarmDetailBean.setGmtModified(new Date());

        patrolAlarmDetailMapper.updateFocusAlarmDetail(patrolAlarmDetailBean);

        return ResultVo.success();
    }

    @Override
    public ResultVo getHistortyAlarmDetail(PatrolAlarmDetailRequest patrolAlarmDetailRequest) {

        PageHelper.startPage(patrolAlarmDetailRequest.getCurrent(), patrolAlarmDetailRequest.getSize());

        List<PatrolAlarmDetailResponse> histortyAlarmDetail = patrolAlarmDetailMapper.getHistortyAlarmDetail(patrolAlarmDetailRequest);
        for(PatrolAlarmDetailResponse bean:histortyAlarmDetail){
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
            if(null!=deviceBean){
                bean.setDeviceRoadName(deviceBean.getDeviceName());
            }
        }
        return ResultVo.success(PageReturnUtils.getPageMap(histortyAlarmDetail, patrolAlarmDetailRequest.getCurrent(), patrolAlarmDetailRequest.getSize()));
    }
}
