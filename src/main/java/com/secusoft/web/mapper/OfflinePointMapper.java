package com.secusoft.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.secusoft.web.model.OfflinePointBean;
import com.secusoft.web.model.OfflineVideoBean;

/**
 * 离线点位Mapper
 * @author ChenDong
 * @company 视在数科
 * @date 2019年6月6日
 */
@Mapper
public interface OfflinePointMapper extends BaseMapper<OfflinePointBean>{

    /**
     * 查询离线点位数量
     * @author ChenDong
     * @date 2019年6月6日
     * @param queryBean
     * @return
     */
    long readOfflinePointCountByParam(OfflinePointBean queryBean);
    
    /**
     * 查询离线点位list
     * @author ChenDong
     * @date 2019年6月6日
     * @param queryBean
     * @return
     */
    List<OfflinePointBean> readOfflinePointListByParam(OfflinePointBean queryBean);
    
     /**
      * 关联查询离线点位(含关联下级列表)
      * @author ChenDong
      * @date 2019年6月6日
      * @param pointId
      * @return
      */
     List<OfflineVideoBean> readOfflinePoint(@Param("folderId") Long folderId);
}
