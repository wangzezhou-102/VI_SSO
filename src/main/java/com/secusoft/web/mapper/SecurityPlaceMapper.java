package com.secusoft.web.mapper;

import com.secusoft.web.model.SecurityPlaceBean;
import com.secusoft.web.model.SecurityTaskPlaceBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huanghao
 * @date 2019-07-02
 */
public interface SecurityPlaceMapper {
    List<SecurityTaskPlaceBean> selectSecirityTaskPlaceByTypeId(@Param("id") Integer id);
    List<SecurityPlaceBean> selectSecirityTaskPlace();
}
