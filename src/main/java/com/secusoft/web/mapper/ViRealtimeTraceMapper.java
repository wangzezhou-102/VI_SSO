package com.secusoft.web.mapper;

import com.secusoft.web.model.ViRealtimeTraceBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ViRealtimeTraceMapper {

    void insertViRealtimeTrace(ViRealtimeTraceBean bean);

    void updateViRealtimeTrace(ViRealtimeTraceBean bean);

    /**
     * 删除一条实时追踪任务
     */
    void delViRealtimeTrace(@Param("id") Integer id);

    List<ViRealtimeTraceBean> getAllViRealtimeTrace(ViRealtimeTraceBean viRealtimeTraceBean);

    ViRealtimeTraceBean getViRealtimeTraceById(ViRealtimeTraceBean viRealtimeTraceBean);
}
