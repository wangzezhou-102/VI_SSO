package com.secusoft.web.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.secusoft.web.model.ViPrivateMemberBean;
import com.secusoft.web.model.ViPrivateMemberVo;
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
    void delViPrivateMember(@Param("id") Integer id);

    /**
     * 获取所有自定义库信息
     * @return
     */
    List<ViPrivateMemberBean> getAllViPrivateMember(ViPrivateMemberVo viPrivateMemberVo);

    /**
     * 根据ObjectId获取ViPrivateMemberBean
     * @param objectId
     * @return
     */
    ViPrivateMemberBean getViPrivateMemberByObjectId(@Param("objectId") String objectId);
}
