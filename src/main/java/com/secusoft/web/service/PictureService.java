package com.secusoft.web.service;

import com.secusoft.web.model.PictureBean;
import com.secusoft.web.model.ResultVo;

public interface PictureService {
    ResultVo addPicture(PictureBean pictureBean);
    ResultVo removePicture(String id);
    ResultVo getPictureById(String id);
}
