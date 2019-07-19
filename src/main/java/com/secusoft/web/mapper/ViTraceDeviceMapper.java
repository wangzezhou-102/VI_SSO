package com.secusoft.web.mapper;

import com.secusoft.web.model.ViRealtimeTraceBean;
import com.secusoft.web.model.ViTaskDeviceBean;
import com.secusoft.web.model.ViTraceDeviceBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ViTraceDeviceMapper {


    void insertViTraceDevice(ViTraceDeviceBean bean);

    void updateViTraceDevice(ViTraceDeviceBean bean);

    /**
     * 删除一条实时追踪任务
     */
    void delViTraceDevice(@Param("id") Integer id);
    /**
     * 批量插入
     * @param list
     */
    void insertBatch(List<ViTraceDeviceBean> list);

    List<ViTraceDeviceBean> getAllViTraceDevice(ViTraceDeviceBean bean);

    /**
     * 批量删除关联设备
     * @param list
     */
    void delBatchViTaskDevice(List<ViTraceDeviceBean> list);
}
