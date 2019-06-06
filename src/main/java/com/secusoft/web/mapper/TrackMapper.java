package com.secusoft.web.mapper;

import com.secusoft.web.model.Track;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TrackMapper {
    int insertTrack(Track track);
    int insertTrackPicture(@Param("ids") List picIds, @Param("trackid") String trackId);
    int deleteTrackById(String id);
    int deletcTrackPictureById(String id);
    int updateTrackNameById(Track track);
    List<String> selectTrackPictureByTid(String tid);
    Track selectTrackById(Object id);
}
