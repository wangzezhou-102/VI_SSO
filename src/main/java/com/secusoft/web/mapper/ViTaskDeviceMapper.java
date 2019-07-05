package com.secusoft.web.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.secusoft.web.model.ViTaskDeviceBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chjiang
 * @since 2019/6/6 14:23
 */
public interface ViTaskDeviceMapper extends BaseMapper<ViTaskDeviceBean> {

    /**
     * 添加一条布控任务与设备关联表
     */
    void insertViTaskDevice(ViTaskDeviceBean viTaskDeviceBean);


    /**
     * 更新一条布控任务与设备关联表
     */
    void updateViTaskDevice(ViTaskDeviceBean viTaskDeviceBean);


    /**
     * 删除一条布控任务与设备关联表
     */
    void deleteViTaskDevice(@Param("id") Integer id);

    /**
     * 删除布控任务相关所有设备 关联表
     */
    void deleteViTaskDeviceByTaskId(@Param("taskId") String taskId);
    /**
     * 批量插入
     * @param list
     */
    void insertBatch(List<ViTaskDeviceBean> list);

    /**
     * 统计已执行布控任务的设备数
     * @return
     */
    Integer taskDeviceCount();

    /**
     * 根据相关条件获取所关联的设备信息
     * @param viTaskDeviceBean
     * @return
     */
    List<ViTaskDeviceBean> getViTaskDeviceByObject(ViTaskDeviceBean viTaskDeviceBean);

    /**
     * 根据id获取对应的设备信息
     * @param viTaskDeviceBean
     * @return
     */
    List<ViTaskDeviceBean> getAllViTaskDevice(ViTaskDeviceBean viTaskDeviceBean);

    /**
     * 批量删除关联设备
     * @param list
     */
    void delBatchViTaskDevice(List<ViTaskDeviceBean> list);

    /**
     * 获取活动中的设备
     * @param deviceId
     * @return
     */
    List<ViTaskDeviceBean> getActionDevice(@Param("deviceId") String deviceId);
    /**
     * 获取任务关联设备
     * @param taskId
     * @return
     */
    List<ViTaskDeviceBean> selectViTaskRepoByTaskId(@Param("taskId") String taskId);

}
