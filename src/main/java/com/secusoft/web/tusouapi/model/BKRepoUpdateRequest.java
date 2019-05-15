package com.secusoft.web.tusouapi.model;

/**
 * 更新布控库request参数
 */
public class BKRepoUpdateRequest{

    /**
     * 布控库id
     */
    private String bkid;
    /**
     * 布控库相关信息
     */
    private BKRepoMeta meta;

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
     * @return the meta
     */
    public BKRepoMeta getMeta() {
        return meta;
    }

    /**
     * @param meta the meta to set
     */
    public void setMeta(BKRepoMeta meta) {
        this.meta = meta;
    }


    
}