package com.secusoft.web.model;

import com.baomidou.mybatisplus.annotations.TableField;

import java.io.Serializable;

/**
 * 分页BEAN
 * @author ChenDong
 * @company 视在数科
 * @date 2019年5月28日
 */
public class Page implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // 行数
    @TableField(exist = false)
    private Integer pageSize;
    
    // 当前页数
    @TableField(exist = false)
    private Integer pageNumber;
    
    // 总页数
    @TableField(exist = false)
    private Long total;

    // 总数
    @TableField(exist = false)
    private Long count;


    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Page [pageSize=" + pageSize + ", pageNumber=" + pageNumber + ", total=" + total + ", count=" + count
                + "]";
    }

    
    
}
