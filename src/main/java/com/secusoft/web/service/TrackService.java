package com.secusoft.web.service;


import com.secusoft.web.model.Picture;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.Track;

import java.util.List;

public interface TrackService {
    ResultVo addTrack(Track track, List<Picture> pictures);
    ResultVo removeTrack(Track track);
    ResultVo updateTrackName(Track track);
    ResultVo updateTrack(Track track,List<Picture> pictures);
    ResultVo readTrack(Track track);
}
