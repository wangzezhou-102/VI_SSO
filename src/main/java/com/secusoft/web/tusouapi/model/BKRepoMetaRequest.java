package com.secusoft.web.tusouapi.model;

/**
 * 获取布控库的meta信息request参数
 */
public class BKRepoMetaRequest{

    /**
     * 布控库id
     */
    private String bkid;

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

    
}