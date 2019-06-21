package com.secusoft.web.service.impl;

import com.secusoft.web.mapper.ViTaskDeviceMapper;
import com.secusoft.web.model.ViTaskDeviceBean;
import com.secusoft.web.service.ViTaskDeviceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author chjiang
 * @since 2019/6/14 16:44
 */
@Service
public class ViTaskDeviceServiceImpl implements ViTaskDeviceService {

    @Resource
    ViTaskDeviceMapper viTaskDeviceMapper;


    @Override
    public ViTaskDeviceBean getViTaskDeviceByObject(ViTaskDeviceBean viTaskDeviceBean) {
        viTaskDeviceMapper.getViTaskDeviceByObject(viTaskDeviceBean);
        return null;
    }
}
