package com.secusoft.web.mapper;

import com.secusoft.web.model.ZdryBean;

import java.util.List;

/**
 * @author chjiang
 * @since 2019/6/25 14:48
 */
public interface ZdryMapper {
    /**
     * 全国在逃人员
     * @param list
     */
    void insertBatchQgzt(List<ZdryBean> list);
    /**
     * 省高院布控人员
     * @param list
     */
    void insertBatchSgy(List<ZdryBean> list);
    /**
     * 涉毒脱失人员
     * @param list
     */
    void insertBatchSdts(List<ZdryBean> list);


    /**
     * 全国在逃人员
     * @param zdryBean
     */
    void insertQgzt(ZdryBean zdryBean);
    /**
     * 省高院布控人员
     * @param zdryBean
     */
    void insertSgy(ZdryBean zdryBean);
    /**
     * 涉毒脱失人员
     * @param zdryBean
     */
    void insertSdts(ZdryBean zdryBean);

    /**
     * 过滤后的全国在逃布控库人员
     * @return
     */
    List<ZdryBean> orderbyPartitionQgzt();

    /**
     * 过滤后的省高院布控库人员
     * @return
     */
    List<ZdryBean> orderbyPartitionSgy();

    /**
     * 过滤后的涉毒脱失布控库人员
     * @return
     */
    List<ZdryBean> orderbyPartitionSdzt();
}
