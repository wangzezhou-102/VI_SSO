package com.secusoft.web.tusouapi.model;

public class BKTaskDeleteRequest {

    /**
     * 布控任务id 多个之间⽤逗号","隔开
     */
    private String taskIds;

    public String getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(String taskIds) {
        this.taskIds = taskIds;
    }
}
