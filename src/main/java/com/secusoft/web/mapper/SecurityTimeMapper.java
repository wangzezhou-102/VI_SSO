package com.secusoft.web.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.secusoft.web.model.SecurityTaskBean;
import com.secusoft.web.model.SecurityTimeBean;

import java.util.List;

/**
 * @author huanghao
 * @date 2019-07-08
 */
public interface SecurityTimeMapper extends BaseMapper<SecurityTimeBean> {
    List<SecurityTimeBean> selectTimeByTaskId(String taskId);
    void updateTimeById(SecurityTimeBean securityTimeBean);
    void deleteTimeByTaskId(SecurityTaskBean securityTaskBean);
}
