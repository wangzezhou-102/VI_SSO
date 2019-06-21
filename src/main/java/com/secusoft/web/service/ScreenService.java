package com.secusoft.web.service;

import com.secusoft.web.model.ResultVo;

/**
 * 蓝色大屏
 * @author hbxing
 * @date 2019年6月18日
 */
public interface ScreenService {
    /**
     * 蓝色大屏  获得视频应用数 更新至德清数据库
     */
    void readVideoApplication();

    /**
     * 浅色大屏  更新数据到德清数据库
     */
    void updateScreenDateIndicator();

    /**
     *
     */
    void updateBlueScreenFloatingFrame();
    /**
     * 蓝色大屏浮框 功能模块使用统计
     *
     */
    ResultVo functionUseNumber();
    /**
     * 蓝色大屏浮框 视频路数统计
     */
    ResultVo videoPathNumber();
    /**
     *蓝色大屏浮框  报警统计
     */
    ResultVo alaramNumber();
    /**
     * 蓝色大屏浮框 分局算力分配
     */
    ResultVo bureauDeviceDistribution();
    /**
     * 蓝色大屏浮框 视频巡逻统计
     */
    ResultVo videoPatrolNumber();
    /**
     * 蓝色大屏浮框 当日抓拍统计
     */
    ResultVo snapNumberToday();

}
