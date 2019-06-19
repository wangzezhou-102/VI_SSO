package com.secusoft.web.service;

import com.secusoft.web.model.ResultVo;

/**
 * 蓝色大屏
 * @author hbxing
 * @date 2019年6月18日
 */
public interface BlueScreenService {
    ResultVo readVideoApplication();
    void updateScreenDateIndicator();

}
