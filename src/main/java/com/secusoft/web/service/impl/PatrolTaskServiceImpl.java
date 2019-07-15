package com.secusoft.web.service.impl;

import com.secusoft.web.config.BkrepoConfig;
import com.secusoft.web.config.NormalConfig;
import com.secusoft.web.config.ServiceApiConfig;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.core.exception.BussinessException;
import com.secusoft.web.mapper.*;
import com.secusoft.web.model.*;
import com.secusoft.web.service.PatrolTaskService;
import com.secusoft.web.serviceapi.ServiceApiClient;
import com.secusoft.web.serviceapi.model.BaseResponse;
import com.secusoft.web.task.PatrolStartTask;
import com.secusoft.web.task.PatrolStopTask;
import com.secusoft.web.task.PatrolVideoStreamStartTask;
import com.secusoft.web.task.PatrolVideoStreamStopTask;
import com.secusoft.web.tusouapi.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class PatrolTaskServiceImpl implements PatrolTaskService {
    private static Logger logger = LoggerFactory.getLogger(PatrolTaskServiceImpl.class);
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
    @Resource
    private PatrolRouteMapper patrolRouteMapper;
    @Resource
    private SysOrgRoadMapper sysOrgRoadMapper;
    @Resource
    BkrepoConfig bkrepoConfig;

    @Override
    @Transactional
    public ResultVo insertPatrolTak(PatrolTaskRequest patrolTaskRequest) {
        //判断算力
       /* if (!validUpdateCalute(patrolTaskRequest)) {
            return ResultVo.failure(BizExceptionEnum.TASK_CALUATE_FAIL.getCode(), BizExceptionEnum.TASK_CALUATE_FAIL.getMessage());
        }*/
        PatrolTaskBean patrolTaskBean = new PatrolTaskBean();
        patrolTaskBean.setValidState(0);
        //任务名称重复异常
        List<PatrolTaskBean> patrolTaskBeans = patrolTaskMapper.selectPatrolTaskAll(patrolTaskBean);
        for (PatrolTaskBean patrolTaskBean1:patrolTaskBeans) {
            if(patrolTaskBean1.getPatrolName().equals(patrolTaskRequest.getPatrolName())){
                throw new BussinessException(BizExceptionEnum.REPEAT);
            }
        }
        //新增时间模板
        String timeTemplateId = UUID.randomUUID().toString().replaceAll("-","").toLowerCase();
        PatrolTimeTemplateBean patrolTimeTemplateBean = new PatrolTimeTemplateBean();
        patrolTimeTemplateBean.setTimeTemplateId(timeTemplateId);
        patrolTimeTemplateBean.setCycle(patrolTaskRequest.getCycle());
        patrolTimeTemplateMapper.insertPatrolTimeTemplate(patrolTimeTemplateBean);
        //增加时间模板对应的时间段
        List<PatrolTimeSegmentBean> patrolTimeSegmentBeans = patrolTaskRequest.getPatrolTimeSegments();
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
        //新增任务 32uuid
        String taskId = "p"+UUID.randomUUID().toString().replaceAll("-","").toLowerCase();
        patrolTaskBean.setTaskId(taskId);
        patrolTaskBean.setStatus(0);
        patrolTaskBean.setEnable(0);
        patrolTaskBean.setValidState(1);
        patrolTaskBean.setEndState(0);
        patrolTaskBean.setBeginTime(new Date());
        patrolTaskBean.setCreateTime(new Date());
        patrolTaskBean.setPatrolName(patrolTaskRequest.getPatrolName());
        patrolTaskBean.setOrgCode(patrolTaskRequest.getOrgCode());
        patrolTaskBean.setCreateId(patrolTaskRequest.getUpdateId());
        patrolTaskBean.setUpdateId(patrolTaskRequest.getUpdateId());
        patrolTaskBean.setUpdateTime(new Date());
        patrolTaskBean.setTimeTemplateId(timeTemplateId);
        patrolTaskMapper.insertPatrolTask(patrolTaskBean);
        //关联任务预置路线
        PatrolTaskRouteBean patrolTaskRouteBean = new PatrolTaskRouteBean();
        PatrolRouteDeviceBean patrolRouteDeviceBean = new PatrolRouteDeviceBean();
        patrolTaskRouteBean.setTaskId(taskId);
        List<String> deviceIds = new ArrayList<>();//设备id集合（不重复）
        List<PatrolRouteBean> patrolRouteBeans = patrolTaskRequest.getPatrolRoutes();
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
        List<DeviceBean> deviceBeans = patrolTaskRequest.getDevices();
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
        List<ViRepoBean> viRepoBeans = patrolTaskRequest.getRepoes();
        ViTaskRepoBean viTaskRepoBean = new ViTaskRepoBean();
        for (ViRepoBean viRepoBean:viRepoBeans) {
            viTaskRepoBean.setTaskId(taskId);
            viTaskRepoBean.setRepoId(viRepoBean.getId());
            viTaskRepoMapper.insertViTaskRepo(viTaskRepoBean);
        }
       /* //下发布控任务创建
        BaseResponse baseResponse = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBktaskSubmit(), BktaskSubmit(patrolTaskBean));
        String errorCode = baseResponse.getCode();
        String errorMsg = baseResponse.getMessage();
        //判断布控任务添加是否成功
        if (!String.valueOf(BizExceptionEnum.OK.getCode()).equals(errorCode)) {
            throw new RuntimeException(StringUtils.hasLength(errorMsg) ? errorMsg : "巡逻任务添加失败！");
        }*/
        //定时任务开启
        TQTimeTask(patrolTaskBean);

        return ResultVo.success();
    }
    //巡逻任务编辑
    @Override
    @Transactional
    public ResultVo updatePatrolTask(PatrolTaskRequest patrolTaskRequest){
        //判断算力
        if (!validUpdateCalute(patrolTaskRequest)) {
            return ResultVo.failure(BizExceptionEnum.TASK_CALUATE_UPDATE_FAIL.getCode(), BizExceptionEnum.TASK_CALUATE_UPDATE_FAIL.getMessage());
        }
        PatrolTaskBean patrolTaskBean = new PatrolTaskBean();
        patrolTaskBean.setValidState(0);
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
        if(patrolTaskBean.getEnable() == 2){//任务首次开启，任务下发,
            //patrolTaskBean.setStatus(1);
            patrolTaskBean.setEndState(1);
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
        List<PatrolTimeSegmentBean> patrolTimeSegmentBeans = patrolTaskRequest.getPatrolTimeSegments();
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
        List<PatrolRouteBean> patrolRouteBeans = patrolTaskRequest.getPatrolRoutes();
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
        List<DeviceBean> deviceBeans = patrolTaskRequest.getDevices();
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
        List<ViRepoBean> viRepoBeans = patrolTaskRequest.getRepoes();
        ViTaskRepoBean viTaskRepoBean = new ViTaskRepoBean();
        for (ViRepoBean viRepoBean:viRepoBeans) {
            viTaskRepoBean.setTaskId(patrolTaskRequest.getTaskId());
            viTaskRepoBean.setRepoId(viRepoBean.getId());
            viTaskRepoMapper.insertViTaskRepo(viTaskRepoBean);
        }
        //判断任务是否开启，并且设备列表和布控库列表是否一致，否则任务重新下发

        return ResultVo.success();
    }

    //巡逻任务查询
    @Override
    @Transactional
    public ResultVo selectPatrolTask(PatrolTaskBean patrolTaskBean){
        //获取任务直接信息
        PatrolTaskBean patrolTaskBean1 = patrolTaskMapper.selectPatrolTaskByPrimaryKey(patrolTaskBean);
        PatrolTaskRequest patrolTaskRequest = new PatrolTaskRequest();
        patrolTaskRequest.setTaskId(patrolTaskBean1.getTaskId());
        patrolTaskRequest.setPatrolName(patrolTaskBean1.getPatrolName());
        patrolTaskRequest.setEnable(patrolTaskBean1.getEnable());
        patrolTaskRequest.setStatus(patrolTaskBean1.getStatus());
        patrolTaskRequest.setTimeTemplateId(patrolTaskBean1.getTimeTemplateId());
        //获取时间模板信息
        PatrolTimeTemplateBean patrolTimeTemplateBean = new PatrolTimeTemplateBean();
        patrolTimeTemplateBean.setTimeTemplateId(patrolTaskBean1.getTimeTemplateId());
        PatrolTimeTemplateBean patrolTimeTemplateBean1 = patrolTimeTemplateMapper.selectPatroltTimeTemplate(patrolTimeTemplateBean);
        patrolTaskRequest.setPatrolTimeSegments(patrolTimeTemplateBean1.getPatrolTimeSegmentBeans());
        //获取关联预置路线
        PatrolTaskRouteBean patrolTaskRouteBean = new PatrolTaskRouteBean();
        patrolTaskRouteBean.setTaskId(patrolTaskRequest.getTaskId());
        List<PatrolTaskRouteBean> patrolTaskRouteBeans = patrolTaskRouteMapper.selectPatroltTaskRoute(patrolTaskRouteBean);
        List<PatrolRouteBean> patrolRouteBeans = new ArrayList<>();
        PatrolRouteBean patrolRouteBean = new PatrolRouteBean();
        for (PatrolTaskRouteBean patrolTaskRouteBean1:patrolTaskRouteBeans) {
            patrolRouteBean.setId(patrolTaskRouteBean1.getRouteId());
            patrolRouteBeans.add(patrolRouteBean);
        }
        patrolTaskRequest.setPatrolRoutes(patrolRouteBeans);
        //获取关联设备列表
        List<ViTaskDeviceBean> viTaskDeviceBeans = viTaskDeviceMapper.selectViTaskRepoByTaskId(patrolTaskRequest.getTaskId());
        List<DeviceBean> deviceBeans = new ArrayList<>();
        DeviceBean deviceBean = new DeviceBean();
        for (ViTaskDeviceBean viTaskDeviceBean:viTaskDeviceBeans) {
            deviceBean.setDeviceId(viTaskDeviceBean.getDeviceId());
            deviceBeans.add(deviceBean);
        }
        patrolTaskRequest.setDevices(deviceBeans);
        //获取关联布控库
        List<ViTaskRepoBean> viTaskRepoBeans = viTaskRepoMapper.selectViTaskRepo(patrolTaskRequest.getTaskId());
        List<ViRepoBean> viRepoBeans = new ArrayList<>();
        ViRepoBean viRepoBean = new ViRepoBean();
        for (ViTaskRepoBean viTaskRepoBean:viTaskRepoBeans) {
            viRepoBean.setId(viTaskRepoBean.getRepoId());
            viRepoBeans.add(viRepoBean);
        }
        patrolTaskRequest.setRepoes(viRepoBeans);
        return ResultVo.success(patrolTaskRequest);
    }
    //查询多条巡逻任务
    @Override
    public ResultVo selectPatrolTaskAll( ){
        PatrolTaskBean patrolTaskBean = new PatrolTaskBean();
        patrolTaskBean.setValidState(0);
        List<PatrolTaskBean> patrolTaskBeans = patrolTaskMapper.selectPatrolTaskAll(patrolTaskBean);
        return ResultVo.success(patrolTaskBeans);
    }
    //巡逻任务删除
    @Override
    public ResultVo deletePatrolTask(PatrolTaskBean patrolTaskBean){
        logger.info("开始删除布控任务");
        //组装数据下发天擎
        BaseRequest<BKTaskDeleteRequest> bkTaskDeleteRequestBaseRequest = new BaseRequest<>();
        BKTaskDeleteRequest bkTaskDeleteRequest = new BKTaskDeleteRequest();
        bkTaskDeleteRequest.setTaskIds(patrolTaskBean.getTaskId());
        bkTaskDeleteRequestBaseRequest.setData(bkTaskDeleteRequest);
        bkTaskDeleteRequestBaseRequest.setRequestId(bkrepoConfig.getRequestId());

        BaseResponse baseResponse = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBktaskSubmit(), bkTaskDeleteRequestBaseRequest);
        String errorCode = baseResponse.getCode();
        String errorMsg = baseResponse.getMessage();
        //判断布控任务是否删除成功
        if (!String.valueOf(BizExceptionEnum.OK.getCode()).equals(errorCode)) {
            return ResultVo.failure(BizExceptionEnum.TASK_DELETE_FAIL.getCode(), StringUtils.hasLength(errorMsg) ? errorMsg : BizExceptionEnum.TASK_DELETE_FAIL.getMessage());
        }
        patrolTaskMapper.updatePatrolTask(patrolTaskBean);

        logger.info("结束删除布控任务");
        return ResultVo.success();
    }
    //开启任务
    @Override
    public ResultVo startPatrolTask(PatrolTaskBean patrolTaskBean) {
        try {
            PatrolStartTask patrolStartTask = new PatrolStartTask(patrolTaskBean);
            patrolStartTask.run();
        } catch (Exception e) {
            return ResultVo.failure(BizExceptionEnum.TASK_START_FAIL.getCode(), BizExceptionEnum.TASK_START_FAIL.getMessage());
        }
        return ResultVo.success();
    }

    //终止任务
    @Override
    public ResultVo stopPatrolTask(PatrolTaskBean patrolTaskBean){
        try {
            PatrolStopTask patrolStopTask = new PatrolStopTask(patrolTaskBean);
            patrolStopTask.run();
        } catch (Exception ex) {
            return ResultVo.failure(BizExceptionEnum.TASK_STOP_FAIL.getCode(), BizExceptionEnum.TASK_STOP_FAIL.getMessage());
        }
        return ResultVo.success();
    }

   /**
     * 新增/更新巡逻任务验证算力是否充足
     *
     */
    private boolean validUpdateCalute(PatrolTaskRequest  patrolTaskRequest) {
        //活动中的设备(与任务关联的所有设备)
        ViTaskDeviceBean viTaskDeviceBean = new ViTaskDeviceBean();
        List<ViTaskDeviceBean> viTaskDeviceByObject = viTaskDeviceMapper.getViTaskDeviceByObject(viTaskDeviceBean);
        //设备去重
        List<String> deviceIds = new ArrayList<>();
        for (ViTaskDeviceBean viTaskDeviceBean1:viTaskDeviceByObject) {
            if(!deviceIds.contains(viTaskDeviceBean1.getDeviceId())){
                deviceIds.add(viTaskDeviceBean1.getDeviceId());
            }
        }
        //组织总算力
        SysOrgRoadBean sysOrgRoadBean = new SysOrgRoadBean();
        sysOrgRoadBean.setOrgCode(patrolTaskRequest.getOrgCode());
        SysOrgRoadBean sysOrgRoadByOrgCode = sysOrgRoadMapper.getSysOrgRoadByOrgCode(sysOrgRoadBean);
        //不同设备集合
        List<String> diffrientDevices = new ArrayList<>();
        if(deviceIds.size() == sysOrgRoadByOrgCode.getUsedRoads()){//算力匹配正常
            List<DeviceBean> devices = patrolTaskRequest.getDevices();
            for (DeviceBean deviceBean:devices) {
                if(!deviceIds.contains(deviceBean.getDeviceId())){
                    diffrientDevices.add(deviceBean.getDeviceId());
                }
            }
        }else{//使用算力不相符
           return false;
        }
        //判断算力是否充足
        if(diffrientDevices.size()+deviceIds.size() > sysOrgRoadByOrgCode.getTotalRoads() ){
            return false;
        }else{
            return true;
        }
    }
    /**
     * 新老设备列表对比
     *
     * @param oldList
     * @param newList
     * @param type    true-返回原设备差集合 false-返回设备列表中不存在的集合
     * @return
     */
    private List<ViTaskDeviceBean> removeToDevice(List<ViTaskDeviceBean> oldList, List<ViTaskDeviceBean> newList, boolean type) {
        List<ViTaskDeviceBean> returnNewList = new ArrayList<>();//拟新增的设备
        Set<ViTaskDeviceBean> beanSet = new HashSet<>();//拟去除的设备
        Set<ViTaskDeviceBean> beanSameSet = new HashSet<>();
        for (ViTaskDeviceBean newBean : newList) {
            boolean result = true;
            for (ViTaskDeviceBean oldBean : oldList) {
                //判断是否新老设备列表中的ID一致，若不一致则加入set集合，一致则不加入需要新增的集合，切移除原先已加入的设备信息
                if (newBean.getDeviceId().equals(oldBean.getDeviceId())) {
                    result = false;
                    beanSameSet.add(oldBean);
                } else {
                    beanSet.add(oldBean);
                }
            }
            if (result) {
                returnNewList.add(newBean);
            }
        }

        beanSet.removeAll(beanSameSet);
        return type ? new ArrayList<>(beanSet) : returnNewList;
    }
    /**
     * 新老布控库列表对比
     *
     * @param oldList
     * @param newList
     * @param type     true-返回原布控库差集合 false-返回布控库列表中不存在的集合
     * @return
     */
    private List<ViTaskRepoBean> removeToRepo(List<ViTaskRepoBean> oldList, List<ViTaskRepoBean> newList, boolean type) {
        List<ViTaskRepoBean> returnNewList = new ArrayList<>();
        Set<ViTaskRepoBean> beanSet = new HashSet<>();
        Set<ViTaskRepoBean> beanSameSet = new HashSet<>();
        for (ViTaskRepoBean newBean : newList) {
            boolean result = true;
            for (ViTaskRepoBean oldBean : oldList) {
                if (newBean.getRepoId().equals(oldBean.getRepoId())) {
                    result = false;
                    beanSameSet.add(oldBean);
                } else {
                    beanSet.add(oldBean);
                }
            }
            if (result) {
                returnNewList.add(newBean);
            }
        }
        beanSet.removeAll(beanSameSet);
        return type ? new ArrayList<>(beanSet) : returnNewList;
    }

    /**
     * 巡逻定时任务
     *
     * @param patrolTaskBean
     */
    private void TQTimeTask(PatrolTaskBean patrolTaskBean) {
        Timer timer = new Timer();
        //设备提前5分钟启动码流计划任务
        videoStreamStartTask(timer, patrolTaskBean);
        //开始布控任务
        surveyStartTask(timer, patrolTaskBean);
        //停止布控任务
        surveyStopTask(timer, patrolTaskBean);
        //设备延后固定时间停止码流计划任务
        videoStreamStopTask(timer, patrolTaskBean);
    }
    /**
     * 设备启流
     *
     * @param timer
     * @param patrolTaskBean
     */
    private void videoStreamStartTask(Timer timer, PatrolTaskBean patrolTaskBean) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(patrolTaskBean.getBeginTime());
        //设备提前5分钟启动码流计划任务
        calendar.add(Calendar.MINUTE, Integer.parseInt("-" + NormalConfig.getStreamMinute()));
        //
        timer.schedule(new PatrolVideoStreamStartTask(patrolTaskBean), patrolTaskBean.getEnable() == 2 ? new Date() : calendar.getTime());

    }

    /**
     * 开始巡逻任务
     *
     * @param timer
     * @param patrolTaskBean
     */
    private void surveyStartTask(Timer timer, PatrolTaskBean patrolTaskBean) {
        Calendar calendar = Calendar.getInstance();
        //设备提前5分钟启动码流计划任务
        calendar.setTime(patrolTaskBean.getBeginTime());
        timer.schedule(new PatrolStartTask(patrolTaskBean), patrolTaskBean.getEnable() == 2 ? new Date() : calendar.getTime());
    }

    /**
     * 结束巡逻任务
     *
     * @param timer
     * @param patrolTaskBean
     */
    private void surveyStopTask(Timer timer, PatrolTaskBean patrolTaskBean) {
        Calendar calendar = Calendar.getInstance();
        //设备提前5分钟启动码流计划任务
        calendar.setTime(patrolTaskBean.getBeginTime());
        timer.schedule(new PatrolStopTask(patrolTaskBean), calendar.getTime());
    }

    /**
     * 设备停流
     *
     * @param timer
     * @param patrolTaskBean
     */
    private void videoStreamStopTask(Timer timer, PatrolTaskBean patrolTaskBean) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(patrolTaskBean.getBeginTime());
        //设备5分钟后停止码流计划任务
        calendar.add(Calendar.MINUTE, Integer.parseInt("-" + NormalConfig.getStreamMinute()));
        timer.schedule(new PatrolVideoStreamStopTask(patrolTaskBean), calendar.getTime());
    }

    /**
     * 下发布控任务创建
     *
     * @param patrolTaskBean
     * @return
     */
    private BaseRequest<BKTaskSubmitRequest> BktaskSubmit(PatrolTaskBean patrolTaskBean) {
        //下发布控任务创建
        BaseRequest<BKTaskSubmitRequest> bkTaskSubmitRequestBaseRequest = new BaseRequest<>();
        BKTaskSubmitRequest bkTaskSubmitRequest = new BKTaskSubmitRequest();
        bkTaskSubmitRequest.setTaskId(patrolTaskBean.getTaskId());
        BKTaskMeta bkTaskMeta = new BKTaskMeta();
        bkTaskMeta.setBkid(bkrepoConfig.getBkid());
        bkTaskMeta.setDesc(patrolTaskBean.getPatrolName());
        ArrayList<BKTaskCameraInfo> bkTaskCameraInfos = new ArrayList<>();
        for (ViTaskDeviceBean viTaskDeviceBean : patrolTaskBean.getViTaskDevices()) {
            BKTaskCameraInfo bkTaskCameraInfo = new BKTaskCameraInfo();
            bkTaskCameraInfo.setCameraId(viTaskDeviceBean.getDeviceId());
            bkTaskCameraInfo.setThreshold(bkrepoConfig.getThreshold());
            bkTaskCameraInfos.add(bkTaskCameraInfo);
        }
        bkTaskMeta.setCameraInfo(bkTaskCameraInfos);
        bkTaskSubmitRequest.setMeta(bkTaskMeta);
        bkTaskSubmitRequestBaseRequest.setData(bkTaskSubmitRequest);
        bkTaskSubmitRequestBaseRequest.setRequestId(bkrepoConfig.getRequestId());

        return bkTaskSubmitRequestBaseRequest;
    }


}