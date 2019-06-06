package com.secusoft.web.Service.impl;

import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.secusoft.web.Service.ViRepoService;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.mapper.ViRepoMapper;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.ViRepoBean;
import com.secusoft.web.model.ViRepoVo;
import com.secusoft.web.utils.PageReturnUtils;
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

    /**
     * 添加布控库
     * @param viRepoBean
     * @return
     */
    @Override
    public ResultVo insertViRepo(ViRepoBean viRepoBean) {
        if(!StringUtils.hasLength(viRepoBean.getBkname())){
            return ResultVo.failure(BizExceptionEnum.REPO_NAME_NULL.getCode(), BizExceptionEnum.REPO_NAME_NULL.getMessage());
        }
        viRepoMapper.insertViRepo(viRepoBean);
        return ResultVo.success();
    }

    /**
     * 更新布控库
     * @param viRepoBean
     * @return
     */
    @Override
    public ResultVo updateViRepo(ViRepoBean viRepoBean) {
        if(!StringUtils.hasLength(viRepoBean.getBkname())){
            return ResultVo.failure(BizExceptionEnum.REPO_NAME_NULL.getCode(), BizExceptionEnum.REPO_NAME_NULL.getMessage());
        }
        viRepoBean.setGmtModified(new Date());
        viRepoMapper.updateViRepo(viRepoBean);
        return ResultVo.success();
    }

    /**
     * 删除布控库
     * @param id
     * @return
     */
    @Override
    public ResultVo delViRepo(Integer id) {
        if(id==0){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        viRepoMapper.delViRepo(id);
        return ResultVo.success();
    }

    @Override
    public ResultVo getAllViRepo(ViRepoVo viRepoVo) {
        PageHelper.startPage(viRepoVo.getCurrent(),viRepoVo.getSize());

        List<ViRepoBean> list=viRepoMapper.getAllViRepo();

        return ResultVo.success(PageReturnUtils.getPageMap(list,viRepoVo.getCurrent(),viRepoVo.getSize()));
    }
}
