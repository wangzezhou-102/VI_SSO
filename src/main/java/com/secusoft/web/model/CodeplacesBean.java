package com.secusoft.web.model;

import java.util.List;

/**
 * @author huanghao
 * @date 2019-07-03
 */
public class CodeplacesBean {

    private String typeCode;

    private List<Integer> place;

    public CodeplacesBean() {
    }

    public CodeplacesBean(String typeCode, List<Integer> place) {
        this.typeCode = typeCode;
        this.place = place;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public List<Integer> getPlace() {
        return place;
    }

    public void setPlace(List<Integer> place) {
        this.place = place;
    }
}
