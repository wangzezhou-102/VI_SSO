package com.secusoft.web.service.impl;

import com.secusoft.web.mapper.PictureMapper;
import com.secusoft.web.mapper.TrackMapper;
import com.secusoft.web.model.PictureBean;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.TrackBean;
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
    public ResultVo addTrack(TrackBean trackBean, List<PictureBean> pictureBeans) {
        trackMapper.insertTrack(trackBean);
        pictureMapper.insertMorePicture(pictureBeans);
        ArrayList<String> picIds = new ArrayList<>();
        for (PictureBean pictureBean : pictureBeans) {
            picIds.add(pictureBean.getId());
        }
        trackMapper.insertTrackPicture(picIds, trackBean.getId());
        return ResultVo.success();
    }

    @Override
    public ResultVo removeTrack(TrackBean trackBean) {
        trackMapper.deletcTrackPictureById(trackBean.getId());
        trackMapper.deleteTrackById(trackBean.getId());
        return ResultVo.success();
    }

    @Override
    public ResultVo updateTrackName(TrackBean trackBean) {
        trackMapper.updateTrackNameById(trackBean);
        return ResultVo.success();
    }


    @Override
    public ResultVo updateTrack(TrackBean trackBean, List<PictureBean> pictureBeans) {
        List<String> pids = trackMapper.selectTrackPictureByTid(trackBean.getId());
        pictureMapper.deleteMorePictureById(pids);
        trackMapper.deletcTrackPictureById(trackBean.getId());
        pictureMapper.insertMorePicture(pictureBeans);
        ArrayList<String> picIds = new ArrayList<>();
        for (PictureBean pictureBean : pictureBeans) {
            picIds.add(pictureBean.getId());
        }
        trackMapper.insertTrackPicture(picIds, trackBean.getId());
        return null;
    }

    @Override
    public ResultVo readTrack(TrackBean trackBean) {

        return null;
    }
}
