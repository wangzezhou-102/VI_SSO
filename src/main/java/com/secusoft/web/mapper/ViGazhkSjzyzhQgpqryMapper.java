package com.secusoft.web.mapper;

import com.secusoft.web.model.gazhk.ViGazhkSjzyzhQgpqryBean;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author chjiang
 * @since 2019/6/10 17:54
 */
@Mapper
public interface ViGazhkSjzyzhQgpqryMapper {
    /**
     * 添加一条新数据
     * @param viGazhkSjzyzhQgpqryBean
     */
    void addViGazhkSjzyzhQgpqry(ViGazhkSjzyzhQgpqryBean viGazhkSjzyzhQgpqryBean);

    /**
     * 清空表
     */
    void truncateTable();
}
