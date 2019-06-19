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

    Integer selectTuSouNumber(@Param("userIds") List<Integer> userIds);
    /**
     * 查询最近的三条搜索记录
     * @return
     */
    List<SysOperationLog> selectThreeLog();
    /**
     * 目标搜图使用次数
     */
    Integer tusouSearchTotal();
}
