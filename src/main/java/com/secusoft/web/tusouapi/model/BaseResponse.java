package com.secusoft.web.tusouapi.model;

public class BaseResponse<T>{


    /**
     * 返回结果状态码 SUCCESS:返回结果成功  FAILED:返回结果失败
     */
    private String errorCode;
    /**
     * 返回的结果显示信息
     */
    private String errorMsg;
    /**
     *  返回的结果数据 jsonArray
     */
    private T data;
    /**
     * 本次查询的结果总数
     */
    private Long totalCount;

    /**
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return the errorMsg
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * @param errorMsg the errorMsg to set
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
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

    /**
     * @return the totalCount
     */
    public Long getTotalCount() {
        return totalCount;
    }

    /**
     * @param totalCount the totalCount to set
     */
    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    

}