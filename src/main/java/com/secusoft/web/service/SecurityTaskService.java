package com.secusoft.web.service;

import com.secusoft.web.model.ResultVo;

/**
 * @author huanghao
 * @date 2019-07-02
 */
public interface SecurityTaskService {
    /**
     * 分开展示 类型地点关系
     * @return
     */
    ResultVo getSecurityTypePlace();

    /**
     * 嵌套展示 类型地点关系
     * @return
     */
    ResultVo getSecurityTaskType();
    ResultVo getAllViRepo();
}
