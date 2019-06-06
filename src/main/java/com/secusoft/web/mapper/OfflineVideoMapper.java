package com.secusoft.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.secusoft.web.model.OfflineVideoBean;

/**
 * 离线视频Mapper
 * @author ChenDong
 * @company 视在数科
 * @date 2019年6月6日
 */
@Mapper
public interface OfflineVideoMapper extends BaseMapper<OfflineVideoBean>{

    /**
     * 查询离线视频数量
     * @author ChenDong
     * @date 2019年6月6日
     * @param queryBean
     * @return
     */
    long readOfflineVideoCountByParam(OfflineVideoBean queryBean);
    
    /**
     * 查询离线视频list
     * @author ChenDong
     * @date 2019年6月6日
     * @param queryBean
     * @return
     */
    List<OfflineVideoBean> readOfflineVideoListByParam(OfflineVideoBean queryBean);
    
    /**
     * 关联查询离线视频
     * @author ChenDong
     * @date 2019年6月6日
     * @param pointId
     * @return
     */
    List<OfflineVideoBean> readOfflineVideo(@Param("pointId") Long pointId);
}
