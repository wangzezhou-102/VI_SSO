package com.secusoft.web.service.impl;

import com.secusoft.web.core.exception.BizException;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.model.Folder;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.FolderService;
import com.secusoft.web.mapper.FolderMapper;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
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
        folder.setStatus(1);
        if(folder == null || StringUtils.isEmpty(folder.getId())){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        folderMapper.updateNonEmptyFolderById(folder);
        return ResultVo.success();
    }

    @Override
    public ResultVo setFolderName(Folder folder) {
        folderMapper.updateNonEmptyFolderById(folder);
        return ResultVo.success();
    }

    @Override
    public List<Folder> getFolderByStatus(int status) {
        List<Folder> folders = folderMapper.selectByStatus(status);
        return folders;
    }

    @Override
    public Map<String, Object> getPicById(String id) {
        return null;
    }
}
