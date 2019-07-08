package com.secusoft.web.model;

import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 安保任务地点中间表
 * @author huanghao
 * @date 2019-07-08
 */
@TableName("vi_security_task_place")
public class SecurityTaskPlaceBean {
    private Integer id;//主键ID
    private String taskId;//任务ID,UUID
    private Integer placeId;//地点ID
    private Integer typeCode;//区分具体135范围类型，1表示一分钟圈，3表示3分钟圈，6表示大型活动
    public SecurityTaskPlaceBean() {
        super();
    }
    public SecurityTaskPlaceBean(Integer id,String taskId,Integer placeId,Integer typeCode) {
        super();
        this.id = id;
        this.taskId = taskId;
        this.placeId = placeId;
        this.typeCode = typeCode;
    }
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Integer getPlaceId() {
        return this.placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public Integer getTypeCode() {
        return this.typeCode;
    }

    public void setTypeCode(Integer typeCode) {
        this.typeCode = typeCode;
    }

}
