package com.secusoft.web.mapper;

import com.secusoft.web.model.SecurityTaskBean;
import com.secusoft.web.model.SecurityTaskRequest;

import java.util.List;

/**
 * @author huanghao
 * @date 2019-07-08
 */
public interface SecurityTaskMapper {
    /**
     * 增加一条安保任务
     */
    void insertSecurityTask(SecurityTaskBean securityTaskBean);

    /**
     * 删除一条安保任务(软删除)
     */
    void delSecurityTask(SecurityTaskRequest securityTaskRequest);

    /**
     * 更新一条布控任务布控状态
     */
    void updateSecurityTask(SecurityTaskBean securityTaskBean);


    /**
     * 获取所有布控任务
     */
    List<SecurityTaskRequest> selectAllSecurityTask();

    /**
     * 获取条件查询并未删除的任务
     */
    List<SecurityTaskBean> selectSecurityTaskBeanByObj(SecurityTaskBean securityTaskbean);
}
