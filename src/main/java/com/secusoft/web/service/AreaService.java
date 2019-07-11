package com.secusoft.web.service;

import com.secusoft.web.model.AreaBean;
import com.secusoft.web.model.ResultVo;

import java.util.List;

public interface AreaService {
    ResultVo addArea(AreaBean areaBean, List deviceIds);
    ResultVo removeArea(AreaBean areaBean);
    ResultVo updateAreaName(AreaBean areaBean);
    ResultVo updateArea(AreaBean areaBean, List deviceIds);
    ResultVo readArea(AreaBean areaBean);
    ResultVo removeAreaByFolderId(Integer folderId);
}
