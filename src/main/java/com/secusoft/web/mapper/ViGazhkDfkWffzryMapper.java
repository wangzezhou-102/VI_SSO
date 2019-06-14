package com.secusoft.web.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.secusoft.web.model.gazhk.ViGazhkDfkWffzryBean;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author chjiang
 * @since 2019/6/10 10:13
 */
@Mapper
public interface ViGazhkDfkWffzryMapper extends BaseMapper<ViGazhkDfkWffzryBean> {

    /**
     * 添加一条新数据
     * @param viGazhkDfkWffzryBean
     */
    void addViGazhkDfkWffzry(ViGazhkDfkWffzryBean viGazhkDfkWffzryBean);

    /**
     * 清空表
     */
    void truncateTable();
}
