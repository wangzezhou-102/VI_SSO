package com.secusoft.web.mapper;

import com.secusoft.web.model.PictureBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PictureMapper {
    int insertPicture(PictureBean pictureBean);
    int deletePictureById(String id);
    int deleteMorePictureById(@Param("pids") List<String> pids);
    PictureBean selectPictureById(String id);
    List<PictureBean>  selectPictureByTid(String tid);
    List<PictureBean>  selectPictureByFid(String fid);
    int insertMorePicture(List<PictureBean> pictureBeans);
}
