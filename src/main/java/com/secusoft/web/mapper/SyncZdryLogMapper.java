package com.secusoft.web.mapper;

import com.secusoft.web.model.SyncZdryLogBean;

/**
 * @author chjiang
 * @since 2019/6/28 15:44
 */
public interface SyncZdryLogMapper {

    SyncZdryLogBean selectByBean(SyncZdryLogBean syncZdryLogBean);
}
