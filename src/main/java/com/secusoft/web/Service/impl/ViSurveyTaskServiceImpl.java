package com.secusoft.web.Service.impl;

import com.secusoft.web.Service.ViSurveyTaskService;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.mapper.ViSurveyTaskMapper;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.ViSurveyTask;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ViSurveyTaskServiceImpl implements ViSurveyTaskService {

    @Resource
    ViSurveyTaskMapper viSurveyTaskMapper;

    @Override
    public ResultVo insertViSurveyTask(ViSurveyTask viSurveyTask) {
        if(viSurveyTask == null){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        viSurveyTaskMapper.insertViSurveyTask(viSurveyTask);
        return ResultVo.success();
    }

    @Override
    public ResultVo updateViSurveyTask(ViSurveyTask viSurveyTask) {
        if(viSurveyTask == null){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
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
}
