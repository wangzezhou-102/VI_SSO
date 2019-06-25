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
     * 临控人员
     * @param list
     */
    void insertBatchLk(List<ZdryBean> list);
    /**
     * 涉毒脱失人员
     * @param list
     */
    void insertBatchSdts(List<ZdryBean> list);
}
