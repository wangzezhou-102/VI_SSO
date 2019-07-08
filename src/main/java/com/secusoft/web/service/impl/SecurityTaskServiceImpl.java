package com.secusoft.web.service.impl;

import com.secusoft.web.config.BkrepoConfig;
import com.secusoft.web.config.NormalConfig;
import com.secusoft.web.config.ServiceApiConfig;
import com.secusoft.web.core.emuns.ViRepoBkTypeEnum;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.mapper.*;
import com.secusoft.web.model.*;
import com.secusoft.web.service.SecurityTaskService;
import com.secusoft.web.serviceapi.ServiceApiClient;
import com.secusoft.web.serviceapi.model.BaseResponse;
import com.secusoft.web.task.SurveyStartTask;
import com.secusoft.web.task.SurveyStopTask;
import com.secusoft.web.task.VideoStreamStartTask;
import com.secusoft.web.task.VideoStreamStopTask;
import com.secusoft.web.tusouapi.model.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
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
//        if (!validUpdateCalute(viSurveyTaskRequest, null, null)) {
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
        //初始化0任务关闭
        securityTaskBean.setEnable(0);
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

        //下发布控任务创建
//        BaseResponse baseResponse = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBktaskSubmit(), BktaskSubmit(securityTimeBean));
//        Object dataJson = baseResponse.getData();
//        String errorCode = baseResponse.getCode();
//        String errorMsg = baseResponse.getMessage();
//        //判断布控任务添加是否成功
//        if (!String.valueOf(BizExceptionEnum.OK.getCode()).equals(errorCode)) {
//            throw new RuntimeException(StringUtils.hasLength(errorMsg) ? errorMsg : "布控任务添加失败！");
//        }
//
//        TQTimeTask(securityTimeBean);

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
        return ResultVo.success();
    }

    @Transactional
    @Override
    public ResultVo setSecurityTask(SecurityTaskRequest securityTaskRequest) {
        return null;
    }

    @Override
    public ResultVo getSecurityTask(SecurityTaskRequest securityTaskRequest) {

        return null;
    }

    @Override
    public ResultVo getSecurityTaskList(SecurityTaskRequest securityTaskRequest) {

        List<SecurityTaskRequest> securityTaskRequests = securityTaskMapper.selectAllSecurityTask();

        return ResultVo.success(securityTaskRequests);
    }

    @Override
    public ResultVo startSecurityTask(SecurityTaskRequest securityTaskRequest) {
        return null;
    }

    @Override
    public ResultVo stopSecurityTask(SecurityTaskRequest securityTaskRequest) {
        return null;
    }

    /**
     * 新增/更新布控任务验证算力是否充足
     *
     * @param viSurveyTaskRequest 请求参数
     * @param cutDeviceList       拟去除的设备
     * @param diffrientDevice     拟新增的设备
     * @return
     */
    private boolean validUpdateCalute(ViSurveyTaskRequest viSurveyTaskRequest, List<ViTaskDeviceBean> cutDeviceList, List<ViTaskDeviceBean> diffrientDevice) {

//        log.info("原先的设备：" + StringUtils.arrayToDelimitedString(viSurveyTaskRequest.getSurveyDevice(), ","));
//        //活动中的设备
//        List<ViTaskDeviceBean> actionDeviceList = viTaskDeviceMapper.getActionDevice(StringUtils.arrayToDelimitedString(viSurveyTaskRequest.getSurveyDevice(), ","));
//        //新增设备数
//        Integer inactiveDevice = viSurveyTaskRequest.getSurveyDevice().length;
//        //准备布控的设备数
//        Integer readyDevice = inactiveDevice - actionDeviceList.size();
//        SysOrgRoadBean sysOrgRoadBean = new SysOrgRoadBean();
//        sysOrgRoadBean.setOrgCode("330401");
//        SysOrgRoadBean sysOrgRoadByOrgCode = sysOrgRoadMapper.getSysOrgRoadByOrgCode(sysOrgRoadBean);
//        if (cutDeviceList == null || diffrientDevice == null) {
//            if ((sysOrgRoadByOrgCode.getUsedRoads() + readyDevice) > sysOrgRoadByOrgCode.getTotalRoads()) {
//                return false;
//            }
//        } else {
//            if ((sysOrgRoadByOrgCode.getUsedRoads() - cutDeviceList.size() + diffrientDevice.size()) > sysOrgRoadByOrgCode.getTotalRoads()) {
//                return false;
//            }
//        }
        return true;
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
     * @param viSurveyTaskBean
     */
    private void TQTimeTask(ViSurveyTaskBean viSurveyTaskBean) {
//        ViSurveyTaskRequest viSurveyTaskRequest = new ViSurveyTaskRequest();
//        viSurveyTaskRequest.setId(id);
//        List<ViSurveyTaskBean> lists = viSurveyTaskMapper.getAllViSurveyTask(viSurveyTaskRequest);
//
//        ViSurveyTaskBean viSurveyTaskBean = lists.get(0);

        Timer timer = new Timer();
        //设备提前5分钟启动码流计划任务
        videoStreamStartTask(timer, viSurveyTaskBean);

        //开始布控任务
        surveyStartTask(timer, viSurveyTaskBean);

        //停止布控任务
        surveyStopTask(timer, viSurveyTaskBean);

        //设备延后固定时间停止码流计划任务
        videoStreamStopTask(timer, viSurveyTaskBean);
    }

    /**
     * 设备启流
     *
     * @param timer
     * @param viSurveyTaskBean
     */
    private void videoStreamStartTask(Timer timer, ViSurveyTaskBean viSurveyTaskBean) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(viSurveyTaskBean.getBeginTime());
        //设备提前5分钟启动码流计划任务
        calendar.add(Calendar.MINUTE, Integer.parseInt("-" + NormalConfig.getStreamMinute()));
        timer.schedule(new VideoStreamStartTask(viSurveyTaskBean), viSurveyTaskBean.getEnable() == 1 ? new Date() : calendar.getTime());
    }

    /**
     * 开始布控任务
     *
     * @param timer
     * @param viSurveyTaskBean
     */
    private void surveyStartTask(Timer timer, ViSurveyTaskBean viSurveyTaskBean) {
        Calendar calendar = Calendar.getInstance();
        //设备提前5分钟启动码流计划任务
        calendar.setTime(viSurveyTaskBean.getBeginTime());
        timer.schedule(new SurveyStartTask(viSurveyTaskBean), viSurveyTaskBean.getEnable() == 1 ? new Date() : calendar.getTime());
    }


    /**
     * 结束布控任务
     *
     * @param timer
     * @param viSurveyTaskBean
     */
    private void surveyStopTask(Timer timer, ViSurveyTaskBean viSurveyTaskBean) {
        Calendar calendar = Calendar.getInstance();
        //设备提前5分钟启动码流计划任务
        calendar.setTime(viSurveyTaskBean.getBeginTime());
        timer.schedule(new SurveyStopTask(viSurveyTaskBean), calendar.getTime());
    }

    /**
     * 设备停流
     *
     * @param timer
     * @param viSurveyTaskBean
     */
    private void videoStreamStopTask(Timer timer, ViSurveyTaskBean viSurveyTaskBean) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(viSurveyTaskBean.getBeginTime());
        //设备提前5分钟启动码流计划任务
        calendar.add(Calendar.MINUTE, Integer.parseInt("-" + NormalConfig.getStreamMinute()));
        timer.schedule(new VideoStreamStopTask(viSurveyTaskBean), calendar.getTime());
    }

    /**
     * 下发布控任务创建
     *
     * @param viSurveyTaskBean
     * @return
     */
    private BaseRequest<BKTaskSubmitRequest> BktaskSubmit(ViSurveyTaskBean viSurveyTaskBean) {

        //下发布控任务创建
        BaseRequest<BKTaskSubmitRequest> bkTaskSubmitRequestBaseRequest = new BaseRequest<>();
        BKTaskSubmitRequest bkTaskSubmitRequest = new BKTaskSubmitRequest();
        bkTaskSubmitRequest.setTaskId(viSurveyTaskBean.getTaskId());
        BKTaskMeta bkTaskMeta = new BKTaskMeta();
        bkTaskMeta.setBkid(bkrepoConfig.getBkid());
        bkTaskMeta.setDesc(viSurveyTaskBean.getSurveyName());
        ArrayList<BKTaskCameraInfo> bkTaskCameraInfos = new ArrayList<>();
        for (ViTaskDeviceBean viTaskDeviceBean : viSurveyTaskBean.getViTaskDeviceList()) {
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
