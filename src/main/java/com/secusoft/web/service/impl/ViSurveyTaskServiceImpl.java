package com.secusoft.web.service.impl;

import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.mapper.ViSurveyTaskMapper;
import com.secusoft.web.mapper.ViTaskDeviceMapper;
import com.secusoft.web.mapper.ViTaskRepoMapper;
import com.secusoft.web.model.*;
import com.secusoft.web.service.ViSurveyTaskService;
import com.secusoft.web.utils.PageReturnUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ViSurveyTaskServiceImpl implements ViSurveyTaskService {

    @Resource
    ViSurveyTaskMapper viSurveyTaskMapper;

    @Resource
    ViTaskDeviceMapper viTaskDeviceMapper;

    @Resource
    ViTaskRepoMapper viTaskRepoMapper;

    @Override
    public ResultVo insertViSurveyTask(ViSurveyTaskRequest viSurveyTaskRequest) {
        if (viSurveyTaskRequest == null) {
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        if (!StringUtils.hasLength(viSurveyTaskRequest.getSurveyName())) {
            return ResultVo.failure(BizExceptionEnum.TASK_NANE_NULL.getCode(),
                    BizExceptionEnum.TASK_NANE_NULL.getMessage());
        }
        if (viSurveyTaskRequest.getBeginTime() != null && viSurveyTaskRequest.getEndTime() != null) {
            if (viSurveyTaskRequest.getBeginTime().compareTo(viSurveyTaskRequest.getEndTime()) > 0) {
                return ResultVo.failure(BizExceptionEnum.TASK_DATE_WRONG.getCode(),
                        BizExceptionEnum.TASK_DATE_WRONG.getMessage());
            }
        } else {
            return ResultVo.failure(BizExceptionEnum.TASK_DATE_NULL.getCode(),
                    BizExceptionEnum.TASK_DATE_NULL.getMessage());
        }
        ViSurveyTaskBean viSurveyTaskBean=new ViSurveyTaskBean();
        viSurveyTaskBean.setBeginTime(viSurveyTaskRequest.getBeginTime());
        viSurveyTaskBean.setEndTime(viSurveyTaskRequest.getEndTime());
        viSurveyTaskBean.setSurveyName(viSurveyTaskRequest.getSurveyName());
        viSurveyTaskBean.setSurveyType(viSurveyTaskRequest.getSurveyType());
        viSurveyTaskBean.setAreaType(viSurveyTaskRequest.getAreaType());
        viSurveyTaskBean.setSurveyName(viSurveyTaskRequest.getSurveyName());
        //由于taskid唯一，先放置一个随机数区别
        int randomNumber = (int) Math.round(Math.random() * (25000 - 1) + 1);
        viSurveyTaskBean.setTaskId("t" + randomNumber);
        viSurveyTaskBean.setTopic("topict" + randomNumber);
        viSurveyTaskMapper.insertViSurveyTask(viSurveyTaskBean);
        System.out.println(viSurveyTaskBean.getId());
        //获取id，重新赋值taskid和topic
        viSurveyTaskBean.setTaskId("t" + viSurveyTaskBean.getId());
        viSurveyTaskBean.setTopic("topict" + viSurveyTaskBean.getId());
        viSurveyTaskMapper.updateViSurveyTask(viSurveyTaskBean);
        //设备信息
        String[] device=viSurveyTaskRequest.getSurveyDevice().split(",");
        List<ViTaskDeviceBean> list = new ArrayList<>();
        for (String str:device){
            ViTaskDeviceBean viTaskDeviceBean=new ViTaskDeviceBean();
            viTaskDeviceBean.setDeviceId(str);
            viTaskDeviceBean.setTaskId(viSurveyTaskBean.getTaskId());
            list.add(viTaskDeviceBean);
        }
        viTaskDeviceMapper.insertBatch(list);
        //布控库信息
        String[] repo=viSurveyTaskRequest.getSurveyRepo().split(",");
        List<ViTaskRepoBean> listRepo = new ArrayList<>();
        for (String str:repo){
            ViTaskRepoBean viTaskRepoBean=new ViTaskRepoBean();
            viTaskRepoBean.setRepoId(Integer.parseInt(str));
            viTaskRepoBean.setTaskId(viSurveyTaskBean.getTaskId());
            listRepo.add(viTaskRepoBean);
        }
        viTaskRepoMapper.insertBatch(listRepo);

//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(viSurveyTaskBean.getBeginTime());
//        Timer timer = new Timer();
//        //设备提前5分钟启动码流计划任务
//        calendar.add(Calendar.MINUTE, Integer.parseInt("-" + NormalConfig.getStreamMinute()));
//        timer.schedule(new VideoStreamStartTask(viSurveyTaskBean), calendar.getTime());
//
//        //布控任务提前2秒开始布控任务
//        calendar.setTime(viSurveyTaskBean.getBeginTime());
//        calendar.add(Calendar.SECOND, -2);
//        timer.schedule(new SurveyStartTask(viSurveyTaskBean), calendar.getTime());
//
//        calendar.setTime(viSurveyTaskBean.getEndTime());
//
//        //布控任务延后2秒停止布控任务
//        calendar.setTime(viSurveyTaskBean.getEndTime());
//        calendar.add(Calendar.SECOND, 2);
//        timer.schedule(new SurveyStopTask(viSurveyTaskBean), calendar.getTime());
//
//        //设备延后固定时间停止码流计划任务
//        calendar.setTime(viSurveyTaskBean.getEndTime());
//        calendar.add(Calendar.MINUTE, Integer.parseInt(NormalConfig.getStreamMinute()));
//        timer.schedule(new VideoStreamStopTask(viSurveyTaskRequest.getSurveyDevice()), calendar.getTime());

        return ResultVo.success();
    }

    @Override
    public ResultVo updateViSurveyTask(ViSurveyTaskBean viSurveyTaskBean) {
        if (viSurveyTaskBean == null) {
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        if (!StringUtils.hasLength(viSurveyTaskBean.getSurveyName())) {
            return ResultVo.failure(BizExceptionEnum.TASK_NANE_NULL.getCode(),
                    BizExceptionEnum.TASK_NANE_NULL.getMessage());
        }
        if (viSurveyTaskBean.getBeginTime() != null && viSurveyTaskBean.getEndTime() != null) {
            if (viSurveyTaskBean.getBeginTime().compareTo(viSurveyTaskBean.getEndTime()) > 0) {
                return ResultVo.failure(BizExceptionEnum.TASK_DATE_WRONG.getCode(),
                        BizExceptionEnum.TASK_DATE_WRONG.getMessage());
            }
        }
        viSurveyTaskBean.setModifyTime(new Date());
        viSurveyTaskMapper.updateViSurveyTask(viSurveyTaskBean);
        return ResultVo.success();
    }

    @Override
    public ResultVo delViSurveyTask(Integer id) {
        if (id == 0) {
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        viSurveyTaskMapper.delViSurveyTask(id);
        return ResultVo.success();
    }

    @Override
    public ResultVo getAllInformation(ViSurveyTaskVo viSurveyTaskVo) {
        PageHelper.startPage(viSurveyTaskVo.getCurrent(), viSurveyTaskVo.getSize());

        List<ViSurveyTaskBean> list = viSurveyTaskMapper.getAllViSurveyTask();

        return ResultVo.success(PageReturnUtils.getPageMap(list, viSurveyTaskVo.getCurrent(), viSurveyTaskVo.getSize()));
    }
}
