package com.secusoft.web.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.secusoft.web.config.BkContinueTrackingConfig;
import com.secusoft.web.config.BkrepoConfig;
import com.secusoft.web.model.BkContinueTrackingRequest;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.BkContinueTrackingService;
import com.secusoft.web.tusouapi.model.BaseRequest;
import com.secusoft.web.tusouapi.model.BaseResponse;
import com.secusoft.web.tusouapi.model.SearchRequestData;
import com.secusoft.web.tusouapi.service.TuSouSearchService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Service
public class BkContinueTrackingServiceImpl implements BkContinueTrackingService {


    @Resource
    TuSouSearchService tuSouSearch;

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
        return ResultVo.success(search);
    }
}
