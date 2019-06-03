package com.secusoft.web.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


@TableName("vi_survey_task")
public class ViSurveyTask extends Model<ViSurveyTask> {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String task_id;
    private String survey_name;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date begin_time;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date end_time;
    private Integer survey_type;
    private Integer survey_status;
    private Integer enable;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getSurvey_name() {
        return survey_name;
    }

    public void setSurvey_name(String survey_name) {
        this.survey_name = survey_name;
    }

    public Date getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(Date begin_time) {
        this.begin_time = begin_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public Integer getSurvey_type() {
        return survey_type;
    }

    public void setSurvey_type(Integer survey_type) {
        this.survey_type = survey_type;
    }

    public Integer getSurvey_status() {
        return survey_status;
    }

    public void setSurvey_status(Integer survey_status) {
        this.survey_status = survey_status;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ViSurveyTask{" +
                "id=" + id +
                ", task_id='" + task_id + '\'' +
                ", survey_name='" + survey_name + '\'' +
                ", begin_time=" + begin_time +
                ", end_time=" + end_time +
                ", survey_type=" + survey_type +
                ", survey_status=" + survey_status +
                ", enable=" + enable +
                '}';
    }
}
