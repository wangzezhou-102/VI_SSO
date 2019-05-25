package com.secusoft.web.Service.impl;

import com.secusoft.web.Service.BoxSelectService;
import com.secusoft.web.model.Point;
import com.secusoft.web.shipinapi.model.Camera;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BoxSelectServiceImpl implements BoxSelectService {
    @Override
    public Map<String, Object> isPtInPoly(List<Camera> cameras,List<Point> ps) {
        Double maxLon,minLon,maxLat,minLat;
        List<Double> lon=new ArrayList<>();
        List<Double> lat=new ArrayList<>();
        for (Point point : ps) {
            lon.add(point.getLongitude());
            lat.add(point.getLatitude());
        }
        maxLon= Collections.max(lon);
        minLon= Collections.min(lon);
        maxLat= Collections.max(lat);
        minLat= Collections.min(lat);
        //System.out.println(maxLon+"/"+minLon+"/"+maxLat+"/"+minLat);

        Map<String, Object> map = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        for (Camera camera : cameras) {
            Double ALon = Double.valueOf(camera.getLongitude());
            Double ALat = Double.valueOf(camera.getLatitude());

            int iSum, iCount, iIndex;
            double dLon1 = 0, dLon2 = 0, dLat1 = 0, dLat2 = 0, dLon;
            //最少三个点才能确定一个面
            if (ps.size() < 3) {
                return null;
            }
            iSum = 0;
            iCount = ps.size();
            for (iIndex = 0; iIndex < iCount; iIndex++) {
                //保证每次取到的都是相邻的点
                if (iIndex == iCount - 1) {
                    dLon1 = ps.get(iIndex).getLongitude();
                    dLat1 = ps.get(iIndex).getLatitude();
                    dLon2 = ps.get(0).getLongitude();
                    dLat2 = ps.get(0).getLatitude();
                } else {
                    dLon1 =  ps.get(iIndex).getLongitude();
                    dLat1 =  ps.get(iIndex).getLatitude();
                    dLon2 =  ps.get(iIndex+1).getLongitude();
                    dLat2 =  ps.get(iIndex+1).getLatitude();
                }
                // 以下语句判断A点是否在边的两端点的水平平行线之间，在则可能有交点，开始判断交点是否在左射线上
                if (((ALat >= dLat1) && (ALat < dLat2)) || ((ALat >= dLat2) && (ALat < dLat1))) {
                    if (Math.abs(dLat1 - dLat2) > 0) {
                        //得到 A点向左射线与边的交点的x坐标（斜率相同）：
                        dLon = dLon1 - ((dLon1 - dLon2) * (dLat1 - ALat)) / (dLat1 - dLat2);
                        // 如果交点在A点左侧（说明是做射线与 边的交点），则射线与边的全部交点数加一：
                        if (dLon < ALon) {
                            iSum++;
                        }
                    }
                }
            }
            if ((iSum % 2) != 0) {
                if (sb.length() == 0) {
                    sb.append(camera.getCameraId());
                } else {
                    sb.append(",");
                    sb.append(camera.getCameraId());
                }
            }
        }
        map.put("cameraId",sb);
        return map;
    }

    @Override
    public Map<String, Object> isPtInPoly2(List<Camera> cameras, Point point, Double radius) {
        Map<String, Object> map = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        for (Camera camera : cameras) {
            Double ALon = Double.valueOf(camera.getLongitude());
            Double ALat = Double.valueOf(camera.getLatitude());

            //返回所有平方和的   平方根
            double d = Math.hypot( (point.getLongitude() - ALon ), (point.getLatitude() - ALat) );
            if( d <= radius){
                if (sb.length() == 0) {
                    sb.append(camera.getCameraId());
                } else {
                    sb.append(",");
                    sb.append(camera.getCameraId());
                }
            }
        }
        map.put("cameraId",sb);
        return map;
    }


}
