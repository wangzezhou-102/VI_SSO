package com.secusoft.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.mapper.ViBasicMemberMapper;
import com.secusoft.web.mapper.ViRepoMapper;
import com.secusoft.web.model.*;
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

    @Resource
    ViRepoMapper viRepoMapper;

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
        List<ViMemberVo> list=viBasicMemberMapper.getAllViBasicMember(viBasicMemberBean);
        return ResultVo.success(list);
    }

    @Override
    public ResultVo getPagedViBasicMember(ViBasicMemberVo viBasicMemberVo) {

        if (null == viBasicMemberVo.getRepoId() || viBasicMemberVo.getRepoId() <= 0) {
            return ResultVo.failure(BizExceptionEnum.BKREPO_ID_NULL.getCode(), BizExceptionEnum.BKREPO_ID_NULL.getMessage());
        }
        ViRepoBean viRepoBean = new ViRepoBean();
        viRepoBean.setId(viBasicMemberVo.getRepoId());
        ViRepoBean viRepo = viRepoMapper.getViRepoById(viRepoBean);
        if (viRepo == null) {
            return ResultVo.failure(BizExceptionEnum.NOT_FOUND.getCode(), BizExceptionEnum.NOT_FOUND.getMessage());
        }

        PageHelper.startPage(viBasicMemberVo.getCurrent(),viBasicMemberVo.getSize());
        List<ViMemberVo> list=viBasicMemberMapper.getAllViBasicMemberByPaged(viBasicMemberVo);

        return ResultVo.success(PageReturnUtils.getPageMap(list,viBasicMemberVo.getCurrent(),viBasicMemberVo.getSize()));
    }

    /**
     * 是否关注该布控
     * @param viBasicMemberBean
     * @return
     */
    @Override
    public ResultVo updateFocusMenber(ViBasicMemberBean viBasicMemberBean) {
        if(null==viBasicMemberBean){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        if(viBasicMemberBean.getId()==null){
            return ResultVo.failure(BizExceptionEnum.BKMEMBER_ID_NULL.getCode(), BizExceptionEnum.BKMEMBER_ID_NULL.getMessage());
        }
        viBasicMemberMapper.updateFocusMenber(viBasicMemberBean);
        return ResultVo.success();
    }

}
