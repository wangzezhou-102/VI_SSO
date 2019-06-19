package com.secusoft.web.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * 组织支持并发路数mapper
 * @author hbxing
 * @company 视在数科
 * @date 2019年6月18日
 */

public interface SysOrganizationRoadMapper {
    Integer selectOrgRoadByOrgCode(@Param("orgCode")String orgCode);
}
