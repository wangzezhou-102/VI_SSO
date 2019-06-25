package com.secusoft.web.service;

import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.ViSurveyTaskRequest;
import com.secusoft.web.model.ViSurveyTaskVo;

public interface ViSurveyTaskService {

    ResultVo insertViSurveyTask(ViSurveyTaskRequest viSurveyTaskRequest);

    ResultVo updateViSurveyTask(ViSurveyTaskRequest viSurveyTaskRequest);

    ResultVo delViSurveyTask(ViSurveyTaskRequest viSurveyTaskRequest);

    ResultVo getAllInformation(ViSurveyTaskVo viSurveyTaskVo);

    ResultVo startViSurveyTask(ViSurveyTaskRequest viSurveyTaskRequest);

    ResultVo stopViSurveyTask(ViSurveyTaskRequest viSurveyTaskRequest);
}
