package com.secusoft.web.service.impl;

import com.secusoft.web.mapper.SysOperationLogMapper;
import com.secusoft.web.mapper.ViSurveyTaskMapper;
import com.secusoft.web.mapper.ViTaskDeviceMapper;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.TotalInfoBean;
import com.secusoft.web.service.TotalInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author huanghao
 * @date 2019-06-19
 */
@Service
public class TotalInfoServiceImpl implements TotalInfoService {
    @Resource
    ViSurveyTaskMapper viSurveyTaskMapper;
    @Resource
    ViTaskDeviceMapper viTaskDeviceMapper;
    @Resource
    SysOperationLogMapper sysOperationLogMapper;

    @Override
    public ResultVo getTotalInfo() {
        TotalInfoBean totalInfoBean = new TotalInfoBean();
        //摄像头并发路数暂定5000
        totalInfoBean.setDeviceConcurrenceTotal(5000);
        //摄像头使用总路数为 参与布控 安保 巡逻 三者 设备的总和
        Integer taskDeviceCount = viTaskDeviceMapper.taskDeviceCount();
        totalInfoBean.setDeviceUsedTotal(taskDeviceCount);
        totalInfoBean.setSeachUsedTotal(sysOperationLogMapper.tusouSearchTotal());
        //布控追踪任务个数
        totalInfoBean.setSurveyTaskTotal(viSurveyTaskMapper.getEnabledViSurveyTaskCount());
        //安保护航 跟 区域巡逻 暂定0
        totalInfoBean.setSecurityTaskTotal(0);
        totalInfoBean.setAreaPatrollingTaskTotal(0);

        return ResultVo.success(totalInfoBean);
    }
}
