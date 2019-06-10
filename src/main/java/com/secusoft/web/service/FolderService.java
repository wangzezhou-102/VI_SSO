package com.secusoft.web.service;

import com.secusoft.web.model.FolderBean;
import com.secusoft.web.model.ResultVo;

import java.util.Map;

public interface FolderService {
    ResultVo addFolder(FolderBean folderBean);
    ResultVo removeFolder(String id);
    ResultVo setFolderStatus(FolderBean folderBean);
    ResultVo setFolderName(FolderBean folderBean);
    ResultVo getFolderByStatus(Integer status);
    Map<String, Object> getPicById(String id);

}
