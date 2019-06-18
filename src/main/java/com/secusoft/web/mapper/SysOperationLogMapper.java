package com.secusoft.web.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.secusoft.web.model.SysOperationLog;

import java.util.List;

/**
 * @author huanghao
 * @date 2019-06-17
 */
public interface SysOperationLogMapper extends BaseMapper<SysOperationLog> {
    /**
     *   查询最新的一条搜索记录
     */
    SysOperationLog selectNewLog();

    List<SysOperationLog> selectThree();
}
