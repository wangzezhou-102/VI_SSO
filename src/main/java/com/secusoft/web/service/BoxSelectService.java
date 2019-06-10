package com.secusoft.web.service;

import com.secusoft.web.model.PointBean;
import com.secusoft.web.shipinapi.model.Camera;

import java.util.List;
import java.util.Map;

public interface BoxSelectService {

     Map<String,Object> isPtInPoly( List<Camera> cameras,List<PointBean> pointBeans);

     Map<String,Object> isPtInPoly2(List<Camera> cameras, PointBean pointBean, Double radius);
}
