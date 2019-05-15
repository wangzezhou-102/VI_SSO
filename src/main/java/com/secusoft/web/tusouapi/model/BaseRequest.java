package com.secusoft.web.tusouapi.model;


/**
 * requestBody的基础对象
 */
public class BaseRequest<T>{

    /**
     * 请求id
     */
    private String requestId;

    /**
     * 跳过开始的结果数,默认 0
     */
    private String from;

    /**
     * 返回结果总数，默认10
     */
    private String size;

    /**
     * 业务相关数据，接⼝不同，参数不同
     */
    private T data;

    /**
     * @return the requestId
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * @param requestId the requestId to set
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    /**
     * @return the from
     */
    public String getFrom() {
        return from;
    }

    /**
     * @param from the from to set
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * @return the size
     */
    public String getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     * @return the data
     */
    public T getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(T data) {
        this.data = data;
    }

    
}