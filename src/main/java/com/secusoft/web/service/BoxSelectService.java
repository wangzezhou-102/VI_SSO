package com.secusoft.web.service;

import com.secusoft.web.model.DeviceBean;
import com.secusoft.web.model.PointBean;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.shipinapi.model.Camera;

import java.util.List;
import java.util.Map;

public interface BoxSelectService {

     ResultVo isPtInPoly(List<PointBean> pointBeans);

     ResultVo isPtInPoly2(PointBean pointBean, Double radius);
}
