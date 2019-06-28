package com.secusoft.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.secusoft.web.config.BkrepoConfig;
import com.secusoft.web.config.NormalConfig;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.mapper.ViSurveyTaskMapper;
import com.secusoft.web.mapper.ViTaskDeviceMapper;
import com.secusoft.web.mapper.ViTaskRepoMapper;
import com.secusoft.web.model.*;
import com.secusoft.web.service.ViSurveyTaskService;
import com.secusoft.web.serviceapi.model.BaseResponse;
import com.secusoft.web.task.SurveyStartTask;
import com.secusoft.web.task.SurveyStopTask;
import com.secusoft.web.task.VideoStreamStartTask;
import com.secusoft.web.task.VideoStreamStopTask;
import com.secusoft.web.tusouapi.model.*;
import com.secusoft.web.utils.PageReturnUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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
    BkrepoConfig bkrepoConfig;


    @Transactional
    @Override
    public ResultVo insertViSurveyTask(ViSurveyTaskRequest viSurveyTaskRequest) {
        if (viSurveyTaskRequest == null) {
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        if (!StringUtils.hasLength(viSurveyTaskRequest.getSurveyName())) {
            return ResultVo.failure(BizExceptionEnum.TASK_NANE_NULL.getCode(),
                    BizExceptionEnum.TASK_NANE_NULL.getMessage());
        }
        if (viSurveyTaskRequest.getBeginTime() != null && viSurveyTaskRequest.getEndTime() != null) {
            if (viSurveyTaskRequest.getBeginTime().compareTo(viSurveyTaskRequest.getEndTime()) > 0) {
                return ResultVo.failure(BizExceptionEnum.TASK_DATE_WRONG.getCode(),
                        BizExceptionEnum.TASK_DATE_WRONG.getMessage());
            }
        } else {
            return ResultVo.failure(BizExceptionEnum.TASK_DATE_NULL.getCode(),
                    BizExceptionEnum.TASK_DATE_NULL.getMessage());
        }
        List<ViSurveyTaskBean> surveyTaskList = viSurveyTaskMapper.getAllViSurveyTask(viSurveyTaskRequest);
        if (surveyTaskList.size() > 0) {
            return ResultVo.failure(BizExceptionEnum.TASK_NANE_REPEATED.getCode(),
                    BizExceptionEnum.TASK_NANE_REPEATED.getMessage());
        }
        log.info("开始新增布控任务");
        ViSurveyTaskBean viSurveyTaskBean = new ViSurveyTaskBean();
        viSurveyTaskBean.setBeginTime(viSurveyTaskRequest.getBeginTime());
        viSurveyTaskBean.setEndTime(viSurveyTaskRequest.getEndTime());
        viSurveyTaskBean.setSurveyName(viSurveyTaskRequest.getSurveyName());
        viSurveyTaskBean.setSurveyType(viSurveyTaskRequest.getSurveyType());
        viSurveyTaskBean.setAreaType(viSurveyTaskRequest.getAreaType());
        viSurveyTaskBean.setSurveyName(viSurveyTaskRequest.getSurveyName());
        viSurveyTaskBean.setSurveyStatus(2);
        viSurveyTaskBean.setEnable(2);
        //由于taskid唯一，先放置一个随机数区别
        int randomNumber = (int) Math.round(Math.random() * (25000 - 1) + 1);
        viSurveyTaskBean.setTaskId("t" + randomNumber);
        viSurveyTaskBean.setTopic("topict" + randomNumber);
        viSurveyTaskMapper.insertViSurveyTask(viSurveyTaskBean);
        System.out.println(viSurveyTaskBean.getId());
        //获取id，重新赋值taskid和topic
        viSurveyTaskBean.setTaskId("t" + viSurveyTaskBean.getId());
        viSurveyTaskBean.setTopic("topict" + viSurveyTaskBean.getId());
        viSurveyTaskMapper.updateViSurveyTask(viSurveyTaskBean);
        //设备信息
        String[] device = viSurveyTaskRequest.getSurveyDevice().split(",");
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
        String[] repo = viSurveyTaskRequest.getSurveyRepo().split(",");
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
        BaseRequest<BKTaskSubmitRequest> bkTaskSubmitRequestBaseRequest = new BaseRequest<>();
        BKTaskSubmitRequest bkTaskSubmitRequest = new BKTaskSubmitRequest();
        bkTaskSubmitRequest.setTaskId(viSurveyTaskBean.getTaskId());
        BKTaskMeta bkTaskMeta = new BKTaskMeta();
        bkTaskMeta.setBkid(bkrepoConfig.getBkid());
        bkTaskMeta.setDesc(viSurveyTaskBean.getSurveyName());
        ArrayList<BKTaskCameraInfo> bkTaskCameraInfos = new ArrayList<>();
        for (String str : device) {
            BKTaskCameraInfo bkTaskCameraInfo = new BKTaskCameraInfo();
            bkTaskCameraInfo.setCameraId(str);
            bkTaskCameraInfo.setThreshold(bkrepoConfig.getThreshold());
            bkTaskCameraInfos.add(bkTaskCameraInfo);
        }
        bkTaskMeta.setCameraInfo(bkTaskCameraInfos);
        bkTaskSubmitRequest.setMeta(bkTaskMeta);

        bkTaskSubmitRequestBaseRequest.setData(bkTaskSubmitRequest);
        bkTaskSubmitRequestBaseRequest.setRequestId(bkrepoConfig.getRequestId());

        //BaseResponse baseResponse = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBktaskSubmit(), bkTaskSubmitRequestBaseRequest);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(String.valueOf(BizExceptionEnum.OK.getCode()));
        Object dataJson = baseResponse.getData();
        String errorCode = baseResponse.getCode();
        String errorMsg = baseResponse.getMessage();

        //判断布控任务添加是否成功
        if (!String.valueOf(BizExceptionEnum.OK.getCode()).equals(errorCode)) {
            throw new RuntimeException("布控任务添加失败！");
        }

        TQTimeTask(viSurveyTaskBean);

        log.info("结束新增布控任务");
        return ResultVo.success();
    }

    @Override
    public ResultVo updateViSurveyTask(ViSurveyTaskRequest viSurveyTaskRequest) {
        if (viSurveyTaskRequest == null) {
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        if (viSurveyTaskRequest.getId() == null || viSurveyTaskRequest.getId() <= 0) {
            return ResultVo.failure(BizExceptionEnum.TASK_ID_NULL.getCode(), BizExceptionEnum.TASK_ID_NULL.getMessage());
        }
        ViSurveyTaskBean bean = viSurveyTaskMapper.selectById(viSurveyTaskRequest.getId());

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
            bean.setSurveyName(bean.getSurveyName());
        }
        //判断开始时间是否一致
        if (viSurveyTaskRequest.getBeginTime().compareTo(bean.getBeginTime()) != 0) {
            bean.setBeginTime(bean.getBeginTime());
        }
        //判断结束时间是否一致
        if (viSurveyTaskRequest.getEndTime().compareTo(bean.getEndTime()) != 0) {
            bean.setEndTime(bean.getEndTime());
        }
        if(StringUtils.hasLength(viSurveyTaskRequest.getSurveyDevice())) {
            List<String> listDevice = Arrays.asList(viSurveyTaskRequest.getSurveyDevice().split(","));

            List<ViTaskDeviceBean> collect =
                    bean.getViTaskDeviceList().stream().filter((ViTaskDeviceBean beans) -> listDevice.contains(beans.getDeviceId())).collect(Collectors.toList());
            List<ViTaskDeviceBean> removeDevice =
                    bean.getViTaskDeviceList().stream().filter((ViTaskDeviceBean beans) -> !listDevice.contains(beans.getDeviceId())).collect(Collectors.toList());
            //判断需要移除的设备或者新老设备是否一致
            if ((removeDevice.size() != 0 || !bean.getViTaskDeviceList().containsAll(collect))&&bean.getSurveyStatus()==1&&bean.getEnable()==1) {
                log.info("暂停任务");
                SurveyStopTask surveyStopTask = new SurveyStopTask(bean);
                surveyStopTask.run();

                log.info("暂停相关设备");
                ViSurveyTaskBean taskBean=bean;
                taskBean.setViTaskDeviceList(removeDevice);
                VideoStreamStopTask videoStreamStopTask=new VideoStreamStopTask(taskBean);
                videoStreamStopTask.run();
            }

            List<ViTaskDeviceBean> addDevice = new ArrayList<>();
            for (String str : listDevice) {
                boolean result = true;
                for (ViTaskDeviceBean deviceBean : bean.getViTaskDeviceList()) {
                    if (deviceBean.getDeviceId().equals(str)) {
                        result = false;
                    }
                }
                if (result) {
                    ViTaskDeviceBean viTaskDeviceBean = new ViTaskDeviceBean();
                    viTaskDeviceBean.setDeviceId(str);
                    viTaskDeviceBean.setTaskId(bean.getTaskId());
                    addDevice.add(viTaskDeviceBean);
                }
            }

            viTaskDeviceMapper.insertBatch(addDevice);
            collect.addAll(addDevice);
            bean.setViTaskDeviceList(collect);
        }
        viSurveyTaskMapper.updateViSurveyTask(bean);
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

//        BaseResponse baseResponse = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBktaskSubmit(), bkTaskDeleteRequestBaseRequest);
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
        List<ViSurveyTaskBean> list = viSurveyTaskMapper.getAllViSurveyTask(viSurveyTaskRequest);

        return ResultVo.success(PageReturnUtils.getPageMap(list, viSurveyTaskVo.getCurrent(),
                viSurveyTaskVo.getSize()));
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
        videoStreamStartTask(timer,viSurveyTaskBean);

        //开始布控任务
        surveyStartTask(timer,viSurveyTaskBean);

        //停止布控任务
        surveyStopTask(timer,viSurveyTaskBean);

        //设备延后固定时间停止码流计划任务
        videoStreamStopTask(timer,viSurveyTaskBean);
    }

    /**
     * 设备启流
     * @param timer
     * @param viSurveyTaskBean
     */
    private void videoStreamStartTask(Timer timer,ViSurveyTaskBean viSurveyTaskBean){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(viSurveyTaskBean.getBeginTime());
        //设备提前5分钟启动码流计划任务
        calendar.add(Calendar.MINUTE, Integer.parseInt("-" + NormalConfig.getStreamMinute()));
        timer.schedule(new VideoStreamStartTask(viSurveyTaskBean), calendar.getTime());
    }

    /**
     * 开始布控任务
     * @param timer
     * @param viSurveyTaskBean
     */
    private void surveyStartTask(Timer timer,ViSurveyTaskBean viSurveyTaskBean){
        Calendar calendar = Calendar.getInstance();
        //设备提前5分钟启动码流计划任务
        calendar.setTime(viSurveyTaskBean.getBeginTime());
        timer.schedule(new SurveyStartTask(viSurveyTaskBean), calendar.getTime());
    }


    /**
     * 结束布控任务
     * @param timer
     * @param viSurveyTaskBean
     */
    private void surveyStopTask(Timer timer,ViSurveyTaskBean viSurveyTaskBean){
        Calendar calendar = Calendar.getInstance();
        //设备提前5分钟启动码流计划任务
        calendar.setTime(viSurveyTaskBean.getBeginTime());
        timer.schedule(new SurveyStopTask(viSurveyTaskBean), calendar.getTime());
    }

    /**
     * 设备停流
     * @param timer
     * @param viSurveyTaskBean
     */
    private void videoStreamStopTask(Timer timer,ViSurveyTaskBean viSurveyTaskBean){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(viSurveyTaskBean.getBeginTime());
        //设备提前5分钟启动码流计划任务
        calendar.add(Calendar.MINUTE, Integer.parseInt("-" + NormalConfig.getStreamMinute()));
        timer.schedule(new VideoStreamStopTask(viSurveyTaskBean), calendar.getTime());
    }

    /**
     * 运力计算
     *
     * @return
     */
    private boolean computing() {
        boolean result = false;

        return false;
    }
}
