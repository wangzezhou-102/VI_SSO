package com.secusoft.web.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.secusoft.web.model.ViRepoBean;
import com.secusoft.web.model.ViTaskRepoBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chjiang
 * @since 2019/6/6 14:23
 */
public interface ViRepoMapper extends BaseMapper<ViRepoBean> {

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
    /**
     * 根据ID获取布控库
     * @return
     */
    ViRepoBean selectViRepoById(ViRepoBean viRepoBean);

    /**
     * 根据表名称获取布控库
     * @return
     */
    ViRepoBean selectViRepoByTableName(ViRepoBean viRepoBean);

    List<ViRepoBean> getAllViRepoByViTaskRepoList(List<ViTaskRepoBean> list);
}
