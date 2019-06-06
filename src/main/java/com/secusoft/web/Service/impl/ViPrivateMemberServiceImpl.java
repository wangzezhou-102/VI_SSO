package com.secusoft.web.Service.impl;

import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.secusoft.web.Service.ViPrivateMemberService;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.mapper.ViPrivateMemberMapper;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.ViPrivateMemberBean;
import com.secusoft.web.model.ViPrivateMemberVo;
import com.secusoft.web.utils.PageReturnUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author chjiang
 * @since 2019/6/6 14:52
 */
@Service
public class ViPrivateMemberServiceImpl implements ViPrivateMemberService {

    @Resource
    ViPrivateMemberMapper viPrivateMemberMapper;

    @Override
    public ResultVo insertViPrivateMember(ViPrivateMemberBean viPrivateMemberBean) {
        if(!StringUtils.hasLength(viPrivateMemberBean.getIdentityName())){
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_IDENTITYNAME_NULL.getCode(), BizExceptionEnum.PRIVATEREPO_IDENTITYNAME_NULL.getMessage());
        }
        if(!StringUtils.hasLength(viPrivateMemberBean.getIdentityId())){
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_IDENTITYID_NULL.getCode(), BizExceptionEnum.PRIVATEREPO_IDENTITYID_NULL.getMessage());
        }
        if(!StringUtils.hasLength(viPrivateMemberBean.getIdentityId())){
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_IMAGEURL_NULL.getCode(), BizExceptionEnum.PRIVATEREPO_IMAGEURL_NULL.getMessage());
        }
        if(!StringUtils.hasLength(viPrivateMemberBean.getIdentityId())){
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_REPOID_NULL.getCode(), BizExceptionEnum.PRIVATEREPO_REPOID_NULL.getMessage());
        }
        //由于object_id唯一，先放置一个随机数区别
        int randomNumber = (int) Math.round(Math.random()*(25000-1)+1);
        viPrivateMemberBean.setObjectId("vi_private_"+randomNumber);

        viPrivateMemberMapper.insertViPrivateMember(viPrivateMemberBean);
        viPrivateMemberBean.setObjectId("vi_private_"+viPrivateMemberBean.getId());

        viPrivateMemberMapper.updateViPrivateMember(viPrivateMemberBean);
        return ResultVo.success();
    }

    @Override
    public ResultVo updateViPrivateMember(ViPrivateMemberBean viPrivateMemberBean) {
        if(!StringUtils.hasLength(viPrivateMemberBean.getIdentityName())){
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_IDENTITYNAME_NULL.getCode(), BizExceptionEnum.PRIVATEREPO_IDENTITYNAME_NULL.getMessage());
        }
        if(!StringUtils.hasLength(viPrivateMemberBean.getIdentityId())){
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_IDENTITYID_NULL.getCode(), BizExceptionEnum.PRIVATEREPO_IDENTITYID_NULL.getMessage());
        }
        if(!StringUtils.hasLength(viPrivateMemberBean.getIdentityId())){
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_IMAGEURL_NULL.getCode(), BizExceptionEnum.PRIVATEREPO_IMAGEURL_NULL.getMessage());
        }
        if(!StringUtils.hasLength(viPrivateMemberBean.getIdentityId())){
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_REPOID_NULL.getCode(), BizExceptionEnum.PRIVATEREPO_REPOID_NULL.getMessage());
        }
        viPrivateMemberBean.setModifyTime(new Date());
        viPrivateMemberMapper.updateViPrivateMember(viPrivateMemberBean);
        return ResultVo.success();
    }

    @Override
    public ResultVo delViPrivateMember(Integer id) {
        if(id==0){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        viPrivateMemberMapper.delViPrivateMember(id);
        return ResultVo.success();
    }

    @Override
    public ResultVo getAllViPrivateMember(ViPrivateMemberVo viPrivateMemberVo) {
        PageHelper.startPage(viPrivateMemberVo.getCurrent(),viPrivateMemberVo.getSize());

        List<ViPrivateMemberBean> list=viPrivateMemberMapper.getAllViPrivateMember();

        return ResultVo.success(PageReturnUtils.getPageMap(list,viPrivateMemberVo.getCurrent(),viPrivateMemberVo.getSize()));
    }
}
