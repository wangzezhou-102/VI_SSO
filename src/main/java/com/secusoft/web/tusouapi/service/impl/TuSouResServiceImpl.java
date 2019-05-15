package com.secusoft.web.tusouapi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.secusoft.web.tusouapi.TuSouClient;
import com.secusoft.web.tusouapi.model.*;
import com.secusoft.web.tusouapi.service.TuSouResService;

public class TuSouResServiceImpl implements TuSouResService {


    @Override
    public BaseResponse<JSONArray> resStart(BaseRequest<ResStartRequest> request) {
        return null;
    }

    @Override
    public BaseResponse<JSONArray> resStop(BaseRequest<ResStopRequest> request) {
        return null;
    }

    @Override
    public BaseResponse<JSONArray> resList(BaseRequest<Object> request) {

        String requestStr = JSON.toJSONString(request);
        String responseStr = TuSouClient.getClientConnectionPool().fetchByPostMethod(TuSouClient.Path_RES_LIST,
                requestStr);
        BaseResponse<JSONArray> response = JSON.parseObject(responseStr, BaseResponse.class);
        return response;
    }

    @Override
    public BaseResponse<JSONArray> resDelete(BaseRequest<ResDeleteRequest> request) {
        return null;
    }
}
