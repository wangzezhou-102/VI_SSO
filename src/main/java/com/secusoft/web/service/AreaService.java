package com.secusoft.web.service;

import com.secusoft.web.model.Area;
import com.secusoft.web.model.ResultVo;
import org.springframework.http.ResponseEntity;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.List;

public interface AreaService {
    ResultVo addArea(Area area, List deviceIds);
    ResultVo removeArea(Area area);
    ResultVo updateAreaName(Area area);
    ResultVo updateArea(Area area,List deviceIds);
    ResultVo readArea(Area area);
}
