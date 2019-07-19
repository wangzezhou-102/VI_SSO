package com.secusoft.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.secusoft.web.config.BkContinueTrackingConfig;
import com.secusoft.web.config.BkrepoConfig;
import com.secusoft.web.config.ServiceApiConfig;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.mapper.ViRealtimeTraceMapper;
import com.secusoft.web.mapper.ViTraceDeviceMapper;
import com.secusoft.web.model.*;
import com.secusoft.web.service.ViRealtimeTraceService;
import com.secusoft.web.serviceapi.ServiceApiClient;
import com.secusoft.web.tusouapi.model.BaseRequest;
import com.secusoft.web.tusouapi.model.SearchRequestData;
import com.secusoft.web.utils.PageReturnUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ViRealtimeTraceServiceImpl implements ViRealtimeTraceService {
    private static Logger log = LoggerFactory.getLogger(ViRealtimeTraceServiceImpl.class);


    @Resource
    BkrepoConfig bkrepoConfig;

    @Resource
    ViRealtimeTraceMapper viRealtimeTraceMapper;

    @Resource
    ViTraceDeviceMapper viTraceDeviceMapper;


    @Override
    public ResultVo insertViRealtimeTrace(ViRealtimeTraceRequest request) {
        if (request == null) {
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        if (!StringUtils.hasLength(request.getTraceName())) {
            return ResultVo.failure(BizExceptionEnum.TRACE_NANE_NULL.getCode(), BizExceptionEnum.TRACE_NANE_NULL.getMessage());
        }
        if (!StringUtils.hasLength(request.getContent())) {
            return ResultVo.failure(BizExceptionEnum.TRACE_IMGCONTENT_NULL.getCode(), BizExceptionEnum.TRACE_IMGCONTENT_NULL.getMessage());
        }

        if (request.getDeviceId() == null || request.getDeviceId().length == 0) {
            return ResultVo.failure(BizExceptionEnum.TRACE_DEVICE_NULL.getCode(), BizExceptionEnum.TRACE_DEVICE_NULL.getMessage());
        }

        ViRealtimeTraceBean bean = new ViRealtimeTraceBean();
        bean.setTraceName(request.getTraceName());
        bean.setValidState(1);
        ViRealtimeTraceBean viRealtimeTraceById = viRealtimeTraceMapper.getViRealtimeTraceById(bean);
        if (viRealtimeTraceById != null) {
            return ResultVo.failure(BizExceptionEnum.TRACE_NANE_REPEATED.getCode(), BizExceptionEnum.TRACE_NANE_REPEATED.getMessage());
        }

        bean = new ViRealtimeTraceBean();

        //复制对象
        BeanCopier beanCopier = BeanCopier.create(ViRealtimeTraceRequest.class, ViRealtimeTraceBean.class, false);
        beanCopier.copy(request, bean, null);

        bean.setTraceId("t" + UUID.randomUUID().toString().replace("-", "").toLowerCase());
        bean.setUserId("1");
        viRealtimeTraceMapper.insertViRealtimeTrace(bean);

        List<ViTraceDeviceBean> list = new ArrayList<>();
        for (String str : request.getDeviceId()) {
            ViTraceDeviceBean viTraceDeviceBean = new ViTraceDeviceBean();
            viTraceDeviceBean.setTraceId(bean.getTraceId());
            viTraceDeviceBean.setDeviceId(str);
            list.add(viTraceDeviceBean);
        }
        viTraceDeviceMapper.insertBatch(list);

        return ResultVo.success();
    }

    @Override
    public ResultVo updateViRealtimeTrace(ViRealtimeTraceRequest request) {
        if (request == null) {
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }

        if (request.getId() == null || request.getId() <= 0) {
            return ResultVo.failure(BizExceptionEnum.TRACE_ID_NULL.getCode(), BizExceptionEnum.TRACE_ID_NULL.getMessage());
        }

        if (!StringUtils.hasLength(request.getTraceName())) {
            return ResultVo.failure(BizExceptionEnum.TRACE_NANE_NULL.getCode(), BizExceptionEnum.TRACE_NANE_NULL.getMessage());
        }
        if (!StringUtils.hasLength(request.getContent())) {
            return ResultVo.failure(BizExceptionEnum.TRACE_IMGCONTENT_NULL.getCode(), BizExceptionEnum.TRACE_IMGCONTENT_NULL.getMessage());
        }

        if (request.getDeviceId() == null || request.getDeviceId().length == 0) {
            return ResultVo.failure(BizExceptionEnum.TRACE_DEVICE_NULL.getCode(), BizExceptionEnum.TRACE_DEVICE_NULL.getMessage());
        }

        ViRealtimeTraceBean bean = new ViRealtimeTraceBean();
        bean.setId(request.getId());
        bean.setValidState(1);
        ViRealtimeTraceBean viRealtimeTraceById = viRealtimeTraceMapper.getViRealtimeTraceById(bean);
        if (viRealtimeTraceById == null) {
            return ResultVo.failure(BizExceptionEnum.NOT_FOUND.getCode(), BizExceptionEnum.NOT_FOUND.getMessage());
        }

        if (!viRealtimeTraceById.getTraceName().equals(request.getTraceName())) {
            bean = new ViRealtimeTraceBean();
            bean.setTraceName(request.getTraceName());
            bean.setValidState(1);
            viRealtimeTraceById = viRealtimeTraceMapper.getViRealtimeTraceById(bean);
            if (viRealtimeTraceById != null && viRealtimeTraceById.getId() != request.getId()) {
                return ResultVo.failure(BizExceptionEnum.TRACE_NANE_REPEATED.getCode(), BizExceptionEnum.TRACE_NANE_REPEATED.getMessage());
            }
            viRealtimeTraceById.setTraceName(request.getTraceName());
        }

        if (!viRealtimeTraceById.getContent().equals(request.getContent())) {
            viRealtimeTraceById.setContent(request.getContent());
        }
        //取需去除设备列表
        List<ViTraceDeviceBean> cutDeviceList = null;
        //取新增设备列表
        List<ViTraceDeviceBean> diffrientDevice = null;
        if (request.getDeviceId() != null || request.getDeviceId().length > 0) {
            List<String> listDevice = Arrays.asList(request.getDeviceId());
            List<ViTraceDeviceBean> newDevice = new ArrayList<>();
            for (String str : listDevice) {
                ViTraceDeviceBean beans = new ViTraceDeviceBean();
                beans.setDeviceId(str);
                beans.setTraceId(viRealtimeTraceById.getTraceId());
                newDevice.add(beans);
            }

            //拟去除设备列表
            cutDeviceList = removeToDevice(viRealtimeTraceById.getViTraceDeviceBeans(), newDevice, true);
            //拟新增设备列表
            diffrientDevice = removeToDevice(viRealtimeTraceById.getViTraceDeviceBeans(), newDevice, false);

            if (diffrientDevice.size() > 0) {
                viTraceDeviceMapper.insertBatch(diffrientDevice);
                viRealtimeTraceById.getViTraceDeviceBeans().addAll(diffrientDevice);
                log.info("ViTaskDeviceList addAll：" + viRealtimeTraceById.getViTraceDeviceBeans().size());
            }
            if (cutDeviceList.size() > 0) {
                viTraceDeviceMapper.delBatchViTaskDevice(cutDeviceList);
                viRealtimeTraceById.getViTraceDeviceBeans().removeAll(cutDeviceList);
                log.info("ViTaskDeviceList removeAll：" + viRealtimeTraceById.getViTraceDeviceBeans().size());
            }
        }
        viRealtimeTraceById.setModifyTime(new Date());
        viRealtimeTraceMapper.updateViRealtimeTrace(viRealtimeTraceById);
        return ResultVo.success();
    }

    @Override
    public ResultVo delViRealtimeTrace(ViRealtimeTraceRequest request) {
        if (request == null) {
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }

        if (request.getId() == null || request.getId() <= 0) {
            return ResultVo.failure(BizExceptionEnum.TRACE_ID_NULL.getCode(), BizExceptionEnum.TRACE_ID_NULL.getMessage());
        }
        viRealtimeTraceMapper.delViRealtimeTrace(request.getId());
        return ResultVo.success();
    }

    @Override
    public ResultVo getAllViRealtimeTrace(ViRealtimeTraceVo viRealtimeTraceVo) {
        PageHelper.startPage(viRealtimeTraceVo.getCurrent(), viRealtimeTraceVo.getSize());
        List<ViRealtimeTraceBean> allViRealtimeTrace = viRealtimeTraceMapper.getAllViRealtimeTrace(null);
        Map<String, Object> pageMap = PageReturnUtils.getPageMap(allViRealtimeTrace, viRealtimeTraceVo.getCurrent(), viRealtimeTraceVo.getSize());

        ArrayList<ViRealtimeTraceBean> records = (ArrayList<ViRealtimeTraceBean>) pageMap.get("records");
        for (ViRealtimeTraceBean viRealtimeTraceBean : records) {
            ViTraceDeviceBean viTraceDeviceBean = new ViTraceDeviceBean();
            viTraceDeviceBean.setTraceId(viRealtimeTraceBean.getTraceId());
            List<ViTraceDeviceBean> allViTraceDevice = viTraceDeviceMapper.getAllViTraceDevice(viTraceDeviceBean);
            viRealtimeTraceBean.setViTraceDeviceBeans(allViTraceDevice);
        }
        pageMap.put("records", records);

        return ResultVo.success(pageMap);
    }

    @Override
    public ResultVo viRealtimeTraceSearch(ViRealtimeTraceRequest request) {
        BaseRequest<SearchRequestData> baseRequest = new BaseRequest<>();
        //这个随机生成也可以
        baseRequest.setRequestId(bkrepoConfig.getRequestId());
        //from 是跳过多少个结果    size是本次展示多少个结果
        baseRequest.setFrom(request.getFrom());
        baseRequest.setSize(request.getSize());

        SearchRequestData searchRequestData = new SearchRequestData();
        //以下三个固定
        searchRequestData.setUid(BkContinueTrackingConfig.getUid());
        searchRequestData.setTaskId(BkContinueTrackingConfig.getTaskId());
        searchRequestData.setType(BkContinueTrackingConfig.getType());

        //要搜索的时间 采用时间戳
        searchRequestData.setStartTime(request.getStartTime().getTime());
        searchRequestData.setEndTime(request.getEndTime().getTime());

        //图片的base64  不带前面的base64标记
        searchRequestData.setContents(request.getContent());

        //要搜索的设备ID      用，隔开
        //searchRequestData.setCameraId(StringUtils.arrayToDelimitedString(bkContinueTrackingRequest.getDeviceId(), ","));
        baseRequest.setData(searchRequestData);

        System.out.println(JSON.toJSONString(baseRequest));
        //这里可以直接将search返回给前端  默认已相似度排序
        com.secusoft.web.serviceapi.model.BaseResponse baseResponse = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getTusouSearch(),
                baseRequest);

        String code = baseResponse.getCode();
        if (!String.valueOf(BizExceptionEnum.OK.getCode()).equals(code)) {
            return ResultVo.failure(BizExceptionEnum.REQUEST_SERVER_ERROR.getCode(), BizExceptionEnum.REQUEST_SERVER_ERROR.getMessage());
        }

        return ResultVo.success(baseResponse.getData());
    }

    /**
     * 新老设备列表对比
     *
     * @param oldList
     * @param newList
     * @param type    true-返回原设备差集合 false-返回设备列表中不存在的集合
     * @return
     */
    private List<ViTraceDeviceBean> removeToDevice(List<ViTraceDeviceBean> oldList, List<ViTraceDeviceBean> newList, boolean type) {
        List<ViTraceDeviceBean> returnNewList = new ArrayList<>();//拟新增的设备
        Set<ViTraceDeviceBean> beanSet = new HashSet<>();//拟去除的设备
        Set<ViTraceDeviceBean> beanSameSet = new HashSet<>();
        for (ViTraceDeviceBean newBean : newList) {
            boolean result = true;
            for (ViTraceDeviceBean oldBean : oldList) {
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
}
