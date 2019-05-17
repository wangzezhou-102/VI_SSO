package com.secusoft.web.tusouapi.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.secusoft.web.tusouapi.TuSouClient;
import com.secusoft.web.tusouapi.model.*;
import com.secusoft.web.tusouapi.service.TuSouAlgorithmService;
import org.springframework.stereotype.Service;

@Service
public class TuSouAlgorithmServiceImpl implements TuSouAlgorithmService {

    @Override
    public BaseResponse<JSONArray> algorithmAttribute(BaseRequest<AlgorithmAttributeRequest> request) {

        return TuSouClient.getClientConnectionPool().fetchByPostMethod(TuSouClient.Path_ALGORITHM_ATTRIBUTE,request);
    }

    @Override
    public BaseResponse<JSONArray> algorithmFeature(BaseRequest<AlgorithmFeatureRequest> request) {
        return null;
    }

    @Override
    public BaseResponse<JSONArray> algorithmDetect(BaseRequest<AlgorithmDetectRequest> request) {
        return null;
    }
}
