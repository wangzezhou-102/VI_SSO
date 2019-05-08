package com.secusoft.web.common.constant.dictmap;

import com.secusoft.web.common.constant.dictmap.base.AbstractDictMap;

/**
 * 部门的映射
 *
 *
 * @date 2017-05-06 15:01
 */
public class DeptDict extends AbstractDictMap {

    @Override
    public void init() {
        put("deptId", "部门名称");
        put("pid", "上级名称");
        put("fullname", "部门全称");
        put("comments", "描述");
    }

    @Override
    protected void initBeWrapped() {
        putFieldWrapperMethodName("deptId", "getDeptName");
        putFieldWrapperMethodName("pid", "getDeptName");
    }
}
