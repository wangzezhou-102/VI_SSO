package com.secusoft.web.Service;

import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.ViSurveyTask;
import com.secusoft.web.model.ViSurveyTaskVo;

public interface ViSurveyTaskService {

    ResultVo insertViSurveyTask(ViSurveyTask viSurveyTask);

    ResultVo updateViSurveyTask(ViSurveyTask viSurveyTask);

    ResultVo delViSurveyTask(Integer id);

    ResultVo getAllInformation(ViSurveyTaskVo viSurveyTaskVo);
}
