package com.secusoft.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.secusoft.web.mapper.ViBasicMemberMapper;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.ViBasicMemberBean;
import com.secusoft.web.model.ViBasicMemberVo;
import com.secusoft.web.service.ViBasicMemberService;
import com.secusoft.web.utils.PageReturnUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author chjiang
 * @since 2019/6/6 15:40
 */
@Service
public class ViBasicMemberServiceImpl implements ViBasicMemberService {

    @Resource
    ViBasicMemberMapper viBasicMemberMapper;

    @Override
    public ResultVo insertViBasicMember(ViBasicMemberBean viBasicMemberBean) {
        viBasicMemberMapper.insertViBasicMember(viBasicMemberBean);
        return ResultVo.success();
    }

    @Override
    public ResultVo updateViBasicMember(ViBasicMemberBean viBasicMemberBean) {
        viBasicMemberMapper.updateViBasicMember(viBasicMemberBean);
        return ResultVo.success();
    }

    @Override
    public ResultVo delViBasicMember(Integer id) {
        viBasicMemberMapper.delViBasicMember(id);
        return ResultVo.success();
    }

    @Override
    public ResultVo getAllViBasicMember(ViBasicMemberBean viBasicMemberBean) {
        List<ViBasicMemberBean> list=viBasicMemberMapper.getAllViBasicMember(viBasicMemberBean.getObjectId());
        return ResultVo.success(list);
    }

    @Override
    public ResultVo getPagedViBasicMember(ViBasicMemberVo viBasicMemberVo) {
        PageHelper.startPage(viBasicMemberVo.getCurrent(),viBasicMemberVo.getSize());

        List<ViBasicMemberBean> list=viBasicMemberMapper.getAllViBasicMember(viBasicMemberVo.getObjectId());

        return ResultVo.success(PageReturnUtils.getPageMap(list,viBasicMemberVo.getCurrent(),viBasicMemberVo.getSize()));
    }

    /**
     * 是否关注该布控
     * @param viBasicMemberBean
     * @return
     */
    @Override
    public ResultVo updateFocusMenber(ViBasicMemberBean viBasicMemberBean) {
        viBasicMemberMapper.updateFocusMenber(viBasicMemberBean);
        return ResultVo.success();
    }

}
