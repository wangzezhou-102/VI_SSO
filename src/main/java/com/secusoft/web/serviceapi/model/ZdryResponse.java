package com.secusoft.web.serviceapi.model;

import com.secusoft.web.model.ZdryBean;

import java.util.List;

/**
 * @author chjiang
 * @since 2019/6/25 14:15
 */
public class ZdryResponse {

    private Integer total;

    private Integer current;

    private Integer pages;

    private Integer size;

    private List<ZdryBean> records;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<ZdryBean> getRecords() {
        return records;
    }

    public void setRecords(List<ZdryBean> records) {
        this.records = records;
    }
}
