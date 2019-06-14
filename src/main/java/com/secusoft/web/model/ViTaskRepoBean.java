package com.secusoft.web.model;

/**
 * 布控任务与库关联表
 */
public class ViTaskRepoBean {
    private Integer id;

    private String taskId;

    private Integer repoId;

    private ViSurveyTaskBean viSurveyTask;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Integer getRepoId() {
        return repoId;
    }

    public void setRepoId(Integer repoId) {
        this.repoId = repoId;
    }

    public ViSurveyTaskBean getViSurveyTask() {
        return viSurveyTask;
    }

    public void setViSurveyTask(ViSurveyTaskBean viSurveyTask) {
        this.viSurveyTask = viSurveyTask;
    }
}