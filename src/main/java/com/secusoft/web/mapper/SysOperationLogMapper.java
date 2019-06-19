package com.secusoft.web.mapper;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.secusoft.web.model.SysOperationLog;

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

    List<SysOperationLog> selectThree();
    Integer selectTuSouNumber(@Param("userIds") List<Integer> userIds);
}
