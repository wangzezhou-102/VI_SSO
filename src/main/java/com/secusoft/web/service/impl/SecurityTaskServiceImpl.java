package com.secusoft.web.service.impl;

import com.secusoft.web.config.BkrepoConfig;
import com.secusoft.web.config.NormalConfig;
import com.secusoft.web.config.ServiceApiConfig;
import com.secusoft.web.core.emuns.ViRepoBkTypeEnum;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.mapper.*;
import com.secusoft.web.model.*;
import com.secusoft.web.service.BoxSelectService;
import com.secusoft.web.service.SecurityTaskService;
import com.secusoft.web.serviceapi.ServiceApiClient;
import com.secusoft.web.serviceapi.model.BaseResponse;
import com.secusoft.web.task.*;
import com.secusoft.web.tusouapi.model.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author huanghao
 * @date 2019-07-02
 */
@Service
public class SecurityTaskServiceImpl implements SecurityTaskService {
    private static Logger log = LoggerFactory.getLogger(SecurityTaskServiceImpl.class);
    @Resource
    ViRepoMapper viRepoMapper;

    @Resource
    SecurityTaskTypeMapper securityTaskTypeMapper;

    @Resource
    SecurityTaskMapper securityTaskMapper;

    @Resource
    SecurityPlaceMapper securityPlaceMapper;

    @Resource
    SecurityTaskPlaceMapper securityTaskPlaceMapper;

    @Resource
    SecurityTimeMapper securityTimeMapper;

    @Resource
    ViTaskRepoMapper viTaskRepoMapper;

    @Resource
    BkrepoConfig bkrepoConfig;

    @Resource
    ViTaskDeviceMapper viTaskDeviceMapper;

    @Resource
    SysOrgRoadMapper sysOrgRoadMapper;

    @Resource
    BoxSelectService boxSelectService;

    @Override
    public ResultVo getSecurityTypePlace() {
        List<SecurityTaskTypeBean> securityTaskTypeA = securityTaskTypeMapper.selectsecurityTaskTypeA();
        List<SecurityTaskTypeBean> securityTaskTypeB = securityTaskTypeMapper.selectsecurityTaskTypeB();
        securityTaskTypeA.addAll(securityTaskTypeB);

        List<SecurityPlaceVo> securityPlaceVos = securityPlaceMapper.selectSecirityTaskPlace();

        List<SecurityTaskTypeBean> securityTaskTypeBeans = securityTaskTypeMapper.selectsecurityFKType();

        ArrayList<List> result = new ArrayList<>();
        result.add(securityTaskTypeA);
        result.add(securityPlaceVos);
        result.add(securityTaskTypeBeans);
        return ResultVo.success(result);
    }

    @Override
    public ResultVo getSecurityTaskType() {
        List<SecurityTaskTypeBean> securityTaskTypeBeans = securityTaskTypeMapper.selectsecurityTaskType();
        return ResultVo.success(securityTaskTypeBeans);
    }

    @Override
    public ResultVo getAllViRepo() {
        ViRepoBean viRepoBean = new ViRepoBean();
        viRepoBean.setTableName(null);
        viRepoBean.setBktype(null);
        viRepoBean.setType(null);
        List<ViRepoBean> viRepoBeanList = viRepoMapper.getAllViRepo(viRepoBean);
        HashMap<String, Object> map1 = new HashMap<>();
        HashMap<String, Object> map2 = new HashMap<>();
        ArrayList<ViRepoBean> personRepoBeans = new ArrayList<>();
        ArrayList<ViRepoBean> carRepoBeans = new ArrayList<>();
        for (ViRepoBean virepo:viRepoBeanList) {
            if(virepo.getBktype().equals(ViRepoBkTypeEnum.VIREPO_BKTYPE_PEOPLE.getCode()) ){
                personRepoBeans.add(virepo);
            }else if(virepo.getBktype().equals(ViRepoBkTypeEnum.VIREPO_BKTYPE_CAR.getCode())){
                carRepoBeans.add(virepo);
            }
        }
        map1.put("title","人员库");
        map1.put("type","person");
        map1.put("children",personRepoBeans);

        map2.put("title","车辆库");
        map2.put("type","car");
        map2.put("children",carRepoBeans);
        ArrayList<Map> list = new ArrayList<>();
        list.add(map1);
        list.add(map2);
        return ResultVo.success(list);
    }

    @Override
    public ResultVo getSecurityTaskTypeRepo() {
        return ResultVo.success(securityTaskTypeMapper.selectsecurityTaskTypeRepo());
    }

    @Override
    @Transactional
    public ResultVo setSecurityTaskTypeRepo(List<SecurityTaskTypeRepoBean> securityTaskTypeRepoBeans) {
        for (SecurityTaskTypeRepoBean securityTaskTypeRepoBean:securityTaskTypeRepoBeans){
            securityTaskTypeMapper.deletesecurityTaskTypeRepoById(securityTaskTypeRepoBean.getId());
            securityTaskTypeMapper.insertsecurityTaskTypeRepo(securityTaskTypeRepoBean.getRepoIds(),securityTaskTypeRepoBean.getId());
        }
        return ResultVo.success(securityTaskTypeMapper.selectsecurityTaskTypeRepo());
    }

    @Override
    @Transactional
    public ResultVo insertSecurityTask(SecurityTaskRequest securityTaskRequest) {
        if(securityTaskRequest == null){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        if (!StringUtils.hasLength(securityTaskRequest.getSecurityName())) {
            return ResultVo.failure(BizExceptionEnum.TASK_NANE_NULL.getCode(), BizExceptionEnum.TASK_NANE_NULL.getMessage());
        }
        if (CollectionUtils.isNotEmpty(securityTaskRequest.getTimeStamps())) {
            for (SecurityTimeBean securityTimeStamp:securityTaskRequest.getTimeStamps()) {
                if(securityTimeStamp.getBeginTime().compareTo(securityTimeStamp.getEndTime())>0){
                    return ResultVo.failure(BizExceptionEnum.TASK_DATE_WRONG.getCode(), BizExceptionEnum.TASK_DATE_WRONG.getMessage());
                }
            }
        } else {
            return ResultVo.failure(BizExceptionEnum.TASK_DATE_NULL.getCode(), BizExceptionEnum.TASK_DATE_NULL.getMessage());
        }
        SecurityTaskBean securityTaskBean1 = new SecurityTaskBean();
        securityTaskBean1.setSecurityName(securityTaskRequest.getSecurityName());
        List<SecurityTaskBean> securityTaskBeans = securityTaskMapper.selectSecurityTaskBeanByObj(securityTaskBean1);
        if(securityTaskBeans.size() > 0){
            return ResultVo.failure(BizExceptionEnum.TASK_NANE_REPEATED.getCode(), BizExceptionEnum.TASK_NANE_REPEATED.getMessage());
        }

        //判断算力
//        securityTaskBean1.setCodeplacesBeans(securityTaskRequest.getCodeplacesBeans());
//        securityTaskBean1.setOrgCode("999");
//        if (!validUpdateCalute(securityTaskBean1)) {
//            return ResultVo.failure(BizExceptionEnum.TASK_CALUATE_FAIL.getCode(), BizExceptionEnum.TASK_CALUATE_FAIL.getMessage());
//        }
        SecurityTaskBean securityTaskBean = new SecurityTaskBean();
        BeanCopier beanCopier = BeanCopier.create(SecurityTaskRequest.class, SecurityTaskBean.class, false);
        beanCopier.copy(securityTaskRequest,securityTaskBean,null);
        String taskId="s" + UUID.randomUUID().toString().replace("-", "").toLowerCase();
        securityTaskBean.setTaskId(taskId);

        //初始化1有效
        securityTaskBean.setValidState(1);
        //初始化0未完成
        securityTaskBean.setIsfinish(0);
        //安保任务添加
        securityTaskMapper.insertSecurityTask(securityTaskBean);

        //安保任务相关时间段添加
        for (SecurityTimeBean securityTimeBean : securityTaskRequest.getTimeStamps()){
            securityTimeBean.setTaskId(taskId);
            securityTimeMapper.insert(securityTimeBean);
        }

        //安保任务相关布控库添加
        List<Integer> repoids = securityTaskRequest.getRepoids();
        List<ViTaskRepoBean> listRepo = new ArrayList<>();
        for (Integer repoid : repoids) {
            ViTaskRepoBean viTaskRepoBean = new ViTaskRepoBean();
            viTaskRepoBean.setRepoId(repoid);
            viTaskRepoBean.setTaskId(taskId);
            listRepo.add(viTaskRepoBean);
        }
        viTaskRepoMapper.insertBatch(listRepo);

        //安保任务相关地点添加
        List<CodeplacesBean> codeplacesBeans = securityTaskRequest.getCodeplacesBeans();
        for (CodeplacesBean codeplacesBean:codeplacesBeans){
            for (Integer i:codeplacesBean.getPlace()){
                SecurityTaskPlaceBean securityTaskPlaceBean = new SecurityTaskPlaceBean();
                securityTaskPlaceBean.setTaskId(taskId);
                securityTaskPlaceBean.setTypeCode(Integer.valueOf(codeplacesBean.getTypeCode()));
                securityTaskPlaceBean.setPlaceId(i);
                securityTaskPlaceMapper.insert(securityTaskPlaceBean);
            }
        }

        //安保任务设备添加
        List<ViTaskDeviceBean> list = new ArrayList<>();
        if(securityTaskRequest.getSecurityType()==4){
            List<String> cameraId = securityTaskRequest.getCameraId();
            for (String id : cameraId) {
                ViTaskDeviceBean viTaskDeviceBean = new ViTaskDeviceBean();
                viTaskDeviceBean.setDeviceId(id);
                viTaskDeviceBean.setTaskId(taskId);
                viTaskDeviceBean.setStatus(2);
                viTaskDeviceBean.setAction(2);
                list.add(viTaskDeviceBean);
            }
            viTaskDeviceMapper.insertBatch(list);
        }else {
            securityTaskBean1.setCodeplacesBeans(securityTaskRequest.getCodeplacesBeans());
            List<DeviceBean> deviceBeans = SecurityDevice(securityTaskBean1);
             for(DeviceBean deviceBean : deviceBeans){
                ViTaskDeviceBean viTaskDeviceBean = new ViTaskDeviceBean();
                viTaskDeviceBean.setDeviceId(deviceBean.getDeviceId());
                viTaskDeviceBean.setTaskId(taskId);
                viTaskDeviceBean.setStatus(2);
                viTaskDeviceBean.setAction(2);
                list.add(viTaskDeviceBean);
            }
            viTaskDeviceMapper.insertBatch(list);
        }


        // 下发布控任务创建
//        BaseResponse baseResponse = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBktaskSubmit(), BktaskSubmit(securityTaskRequest));
//        Object dataJson = baseResponse.getData();
//        String errorCode = baseResponse.getCode();
//        String errorMsg = baseResponse.getMessage();
//        //判断布控任务添加是否成功
//        if (!String.valueOf(BizExceptionEnum.OK.getCode()).equals(errorCode)) {
//            throw new RuntimeException(StringUtils.hasLength(errorMsg) ? errorMsg : "布控任务添加失败！");
//        }
        //开启定时任务
        List<SecurityTimeBean> securityTimeBeans = securityTimeMapper.selectTimeByTaskId(securityTaskRequest.getTaskId());
        for (int i = 0; i < securityTimeBeans.size(); i++) {
            if (i==0&&securityTaskRequest.getEnable()==1){
                //修改初始时间为当前时间
                SecurityTimeBean securityTimeBean = new SecurityTimeBean();
                securityTimeBean.setBeginTime(System.currentTimeMillis());
                securityTimeBean.setId(securityTimeBeans.get(0).getId());
                securityTimeMapper.updateTimeById(securityTimeBean);

                securityTaskRequest.setId(securityTimeBeans.get(0).getId());
                securityTaskRequest.setBeginTime(System.currentTimeMillis());
                securityTaskRequest.setEndTime(securityTimeBeans.get(i).getEndTime());
               // TQTimeTask(securityTaskRequest);
            }else{
                securityTaskRequest.setId(securityTimeBeans.get(i).getId());
                securityTaskRequest.setBeginTime(securityTimeBeans.get(i).getBeginTime());
                securityTaskRequest.setEndTime(securityTimeBeans.get(i).getEndTime());
               // TQTimeTask(securityTaskRequest);
            }
        }
        return ResultVo.success();
    }

    @Override
    public ResultVo deleteSecurityTask(SecurityTaskRequest securityTaskRequest) {
        if(securityTaskRequest == null){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        if(StringUtils.isEmpty(securityTaskRequest.getTaskId())){
            return ResultVo.failure(BizExceptionEnum.TASK_ID_NULL.getCode(), BizExceptionEnum.TASK_ID_NULL.getMessage());
        }
        //正在启动的任务不能删除
        SecurityTaskBean securityTaskBean = securityTaskMapper.selectSecurityTaskByTaskId(securityTaskRequest);
        if(securityTaskBean.getEnable()==1){
            return ResultVo.failure(BizExceptionEnum.SECURITY_DELETE_FALL.getCode(), BizExceptionEnum.SECURITY_DELETE_FALL.getMessage());
        }

        //组装数据下发天擎
        BaseRequest<BKTaskDeleteRequest> bkTaskDeleteRequestBaseRequest = new BaseRequest<>();
        BKTaskDeleteRequest bkTaskDeleteRequest = new BKTaskDeleteRequest();
        bkTaskDeleteRequest.setTaskIds(securityTaskRequest.getTaskId());
        bkTaskDeleteRequestBaseRequest.setData(bkTaskDeleteRequest);
        bkTaskDeleteRequestBaseRequest.setRequestId(bkrepoConfig.getRequestId());

        //BaseResponse baseResponse = TQClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBktaskSubmit(), bkTaskDeleteRequestBaseRequest);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(String.valueOf(BizExceptionEnum.OK.getCode()));
        Object dataJson = baseResponse.getData();
        String errorCode = baseResponse.getCode();
        String errorMsg = baseResponse.getMessage();
        //判断布控任务是否删除成功
        if (!String.valueOf(BizExceptionEnum.OK.getCode()).equals(errorCode)) {
            return ResultVo.failure(BizExceptionEnum.TASK_DELETE_FAIL.getCode(), StringUtils.hasLength(errorMsg) ? errorMsg : BizExceptionEnum.TASK_DELETE_FAIL.getMessage());
        }
        securityTaskMapper.delSecurityTask(securityTaskRequest);

        List<SecurityTaskBean> securityTaskRequests = securityTaskMapper.selectAllSecurityTaskByIsFinish(0);
        List<SecurityTaskBean> securityTaskBeans = securityTaskMapper.selectAllSecurityTaskByIsFinish(1);
        HashMap<String, List> map = new HashMap<>();
        map.put("nofinish",securityTaskRequests);
        map.put("finish",securityTaskBeans);
        return ResultVo.success(map);
    }

    @Transactional
    @Override
    public ResultVo setSecurityTask(SecurityTaskRequest securityTaskRequest) {
        if(securityTaskRequest == null){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        if(!StringUtils.hasLength(securityTaskRequest.getTaskId())){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        if (!StringUtils.hasLength(securityTaskRequest.getSecurityName())) {
            return ResultVo.failure(BizExceptionEnum.TASK_NANE_NULL.getCode(), BizExceptionEnum.TASK_NANE_NULL.getMessage());
        }
        if (CollectionUtils.isNotEmpty(securityTaskRequest.getTimeStamps())) {
            for (SecurityTimeBean securityTimeStamp:securityTaskRequest.getTimeStamps()) {
                if(securityTimeStamp.getBeginTime().compareTo(securityTimeStamp.getEndTime())>0){
                    return ResultVo.failure(BizExceptionEnum.TASK_DATE_WRONG.getCode(), BizExceptionEnum.TASK_DATE_WRONG.getMessage());
                }
            }
        } else {
            return ResultVo.failure(BizExceptionEnum.TASK_DATE_NULL.getCode(), BizExceptionEnum.TASK_DATE_NULL.getMessage());
        }
        SecurityTaskBean securityTaskBean1 = new SecurityTaskBean();
        securityTaskBean1.setSecurityName(securityTaskRequest.getSecurityName());
        List<SecurityTaskBean> securityTaskBeans = securityTaskMapper.selectSecurityTaskBeanByObj(securityTaskBean1);
        if(securityTaskBeans.size() > 0){
            return ResultVo.failure(BizExceptionEnum.TASK_NANE_REPEATED.getCode(), BizExceptionEnum.TASK_NANE_REPEATED.getMessage());
        }

        //判断算力
//        securityTaskBean1.setCodeplacesBeans(securityTaskRequest.getCodeplacesBeans());
//        securityTaskBean1.setOrgCode("999");
//        if (!validUpdateCalute(securityTaskBean1)) {
//            return ResultVo.failure(BizExceptionEnum.TASK_CALUATE_FAIL.getCode(), BizExceptionEnum.TASK_CALUATE_FAIL.getMessage());
//        }
        //如果修改正在执行的任务   那么就应该是停止布控任务  更新数据库  更新阿里那边 (时间 库 地点 设备)
        //应该跟原来的对比 如果说一致就不修改 不一致再修改

        SecurityTaskBean securityTaskBean = new SecurityTaskBean();
        securityTaskBean.setTaskId(securityTaskRequest.getTaskId());
        securityTaskBean.setSecurityName(securityTaskRequest.getSecurityName());
        securityTaskBean.setSecurityType(securityTaskRequest.getSecurityType());
        securityTaskBean.setSecurityStatus(0);
        securityTaskBean.setEnable(securityTaskRequest.getEnable());
        securityTaskBean.setExposition(securityTaskRequest.getExposition());

        securityTaskMapper.updateSecurityTask(securityTaskBean);
        securityTimeMapper.deleteTimeByTaskId(securityTaskBean);
        viTaskRepoMapper.deleteViTaskRepoByTaskId(securityTaskBean.getTaskId());
        securityTaskPlaceMapper.deleteTaskPlaceByTaskId(securityTaskBean.getTaskId());
        viTaskDeviceMapper.deleteViTaskDeviceByTaskId(securityTaskBean.getTaskId());

        //安保任务相关时间段添加
        for (SecurityTimeBean securityTimeBean : securityTaskRequest.getTimeStamps()){
            securityTimeBean.setTaskId(securityTaskBean.getTaskId());
            securityTimeMapper.insert(securityTimeBean);
        }

        //安保任务相关布控库添加
        List<Integer> repoids = securityTaskRequest.getRepoids();
        List<ViTaskRepoBean> listRepo = new ArrayList<>();
        for (Integer repoid : repoids) {
            ViTaskRepoBean viTaskRepoBean = new ViTaskRepoBean();
            viTaskRepoBean.setRepoId(repoid);
            viTaskRepoBean.setTaskId(securityTaskBean.getTaskId());
            listRepo.add(viTaskRepoBean);
        }
        viTaskRepoMapper.insertBatch(listRepo);

        //安保任务相关地点添加
        List<CodeplacesBean> codeplacesBeans = securityTaskRequest.getCodeplacesBeans();
        for (CodeplacesBean codeplacesBean:codeplacesBeans){
            for (Integer i:codeplacesBean.getPlace()){
                SecurityTaskPlaceBean securityTaskPlaceBean = new SecurityTaskPlaceBean();
                securityTaskPlaceBean.setTaskId(securityTaskBean.getTaskId());
                securityTaskPlaceBean.setTypeCode(Integer.valueOf(codeplacesBean.getTypeCode()));
                securityTaskPlaceBean.setPlaceId(i);
                securityTaskPlaceMapper.insert(securityTaskPlaceBean);
            }
        }

        //安保任务设备添加
        List<ViTaskDeviceBean> list = new ArrayList<>();
        if(securityTaskRequest.getSecurityType()==4){
            List<String> cameraId = securityTaskRequest.getCameraId();
            for (String id : cameraId) {
                ViTaskDeviceBean viTaskDeviceBean = new ViTaskDeviceBean();
                viTaskDeviceBean.setDeviceId(id);
                viTaskDeviceBean.setTaskId(securityTaskBean.getTaskId());
                viTaskDeviceBean.setStatus(2);
                viTaskDeviceBean.setAction(2);
                list.add(viTaskDeviceBean);
            }
            viTaskDeviceMapper.insertBatch(list);
        }else {
            securityTaskBean1.setCodeplacesBeans(securityTaskRequest.getCodeplacesBeans());
            List<DeviceBean> deviceBeans = SecurityDevice(securityTaskBean1);
            for(DeviceBean deviceBean : deviceBeans){
                ViTaskDeviceBean viTaskDeviceBean = new ViTaskDeviceBean();
                viTaskDeviceBean.setDeviceId(deviceBean.getDeviceId());
                viTaskDeviceBean.setTaskId(securityTaskBean.getTaskId());
                viTaskDeviceBean.setStatus(2);
                viTaskDeviceBean.setAction(2);
                list.add(viTaskDeviceBean);
            }
            viTaskDeviceMapper.insertBatch(list);
        }
        //
        return ResultVo.success();
    }

    @Override
    public ResultVo getSecurityTask(SecurityTaskRequest securityTaskRequest) {
        SecurityTaskBean securityTaskBean = securityTaskMapper.selectSecurityTaskByTaskId(securityTaskRequest);
        return ResultVo.success(securityTaskBean);
    }

    @Override
    public ResultVo getSecurityTaskList(SecurityTaskRequest securityTaskRequest) {

        List<SecurityTaskBean> securityTaskRequests = securityTaskMapper.selectAllSecurityTaskByIsFinish(0);
        List<SecurityTaskBean> securityTaskBeans = securityTaskMapper.selectAllSecurityTaskByIsFinish(1);
        HashMap<String, List> map = new HashMap<>();
        map.put("nofinish",securityTaskRequests);
        map.put("finish",securityTaskBeans);
        return ResultVo.success(map);
    }

    @Override
    public ResultVo startSecurityTask(SecurityTaskBean securityTaskBean) {
        if(securityTaskBean == null){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        if(StringUtils.isEmpty(securityTaskBean.getTaskId())){
            return ResultVo.failure(BizExceptionEnum.TASK_ID_NULL.getCode(), BizExceptionEnum.TASK_ID_NULL.getMessage());
        }
        securityTaskBean.setEnable(1);
        //后续跟天擎交互
        securityTaskMapper.updateSecurityTask(securityTaskBean);
        return ResultVo.success();
    }

    @Override
    public ResultVo stopSecurityTask(SecurityTaskBean securityTaskBean) {
        if(securityTaskBean == null){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        if(StringUtils.isEmpty(securityTaskBean.getTaskId())){
            return ResultVo.failure(BizExceptionEnum.TASK_ID_NULL.getCode(), BizExceptionEnum.TASK_ID_NULL.getMessage());
        }
        securityTaskBean.setEnable(0);
        //后续跟天擎交互
        securityTaskMapper.updateSecurityTask(securityTaskBean);
        return ResultVo.success();
    }

    /**
     * 新增/更新布控任务验证算力是否充足
     *
     * @return
     */
    private boolean validUpdateCalute(SecurityTaskBean securityTaskBean) {
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
        sysOrgRoadBean.setOrgCode(securityTaskBean.getOrgCode());
        SysOrgRoadBean sysOrgRoadByOrgCode = sysOrgRoadMapper.getSysOrgRoadByOrgCode(sysOrgRoadBean);

        //此次新增地点相关设备
        List<DeviceBean> deviceBeans = SecurityDevice(securityTaskBean);
        //如果已活动设备直接加此次任务设备小于总路数直接返回true
        if(deviceIds.size() + deviceBeans.size() <= sysOrgRoadByOrgCode.getUsedRoads()){
            return true;
        }else {
            for (DeviceBean deviceBean : deviceBeans){
                if(!deviceIds.contains(deviceBean.getDeviceId())){
                    deviceIds.add(deviceBean.getDeviceId());
                }
            }
        }
        if(deviceIds.size() <= sysOrgRoadByOrgCode.getUsedRoads()){
            return true;
        }else {
            return false;
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
     * @param type    true-返回原布控库差集合 false-返回布控库列表中不存在的集合
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
     * 布控定时任务
     *
     * @param securityTaskRequest
     */
    private void TQTimeTask(SecurityTaskRequest securityTaskRequest) {

        Timer timer = new Timer();
        //设备提前5分钟启动码流计划任务
        videoStreamStartTask(timer, securityTaskRequest);

        //开始布控任务
        surveyStartTask(timer, securityTaskRequest);

        //停止布控任务
        surveyStopTask(timer, securityTaskRequest);

        //设备延后固定时间停止码流计划任务
        videoStreamStopTask(timer, securityTaskRequest);
    }

    /**
     * 设备启流
     *
     * @param timer
     * @param securityTaskRequest
     */
    private void videoStreamStartTask(Timer timer, SecurityTaskRequest securityTaskRequest) {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date parse = simpleDateFormat.parse(simpleDateFormat.format(securityTaskRequest.getBeginTime()));
            calendar.setTime(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //设备提前5分钟启动码流计划任务
        calendar.add(Calendar.MINUTE, Integer.parseInt("-" + NormalConfig.getStreamMinute()));
        timer.schedule(new SecurityVideoStreamStartTask(securityTaskRequest), calendar.getTime());
    }

    /**
     * 开始布控任务
     *
     * @param timer
     * @param securityTaskRequest
     */
    private void surveyStartTask(Timer timer, SecurityTaskRequest securityTaskRequest) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date parse = simpleDateFormat.parse(simpleDateFormat.format(securityTaskRequest.getBeginTime()));
            calendar.setTime(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        timer.schedule(new SecurityStartTask(securityTaskRequest),  calendar.getTime());
    }


    /**
     * 结束布控任务
     *
     * @param timer
     * @param securityTaskRequest
     */
    private void surveyStopTask(Timer timer, SecurityTaskRequest securityTaskRequest) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date parse = simpleDateFormat.parse(simpleDateFormat.format(securityTaskRequest.getEndTime()));
            calendar.setTime(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        timer.schedule(new SecurityStopTask(securityTaskRequest), calendar.getTime());
    }

    /**
     * 设备停流
     *
     * @param timer
     * @param securityTaskRequest
     */
    private void videoStreamStopTask(Timer timer, SecurityTaskRequest securityTaskRequest) {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date parse = simpleDateFormat.parse(simpleDateFormat.format(securityTaskRequest.getEndTime()));
            calendar.setTime(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //设备提前5分钟启动码流计划任务
        calendar.add(Calendar.MINUTE, Integer.parseInt("-" + NormalConfig.getStreamMinute()));
        timer.schedule(new SecurityVideoStreamStopTask(securityTaskRequest), calendar.getTime());

    }

    /**
     * 下发布控任务创建
     *
     */
    private BaseRequest<BKTaskSubmitRequest> BktaskSubmit(SecurityTaskRequest securityTaskRequest) {

        //下发布控任务创建
        BaseRequest<BKTaskSubmitRequest> bkTaskSubmitRequestBaseRequest = new BaseRequest<>();
        BKTaskSubmitRequest bkTaskSubmitRequest = new BKTaskSubmitRequest();
        bkTaskSubmitRequest.setTaskId(securityTaskRequest.getTaskId());
        BKTaskMeta bkTaskMeta = new BKTaskMeta();
        bkTaskMeta.setBkid(bkrepoConfig.getBkid());
        bkTaskMeta.setDesc(securityTaskRequest.getSecurityName());
        ArrayList<BKTaskCameraInfo> bkTaskCameraInfos = new ArrayList<>();
        if(securityTaskRequest.getSecurityType()==4){
            for(String deviceId:securityTaskRequest.getCameraId()){
                BKTaskCameraInfo bkTaskCameraInfo = new BKTaskCameraInfo();
                bkTaskCameraInfo.setCameraId(deviceId);
                bkTaskCameraInfo.setThreshold(bkrepoConfig.getThreshold());
                bkTaskCameraInfos.add(bkTaskCameraInfo);
            }
        }else {
            SecurityTaskBean securityTaskBean = new SecurityTaskBean();
            securityTaskBean.setCodeplacesBeans(securityTaskRequest.getCodeplacesBeans());
            List<DeviceBean> deviceBeans = SecurityDevice(securityTaskBean);
            for (DeviceBean viTaskDeviceBean : deviceBeans) {
                BKTaskCameraInfo bkTaskCameraInfo = new BKTaskCameraInfo();
                bkTaskCameraInfo.setCameraId(viTaskDeviceBean.getDeviceId());
                bkTaskCameraInfo.setThreshold(bkrepoConfig.getThreshold());
                bkTaskCameraInfos.add(bkTaskCameraInfo);
            }
        }
        bkTaskMeta.setCameraInfo(bkTaskCameraInfos);
        bkTaskSubmitRequest.setMeta(bkTaskMeta);

        bkTaskSubmitRequestBaseRequest.setData(bkTaskSubmitRequest);
        bkTaskSubmitRequestBaseRequest.setRequestId(bkrepoConfig.getRequestId());

        return bkTaskSubmitRequestBaseRequest;
    }

    /**
     * 返回该任务下包含的设备列表
     */
    private List<DeviceBean> SecurityDevice(SecurityTaskBean securityTaskBean) {
        List<CodeplacesBean> codeplacesBeans = securityTaskBean.getCodeplacesBeans();
        ArrayList<DeviceBean> deviceBeans = new ArrayList<DeviceBean>();
        //先遍历得出任务中包含的所有地点
        for (CodeplacesBean codeplacesBean :codeplacesBeans){
            List<Integer> place = codeplacesBean.getPlace();
            for (Integer i : place){
                //通过地点id得出对应地点的圆心经纬度
                List<SecurityPlaceBean> securityPlaceBeans = securityPlaceMapper.selectSecirityTaskPlaceByPlaceId(i);
                for (SecurityPlaceBean securityPlaceBean : securityPlaceBeans) {
                    Double longitude = Double.valueOf(securityPlaceBean.getLongitude());
                    Double latitude = Double.valueOf(securityPlaceBean.getLatitude());
                    PointBean pointBean = new PointBean();
                    pointBean.setLongitude(longitude);
                    pointBean.setLatitude(latitude);
                    ResultVo ptInPoly2 = boxSelectService.isPtInPoly2(pointBean, Double.valueOf(securityPlaceBean.getRadius()));
                    List<DeviceBean> deviceBean = (ArrayList<DeviceBean>)ptInPoly2.getData();
                    deviceBeans.addAll(deviceBean);
                }
            }
        }
        ArrayList<DeviceBean> devices = new ArrayList<>();
        for (DeviceBean deviceBean:deviceBeans) {
            if(!devices.contains(deviceBean.getDeviceId())){
                devices.add(deviceBean);
            }
        }
        return deviceBeans;
    }
}
