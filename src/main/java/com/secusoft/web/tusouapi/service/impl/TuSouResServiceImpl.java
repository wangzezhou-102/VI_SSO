package com.secusoft.web.tusouapi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.secusoft.web.tusouapi.TuSouClient;
import com.secusoft.web.tusouapi.model.*;
import com.secusoft.web.tusouapi.service.TuSouResService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TuSouResServiceImpl implements TuSouResService {

    private static Logger log = LoggerFactory.getLogger(TuSouResServiceImpl.class);

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

        return TuSouClient.getClientConnectionPool().fetchByPostMethod(TuSouClient.Path_RES_LIST,request);
    }

    @Override
    public BaseResponse<JSONArray> resDelete(BaseRequest<ResDeleteRequest> request) {
        return null;
    }
}
