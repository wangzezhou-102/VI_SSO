package com.secusoft.web.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.secusoft.web.model.SysOperationLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 操作日志mapper
 * @author hbxing
 * @company 视在数科
 * @date 2019年6月18日
 */

public interface SysOperationLogMapper extends BaseMapper<SysOperationLog> {
    /**
     *   查询最新的一条搜索记录
     */
    SysOperationLog selectNewLog();
    /**
     *某些用户使用目标搜图的次数
     */
    Integer selectTuSouNumber(@Param("userIds") List<Integer> userIds);
    /**
     * 查询最近的三条搜索记录
     * @return
     */
    List<SysOperationLog> selectThreeLog(SysOperationLog sysOperationLog);
    /**
     * 目标搜图使用次数
     */
    Integer tusouSearchTotal();

    Integer selectUseNumberByUserIds(@Param("userIds")List<Integer> userIds);

    List<SysOperationLog> getList();
}
