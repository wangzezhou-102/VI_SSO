package com.secusoft.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.secusoft.web.model.OfflineFolderBean;

/**
 * 离线文件夹Mapper
 * @author ChenDong
 * @company 视在数科
 * @date 2019年6月6日
 */
@Mapper
public interface OfflineFolderMapper extends BaseMapper<OfflineFolderBean>{

    /**
     * 查询离线文件夹数量
     * @author ChenDong
     * @date 2019年6月6日
     * @param queryBean
     * @return
     */
    long readOfflineFolderCountByParam(OfflineFolderBean queryBean);
    
    /**
     * 查询离线文件夹list
     * @author ChenDong
     * @date 2019年6月6日
     * @param queryBean
     * @return
     */
    List<OfflineFolderBean> readOfflineFolderListByParam(OfflineFolderBean queryBean);
    
    /**
     * 查询离线文件夹(含关联下级列表)
     * @author ChenDong
     * @date 2019年6月6日
     * @param queryBean
     * @return
     */
    List<OfflineFolderBean> readOfflineFolder(OfflineFolderBean queryBean);
}
