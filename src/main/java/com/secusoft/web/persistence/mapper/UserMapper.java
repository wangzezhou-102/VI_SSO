package com.secusoft.web.persistence.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.secusoft.web.core.datascope.DataScope;
import com.secusoft.web.persistence.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 管理员表 Mapper 接口
 * </p>
 *
 *
 * @since 2017-07-11
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 修改用户状态
     *
     * @date 2017年2月12日 下午8:42:31
     */
    int setStatus(@Param("userId") Integer userId, @Param("status") int status);

    /**
     * 修改密码
     *
     * @param userId
     * @param pwd
     * @date 2017年2月12日 下午8:54:19
     */
    int changePwd(@Param("userId") Integer userId, @Param("pwd") String pwd);

    /**
     * 根据条件查询用户列表
     *
     * @return
     * @date 2017年2月12日 下午9:14:34
     */
    List<Map<String, Object>> selectUsers(@Param("dataScope") DataScope dataScope, @Param("name") String name, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("deptid") Integer deptid);

    /**
     * 设置用户的角色
     *
     * @return
     * @date 2017年2月13日 下午7:31:30
     */
    int setRoles(@Param("userId") Integer userId, @Param("roleIds") String roleIds);

    /**
     * 通过账号获取用户
     *
     * @param account
     * @return
     * @date 2017年2月17日 下午11:07:46
     */
    User getByAccount(@Param("account") String account);

    //查询登录的账号角色为前端还是后端
    List<Map<String, Object>> getRolecode(@Param("username") String username);
}