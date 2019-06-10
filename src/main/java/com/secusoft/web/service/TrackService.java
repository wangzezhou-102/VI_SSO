package com.secusoft.web.service;


import com.secusoft.web.model.PictureBean;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.TrackBean;

import java.util.List;

public interface TrackService {
    ResultVo addTrack(TrackBean trackBean, List<PictureBean> pictureBeans);
    ResultVo removeTrack(TrackBean trackBean);
    ResultVo updateTrackName(TrackBean trackBean);
    ResultVo updateTrack(TrackBean trackBean, List<PictureBean> pictureBeans);
    ResultVo readTrack(TrackBean trackBean);
}
