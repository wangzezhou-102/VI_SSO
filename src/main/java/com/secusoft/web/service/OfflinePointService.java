package com.secusoft.web.service;

import java.util.List;

import com.secusoft.web.model.OfflinePointBean;
import com.secusoft.web.model.ResultVo;

/**
 * 离线点位service
 * @author ChenDong
 * @company 视在数科
 * @date 2019年6月6日
 */
public interface OfflinePointService {

    /**
     * 添加离线点位
     * @author ChenDong
     * @date 2019年6月6日
     * @param OfflinePointBean
     * @return
     */
    ResultVo addOfflinePoint(OfflinePointBean OfflinePointBean);
    
    /**
     * 删除离线点位
     * @author ChenDong
     * @date 2019年6月6日
     * @param ids
     * @return
     */
    ResultVo deleteOfflinePoint(List<Long> ids);
    
    /**
     * 修改离线点位
     * @author ChenDong
     * @date 2019年6月6日
     * @param OfflinePointBean
     * @return
     */
    ResultVo updateOfflinePoint(OfflinePointBean OfflinePointBean);
    
    /**
     * 查询离线点位
     * @author ChenDong
     * @date 2019年6月6日
     * @param queryBean
     * @return
     */
    ResultVo readOfflinePointByParam(OfflinePointBean queryBean);
}
