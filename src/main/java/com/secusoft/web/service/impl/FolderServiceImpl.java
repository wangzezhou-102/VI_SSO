package com.secusoft.web.service.impl;

import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.model.Folder;
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
    public ResultVo addFolder(Folder folder) {

        if(folder == null || StringUtils.isEmpty(folder.getFolderName())){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        System.out.println(folderMapper.selectCountFolderByObj(folder));
        if(folderMapper.selectCountFolderByObj(folder)==0){
            folder.setStatus(0);
            folderMapper.insertFolder(folder);
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
    public ResultVo setFolderStatus(Folder folder) {
        if(folder == null || StringUtils.isEmpty(folder.getId())){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        folder.setStatus(1);
        folderMapper.updateNonEmptyFolderById(folder);
        return ResultVo.success();
    }

    @Override
    public ResultVo setFolderName(Folder folder) {
        if(folder == null || StringUtils.isEmpty(folder.getFolderName())){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        if(folderMapper.selectCountFolderByObj(folder)==0){
            folderMapper.updateNonEmptyFolderById(folder);
        }else{
            return ResultVo.failure(BizExceptionEnum.REPEAT.getCode(),BizExceptionEnum.REPEAT.getMessage());
        }
        return ResultVo.success();
    }

    @Override
    public ResultVo getFolderByStatus(Integer status) {
        List<Folder> folders = folderMapper.selectByStatus(status);
        return ResultVo.success(folders);
    }

    @Override
    public Map<String, Object> getPicById(String id) {
        return null;
    }
}
