package com.secusoft.web.mapper;

import com.secusoft.web.model.SysOrgRoadBean;

/**
 * @author chjiang
 * @since 2019/6/18 20:02
 */
public interface SysOrgRoadMapper {

    /**
     * 获取对应组织算力相关数据
     * @param sysOrgRoadBean
     * @return
     */
    SysOrgRoadBean getSysOrgRoadByOrgCode(SysOrgRoadBean sysOrgRoadBean);
}
