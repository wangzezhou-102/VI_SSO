package com.secusoft.web.Service.impl;

import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.secusoft.web.Service.ViSurveyTaskService;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.mapper.ViSurveyTaskMapper;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.ViSurveyTask;
import com.secusoft.web.model.ViSurveyTaskVo;
import com.secusoft.web.utils.PageReturnUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ViSurveyTaskServiceImpl implements ViSurveyTaskService {

    @Resource
    ViSurveyTaskMapper viSurveyTaskMapper;

    @Override
    public ResultVo insertViSurveyTask(ViSurveyTask viSurveyTask) {
        if(viSurveyTask == null){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        if(!StringUtils.hasLength(viSurveyTask.getSurveyName())){
            return ResultVo.failure(BizExceptionEnum.TASK_NANE_NULL.getCode(), BizExceptionEnum.TASK_NANE_NULL.getMessage());
        }
        if(viSurveyTask.getBeginTime().compareTo(viSurveyTask.getEndTime())>0){
            return ResultVo.failure(BizExceptionEnum.TASK_DATE_WRONG.getCode(), BizExceptionEnum.TASK_DATE_WRONG.getMessage());
        }
        viSurveyTaskMapper.insertViSurveyTask(viSurveyTask);
        return ResultVo.success();
    }

    @Override
    public ResultVo updateViSurveyTask(ViSurveyTask viSurveyTask) {
        if(viSurveyTask == null){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        if(!StringUtils.hasLength(viSurveyTask.getSurveyName())){
            return ResultVo.failure(BizExceptionEnum.TASK_NANE_NULL.getCode(), BizExceptionEnum.TASK_NANE_NULL.getMessage());
        }
        if(viSurveyTask.getBeginTime().compareTo(viSurveyTask.getEndTime())>0){
            return ResultVo.failure(BizExceptionEnum.TASK_DATE_WRONG.getCode(), BizExceptionEnum.TASK_DATE_WRONG.getMessage());
        }
        viSurveyTaskMapper.updateViSurveyTask(viSurveyTask);
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

        List<ViSurveyTask> list=viSurveyTaskMapper.getAllViSurveyTask();

        return ResultVo.success(PageReturnUtils.getPageMap(list,viSurveyTaskVo.getCurrent(),viSurveyTaskVo.getSize()));
    }
}
