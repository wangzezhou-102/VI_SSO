package com.secusoft.web.mapper;

import com.secusoft.web.model.CodeplacesBean;
import com.secusoft.web.model.SecurityPlaceVo;
import com.secusoft.web.model.SecurityPlaceBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huanghao
 * @date 2019-07-02
 */
public interface SecurityPlaceMapper {
    List<SecurityPlaceBean> selectSecirityTaskPlaceByTypeId(@Param("id") Integer id);
    List<SecurityPlaceVo> selectSecirityTaskPlace();
}
