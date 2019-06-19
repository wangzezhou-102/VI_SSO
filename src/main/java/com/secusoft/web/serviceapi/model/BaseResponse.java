package com.secusoft.web.serviceapi.model;

public class BaseResponse<T>{


    /**
     * 返回结果状态码 SUCCESS:返回结果成功  FAILED:返回结果失败
     */
    private String code;
    /**
     * 返回的结果显示信息
     */
    private String message;
    /**
     *  返回的结果数据 jsonArray
     */
    private T data;
    /**
     * 本次查询的结果总数
     */
    private Long total;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}