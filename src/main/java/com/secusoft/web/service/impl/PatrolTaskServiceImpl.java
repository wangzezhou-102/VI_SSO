package com.secusoft.web.service.impl;


import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.core.exception.BussinessException;
import com.secusoft.web.mapper.*;
import com.secusoft.web.model.*;
import com.secusoft.web.service.PatrolTaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PatrolTaskServiceImpl implements PatrolTaskService {
    @Resource
    private PatrolTaskMapper patrolTaskMapper;
    @Resource
    private PatrolTimeTemplateMapper patrolTimeTemplateMapper;
    @Resource
    private PatrolTimeSegmentMapper patrolTimeSegmentMapper;
    @Resource
    private PatrolTaskRouteMapper patrolTaskRouteMapper;
    @Resource
    private PatrolRouteDeviceMapper patrolRouteDeviceMapper;
    @Resource
    private ViTaskDeviceMapper viTaskDeviceMapper;
    @Resource
    private ViTaskRepoMapper viTaskRepoMapper;

    @Override
    @Transactional
    public ResultVo insertPatrolTak(PatrolTaskRequest patrolTaskRequest) {
        PatrolTaskBean patrolTaskBean = new PatrolTaskBean();
        patrolTaskBean.setStatus(3);
        //任务名称重复异常
        List<PatrolTaskBean> patrolTaskBeans = patrolTaskMapper.selectPatrolTaskAll(patrolTaskBean);
        for (PatrolTaskBean patrolTaskBean1:patrolTaskBeans) {
            if(patrolTaskBean1.getPatrolName().equals(patrolTaskRequest.getPatrolName())){
                throw new BussinessException(BizExceptionEnum.REPEAT);
            }
        }
        //新增时间模板
        String timeTemplateId = UUID.randomUUID().toString().replaceAll("-","");
        PatrolTimeTemplateBean patrolTimeTemplateBean = new PatrolTimeTemplateBean();
        patrolTimeTemplateBean.setTimeTemplateId(timeTemplateId);
        patrolTimeTemplateBean.setCycle(patrolTaskRequest.getCycle());
        patrolTimeTemplateMapper.insertPatrolTimeTemplate(patrolTimeTemplateBean);
        //增加时间模板对应的时间段
        List<PatrolTimeSegmentBean> patrolTimeSegmentBeans = patrolTaskRequest.getPatrolTimeSegmentBeans();
        for (PatrolTimeSegmentBean patrolTimeSegmentBean:patrolTimeSegmentBeans) {
            if(patrolTaskRequest.getCycle() == 0){// 循环类型按日 0 添加日期信息，星期无意义
                patrolTimeSegmentBean.setTimeTemplateId(timeTemplateId);
                patrolTimeSegmentMapper.insertPatrolTimeSegment(patrolTimeSegmentBean);
            }
            if(patrolTaskRequest.getCycle() == 1){// 循环类型按周 添加日期信息 星期有意义
                patrolTimeSegmentBean.setTimeTemplateId(timeTemplateId);
                patrolTimeSegmentMapper.insertPatrolTimeSegment(patrolTimeSegmentBean);
            }
        }
        //新增任务 uuid
        String taskId = "p"+UUID.randomUUID().toString().replaceAll("-","");
        patrolTaskBean.setTaskId(taskId);
        patrolTaskBean.setStatus(0);
        patrolTaskBean.setEnable(0);
        patrolTaskBean.setBeginTime(new Date());
        patrolTaskBean.setCreateTime(new Date());
        patrolTaskBean.setPatrolName(patrolTaskRequest.getPatrolName());
        patrolTaskBean.setOrgCode(patrolTaskRequest.getOrgCode());
        patrolTaskBean.setCreateId(patrolTaskRequest.getUpdateId());
        patrolTaskBean.setUpdateId(patrolTaskRequest.getUpdateId());
        patrolTaskBean.setUpdateTime(new Date());
        patrolTaskBean.setTimeTemplateId(timeTemplateId);
        patrolTaskMapper.insertPatrolTask(patrolTaskBean);
        //关联任务预置路线(先删除关联路线)
        PatrolTaskRouteBean patrolTaskRouteBean = new PatrolTaskRouteBean();
        PatrolRouteDeviceBean patrolRouteDeviceBean = new PatrolRouteDeviceBean();
        patrolTaskRouteBean.setTaskId(taskId);
        //patrolTaskRouteMapper.deletePatrolTaskRoute(patrolTaskRouteBean);
        List<String> deviceIds = new ArrayList<>();//设备id集合（不重复）
        List<PatrolRouteBean> patrolRouteBeans = patrolTaskRequest.getPatrolRouteBeans();
        if(!patrolRouteBeans.isEmpty()){
            for (PatrolRouteBean patrolRouteBean:patrolRouteBeans) {
                patrolTaskRouteBean.setRouteId(patrolRouteBean.getId());
                patrolTaskRouteMapper.insertPatrolTaskRoute(patrolTaskRouteBean);
                //路线设备与任务完关联
                patrolRouteDeviceBean.setRouteId(patrolRouteBean.getId());
                List<PatrolRouteDeviceBean> patrolRouteDeviceBeans = patrolRouteDeviceMapper.selectPatrolRouteDeviceByRouteId(patrolRouteDeviceBean);
                for (PatrolRouteDeviceBean patrolRouteDeviceBean1:patrolRouteDeviceBeans) {
                    if(!deviceIds.contains(patrolRouteDeviceBean1.getDeviceId())){//路线关联设备 去重
                        deviceIds.add(patrolRouteDeviceBean1.getDeviceId());
                    }
                }
            }
        }
        //添加任务关联设备
        List<DeviceBean> deviceBeans = patrolTaskRequest.getDeviceBeans();
        for (DeviceBean deviceBean:deviceBeans) {//设备列表和路线重复设备 去重
            if(!deviceIds.contains(deviceBean.getDeviceId())){
                deviceIds.add(deviceBean.getDeviceId());
            }
        }
        ViTaskDeviceBean viTaskDeviceBean = new ViTaskDeviceBean();
        for (String deviceId:deviceIds) {
            viTaskDeviceBean.setTaskId(taskId);
            viTaskDeviceBean.setDeviceId(deviceId);
            viTaskDeviceBean.setStatus(2);
            viTaskDeviceBean.setAction(2);
            viTaskDeviceMapper.insertViTaskDevice(viTaskDeviceBean);
        }
        //关联任务布控库
        List<ViRepoBean> viRepoBeans = patrolTaskRequest.getViRepoBeans();
        ViTaskRepoBean viTaskRepoBean = new ViTaskRepoBean();
        for (ViRepoBean viRepoBean:viRepoBeans) {
            viTaskRepoBean.setTaskId(taskId);
            viTaskRepoBean.setRepoId(viRepoBean.getId());
            viTaskRepoMapper.insertViTaskRepo(viTaskRepoBean);
        }
        return ResultVo.success();
    }
    //巡逻任务编辑
    @Override
    @Transactional
    public ResultVo updatePatrolTask(PatrolTaskRequest patrolTaskRequest){
        PatrolTaskBean patrolTaskBean = new PatrolTaskBean();
        patrolTaskBean.setStatus(3);
        //任务名称重复异常
        List<PatrolTaskBean> patrolTaskBeans = patrolTaskMapper.selectPatrolTaskAll(patrolTaskBean);
        for (PatrolTaskBean patrolTaskBean1:patrolTaskBeans) {
            if(patrolTaskBean1.getPatrolName().equals(patrolTaskRequest.getPatrolName())){
                throw new BussinessException(BizExceptionEnum.REPEAT);
            }
        }
        //修改任务直接信息
        patrolTaskBean.setTaskId(patrolTaskRequest.getTaskId());
        patrolTaskBean.setPatrolName(patrolTaskRequest.getPatrolName());
        patrolTaskBean.setEnable(patrolTaskRequest.getEnable());
        patrolTaskBean.setUpdateId(patrolTaskRequest.getUpdateId());
        patrolTaskBean.setUpdateTime(new Date());
        if(patrolTaskBean.getEnable() == 1){//任务首次开启，任务下发，巡逻任务状态变为 1 执行中
            patrolTaskBean.setStatus(1);
        }
        patrolTaskMapper.updatePatrolTask(patrolTaskBean);
        //修改任务时间模板
        PatrolTimeTemplateBean patrolTimeTemplateBean = new PatrolTimeTemplateBean();
        patrolTimeTemplateBean.setCycle(patrolTaskRequest.getCycle());
        patrolTimeTemplateBean.setTimeTemplateId(patrolTaskRequest.getTimeTemplateId());
        patrolTimeTemplateMapper.updatePatrolTimeTemplate(patrolTimeTemplateBean);//修改循环周期
        PatrolTimeSegmentBean patrolTimeSegmentBean = new PatrolTimeSegmentBean();
        patrolTimeSegmentBean.setTimeTemplateId(patrolTaskRequest.getTimeTemplateId());
        patrolTimeSegmentMapper.deletePatrolTimeSegment(patrolTimeSegmentBean);//删除时间模板对应时间段
        //增加时间模板对应的时间段
        List<PatrolTimeSegmentBean> patrolTimeSegmentBeans = patrolTaskRequest.getPatrolTimeSegmentBeans();
        for (PatrolTimeSegmentBean patrolTimeSegmentBean1:patrolTimeSegmentBeans) {
            if(patrolTaskRequest.getCycle() == 0){// 循环类型按日 0 添加日期信息，星期无意义
                patrolTimeSegmentBean1.setTimeTemplateId(patrolTaskRequest.getTimeTemplateId());
                patrolTimeSegmentMapper.insertPatrolTimeSegment(patrolTimeSegmentBean1);
            }
            if(patrolTaskRequest.getCycle() == 1){// 循环类型按周 添加日期信息 星期有意义
                patrolTimeSegmentBean1.setTimeTemplateId(patrolTaskRequest.getTimeTemplateId());
                patrolTimeSegmentMapper.insertPatrolTimeSegment(patrolTimeSegmentBean1);
            }
        }
        //关联任务预置路线(先删除关联路线)
        PatrolTaskRouteBean patrolTaskRouteBean = new PatrolTaskRouteBean();
        PatrolRouteDeviceBean patrolRouteDeviceBean = new PatrolRouteDeviceBean();
        patrolTaskRouteBean.setTaskId(patrolTaskRequest.getTaskId());
        patrolTaskRouteMapper.deletePatrolTaskRoute(patrolTaskRouteBean);
        List<String> deviceIds = new ArrayList<>();//设备id集合（不重复）
        List<PatrolRouteBean> patrolRouteBeans = patrolTaskRequest.getPatrolRouteBeans();
        if(!patrolRouteBeans.isEmpty()){
            for (PatrolRouteBean patrolRouteBean:patrolRouteBeans) {
                patrolTaskRouteBean.setRouteId(patrolRouteBean.getId());
                patrolTaskRouteMapper.insertPatrolTaskRoute(patrolTaskRouteBean);
                //路线设备与任务 关联
                patrolRouteDeviceBean.setRouteId(patrolRouteBean.getId());
                List<PatrolRouteDeviceBean> patrolRouteDeviceBeans = patrolRouteDeviceMapper.selectPatrolRouteDeviceByRouteId(patrolRouteDeviceBean);
                for (PatrolRouteDeviceBean patrolRouteDeviceBean1:patrolRouteDeviceBeans) {
                    if(!deviceIds.contains(patrolRouteDeviceBean1.getDeviceId())){//路线关联设备 去重
                        deviceIds.add(patrolRouteDeviceBean1.getDeviceId());
                    }
                }
            }
        }
        //添加任务关联设备（先删除）
        List<DeviceBean> deviceBeans = patrolTaskRequest.getDeviceBeans();
        for (DeviceBean deviceBean:deviceBeans) {//设备列表和路线重复设备 去重
            if(!deviceIds.contains(deviceBean.getDeviceId())){
                deviceIds.add(deviceBean.getDeviceId());
            }
        }
        viTaskDeviceMapper.deleteViTaskDeviceByTaskId(patrolTaskRequest.getTaskId());//删除之前任务关联所有设备信息，重新添加
        ViTaskDeviceBean viTaskDeviceBean = new ViTaskDeviceBean();
        for (String deviceId:deviceIds) {
            viTaskDeviceBean.setTaskId(patrolTaskRequest.getTaskId());
            viTaskDeviceBean.setDeviceId(deviceId);
            if(patrolTaskRequest.getEnable() == 1){//设备状态 调整不清楚 （后面完成）
                viTaskDeviceBean.setAction(1);
                viTaskDeviceBean.setStatus(1);
            }
            if(patrolTaskRequest.getEnable() == 0){
                viTaskDeviceBean.setAction(0);
                viTaskDeviceBean.setStatus(1);
            }
            viTaskDeviceMapper.insertViTaskDevice(viTaskDeviceBean);
        }
        //关联任务布控库
        viTaskRepoMapper.deleteViTaskRepoByTaskId(patrolTaskRequest.getTaskId());
        List<ViRepoBean> viRepoBeans = patrolTaskRequest.getViRepoBeans();
        ViTaskRepoBean viTaskRepoBean = new ViTaskRepoBean();
        for (ViRepoBean viRepoBean:viRepoBeans) {
            viTaskRepoBean.setTaskId(patrolTaskRequest.getTaskId());
            viTaskRepoBean.setRepoId(viRepoBean.getId());
            viTaskRepoMapper.insertViTaskRepo(viTaskRepoBean);
        }
        return ResultVo.success();
    }
    //巡逻任务删除
    public ResultVo deletePatrolTask(PatrolTaskBean patrolTaskBean){
        if(patrolTaskBean.getStatus() == 0){
            patrolTaskMapper.deletePatrolTask(patrolTaskBean);
        }
        return ResultVo.success();
    }
    //巡逻任务查询
    public ResultVo selectPatrolTask(PatrolTaskBean patrolTaskBean){

        return ResultVo.success();
    }
    //查询多条巡逻任务
    public ResultVo selectPatrolTaskAll( ){
        PatrolTaskBean patrolTaskBean = new PatrolTaskBean();
        patrolTaskBean.setStatus(3);
        List<PatrolTaskBean> patrolTaskBeans = patrolTaskMapper.selectPatrolTaskAll(patrolTaskBean);
        return ResultVo.success(patrolTaskBeans);
    }

}
