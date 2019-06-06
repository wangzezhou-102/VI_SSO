package com.secusoft.web.service;

import com.secusoft.web.model.Folder;
import com.secusoft.web.model.ResultVo;

import java.util.List;
import java.util.Map;

public interface FolderService {
    ResultVo addFolder(Folder folder);
    ResultVo removeFolder(String id);
    ResultVo setFolderStatus(Folder folder);
    ResultVo setFolderName(Folder folder);
    ResultVo getFolderByStatus(Integer status);
    Map<String, Object> getPicById(String id);

}
