package com.secusoft.web.Service.impl;

import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.secusoft.web.Service.ViSurveyTaskService;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.mapper.ViSurveyTaskMapper;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.ViSurveyTaskBean;
import com.secusoft.web.model.ViSurveyTaskVo;
import com.secusoft.web.utils.PageReturnUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ViSurveyTaskServiceImpl implements ViSurveyTaskService {

    @Resource
    ViSurveyTaskMapper viSurveyTaskMapper;

    @Override
    public ResultVo insertViSurveyTask(ViSurveyTaskBean viSurveyTaskBean) {
        if(viSurveyTaskBean == null){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        if(!StringUtils.hasLength(viSurveyTaskBean.getSurveyName())){
            return ResultVo.failure(BizExceptionEnum.TASK_NANE_NULL.getCode(), BizExceptionEnum.TASK_NANE_NULL.getMessage());
        }
        if(viSurveyTaskBean.getBeginTime()!=null&& viSurveyTaskBean.getEndTime()!=null) {
            if (viSurveyTaskBean.getBeginTime().compareTo(viSurveyTaskBean.getEndTime()) > 0) {
                return ResultVo.failure(BizExceptionEnum.TASK_DATE_WRONG.getCode(), BizExceptionEnum.TASK_DATE_WRONG.getMessage());
            }
        }else{
            return ResultVo.failure(BizExceptionEnum.TASK_DATE_NULL.getCode(), BizExceptionEnum.TASK_DATE_NULL.getMessage());
        }
        //由于taskid唯一，先放置一个随机数区别
        int randomNumber = (int) Math.round(Math.random()*(25000-1)+1);
        viSurveyTaskBean.setTaskId("t"+randomNumber);
        viSurveyTaskBean.setTopic("topict"+randomNumber);
        viSurveyTaskMapper.insertViSurveyTask(viSurveyTaskBean);
        System.out.println(viSurveyTaskBean.getId());
        //获取id，重新赋值taskid和topic
        viSurveyTaskBean.setTaskId("t"+ viSurveyTaskBean.getId());
        viSurveyTaskBean.setTopic("topict"+ viSurveyTaskBean.getId());
        viSurveyTaskMapper.updateViSurveyTask(viSurveyTaskBean);
        return ResultVo.success();
    }

    @Override
    public ResultVo updateViSurveyTask(ViSurveyTaskBean viSurveyTaskBean) {
        if(viSurveyTaskBean == null){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        if(!StringUtils.hasLength(viSurveyTaskBean.getSurveyName())){
            return ResultVo.failure(BizExceptionEnum.TASK_NANE_NULL.getCode(), BizExceptionEnum.TASK_NANE_NULL.getMessage());
        }
        if(viSurveyTaskBean.getBeginTime()!=null&& viSurveyTaskBean.getEndTime()!=null) {
            if (viSurveyTaskBean.getBeginTime().compareTo(viSurveyTaskBean.getEndTime()) > 0) {
                return ResultVo.failure(BizExceptionEnum.TASK_DATE_WRONG.getCode(), BizExceptionEnum.TASK_DATE_WRONG.getMessage());
            }
        }
        viSurveyTaskBean.setModifyTime(new Date());
        viSurveyTaskMapper.updateViSurveyTask(viSurveyTaskBean);
        return ResultVo.success();
    }

    @Override
    public ResultVo delViSurveyTask(Integer id) {
        if(id==0){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        viSurveyTaskMapper.delViSurveyTask(id);
        return ResultVo.success();
    }

    @Override
    public ResultVo getAllInformation(ViSurveyTaskVo viSurveyTaskVo) {
        PageHelper.startPage(viSurveyTaskVo.getCurrent(),viSurveyTaskVo.getSize());

        List<ViSurveyTaskBean> list=viSurveyTaskMapper.getAllViSurveyTask();

        return ResultVo.success(PageReturnUtils.getPageMap(list,viSurveyTaskVo.getCurrent(),viSurveyTaskVo.getSize()));
    }
}
