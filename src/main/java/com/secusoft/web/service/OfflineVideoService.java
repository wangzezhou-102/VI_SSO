package com.secusoft.web.service;

import java.util.List;

import com.secusoft.web.model.OfflineVideoBean;
import com.secusoft.web.model.ResultVo;

/**
 * 离线视频service
 * @author ChenDong
 * @company 视在数科
 * @date 2019年6月6日
 */
public interface OfflineVideoService {

    /**
     * 添加离线视频
     * @author ChenDong
     * @date 2019年6月6日
     * @param OfflineVideoBean
     * @return
     */
    ResultVo addOfflineVideo(OfflineVideoBean OfflineVideoBean);
    
    /**
     * 删除离线视频
     * @author ChenDong
     * @date 2019年6月6日
     * @param ids
     * @return
     */
    ResultVo deleteOfflineVideo(List<Long> ids);
    
    /**
     * 修改离线视频
     * @author ChenDong
     * @date 2019年6月6日
     * @param OfflineVideoBean
     * @return
     */
    ResultVo updateOfflineVideo(OfflineVideoBean OfflineVideoBean);
    
    /**
     * 查询离线视频
     * @author ChenDong
     * @date 2019年6月6日
     * @param queryBean
     * @return
     */
    ResultVo readOfflineVideoByParam(OfflineVideoBean queryBean);
}
