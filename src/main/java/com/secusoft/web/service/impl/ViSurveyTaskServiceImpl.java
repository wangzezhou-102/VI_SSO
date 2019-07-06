package com.secusoft.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.secusoft.web.config.BkrepoConfig;
import com.secusoft.web.config.NormalConfig;
import com.secusoft.web.config.ServiceApiConfig;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.mapper.SysOrgRoadMapper;
import com.secusoft.web.mapper.ViSurveyTaskMapper;
import com.secusoft.web.mapper.ViTaskDeviceMapper;
import com.secusoft.web.mapper.ViTaskRepoMapper;
import com.secusoft.web.model.*;
import com.secusoft.web.service.ViSurveyTaskService;
import com.secusoft.web.serviceapi.*;
import com.secusoft.web.serviceapi.model.BaseResponse;
import com.secusoft.web.task.SurveyStartTask;
import com.secusoft.web.task.SurveyStopTask;
import com.secusoft.web.task.VideoStreamStartTask;
import com.secusoft.web.task.VideoStreamStopTask;
import com.secusoft.web.tusouapi.model.*;
import com.secusoft.web.utils.PageReturnUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ViSurveyTaskServiceImpl implements ViSurveyTaskService {
    private static Logger log = LoggerFactory.getLogger(ViSurveyTaskServiceImpl.class);

    @Resource
    ViSurveyTaskMapper viSurveyTaskMapper;

    @Resource
    ViTaskDeviceMapper viTaskDeviceMapper;

    @Resource
    ViTaskRepoMapper viTaskRepoMapper;

    @Resource
    SysOrgRoadMapper sysOrgRoadMapper;

    @Resource
    BkrepoConfig bkrepoConfig;


    @Transactional
    @Override
    public ResultVo insertViSurveyTask(ViSurveyTaskRequest viSurveyTaskRequest) {

        String requestStr = JSON.toJSONString(viSurveyTaskRequest);

        log.info("viSurveyTaskRequest：" + requestStr);
        if (viSurveyTaskRequest == null) {
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        if (!StringUtils.hasLength(viSurveyTaskRequest.getSurveyName())) {
            return ResultVo.failure(BizExceptionEnum.TASK_NANE_NULL.getCode(), BizExceptionEnum.TASK_NANE_NULL.getMessage());
        }
        if (viSurveyTaskRequest.getBeginTime() != null && viSurveyTaskRequest.getEndTime() != null) {
            if (viSurveyTaskRequest.getBeginTime().compareTo(viSurveyTaskRequest.getEndTime()) > 0) {
                return ResultVo.failure(BizExceptionEnum.TASK_DATE_WRONG.getCode(), BizExceptionEnum.TASK_DATE_WRONG.getMessage());
            }
        } else {
            return ResultVo.failure(BizExceptionEnum.TASK_DATE_NULL.getCode(), BizExceptionEnum.TASK_DATE_NULL.getMessage());
        }
        //判断算力
        if (!validUpdateCalute(viSurveyTaskRequest, null, null)) {
            return ResultVo.failure(BizExceptionEnum.TASK_CALUATE_FAIL.getCode(), BizExceptionEnum.TASK_CALUATE_FAIL.getMessage());
        }

        List<ViSurveyTaskBean> surveyTaskList = viSurveyTaskMapper.getAllViSurveyTask(viSurveyTaskRequest);
        if (surveyTaskList.size() > 0) {
            return ResultVo.failure(BizExceptionEnum.TASK_NANE_REPEATED.getCode(), BizExceptionEnum.TASK_NANE_REPEATED.getMessage());
        }
        log.info("开始新增布控任务");
        ViSurveyTaskBean viSurveyTaskBean = new ViSurveyTaskBean();
        //复制对象
        BeanCopier beanCopier = BeanCopier.create(ViSurveyTaskRequest.class, ViSurveyTaskBean.class, false);
        beanCopier.copy(viSurveyTaskRequest, viSurveyTaskBean, null);
//        viSurveyTaskBean.setBeginTime(viSurveyTaskRequest.getBeginTime());
//        viSurveyTaskBean.setEndTime(viSurveyTaskRequest.getEndTime());
//        viSurveyTaskBean.setSurveyName(viSurveyTaskRequest.getSurveyName());
//        viSurveyTaskBean.setSurveyType(viSurveyTaskRequest.getSurveyType());
//        viSurveyTaskBean.setAreaType(viSurveyTaskRequest.getAreaType());
//        viSurveyTaskBean.setSurveyName(viSurveyTaskRequest.getSurveyName());
        viSurveyTaskBean.setTaskId("t" + UUID.randomUUID().toString().replace("-", "").toLowerCase());
        viSurveyTaskMapper.insertViSurveyTask(viSurveyTaskBean);

        //设备信息
        String[] device = viSurveyTaskRequest.getSurveyDevice();
        List<ViTaskDeviceBean> list = new ArrayList<>();
        for (String str : device) {
            ViTaskDeviceBean viTaskDeviceBean = new ViTaskDeviceBean();
            viTaskDeviceBean.setDeviceId(str);
            viTaskDeviceBean.setTaskId(viSurveyTaskBean.getTaskId());
            viTaskDeviceBean.setViSurveyTask(viSurveyTaskBean);
            viTaskDeviceBean.setStatus(2);
            viTaskDeviceBean.setAction(2);
            list.add(viTaskDeviceBean);
        }
        viTaskDeviceMapper.insertBatch(list);
        viSurveyTaskBean.setViTaskDeviceList(list);
        //布控库信息
        String[] repo = viSurveyTaskRequest.getSurveyRepo();
        List<ViTaskRepoBean> listRepo = new ArrayList<>();
        for (String str : repo) {
            ViTaskRepoBean viTaskRepoBean = new ViTaskRepoBean();
            viTaskRepoBean.setRepoId(Integer.parseInt(str));
            viTaskRepoBean.setTaskId(viSurveyTaskBean.getTaskId());
            viTaskRepoBean.setViSurveyTask(viSurveyTaskBean);
            listRepo.add(viTaskRepoBean);
        }
        viTaskRepoMapper.insertBatch(listRepo);
        viSurveyTaskBean.setViTaskRepoList(listRepo);

        //下发布控任务创建
        BaseResponse baseResponse = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBktaskSubmit(), BktaskSubmit(viSurveyTaskBean));

//        BaseResponse baseResponse = new BaseResponse();
//        baseResponse.setCode(String.valueOf(BizExceptionEnum.OK.getCode()));
        Object dataJson = baseResponse.getData();
        String errorCode = baseResponse.getCode();
        String errorMsg = baseResponse.getMessage();
        //判断布控任务添加是否成功
        if (!String.valueOf(BizExceptionEnum.OK.getCode()).equals(errorCode)) {
            throw new RuntimeException(StringUtils.hasLength(errorMsg) ? errorMsg : "布控任务添加失败！");
        }

        //TQTimeTask(viSurveyTaskBean);

        log.info("结束新增布控任务");
        return ResultVo.success();
    }

    @Transactional
    @Override
    public ResultVo updateViSurveyTask(ViSurveyTaskRequest viSurveyTaskRequest) {
        String requestStr = JSON.toJSONString(viSurveyTaskRequest);

        log.info(requestStr);
        if (viSurveyTaskRequest == null) {
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        if (viSurveyTaskRequest.getId() == null || viSurveyTaskRequest.getId() <= 0) {
            return ResultVo.failure(BizExceptionEnum.TASK_ID_NULL.getCode(), BizExceptionEnum.TASK_ID_NULL.getMessage());
        }
        ViSurveyTaskBean bean = viSurveyTaskMapper.selectBeanByIdOrObjectId(viSurveyTaskRequest);

        if (!StringUtils.hasLength(viSurveyTaskRequest.getSurveyName())) {
            return ResultVo.failure(BizExceptionEnum.TASK_NANE_NULL.getCode(), BizExceptionEnum.TASK_NANE_NULL.getMessage());
        }
        if (viSurveyTaskRequest.getBeginTime() != null && viSurveyTaskRequest.getEndTime() != null) {
            if (viSurveyTaskRequest.getBeginTime().compareTo(viSurveyTaskRequest.getEndTime()) > 0) {
                return ResultVo.failure(BizExceptionEnum.TASK_DATE_WRONG.getCode(), BizExceptionEnum.TASK_DATE_WRONG.getMessage());
            }
        }
        //判断布控任务是否一致
        if (!viSurveyTaskRequest.getSurveyName().equals(bean.getSurveyName())) {
            bean.setSurveyName(viSurveyTaskRequest.getSurveyName());
        }
        //判断开始时间是否一致
        if (viSurveyTaskRequest.getBeginTime() != null && viSurveyTaskRequest.getBeginTime().compareTo(bean.getBeginTime()) != 0) {
            bean.setBeginTime(viSurveyTaskRequest.getBeginTime());
        }
        //判断结束时间是否一致
        if (viSurveyTaskRequest.getEndTime() != null && viSurveyTaskRequest.getEndTime().compareTo(bean.getEndTime()) != 0) {
            bean.setEndTime(viSurveyTaskRequest.getEndTime());
        }
        //取新增设备列表
        List<ViTaskDeviceBean> cutDeviceList = null;
        if (viSurveyTaskRequest.getSurveyDevice().length > 0) {
            List<String> listDevice = Arrays.asList(viSurveyTaskRequest.getSurveyDevice());
            List<ViTaskDeviceBean> newDevice = new ArrayList<>();
            for (String str : listDevice) {
                ViTaskDeviceBean beans = new ViTaskDeviceBean();
                beans.setDeviceId(str);
                beans.setTaskId(bean.getTaskId());
                newDevice.add(beans);
            }
            //拟去除设备列表
            cutDeviceList = removeToDevice(bean.getViTaskDeviceList(), newDevice, true);
            //拟新增设备列表
            List<ViTaskDeviceBean> diffrientDevice = removeToDevice(bean.getViTaskDeviceList(), newDevice, false);

            //判断算力
            if (!validUpdateCalute(viSurveyTaskRequest, cutDeviceList, diffrientDevice)) {
                return ResultVo.failure(BizExceptionEnum.TASK_CALUATE_UPDATE_FAIL.getCode(), BizExceptionEnum.TASK_CALUATE_UPDATE_FAIL.getMessage());
            }
//            List<ViTaskDeviceBean> collect =
//                    bean.getViTaskDeviceList().stream().filter((ViTaskDeviceBean beans) -> listDevice.contains(beans.getDeviceId())).collect(Collectors.toList());
//            List<ViTaskDeviceBean> removeDevice =
//                    bean.getViTaskDeviceList().stream().filter((ViTaskDeviceBean beans) -> !listDevice.contains(beans.getDeviceId())).collect(Collectors.toList());
            //判断需要移除的设备或者新老设备是否一致
            if (cutDeviceList.size() != 0 && bean.getSurveyStatus() == 1 && bean.getEnable() == 1) {
                log.info("暂停任务");
//                SurveyStopTask surveyStopTask = new SurveyStopTask(bean);
//                surveyStopTask.run();
//
                log.info("暂停相关设备");
//                ViSurveyTaskBean taskBean = bean;
//                taskBean.setViTaskDeviceList(cutDeviceList);
//                VideoStreamStopTask videoStreamStopTask = new VideoStreamStopTask(taskBean);
//                videoStreamStopTask.run();
            }
            log.info("ViTaskDeviceList size：" + bean.getViTaskDeviceList().size());
            if (diffrientDevice.size() > 0) {
                viTaskDeviceMapper.insertBatch(diffrientDevice);
                bean.getViTaskDeviceList().addAll(diffrientDevice);
                log.info("ViTaskDeviceList addAll：" + bean.getViTaskDeviceList().size());
            }
            if (cutDeviceList.size() > 0) {
                viTaskDeviceMapper.delBatchViTaskDevice(cutDeviceList);
                bean.getViTaskDeviceList().removeAll(cutDeviceList);
                log.info("ViTaskDeviceList removeAll：" + bean.getViTaskDeviceList().size());
            }
        }

        if (viSurveyTaskRequest.getSurveyRepo().length > 0) {
            List<String> listRepo = Arrays.asList(viSurveyTaskRequest.getSurveyRepo());
            List<ViTaskRepoBean> newRepo = new ArrayList<>();
            for (String str : listRepo) {
                Integer repoId = null;
                try {
                    repoId = Integer.valueOf(str);
                } catch (Exception ex) {
                    continue;
                }
                ViTaskRepoBean beans = new ViTaskRepoBean();
                beans.setRepoId(repoId);
                beans.setTaskId(bean.getTaskId());
                newRepo.add(beans);
            }
            List<ViTaskRepoBean> cutRepoList = removeToRepo(bean.getViTaskRepoList(), newRepo, true);
            List<ViTaskRepoBean> diffrientRepo = removeToRepo(bean.getViTaskRepoList(), newRepo, false);

            if(diffrientRepo.size()>0) {
                viTaskRepoMapper.insertBatch(diffrientRepo);
                bean.getViTaskRepoList().addAll(diffrientRepo);
                log.info("ViTaskRepoList addAll：" + bean.getViTaskRepoList().size());
            }
            log.info("ViTaskRepoList size：" + bean.getViTaskRepoList().size());
            if(cutRepoList.size()>0) {
                viTaskRepoMapper.delBatchViTaskRepo(cutRepoList);
                bean.getViTaskRepoList().removeAll(cutRepoList);
                log.info("ViTaskRepoList removeAll：" + bean.getViTaskRepoList().size());
            }
        }
        viSurveyTaskMapper.updateViSurveyTask(bean);
        if (cutDeviceList != null && cutDeviceList.size() > 0) {
            //判断开始时间是否一致
            if (viSurveyTaskRequest.getBeginTime().compareTo(bean.getBeginTime()) != 0) {
                SurveyStartTask surveyStartTask = new SurveyStartTask(bean);
                surveyStartTask.run();
            }
            //判断结束时间是否一致
            if (viSurveyTaskRequest.getEndTime().compareTo(bean.getEndTime()) != 0) {
                SurveyStopTask surveyStopTask = new SurveyStopTask(bean);
                surveyStopTask.run();
            }
        }
        return ResultVo.success();
    }

    @Override
    public ResultVo delViSurveyTask(ViSurveyTaskRequest viSurveyTaskRequest) {
        if (viSurveyTaskRequest == null) {
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        if (viSurveyTaskRequest.getId() == null && !StringUtils.hasLength(viSurveyTaskRequest.getTaskId())) {
            return ResultVo.failure(BizExceptionEnum.TASK_ID_NULL.getCode(), BizExceptionEnum.TASK_ID_NULL.getMessage());
        }
        log.info("开始删除布控任务");
        //组装数据下发天擎
        BaseRequest<BKTaskDeleteRequest> bkTaskDeleteRequestBaseRequest = new BaseRequest<>();
        BKTaskDeleteRequest bkTaskDeleteRequest = new BKTaskDeleteRequest();
        bkTaskDeleteRequest.setTaskIds(viSurveyTaskRequest.getTaskId());
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
        viSurveyTaskMapper.delViSurveyTask(viSurveyTaskRequest);

        log.info("结束删除布控任务");
        return ResultVo.success();
    }

    @Override
    public ResultVo getAllInformation(ViSurveyTaskVo viSurveyTaskVo) {
        PageHelper.startPage(viSurveyTaskVo.getCurrent(), viSurveyTaskVo.getSize());

        ViSurveyTaskRequest viSurveyTaskRequest = new ViSurveyTaskRequest();
        viSurveyTaskRequest.setSurveyType(viSurveyTaskVo.getSurveyType());
        List<ViSurveyTaskBean> list = viSurveyTaskMapper.getAllViSurveyTaskByPage(viSurveyTaskRequest);

        return ResultVo.success(PageReturnUtils.getPageMap(list, viSurveyTaskVo.getCurrent(), viSurveyTaskVo.getSize()));
    }

    /**
     * 开始任务
     *
     * @param viSurveyTaskRequest
     * @return
     */
    @Override
    public ResultVo startViSurveyTask(ViSurveyTaskRequest viSurveyTaskRequest) {
        if (viSurveyTaskRequest == null) {
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        if (viSurveyTaskRequest.getId() == null || viSurveyTaskRequest.getId() == 0) {
            return ResultVo.failure(BizExceptionEnum.TASK_ID_NULL.getCode(), BizExceptionEnum.TASK_ID_NULL.getMessage());
        }
        List<ViSurveyTaskBean> allViSurveyTask = viSurveyTaskMapper.getAllViSurveyTask(viSurveyTaskRequest);

        if (allViSurveyTask.size() == 0) {
            return ResultVo.failure(BizExceptionEnum.NOT_FOUND.getCode(), BizExceptionEnum.NOT_FOUND.getMessage());
        }
        try {
            SurveyStartTask surveyStartTask = new SurveyStartTask(allViSurveyTask.get(0));
            surveyStartTask.run();
        } catch (Exception ex) {
            return ResultVo.failure(BizExceptionEnum.TASK_START_FAIL.getCode(), BizExceptionEnum.TASK_START_FAIL.getMessage());
        }
        return ResultVo.success();
    }

    /**
     * 结束任务
     *
     * @param viSurveyTaskRequest
     * @return
     */
    @Override
    public ResultVo stopViSurveyTask(ViSurveyTaskRequest viSurveyTaskRequest) {

        if (null == viSurveyTaskRequest) {
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        if (viSurveyTaskRequest.getId() == null || viSurveyTaskRequest.getId() == 0) {
            return ResultVo.failure(BizExceptionEnum.TASK_ID_NULL.getCode(), BizExceptionEnum.TASK_ID_NULL.getMessage());
        }
        List<ViSurveyTaskBean> allViSurveyTask = viSurveyTaskMapper.getAllViSurveyTask(viSurveyTaskRequest);

        if (allViSurveyTask.size() == 0) {
            return ResultVo.failure(BizExceptionEnum.NOT_FOUND.getCode(), BizExceptionEnum.NOT_FOUND.getMessage());
        }

        try {
            SurveyStopTask surveyStopTask = new SurveyStopTask(allViSurveyTask.get(0));
            surveyStopTask.run();
        } catch (Exception ex) {
            return ResultVo.failure(BizExceptionEnum.TASK_STOP_FAIL.getCode(), BizExceptionEnum.TASK_STOP_FAIL.getMessage());
        }
        return ResultVo.success();
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

        log.info("原先的设备：" + StringUtils.arrayToDelimitedString(viSurveyTaskRequest.getSurveyDevice(), ","));
        //活动中的设备
        List<ViTaskDeviceBean> actionDeviceList = viTaskDeviceMapper.getActionDevice(StringUtils.arrayToDelimitedString(viSurveyTaskRequest.getSurveyDevice(), ","));
        //新增设备数
        Integer inactiveDevice = viSurveyTaskRequest.getSurveyDevice().length;
        //准备布控的设备数
        Integer readyDevice = inactiveDevice - actionDeviceList.size();
        SysOrgRoadBean sysOrgRoadBean = new SysOrgRoadBean();
        sysOrgRoadBean.setOrgCode("330401");
        SysOrgRoadBean sysOrgRoadByOrgCode = sysOrgRoadMapper.getSysOrgRoadByOrgCode(sysOrgRoadBean);
        if (cutDeviceList == null || diffrientDevice == null) {
            if ((sysOrgRoadByOrgCode.getUsedRoads() + readyDevice) > sysOrgRoadByOrgCode.getTotalRoads()) {
                return false;
            }
        } else {
            if ((sysOrgRoadByOrgCode.getUsedRoads() - cutDeviceList.size() + diffrientDevice.size()) > sysOrgRoadByOrgCode.getTotalRoads()) {
                return false;
            }
        }
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
