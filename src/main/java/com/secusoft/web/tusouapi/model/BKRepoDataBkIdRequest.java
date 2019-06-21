package com.secusoft.web.tusouapi.model;

/**
 *
 */
public class BKRepoDataBkIdRequest {

    /**
     * [非必须]布控库id ，
     * 查看指定布控库信息，
     * 不传默认查看所有布控
     * 库信息,
     * ⽀持多个布控库id，
     * 多个⽤逗号','隔开
     */
    private String bkid;

    public String getBkid() {
        return bkid;
    }

    public void setBkid(String bkid) {
        this.bkid = bkid;
    }
}
