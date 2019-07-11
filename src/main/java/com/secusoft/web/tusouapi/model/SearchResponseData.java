package com.secusoft.web.tusouapi.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 图搜返回接口Data字段实体
 * @author huanghao
 * @date 2019-06-12
 */
public class SearchResponseData {
    @JSONField(name="_index")
    private String index;

    @JSONField(name="_type")
    private String type;

    @JSONField(name="_source")
    private SearchSource source;

    @JSONField(name="_id")
    private String id;

    @JSONField(name="_score")
    private Double score;

    @JSONField(name="_ext")
    private String ext;

    // 是否已收藏
    private int status;
    /**
     * 保证返回阿里格式的同时拥有自己的id
     */
    private String szId;
    public SearchResponseData() {
    }

    public SearchResponseData(String index, String type, SearchSource source, String id, Double score, String ext, String szId) {
        this.index = index;
        this.type = type;
        this.source = source;
        this.id = id;
        this.score = score;
        this.ext = ext;
        this.szId = szId;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SearchSource getSource() {
        return source;
    }

    public void setSource(SearchSource source) {
        this.source = source;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getSzId() {
        return szId;
    }

    public void setSzId(String szId) {
        this.szId = szId;
    }

    public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "SearchResponseData [index=" + index + ", type=" + type + ", source=" + source + ", id=" + id
				+ ", score=" + score + ", ext=" + ext + ", status=" + status + ", szId=" + szId + "]";
	}
}
