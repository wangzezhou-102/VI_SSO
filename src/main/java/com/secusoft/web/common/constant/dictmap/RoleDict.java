package com.secusoft.web.common.constant.dictmap;

import com.secusoft.web.common.constant.dictmap.base.AbstractDictMap;

/**
 * 角色的字典
 *
 *
 * @date 2017-05-06 15:01
 */
public class RoleDict extends AbstractDictMap {

    @Override
    public void init() {
        put("roleId","角色名称");
        put("name","角色名称");
        put("ids","资源名称");
    }

    @Override
    protected void initBeWrapped() {
        putFieldWrapperMethodName("roleId","getSingleRoleName");
        putFieldWrapperMethodName("ids","getMenuNames");
    }
}
