package com.secusoft.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.secusoft.web.config.BkContinueTrackingConfig;
import com.secusoft.web.config.BkrepoConfig;
import com.secusoft.web.mapper.DeviceMapper;
import com.secusoft.web.model.BkContinueTrackingRequest;
import com.secusoft.web.model.DeviceBean;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.BkContinueTrackingService;
import com.secusoft.web.tusouapi.model.BaseRequest;
import com.secusoft.web.tusouapi.model.BaseResponse;
import com.secusoft.web.tusouapi.model.SearchRequestData;
import com.secusoft.web.tusouapi.model.SearchResponseData;
import com.secusoft.web.tusouapi.service.TuSouSearchService;
import com.secusoft.web.utils.ImageUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class BkContinueTrackingServiceImpl implements BkContinueTrackingService {


    @Resource
    TuSouSearchService tuSouSearch;

    @Resource
    DeviceMapper deviceMapper;

    @Resource
    BkrepoConfig bkrepoConfig;

    @Override
    public ResultVo getBkContinueTracking(BkContinueTrackingRequest bkContinueTrackingRequest) {
        BaseRequest<SearchRequestData> baseRequest = new BaseRequest<>();
        //这个随机生成也可以
        baseRequest.setRequestId(bkrepoConfig.getRequestId());
        //from 是跳过多少个结果    size是本次展示多少个结果
        baseRequest.setFrom(bkContinueTrackingRequest.getFrom());
        baseRequest.setSize(bkContinueTrackingRequest.getSize());

        SearchRequestData searchRequestData = new SearchRequestData();
        //以下三个固定
        searchRequestData.setUid(BkContinueTrackingConfig.getUid());
        searchRequestData.setTaskId(BkContinueTrackingConfig.getTaskId());
        searchRequestData.setType(BkContinueTrackingConfig.getType());

        //要搜索的时间 采用时间戳
        searchRequestData.setStartTime(bkContinueTrackingRequest.getStartTime().getTime());
        searchRequestData.setEndTime(bkContinueTrackingRequest.getEndTime().getTime());

        //图片的base64  不带前面的base64标记
        searchRequestData.setContents(bkContinueTrackingRequest.getContent());

        //要搜索的设备ID      用，隔开
        //searchRequestData.setCameraId(StringUtils.arrayToDelimitedString(bkContinueTrackingRequest.getDeviceId(), ","));
        baseRequest.setData(searchRequestData);
        //这里可以直接将search返回给前端  默认已相似度排序
        BaseResponse<JSONArray> search = tuSouSearch.search(baseRequest);
        JSONArray data = search.getData();
        ArrayList<SearchResponseData> searchResponseData = JSON.parseObject(JSON.toJSONString(data), new TypeReference<ArrayList<SearchResponseData>>() { });

        for (SearchResponseData bean:searchResponseData) {
            DeviceBean deviceBean = deviceMapper.selectDeviceByDeviceId(bean.getSource().getCameraId());

            bean.getSource().setCropImageSignedBase64(ImageUtils.image2Base64("http://33.95.245.249:8006/static/Alarm/201907/Alarm-db558b61d56126ef4c38c5a8556d51d81.jpg"));
            //bean.getSource().setCropImageSignedBase64(ImageUtils.image2Base64(bean.getSource().getCropImageSigned()));
            bean.getSource().setDeviceBean(deviceBean);
        }

        return ResultVo.success(searchResponseData);
    }
}
