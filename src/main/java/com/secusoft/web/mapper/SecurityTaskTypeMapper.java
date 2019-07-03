package com.secusoft.web.mapper;

import com.secusoft.web.model.SecurityTaskTypeBean;

import java.util.List;

/**
 * @author huanghao
 * @date 2019-07-02
 */
public interface SecurityTaskTypeMapper {
    List<SecurityTaskTypeBean> selectsecurityTaskType();
    List<SecurityTaskTypeBean> selectsecurityTaskTypeA();
    List<SecurityTaskTypeBean> selectsecurityTaskTypeB();
}
