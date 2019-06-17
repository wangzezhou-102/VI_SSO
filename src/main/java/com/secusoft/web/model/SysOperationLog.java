package com.secusoft.web.model;

import java.util.Date;

/**
 * @author huanghao
 * @date 2019-06-17
 */
public class SysOperationLog {
    private Integer id;//自增ID
    private Integer type;//0-失败 1-成功 2-异常
    private String title;//功能名称
    private String remoteAddr;//请求源IP地址
    private String requestUri;//请求uri
    private String method;//方法名称
    private String param;//请求参数{}，即入参
    private String result;//返回结果{}
    private String exception;//异常信息
    private Integer userId;//用户账号ID
    private java.util.Date operateDate;//记录时间
    private Long timeout;//毫秒级响应时间

    public SysOperationLog() {
    }

    public SysOperationLog(Integer id, Integer type, String title, String remoteAddr, String requestUri, String method, String param, String result, String exception, Integer userId, Date operateDate, Long timeout) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.remoteAddr = remoteAddr;
        this.requestUri = requestUri;
        this.method = method;
        this.param = param;
        this.result = result;
        this.exception = exception;
        this.userId = userId;
        this.operateDate = operateDate;
        this.timeout = timeout;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    @Override
    public String toString() {
        return "SysOperationLog{" +
                "id=" + id +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", remoteAddr='" + remoteAddr + '\'' +
                ", requestUri='" + requestUri + '\'' +
                ", method='" + method + '\'' +
                ", param='" + param + '\'' +
                ", result='" + result + '\'' +
                ", exception='" + exception + '\'' +
                ", userId=" + userId +
                ", operateDate=" + operateDate +
                ", timeout=" + timeout +
                '}';
    }
}
