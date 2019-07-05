package com.secusoft.web.mapper;

import com.secusoft.web.model.DeviceBean;
import com.secusoft.web.model.FolderBean;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Map;

public interface FolderMapper {
    int insertFolder(FolderBean folderBean);
    int deleteFolderById(@Param("id") String id);
    int updateNonEmptyFolderById(FolderBean folderBean);
    Integer selectCountFolderByObj(FolderBean folderBean);
    List<FolderBean> selectFolderByName(FolderBean folderBean);
    List<FolderBean> selectByStatus(FolderBean queryBean);
    List<FolderBean> selectAllFolder();
    FolderBean selectOneFolder(String fid);
    Map<String, Object> selectPicById(@Param("id")String id);
}
