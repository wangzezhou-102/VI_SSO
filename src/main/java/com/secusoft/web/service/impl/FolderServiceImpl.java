package com.secusoft.web.service.impl;

import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.model.FolderBean;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.FolderService;
import com.secusoft.web.mapper.FolderMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class FolderServiceImpl implements FolderService {
    @Resource
    FolderMapper folderMapper;

    @Override
    public ResultVo addFolder(FolderBean folderBean) {

        if(folderBean == null || StringUtils.isEmpty(folderBean.getFolderName())){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        System.out.println(folderMapper.selectCountFolderByObj(folderBean));
        if(folderMapper.selectCountFolderByObj(folderBean)==0){
            folderBean.setStatus(0);
            folderMapper.insertFolder(folderBean);
        }else {
            return ResultVo.failure(BizExceptionEnum.REPEAT.getCode(),BizExceptionEnum.REPEAT.getMessage());
        }
        return ResultVo.success();
    }

    @Override
    public ResultVo removeFolder(String id) {
        if(StringUtils.isEmpty(id)){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        folderMapper.deleteFolderById(id);
        return ResultVo.success();
    }

    @Override
    public ResultVo setFolderStatus(FolderBean folderBean) {
        if(folderBean == null || StringUtils.isEmpty(folderBean.getId())){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        folderBean.setStatus(1);
        folderMapper.updateNonEmptyFolderById(folderBean);
        return ResultVo.success();
    }

    @Override
    public ResultVo setFolderName(FolderBean folderBean) {
        if(folderBean == null || StringUtils.isEmpty(folderBean.getFolderName())){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        if(folderMapper.selectCountFolderByObj(folderBean)==0){
            folderMapper.updateNonEmptyFolderById(folderBean);
        }else{
            return ResultVo.failure(BizExceptionEnum.REPEAT.getCode(),BizExceptionEnum.REPEAT.getMessage());
        }
        return ResultVo.success();
    }

    @Override
    public ResultVo getFolderByStatus(Integer status) {
        List<FolderBean> folderBeans = folderMapper.selectByStatus(status);
        return ResultVo.success(folderBeans);
    }

    @Override
    public Map<String, Object> getPicById(String id) {
        return null;
    }
}
