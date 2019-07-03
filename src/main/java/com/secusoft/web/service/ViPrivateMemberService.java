package com.secusoft.web.service;

import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.ViPrivateMemberBean;
import com.secusoft.web.model.ViPrivateMemberVo;

/**
 * @author chjiang
 * @since 2019/6/6 14:52
 */
public interface ViPrivateMemberService {

    ResultVo insertViPrivateMember(ViPrivateMemberBean viPrivateMemberBean);

    ResultVo updateViPrivateMember(ViPrivateMemberBean viPrivateMemberBean);

    ResultVo delViPrivateMember(ViPrivateMemberVo viPrivateMemberVo);

    ResultVo getAllViPrivateMember(ViPrivateMemberVo viPrivateMemberVo);

    /**
     * 上传图片
     * @param file
     * @param request
     * @param response
     * @return
     */
    ResultVo uploadImg(ViPrivateMemberVo viPrivateMemberVo);

    ResultVo getInfoSearchMember(ViPrivateMemberVo viPrivateMemberVo);
}
