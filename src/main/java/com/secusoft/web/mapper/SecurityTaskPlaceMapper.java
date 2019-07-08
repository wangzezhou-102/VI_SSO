package com.secusoft.web.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.secusoft.web.model.CodeplacesBean;
import com.secusoft.web.model.SecurityTaskPlaceBean;

import java.util.List;

/**
 * @author huanghao
 * @date 2019-07-08
 */
public interface SecurityTaskPlaceMapper extends BaseMapper<SecurityTaskPlaceBean> {
    List<CodeplacesBean> selectPlaceByTaskId(String taskId);
}
