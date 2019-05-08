package com.secusoft.web.persistence.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.secusoft.web.persistence.model.Dict;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 字典表 Mapper 接口
 * </p>
 *
 *
 * @since 2017-07-11
 */
public interface DictMapper extends BaseMapper<Dict> {
    /**
     * 根据编码获取词典列表
     * @return
     * @date 2017年2月13日 下午11:11:28
     */
    List<Dict> selectByPid(@Param("pid") int pid);

    /**
     * 查询字典列表
     *
     *
     * @Date 2017/4/26 13:04
     */
    List<Map<String,Object>> list(@Param("condition") String conditiion);

    List<Map<String,Object>> selectByName(@Param("name") String name);
}