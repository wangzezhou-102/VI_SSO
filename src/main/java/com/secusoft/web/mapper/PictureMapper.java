package com.secusoft.web.mapper;

import com.secusoft.web.model.PictureBean;

import java.util.List;

public interface PictureMapper {
    int insertPicture(PictureBean pictureBean);
    int deletePictureById(String id);
    int deleteMorePictureById(List<String> pids);
    PictureBean selectPictureById(String id);
    int insertMorePicture(List<PictureBean> pictureBeans);
}
