package com.secusoft.web.mapper;

import com.secusoft.web.model.Area;
import com.secusoft.web.model.Device;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AreaMapper {
    int insertArea(Area area);
    int insertAreaDevice(@Param("ids") List ids, @Param("areaid") String areaid);
    int selectCountAreaByName(Area area);
    int updateAreaNameById(Area are);
    int deleteAreaById(String id);
    int deleteAreaDeviceById(String id);

}
