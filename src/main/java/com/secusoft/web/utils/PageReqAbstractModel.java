package com.secusoft.web.utils;

import com.baomidou.mybatisplus.annotations.TableField;

import java.io.Serializable;

/**
 * @author jch
 * Util类 - 分页工具类
 * Util类 - 分页工具类
 * @version PageReqAbstractModel.java, v 0.1 jch Exp $
 */
public abstract class PageReqAbstractModel implements Serializable {

    public static final Integer MAX_PAGE_SIZE = 500;// 每页最大记录数限制

    @TableField(exist = false)
    private int current = 1;// 当前页码current
    @TableField(exist = false)
    private int size = 10;// 每页记录数size
    @TableField(exist = false)
    private int thisSize;//当前记录数

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if (current < 1) {
            current = 1;
        }
        this.current = current;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        if (size < 1) {
            size = 1;
        } else if (size > MAX_PAGE_SIZE) {
            size = MAX_PAGE_SIZE;
        }
        this.size = size;
    }

    public int getThisSize() {
        thisSize = (current - 1) * size;
        return thisSize;
    }

    public void setThisSize(int thisSize) {
        this.thisSize = thisSize;
    }
}