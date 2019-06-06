package com.secusoft.web.service;

import com.secusoft.web.model.Picture;
import com.secusoft.web.model.ResultVo;

public interface PictureService {
    ResultVo addPicture(Picture picture);
    ResultVo removePicture(String id);
    ResultVo getPictureById(String id);
}
