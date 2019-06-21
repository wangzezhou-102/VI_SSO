package com.secusoft.web.mapper;

import com.secusoft.web.model.ScreenSysOrgRoadBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 组织支持并发路数mapper
 * @author hbxing
 * @company 视在数科
 * @date 2019年6月18日
 */

public interface SysOrganizationRoadMapper {
    Integer selectOrgRoadByOrgCode(@Param("orgCode")String orgCode);
    Integer selectOrgUseRoadByOrgCode(@Param("orgCode")String orgCode);
    List<ScreenSysOrgRoadBean> selectSysOrgRoadBean(@Param("patentCode") String patentCode);
}
