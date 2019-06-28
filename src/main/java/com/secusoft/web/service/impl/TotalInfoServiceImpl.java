package com.secusoft.web.service.impl;

import com.secusoft.web.mapper.SysOperationLogMapper;
import com.secusoft.web.mapper.ViSurveyTaskMapper;
import com.secusoft.web.mapper.ViTaskDeviceMapper;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.TotalInfoBean;
import com.secusoft.web.model.TotalInfoVo;
import com.secusoft.web.service.TotalInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

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
        //摄像头使用总路数为 参与布控 安保 巡逻 三者 设备的总和
        Integer taskDeviceCount = viTaskDeviceMapper.taskDeviceCount();
        //摄像头并发路数暂定5000
        TotalInfoVo  totalInfoVo1= new TotalInfoVo("摄像头并发路数",5000,"路",null,false);
        TotalInfoVo  totalInfoVo2= new TotalInfoVo("摄像头使用次数",taskDeviceCount,"个",null,false);
        TotalInfoVo  totalInfoVo3= new TotalInfoVo("目标搜图使用次数",sysOperationLogMapper.tusouSearchTotal(),"次",null,false);
        TotalInfoVo  totalInfoVo4= new TotalInfoVo("布控追踪执行任务数",viSurveyTaskMapper.getEnabledViSurveyTaskCount(),"个",0,true);
        TotalInfoVo  totalInfoVo5= new TotalInfoVo("安保护航执行任务数",0,"个",0,true);
        TotalInfoVo  totalInfoVo6= new TotalInfoVo("区域巡逻执行任务数",0,"个",0,true);

        ArrayList<TotalInfoVo> totalInfoVos = new ArrayList<>();
        totalInfoVos.add(totalInfoVo1);
        totalInfoVos.add(totalInfoVo2);
        totalInfoVos.add(totalInfoVo3);
        totalInfoVos.add(totalInfoVo4);
        totalInfoVos.add(totalInfoVo5);
        totalInfoVos.add(totalInfoVo6);

        return ResultVo.success(totalInfoVos);
    }
}
