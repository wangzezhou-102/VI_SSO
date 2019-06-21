package com.secusoft.web.service.impl;

import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.secusoft.web.config.BkrepoConfig;
import com.secusoft.web.config.NormalConfig;
import com.secusoft.web.config.ServiceApiConfig;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.mapper.ViSurveyTaskMapper;
import com.secusoft.web.mapper.ViTaskDeviceMapper;
import com.secusoft.web.mapper.ViTaskRepoMapper;
import com.secusoft.web.model.*;
import com.secusoft.web.service.ViSurveyTaskService;
import com.secusoft.web.serviceapi.ServiceApiClient;
import com.secusoft.web.serviceapi.model.BaseResponse;
import com.secusoft.web.task.SurveyStartTask;
import com.secusoft.web.task.SurveyStopTask;
import com.secusoft.web.task.VideoStreamStartTask;
import com.secusoft.web.task.VideoStreamStopTask;
import com.secusoft.web.tusouapi.model.BKTaskCameraInfo;
import com.secusoft.web.tusouapi.model.BKTaskMeta;
import com.secusoft.web.tusouapi.model.BKTaskSubmitRequest;
import com.secusoft.web.tusouapi.model.BaseRequest;
import com.secusoft.web.utils.PageReturnUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;

@Service
public class ViSurveyTaskServiceImpl implements ViSurveyTaskService {

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
        bkTaskSubmitRequestBaseRequest.setRequestId(bkrepoConfig.getRequestId());
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

        BaseResponse baseResponse =
                ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBktaskSubmit(),
                        bkTaskSubmitRequestBaseRequest);

        Object dataJson = baseResponse.getData();
        String errorCode = baseResponse.getCode();
        String errorMsg = baseResponse.getMessage();

        //判断布控任务添加是否成功
        if (BizExceptionEnum.OK.getCode() != Integer.parseInt(errorCode)) {
            throw new RuntimeException("布控任务添加失败！");
        }

        TuSouTimeTask(viSurveyTaskBean);

        return ResultVo.success();
    }

    @Override
    public ResultVo updateViSurveyTask(ViSurveyTaskBean viSurveyTaskBean) {
        if (viSurveyTaskBean == null) {
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        if (!StringUtils.hasLength(viSurveyTaskBean.getSurveyName())) {
            return ResultVo.failure(BizExceptionEnum.TASK_NANE_NULL.getCode(),
                    BizExceptionEnum.TASK_NANE_NULL.getMessage());
        }
        if (viSurveyTaskBean.getBeginTime() != null && viSurveyTaskBean.getEndTime() != null) {
            if (viSurveyTaskBean.getBeginTime().compareTo(viSurveyTaskBean.getEndTime()) > 0) {
                return ResultVo.failure(BizExceptionEnum.TASK_DATE_WRONG.getCode(),
                        BizExceptionEnum.TASK_DATE_WRONG.getMessage());
            }
        }

        return ResultVo.success();
    }

    @Override
    public ResultVo delViSurveyTask(Integer id) {
        if (id == 0) {
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        viSurveyTaskMapper.delViSurveyTask(id);
        return ResultVo.success();
    }

    @Override
    public ResultVo getAllInformation(ViSurveyTaskVo viSurveyTaskVo) {
        PageHelper.startPage(viSurveyTaskVo.getCurrent(), viSurveyTaskVo.getSize());

        List<ViSurveyTaskBean> list = viSurveyTaskMapper.getAllViSurveyTask(null);

        return ResultVo.success(PageReturnUtils.getPageMap(list, viSurveyTaskVo.getCurrent(),
                viSurveyTaskVo.getSize()));
    }

    @Override
    public ResultVo startViSurveyTask(ViSurveyTaskRequest viSurveyTaskRequest) {
        //开始任务
        if (1 == viSurveyTaskRequest.getEnable()) {

        } else if (0 == viSurveyTaskRequest.getEnable()) {//停止任务

        }
        return ResultVo.success();
    }

    /**
     * 布控定时任务
     *
     * @param id
     */
    private void TuSouTimeTask(ViSurveyTaskBean viSurveyTaskBean) {
//        ViSurveyTaskRequest viSurveyTaskRequest = new ViSurveyTaskRequest();
//        viSurveyTaskRequest.setId(id);
//        List<ViSurveyTaskBean> lists = viSurveyTaskMapper.getAllViSurveyTask(viSurveyTaskRequest);
//
//        ViSurveyTaskBean viSurveyTaskBean = lists.get(0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(viSurveyTaskBean.getBeginTime());
        Timer timer = new Timer();
        //设备提前5分钟启动码流计划任务
        calendar.add(Calendar.MINUTE, Integer.parseInt("-" + NormalConfig.getStreamMinute()));
        timer.schedule(new VideoStreamStartTask(viSurveyTaskBean),
                calendar.getTime());

        //开始布控任务
        calendar.setTime(viSurveyTaskBean.getBeginTime());
        timer.schedule(new SurveyStartTask(viSurveyTaskBean), calendar.getTime());

        calendar.setTime(viSurveyTaskBean.getEndTime());

        //停止布控任务
        calendar.setTime(viSurveyTaskBean.getEndTime());
        timer.schedule(new SurveyStopTask(viSurveyTaskBean), calendar.getTime());

        //设备延后固定时间停止码流计划任务
        calendar.setTime(viSurveyTaskBean.getEndTime());
        calendar.add(Calendar.MINUTE, Integer.parseInt(NormalConfig.getStreamMinute()));
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
