package com.secusoft.web.service;

import java.util.List;

import com.secusoft.web.model.OfflineFolderBean;
import com.secusoft.web.model.ResultVo;

/**
 * 离线文件夹service
 * @author ChenDong
 * @company 视在数科
 * @date 2019年6月6日
 */
public interface OfflineFolderService {

    /**
     * 添加离线文件夹
     * @author ChenDong
     * @date 2019年6月6日
     * @param offlineFolderBean
     * @return
     */
    ResultVo addOfflineFolder(OfflineFolderBean offlineFolderBean);
    
    /**
     * 删除离线文件夹
     * @author ChenDong
     * @date 2019年6月6日
     * @param ids
     * @return
     */
    ResultVo deleteOfflineFolder(List<Long> ids);
    
    /**
     * 修改离线文件夹
     * @author ChenDong
     * @date 2019年6月6日
     * @param offlineFolderBean
     * @return
     */
    ResultVo updateOfflineFolder(OfflineFolderBean offlineFolderBean);
    
    /**
     * 查询离线文件夹
     * @author ChenDong
     * @date 2019年6月6日
     * @param queryBean
     * @return
     */
    ResultVo readOfflineFolderByParam(OfflineFolderBean queryBean);
    
    /**
     * 查询离线文件夹(含关联下级列表)
     * @author ChenDong
     * @date 2019年6月6日
     * @param queryBean
     * @return
     */
    ResultVo readOfflineFolder(OfflineFolderBean queryBean);
}
