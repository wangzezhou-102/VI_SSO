package com.secusoft.web.service;

import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.ViSurveyTaskBean;
import com.secusoft.web.model.ViSurveyTaskVo;

public interface ViSurveyTaskService {

    ResultVo insertViSurveyTask(ViSurveyTaskBean viSurveyTaskBean);

    ResultVo updateViSurveyTask(ViSurveyTaskBean viSurveyTaskBean);

    ResultVo delViSurveyTask(Integer id);

    ResultVo getAllInformation(ViSurveyTaskVo viSurveyTaskVo);
}
