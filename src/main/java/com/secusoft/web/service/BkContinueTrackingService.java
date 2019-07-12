package com.secusoft.web.service;

import com.secusoft.web.model.BkContinueTrackingRequest;
import com.secusoft.web.model.ResultVo;

public interface BkContinueTrackingService {

    /**
     * 根据条件获取继续追踪相关数据
     * @param bkContinueTrackingRequest
     * @return
     */
    ResultVo getBkContinueTracking(BkContinueTrackingRequest bkContinueTrackingRequest);
}
