package com.secusoft.web.Service;

import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.ViSurveyTask;
import com.secusoft.web.model.ViSurveyTaskVo;

import java.util.Map;

public interface ViSurveyTaskService {

    ResultVo insertViSurveyTask(ViSurveyTask viSurveyTask);

    ResultVo updateViSurveyTask(ViSurveyTask viSurveyTask);

    ResultVo delViSurveyTask(Integer id);

    Map<String, Object> getAllInformation(ViSurveyTaskVo viSurveyTaskVo);
}
