package com.secusoft.web.mapper;

import com.secusoft.web.model.TrackBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TrackMapper {
    /**
     *增加轨迹
     */
    int insertTrack(TrackBean trackBean);
    /**
     *添加轨迹图片中间表
     */
    int insertTrackPicture(@Param("ids") List picIds, @Param("trackid") String trackId);
    int deleteTrackById(String id);
    /**
     *根据trackId删除轨迹图片中间表
     */
    int deleteTrackPictureById(String id);
    int updateTrackNameById(TrackBean trackBean);
    int selectCountTrackByName(TrackBean trackBean);
    /**
     *以Track对象中不为空的属性查询
     */
    TrackBean selectTrackByObj(TrackBean trackBean);
    List<String> selectTrackPictureByTid(String tid);
    List<TrackBean> selectTrackByFid(String fid);
    TrackBean selectTrackById(Object id);
    
    void deleteTrackPicByPicId(@Param("picId") String picId);
    
    void deleteTrackPicByFolderId(@Param("folderId") Integer folderId);
    void deleteTrackByBean(TrackBean trackBean);
    
    Integer selectTrackCountByBean(TrackBean queryBean);
}
