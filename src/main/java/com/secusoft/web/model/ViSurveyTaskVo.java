package com.secusoft.web.model;

import com.secusoft.web.utils.PageReqAbstractModel;

public class ViSurveyTaskVo extends PageReqAbstractModel {

    private Integer surveyType;

    public Integer getSurveyType() {
        return surveyType;
    }

    public void setSurveyType(Integer surveyType) {
        this.surveyType = surveyType;
    }
}
