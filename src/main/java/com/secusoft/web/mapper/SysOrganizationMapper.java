package com.secusoft.web.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.secusoft.web.model.DeviceBean;
import com.secusoft.web.model.SysOrganizationBean;

import java.util.List;

/**
 * 组织mapper
 * @author ChenDong
 * @company 视在数科
 * @date 2019年6月12日
 */
@Mapper
public interface SysOrganizationMapper extends BaseMapper<SysOrganizationBean> {

    List<SysOrganizationBean> readOrgDeviceTree(SysOrganizationBean queryBean);

    List<SysOrganizationBean> readOrgChildren(@Param("orgId") String orgId);

    List<DeviceBean> readDeviceList(@Param("orgId") String orgId);

    String readOrgNameByOrgCode(@Param("orgId") String orgId);
}
