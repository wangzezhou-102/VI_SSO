package com.secusoft.web.tusouapi.model;

import com.alibaba.fastjson.JSONObject;

/**
 * 添加布控目标request参数
 */
public class BKMemberAddRequest{

    /**
     * [必须]布控库id
     */
    private String bkid;
    /**
     * [必须]布控⽬标id，⼊库的主键
     */
    private String objectId;
    /**
     * 图像内容; base64 编码;与 ossUrls ⼆选⼀;优先级:ossUrls>contents
     */
    private String contents;
    /**
     * 图像内容; base64 编码;与 ossUrls ⼆选⼀;优先级:ossUrls>contents
     */
    private String ossUrl;
    /**
     * 图像特征信息
     */
    private String feature;
    /**
     * 布控属性 根据type动态指定 在算法⽀持的类型中会定义相关的字段 
     */
    private JSONObject attribute;

    /**
     * @return the bkid
     */
    public String getBkid() {
        return bkid;
    }

    /**
     * @param bkid the bkid to set
     */
    public void setBkid(String bkid) {
        this.bkid = bkid;
    }

    /**
     * @return the objectId
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     * @param objectId the objectId to set
     */
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    /**
     * @return the contents
     */
    public String getContents() {
        return contents;
    }

    /**
     * @param contents the contents to set
     */
    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getOssUrl() {
        return ossUrl;
    }

    public void setOssUrl(String ossUrl) {
        this.ossUrl = ossUrl;
    }

    /**
     * @return the feature
     */
    public String getFeature() {
        return feature;
    }

    /**
     * @param feature the feature to set
     */
    public void setFeature(String feature) {
        this.feature = feature;
    }

    /**
     * @return the attribute
     */
    public JSONObject getAttribute() {
        return attribute;
    }

    /**
     * @param attribute the attribute to set
     */
    public void setAttribute(JSONObject attribute) {
        this.attribute = attribute;
    }


    
}