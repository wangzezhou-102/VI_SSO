package com.secusoft.web.tusouapi.model;

public class BKTaskListRequest {

    /**
     * 布控任务id，多个之间用逗号隔开
     */
    private String taskIds;
    /**
     *查询开始位置，默认0
     */
    private int from;
    /**
     * 查询返回结果数量,默认10
     */
    private int size;
    /**
     * 布控任务相关信息同submit接⼝中meta参数 单个或多个信息都可以作为查询条件
     */
    private BKTaskMeta meta;

    public String getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(String taskIds) {
        this.taskIds = taskIds;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public BKTaskMeta getMeta() {
        return meta;
    }

    public void setMeta(BKTaskMeta meta) {
        this.meta = meta;
    }
}
