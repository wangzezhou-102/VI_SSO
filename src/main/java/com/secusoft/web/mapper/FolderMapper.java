package com.secusoft.web.mapper;

import com.secusoft.web.model.Folder;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Map;

public interface FolderMapper {

    int insertFolder(Folder folder);
    int deleteFolderById(@Param("id") String id);
    int updateNonEmptyFolderById(Folder folder);
    Integer selectCountFolderByObj(Folder folder);
    List<Folder> selectByStatus(@Param("status")int status);
    Map<String, Object> selectPicById(@Param("id")String id);
}
