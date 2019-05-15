package com.secusoft.web.tusouapi.service;

import com.alibaba.fastjson.JSONArray;
import com.secusoft.web.tusouapi.model.BaseRequest;
import com.secusoft.web.tusouapi.model.BaseResponse;
import com.secusoft.web.tusouapi.model.ResDeleteRequest;
import com.secusoft.web.tusouapi.model.ResStartRequest;
import com.secusoft.web.tusouapi.model.ResStopRequest;

/**
 * 算法服务管理接⼝
 * 控制不同版本算法服务的启动和停⽌，查看算法服务运⾏状态
 */
public interface TuSouResService{

    /**
     * 启动算法服务
     * @return
     */
    BaseResponse<JSONArray> resStart(BaseRequest<ResStartRequest> request);

    /**
     * 停⽌算法服务
     * @return
     */
    BaseResponse<JSONArray> resStop(BaseRequest<ResStopRequest> request);

    /**
     * 监控算法服务
     * @return
     */
    BaseResponse<JSONArray> resList(BaseRequest<Object> request);
    /**
     * 删除算法服务
     * 在调⽤delete接⼝前，必须先调⽤stop接⼝，先停⽌算法服务
     * @return
     */
    BaseResponse<JSONArray> resDelete(BaseRequest<ResDeleteRequest> request);


}