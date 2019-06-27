package com.secusoft.web.tusouapi.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 图搜返回接口实体
 * @author huanghao
 * @date 2019-06-12
 */
public class SearchResponse {
    @JSONField(name="data")
    private List<SearchResponseData> data;
    private String errorCode;
    private Long totalCount;
    private String errorMsg;

    public SearchResponse() {
    }

    public SearchResponse(List<SearchResponseData> data, String errorCode, Long totalCount, String errorMsg) {
        this.data = data;
        this.errorCode = errorCode;
        this.totalCount = totalCount;
        this.errorMsg = errorMsg;
    }

    public List<SearchResponseData> getData() {
        return data;
    }

    public void setData(List<SearchResponseData> data) {
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "SearchResponse{" +
                "data=" + data +
                ", errorCode='" + errorCode + '\'' +
                ", totalCount=" + totalCount +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
