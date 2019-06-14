package com.secusoft.web.mapper;

import com.secusoft.web.model.ViTaskRepoBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chjiang
 * @since 2019/6/14 13:42
 */
public interface ViTaskRepoMapper {

    /**
     * 添加一条布控任务关联的库
     */
    void insertViTaskRepo(ViTaskRepoBean viTaskRepoBean);


    /**
     * 更新一条布控任务关联的库
     */
    void updateViTaskRepo(ViTaskRepoBean viTaskRepoBean);


    /**
     * 删除一条布控任务关联的库
     */
    void deleteViTaskRepo(@Param("id") Integer id);

    /**
     * 批量插入
     * @param list
     */
    void insertBatch(List<ViTaskRepoBean> list);
}
