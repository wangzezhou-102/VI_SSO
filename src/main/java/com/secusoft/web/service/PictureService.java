package com.secusoft.web.service;

import java.io.UnsupportedEncodingException;

import com.secusoft.web.model.PictureBean;
import com.secusoft.web.model.ResultVo;

public interface PictureService {
    ResultVo addPicture(PictureBean pictureBean) throws UnsupportedEncodingException;
    ResultVo removePicture(String id);
    ResultVo getPictureById(String id);
    
    ResultVo removePictureByFolderId(String folderId);
    
    ResultVo cancelPicture(PictureBean pictureBean);
    ResultVo picturePageHelpe(PictureBean pictureBean);
}
