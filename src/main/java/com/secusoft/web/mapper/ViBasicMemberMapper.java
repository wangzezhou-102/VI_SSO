package com.secusoft.web.mapper;

import com.secusoft.web.model.ViBasicMemberBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chjiang
 * @since 2019/6/6 16:34
 */
public interface ViBasicMemberMapper {

    /**
     * 添加一条自定义库
     */
    void insertViBasicMember(ViBasicMemberBean viBasicMemberBean);


    /**
     * 更新一条自定义库信息
     */
    void updateViBasicMember(ViBasicMemberBean viBasicMemberBean);


    /**
     * 删除一条自定义库信息
     */
    void delViBasicMember(@Param("id") Integer id);

    /**
     * 获取所有自定义库信息
     * @return
     */
    List<ViBasicMemberBean> getAllViBasicMember(@Param("objectId") String objectId);

    /**
     * 是否关注该布控
     * @param viBasicMemberBean
     */
    void updateFocusMenber(ViBasicMemberBean viBasicMemberBean);
}
