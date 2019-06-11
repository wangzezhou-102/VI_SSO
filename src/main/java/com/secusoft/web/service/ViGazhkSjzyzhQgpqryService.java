package com.secusoft.web.service;

import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.gazhk.ViGazhkSjzyzhQgpqryBean;

/**
 * @author chjiang
 * @since 2019/6/10 19:29
 */
public interface ViGazhkSjzyzhQgpqryService {

    /**
     * 添加一条全国扒窃人员信息
     * @param viGazhkSjzyzhQgpqryBean
     * @return
     */
    ResultVo addViGazhkSjzyzhQgpqry(ViGazhkSjzyzhQgpqryBean viGazhkSjzyzhQgpqryBean,String tableName);

     /**
     * 清空表
     */
     ResultVo truncateTable();
}
