package com.secusoft.web.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.secusoft.web.model.SecurityTaskTypeBean;
import com.secusoft.web.model.SecurityTaskTypeRepoBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huanghao
 * @date 2019-07-02
 */
public interface SecurityTaskTypeMapper{

    List<SecurityTaskTypeBean> selectsecurityTaskType();
    List<SecurityTaskTypeBean> selectsecurityTaskTypeA();
    List<SecurityTaskTypeBean> selectsecurityTaskTypeB();
    List<SecurityTaskTypeBean> selectsecurityFKType();

    List<SecurityTaskTypeRepoBean> selectsecurityTaskTypeRepo();
    void insertsecurityTaskTypeRepo(@Param("ids") List repoIds,@Param("id") Integer id);
    void deletesecurityTaskTypeRepoById(Integer id);
}
