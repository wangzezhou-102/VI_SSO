package com.secusoft.web.mapper;

import com.secusoft.web.model.Picture;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PictureMapper {
    int insertPicture(Picture picture);
    int deletePictureById(String id);
    int deleteMorePictureById(List<String> pids);
    Picture selectPictureById(String id);
    int insertMorePicture(List<Picture> pictures);
}
