package com.secusoft.web.tusouapi.model;

/**
 * 创建、更新布控任务request参数
 */
public class BKTaskSubmitRequest{

    /**
     * [必须] 布控任务id
     */
    private String taskId;
    /**
     * 布控任务相关信息,
     */
    private BKTaskMeta meta;


    
    /**
     * @return the taskId
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * @param taskId the taskId to set
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * @return the meta
     */
    public BKTaskMeta getMeta() {
        return meta;
    }

    /**
     * @param meta the meta to set
     */
    public void setMeta(BKTaskMeta meta) {
        this.meta = meta;
    }

    

}