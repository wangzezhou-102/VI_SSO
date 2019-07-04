package com.secusoft.web.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 巡逻任务
 * @author Wangzezhou
 * @company 视在数科
 * @date 2019年7月1日
 */
public class PatrolTaskBean {
   private String taskId;
   //巡逻任务名称
   private String patrolName;
   //巡逻任务状态 0-无操作 1-执行 2-终止 3-删除
   private Integer status;
   //时间模板ID
   private String timeTemplateId;
   //时间模板实体
   private PatrolTimeTemplateBean patrolTimeTemplateBean;
   //开启状态 0-关闭 1-开启
   private Integer enable;
   //开启时间
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date beginTime;
   //结束时间
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date endTime;
   //创建者id
   private Integer createId;
   //创建时间
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date createTime;
   //更新者id
   private Integer updateId;
   //更新时间
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date updateTime;
   //组织code
   private String orgCode;

   public PatrolTimeTemplateBean getPatrolTimeTemplateBean() { return patrolTimeTemplateBean; }

   public void setPatrolTimeTemplateBean(PatrolTimeTemplateBean patrolTimeTemplateBean) { this.patrolTimeTemplateBean = patrolTimeTemplateBean; }

   public String getTaskId() { return taskId; }

   public void setTaskId(String taskId) { this.taskId = taskId; }

   public String getPatrolName() { return patrolName; }

   public void setPatrolName(String patrolName) { this.patrolName = patrolName; }

   public Integer getStatus() { return status; }

   public void setStatus(Integer status) { this.status = status; }

   public String getTimeTemplateId() { return timeTemplateId; }

   public void setTimeTemplateId(String timeTemplateId) { this.timeTemplateId = timeTemplateId; }

   public Integer getEnable() { return enable; }

   public void setEnable(Integer enable) { this.enable = enable; }

   public Date getBeginTime() { return beginTime; }

   public void setBeginTime(Date beginTime) { this.beginTime = beginTime; }

   public Date getEndTime() { return endTime; }

   public void setEndTime(Date endTime) { this.endTime = endTime; }

   public Integer getCreateId() { return createId; }

   public void setCreateId(Integer createId) { this.createId = createId; }

   public Date getCreateTime() { return createTime; }

   public void setCreateTime(Date createTime) { this.createTime = createTime; }

   public Integer getUpdateId() { return updateId; }

   public void setUpdateId(Integer updateId) { this.updateId = updateId; }

   public Date getUpdateTime() { return updateTime; }

   public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }

   public String getOrgCode() { return orgCode; }

   public void setOrgCode(String orgCode) { this.orgCode = orgCode; }

   @Override
   public String toString() {
      return "PatrolTaskBean{" +
              "taskId='" + taskId + '\'' +
              ", patrolName='" + patrolName + '\'' +
              ", status=" + status +
              ", timeTemplateId=" + timeTemplateId +
              ", enable=" + enable +
              ", beginTime=" + beginTime +
              ", endTime=" + endTime +
              ", createId=" + createId +
              ", createTime=" + createTime +
              ", updateId=" + updateId +
              ", updateTime=" + updateTime +
              ", orgCode='" + orgCode + '\'' +
              '}';
   }
   public boolean validateStatus(){
      if( status == null){
         return false;
      }
      return true;
   }
   public boolean validateTaskId(){
      if(taskId == null ){
         return false;
      }
      return true;
   }
}
