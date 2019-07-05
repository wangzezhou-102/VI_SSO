package com.secusoft.web.service;

import com.secusoft.web.model.FolderBean;
import com.secusoft.web.model.ResultVo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface FolderService {
    ResultVo addFolder(FolderBean folderBean);
    ResultVo removeFolder(String id);
    ResultVo setFolderStatus(FolderBean folderBean);
    ResultVo setFolderName(FolderBean folderBean);
    ResultVo getFolderByStatus(FolderBean folderBean);
    ResultVo getFolderByName(FolderBean folderBean);
    ResultVo getAllFolder();
    ResultVo getFolder(String fid);
    Map<String, Object> getPicById(String id);

}
