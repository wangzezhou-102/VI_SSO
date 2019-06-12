package com.secusoft.web.mapper;

import com.secusoft.web.model.AreaBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AreaMapper {
    int insertArea(AreaBean areaBean);
    int insertAreaDevice(@Param("ids") List ids, @Param("areaid") String areaid);
    int selectCountAreaByName(AreaBean areaBean);
    int updateAreaNameById(AreaBean are);
    int deleteAreaById(String id);
    int deleteAreaDeviceById(String id);
    List<AreaBean> selectAreaByFid(String fid);

}
