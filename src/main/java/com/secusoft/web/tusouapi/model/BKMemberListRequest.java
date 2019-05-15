package com.secusoft.web.tusouapi.model;

import com.alibaba.fastjson.JSONObject;

/**
 * 获取当前⽬标库的布控⽬标request参数
 */
public class BKMemberListRequest {

    /**
     * [必须]布控库id
     */
    private String bkid;
    /**
     * 布控⽬标id，⼊库的主键
     */
    private String objectId;

    /**
     * 布控属性 根据type动态指定 在算法⽀持的类型中会定义相关的字段
     */
    private JSONObject attribute;

    /**
     * 查询开始位置，默认0
     */
    private int from;

    /**
     * 查询返回结果数量, 默认10
     */
    private int size;

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

    /**
     * @return the from
     */
    public int getFrom() {
        return from;
    }

    /**
     * @param from the from to set
     */
    public void setFrom(int from) {
        this.from = from;
    }

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }

    
}