package com.secusoft.web.Service;

import com.secusoft.web.model.Point;
import com.secusoft.web.shipinapi.model.Camera;

import java.util.List;
import java.util.Map;

public interface BoxSelectService {

     Map<String,Object> isPtInPoly( List<Camera> cameras,List<Point> points);

     Map<String,Object> isPtInPoly2(List<Camera> cameras,Point point,Double radius);
}
