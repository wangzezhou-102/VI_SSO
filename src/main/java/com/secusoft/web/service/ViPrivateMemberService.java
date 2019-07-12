package com.secusoft.web.service;

import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.ViPrivateMemberBean;
import com.secusoft.web.model.ViPrivateMemberVo;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;

/**
 * @author chjiang
 * @since 2019/6/6 14:52
 */
public interface ViPrivateMemberService {

    ResultVo insertViPrivateMember(ViPrivateMemberBean viPrivateMemberBean, HttpServletRequest httpServletRequest);

    ResultVo updateViPrivateMember(ViPrivateMemberBean viPrivateMemberBean, HttpServletRequest request);

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
