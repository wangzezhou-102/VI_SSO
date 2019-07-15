package com.secusoft.web.service.impl;

import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.secusoft.web.core.emuns.ViRepoBkTypeEnum;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.mapper.ViPrivateMemberMapper;
import com.secusoft.web.mapper.ViRepoMapper;
import com.secusoft.web.model.*;
import com.secusoft.web.service.ViRepoService;
import com.secusoft.web.utils.PageReturnUtils;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author chjiang
 * @since 2019/6/6 14:13
 */
@Service
public class ViRepoServiceImpl implements ViRepoService {

    @Resource
    ViRepoMapper viRepoMapper;

    @Resource
    ViPrivateMemberMapper viPrivateMemberMapper;

    /**
     * 添加布控库
     *
     * @param viRepoBean
     * @return
     */
    @Override
    public ResultVo insertViRepo(ViRepoBean viRepoBean) {
        if (viRepoBean == null) {
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        if (!StringUtils.hasLength(viRepoBean.getBkname())) {
            return ResultVo.failure(BizExceptionEnum.REPO_NAME_NULL.getCode(), BizExceptionEnum.REPO_NAME_NULL.getMessage());
        }
        List<ViRepoBean> list = viRepoMapper.getAllViRepo(viRepoBean);
        if (list.size() > 0) {
            return ResultVo.failure(BizExceptionEnum.RRPO_NAME_REPEATED.getCode(), BizExceptionEnum.RRPO_NAME_REPEATED.getMessage());
        }
        viRepoBean.setType(1);
        viRepoBean.setBktype(1);
        viRepoMapper.insertViRepo(viRepoBean);
        return ResultVo.success(viRepoBean);
    }

    /**
     * 更新布控库
     *
     * @param viRepoBean
     * @return
     */
    @Override
    public ResultVo updateViRepo(ViRepoBean viRepoBean) {
        if (viRepoBean == null) {
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        if (viRepoBean.getId() == null || viRepoBean.getId() <= 0) {
            return ResultVo.failure(BizExceptionEnum.RRPO_ID_EXISTED.getCode(), BizExceptionEnum.RRPO_ID_EXISTED.getMessage());
        }
        if (!StringUtils.hasLength(viRepoBean.getBkname())) {
            return ResultVo.failure(BizExceptionEnum.REPO_NAME_NULL.getCode(), BizExceptionEnum.REPO_NAME_NULL.getMessage());
        }
        ViRepoBean beans = new ViRepoBean();
        beans.setBkname(viRepoBean.getBkname());
        beans.setValidState(1);
        List<ViRepoBean> list = viRepoMapper.getAllViRepo(beans);
        if (list.size() > 0 && list.get(0).getId() != viRepoBean.getId()) {
            return ResultVo.failure(BizExceptionEnum.RRPO_NAME_REPEATED.getCode(), BizExceptionEnum.RRPO_NAME_REPEATED.getMessage());
        }
        ViRepoBean bean = viRepoMapper.selectViRepoById(viRepoBean);
        if (bean == null) {
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        bean.setBkname(viRepoBean.getBkname());
        if (StringUtils.hasLength(viRepoBean.getBkdesc())) {
            bean.setBkdesc(viRepoBean.getBkdesc());
        }
        bean.setGmtModified(new Date());
        viRepoMapper.updateViRepo(bean);
        return ResultVo.success();
    }

    /**
     * 删除布控库
     *
     * @param id
     * @return
     */
    @Override
    public ResultVo delViRepo(ViRepoBean viRepoBean) {
        if (viRepoBean == null) {
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        List<ViRepoBean> allViRepo = viRepoMapper.getAllViRepo(viRepoBean);
        if (allViRepo.size() == 0) {
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        ViPrivateMemberVo bean = new ViPrivateMemberVo();
        bean.setRepoId(viRepoBean.getId());
        List<ViMemberVo> allViPrivateMember = viPrivateMemberMapper.getAllViPrivateMember(bean);

        if (allViPrivateMember.size() > 0) {
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_DELETED_FAILED.getCode(), BizExceptionEnum.PRIVATEREPO_DELETED_FAILED.getMessage());
        }
        viRepoMapper.delViRepo(viRepoBean.getId());
        return ResultVo.success();
    }

    @Override
    public ResultVo getAllViRepo(ViRepoVo viRepoVo) {
        PageHelper.startPage(viRepoVo.getCurrent(), viRepoVo.getSize());
        ViRepoBean viRepoBean = new ViRepoBean();
        //复制对象
        BeanCopier beanCopier = BeanCopier.create(ViRepoVo.class, ViRepoBean.class, false);
        beanCopier.copy(viRepoVo, viRepoBean, null);
        List<ViRepoBean> list = viRepoMapper.getAllViRepo(viRepoBean);

        for (ViRepoBean bean : list) {
            bean.setBktypeValue(ViRepoBkTypeEnum.getDescByType(bean.getBktype()));
        }
        return ResultVo.success(PageReturnUtils.getPageMap(list, viRepoVo.getCurrent(), viRepoVo.getSize()));
    }
}
