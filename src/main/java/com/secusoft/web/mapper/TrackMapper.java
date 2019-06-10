package com.secusoft.web.mapper;

import com.secusoft.web.model.TrackBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TrackMapper {
    int insertTrack(TrackBean trackBean);
    int insertTrackPicture(@Param("ids") List picIds, @Param("trackid") String trackId);
    int deleteTrackById(String id);
    int deletcTrackPictureById(String id);
    int updateTrackNameById(TrackBean trackBean);
    List<String> selectTrackPictureByTid(String tid);
    TrackBean selectTrackById(Object id);
}
