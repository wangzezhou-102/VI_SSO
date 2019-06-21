package com.secusoft.web.service.impl;

import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.core.util.StringUtils;
import com.secusoft.web.mapper.DeviceMapper;
import com.secusoft.web.model.DeviceBean;
import com.secusoft.web.model.PointBean;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.BoxSelectService;
import com.secusoft.web.shipinapi.model.Camera;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class BoxSelectServiceImpl implements BoxSelectService {
    @Resource
    private DeviceMapper deviceMapper;

    @Override
    public ResultVo isPtInPoly(List<PointBean> ps) {
        //获取设备列表待改进
        DeviceBean deviceBean = new DeviceBean();
        List<DeviceBean> devices = deviceMapper.readDeviceList(deviceBean);
        //List<Camera> cameras=JSON.parseObject(CameraServiceImpl.getAllCamera().toString(),new TypeReference<ArrayList<Camera>>(){});
        //List<Camera> cameraList = new ArrayList<>();
        //JSON.parseObject(CameraServiceImpl.getAllCamera().toString(),(Class<ArrayList<Camera>>) cameraList.getClass());
        Double maxLon,minLon,maxLat,minLat;
        List<Double> lon=new ArrayList<>();
        List<Double> lat=new ArrayList<>();
        for (PointBean pointBean : ps) {
            lon.add(pointBean.getLongitude());
            lat.add(pointBean.getLatitude());
        }
        maxLon= Collections.max(lon);
        minLon= Collections.min(lon);
        maxLat= Collections.max(lat);
        minLat= Collections.min(lat);

        ArrayList<DeviceBean> deviceBeans = new ArrayList<>();
        for (DeviceBean camera : devices) {
            if(StringUtils.isNotEmpty(camera.getLongitude())&&StringUtils.isNotEmpty(camera.getLatitude())) {
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
                        dLon1 = ps.get(iIndex).getLongitude();
                        dLat1 = ps.get(iIndex).getLatitude();
                        dLon2 = ps.get(iIndex + 1).getLongitude();
                        dLat2 = ps.get(iIndex + 1).getLatitude();
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
                    //cameraIds.add(camera.getDeviceId());
                    deviceBeans.add(camera);
                }
            }
        }

        return ResultVo.success(deviceBeans);
    }

    @Override
    public ResultVo isPtInPoly2(PointBean pointBean, Double radius) {
        DeviceBean deviceBean = new DeviceBean();
        List<DeviceBean> cameras = deviceMapper.readDeviceList(deviceBean);
        ArrayList<DeviceBean> deviceBeans = new ArrayList<>();
        if(pointBean==null){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(),BizExceptionEnum.PARAM_NULL.getMessage());
        }
        for (DeviceBean camera : cameras) {
            if(StringUtils.isNotEmpty(camera.getLongitude())&&StringUtils.isNotEmpty(camera.getLatitude())) {
                Double ALon = Double.valueOf(camera.getLongitude());
                Double ALat = Double.valueOf(camera.getLatitude());
                //返回所有平方和的   平方根
                double d = Math.hypot((pointBean.getLongitude() - ALon), (pointBean.getLatitude() - ALat));
                //点到圆心的范围小于半径 即在圆内
                if (d <= radius) {
                    deviceBeans.add(camera);
                }
            }
        }
        return ResultVo.success(deviceBeans,Long.valueOf(deviceBeans.size()));
    }


}
