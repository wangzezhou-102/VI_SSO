package com.secusoft.web.tusouapi.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.secusoft.web.tusouapi.TuSouClient;
import com.secusoft.web.tusouapi.model.BaseRequest;
import com.secusoft.web.tusouapi.model.BaseResponse;
import com.secusoft.web.tusouapi.model.SearchRequest;
import com.secusoft.web.tusouapi.service.TuSouSearchService;
import org.springframework.stereotype.Service;

@Service
public class TuSouSearchServiceImpl implements TuSouSearchService {

    @Override
    public BaseResponse<JSONArray> search(BaseRequest<SearchRequest> request) {
        return TuSouClient.getClientConnectionPool().fetchByPostMethod(TuSouClient.Path_SEARCH,request);
    }
}
