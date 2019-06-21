package com.secusoft.web.model;

import java.io.Serializable;

/**
 *蓝色大屏浮框 分局系统使用次数
 * @author hbxing
 * @date 2019/6/20
 */
public class ScreenSystemUseNumber implements Serializable {
    private String orgName;
    private Integer number;

    public ScreenSystemUseNumber() {
    }

    public ScreenSystemUseNumber(String orgName, Integer number) {
        this.orgName = orgName;
        this.number = number;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "ScreenSystemUseNumber{" +
                "orgName='" + orgName + '\'' +
                ", number=" + number +
                '}';
    }
}
