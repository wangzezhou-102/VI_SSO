package com.secusoft.web.Service;

import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.ViBasicMemberBean;
import com.secusoft.web.model.ViBasicMemberVo;

/**
 * @author chjiang
 * @since 2019/6/6 15:40
 */
public interface ViBasicMemberService {


    ResultVo insertViBasicMember(ViBasicMemberBean viBasicMemberBean);

    ResultVo updateViBasicMember(ViBasicMemberBean viBasicMemberBean);

    ResultVo delViBasicMember(Integer id);

    ResultVo getAllViBasicMember(ViBasicMemberBean viBasicMemberBean);

    ResultVo getPagedViBasicMember(ViBasicMemberVo viBasicMemberVo);
}
