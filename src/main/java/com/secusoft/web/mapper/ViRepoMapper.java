package com.secusoft.web.mapper;

import com.secusoft.web.model.ViRepoBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chjiang
 * @since 2019/6/6 14:23
 */
public interface ViRepoMapper {

    /**
     * 添加一条布控库
     */
    void insertViRepo(ViRepoBean viRepoBean);


    /**
     * 更新一条布控库信息
     */
    void updateViRepo(ViRepoBean viRepoBean);


    /**
     * 删除一条布控库
     */
    void delViRepo(@Param("id") Integer id);

    /**
     * 获取所有布控库
     * @return
     */
    List<ViRepoBean> getAllViRepo(ViRepoBean viRepoBean);
}
