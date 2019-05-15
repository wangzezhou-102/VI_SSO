package com.secusoft.web.tusouapi.model;

/**
 * 删除布控目标request参数
 */
public class BKMemberDeleteRequest{

    /**
     * [必须]布控库id
     */
    private String bkid;
    /**
     * 布控⽬标id⽬标列表, 以逗号","分隔
     */
    private String objectIds;

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
     * @return the objectIds
     */
    public String getObjectIds() {
        return objectIds;
    }

    /**
     * @param objectIds the objectIds to set
     */
    public void setObjectIds(String objectIds) {
        this.objectIds = objectIds;
    }


    

}