package com.secusoft.web.service;

import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.ViRealtimeTraceRequest;
import com.secusoft.web.model.ViRealtimeTraceVo;

public interface ViRealtimeTraceService {

    /**
     * 新增实时追踪任务
     * @param request
     * @return
     */
    ResultVo insertViRealtimeTrace(ViRealtimeTraceRequest request);

    /**
     * 新增实时追踪任务
     * @param request
     * @return
     */
    ResultVo updateViRealtimeTrace(ViRealtimeTraceRequest request);

    /**
     * 删除实时追踪任务
     * @param request
     * @return
     */
    ResultVo delViRealtimeTrace(ViRealtimeTraceRequest request);

    ResultVo getAllViRealtimeTrace(ViRealtimeTraceVo viRealtimeTraceVo);

    ResultVo viRealtimeTraceSearch(ViRealtimeTraceRequest request);
}
