package com.secusoft.web.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.secusoft.web.model.DeviceBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceMapper extends BaseMapper<DeviceBean> {
    List<DeviceBean> selectDeviceByPid(String id);
    DeviceBean selectDeviceById(String id);
    DeviceBean selectDeviceByDeviceId(String deviceId);
    List<DeviceBean> selectDeviceByAreaId(String id);

    List<DeviceBean> readDeviceList(DeviceBean queryBean);

    void insetBatchDevice(List<DeviceBean> devices);
    
    void deleteDeviceTable();
    
    void deleteOrgDevice();

    /**
     * 当前账号权限拥有视频监控路数
     * @author hbxing
     * @company 视在数科
     * @date 2019年6月20日
     */
    Integer selectDeviceNumberByCivilCodes(@Param("orgIds") List<String> orgIds);
}
