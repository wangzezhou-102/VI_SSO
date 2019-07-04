package com.secusoft.web.service;

import com.secusoft.web.model.ZdryBean;
import com.secusoft.web.model.ZdryBeanVo;

import java.util.List;
import java.util.Map;

public interface ZdryService {

    /**
     * 过滤后的全国在逃布控库人员
     * @return
     */
    Map<String,Object> orderbyPartitionQgzt(ZdryBeanVo zdryBeanVo);

    /**
     * 过滤后的省高院布控库人员
     * @return
     */
    Map<String,Object> orderbyPartitionSgy(ZdryBeanVo zdryBeanVo);

    /**
     * 过滤后的涉毒脱失布控库人员
     * @return
     */
    Map<String,Object> orderbyPartitionSdzt(ZdryBeanVo zdryBeanVo);
}
