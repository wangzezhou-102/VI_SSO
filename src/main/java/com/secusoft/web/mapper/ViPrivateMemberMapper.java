package com.secusoft.web.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.secusoft.web.model.ViPrivateMemberBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chjiang
 * @since 2019/6/6 15:01
 */
public interface ViPrivateMemberMapper extends BaseMapper<ViPrivateMemberBean> {

    /**
     * 添加一条自定义库
     */
    void insertViPrivateMember(ViPrivateMemberBean viPrivateMemberBean);


    /**
     * 更新一条自定义库信息
     */
    void updateViPrivateMember(ViPrivateMemberBean viPrivateMemberBean);


    /**
     * 删除一条自定义库信息
     */
    void delViPrivateMember(@Param("objectId") String objectId);

    /**
     * 获取所有自定义库信息
     * @return
     */
    List<ViPrivateMemberBean> getAllViPrivateMember();
}
