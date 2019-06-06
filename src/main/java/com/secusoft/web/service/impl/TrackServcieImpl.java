package com.secusoft.web.service.impl;

import com.secusoft.web.mapper.PictureMapper;
import com.secusoft.web.mapper.TrackMapper;
import com.secusoft.web.model.Picture;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.Track;
import com.secusoft.web.service.TrackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TrackServcieImpl implements TrackService {
    @Resource
    private TrackMapper trackMapper;
    @Resource
    private PictureMapper pictureMapper;

    @Override
    public ResultVo addTrack(Track track, List<Picture> pictures) {
        trackMapper.insertTrack(track);
        pictureMapper.insertMorePicture(pictures);
        ArrayList<String> picIds = new ArrayList<>();
        for (Picture picture:pictures) {
            picIds.add(picture.getId());
        }
        trackMapper.insertTrackPicture(picIds,track.getId());
        return ResultVo.success();
    }

    @Override
    public ResultVo removeTrack(Track track) {
        trackMapper.deletcTrackPictureById(track.getId());
        trackMapper.deleteTrackById(track.getId());
        return ResultVo.success();
    }

    @Override
    public ResultVo updateTrackName(Track track) {
        trackMapper.updateTrackNameById(track);
        return ResultVo.success();
    }


    @Override
    public ResultVo updateTrack(Track track, List<Picture> pictures) {
        List<String> pids = trackMapper.selectTrackPictureByTid(track.getId());
        pictureMapper.deleteMorePictureById(pids);
        trackMapper.deletcTrackPictureById(track.getId());
        pictureMapper.insertMorePicture(pictures);
        ArrayList<String> picIds = new ArrayList<>();
        for (Picture picture:pictures) {
            picIds.add(picture.getId());
        }
        trackMapper.insertTrackPicture(picIds,track.getId());
        return null;
    }

    @Override
    public ResultVo readTrack(Track track) {

        return null;
    }
}
