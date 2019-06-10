package com.secusoft.web.mapper;

import com.secusoft.web.model.FolderBean;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Map;

public interface FolderMapper {
    int insertFolder(FolderBean folderBean);
    int deleteFolderById(@Param("id") String id);
    int updateNonEmptyFolderById(FolderBean folderBean);
    Integer selectCountFolderByObj(FolderBean folderBean);
    List<FolderBean> selectByStatus(@Param("status")int status);
    Map<String, Object> selectPicById(@Param("id")String id);
}
