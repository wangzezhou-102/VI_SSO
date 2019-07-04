package com.secusoft.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.secusoft.web.mapper.ZdryMapper;
import com.secusoft.web.model.ZdryBean;
import com.secusoft.web.model.ZdryBeanVo;
import com.secusoft.web.service.ZdryService;
import com.secusoft.web.utils.PageReturnUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ZdryServiceImpl implements ZdryService {

    @Resource
    ZdryMapper zdryMapper;

    @Override
    public Map<String,Object> orderbyPartitionQgzt(ZdryBeanVo zdryBeanVo) {
        PageHelper.startPage(zdryBeanVo.getCurrent(), zdryBeanVo.getSize());
        List<ZdryBean> zdryBeanList = zdryMapper.orderbyPartitionQgzt();
        return PageReturnUtils.getPageMap(zdryBeanList, zdryBeanVo.getCurrent(), zdryBeanVo.getSize());
    }

    @Override
    public Map<String,Object> orderbyPartitionSgy(ZdryBeanVo zdryBeanVo) {
        PageHelper.startPage(zdryBeanVo.getCurrent(), zdryBeanVo.getSize());
        List<ZdryBean> zdryBeanList = zdryMapper.orderbyPartitionSgy();
        return PageReturnUtils.getPageMap(zdryBeanList, zdryBeanVo.getCurrent(), zdryBeanVo.getSize());
    }

    @Override
    public Map<String,Object> orderbyPartitionSdzt(ZdryBeanVo zdryBeanVo) {
        PageHelper.startPage(zdryBeanVo.getCurrent(), zdryBeanVo.getSize());
        List<ZdryBean> zdryBeanList = zdryMapper.orderbyPartitionSdzt();
        return PageReturnUtils.getPageMap(zdryBeanList, zdryBeanVo.getCurrent(), zdryBeanVo.getSize());
    }
}
