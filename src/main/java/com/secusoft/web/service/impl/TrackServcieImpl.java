package com.secusoft.web.service.impl;

import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.core.util.UUIDUtil;
import com.secusoft.web.core.util.UploadUtil;
import com.secusoft.web.mapper.PictureMapper;
import com.secusoft.web.mapper.TrackMapper;
import com.secusoft.web.model.PictureBean;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.TrackBean;
import com.secusoft.web.service.TrackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        if(trackMapper.selectCountTrackByName(trackBean)==0){
            trackMapper.insertTrack(trackBean);
            for (PictureBean pictureBean : pictureBeans) {
                pictureBean.setPicType(1);
                //图片收藏需要下载到本地
                String basePath = System.getProperty("user.dir");
                String folderName = basePath+"pic"+ UploadUtil.getFolder();
                //创建文件名称  类似 org_ehWbXqMCZkg6KwRKsU31Cs.jpg
                String oriFileName = UUIDUtil.getUid("org_") +".jpg";
                String cropFileName = UUIDUtil.getUid("crop_") +".jpg";

                try {
                    //创建并下载到相应的文件夹
                    Files.createDirectories(Paths.get(folderName));
                    UploadUtil.downLoadFromUrl(pictureBean.getCropImageSignedUrl(),cropFileName,folderName);
                    UploadUtil.downLoadFromUrl(pictureBean.getOriImageSignedUrl(),oriFileName,folderName);
                } catch (Exception e) {
                    e.printStackTrace();
                    Path path1 = Paths.get(folderName,oriFileName);
                    Path path2 = Paths.get(folderName,cropFileName);
                    try {
                        Files.deleteIfExists(path1);
                        Files.deleteIfExists(path2);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    return ResultVo.failure(BizExceptionEnum.SERVER_ERROR);
                }
                //存储文件夹+文件名 /2019621/1.jpg
                pictureBean.setLocalCropImageUrl(UploadUtil.getFolder()+cropFileName);
                pictureBean.setLocalOriImageUrl(UploadUtil.getFolder()+oriFileName);
            }
            pictureMapper.insertMorePicture(pictureBeans);
            ArrayList<String> picIds = new ArrayList<>();
            for (PictureBean pictureBean : pictureBeans) {
                picIds.add(pictureBean.getId());
            }
            trackMapper.insertTrackPicture(picIds, trackBean.getId());
            return ResultVo.success();
        }else {
            return ResultVo.failure(BizExceptionEnum.REPEAT.getCode(),BizExceptionEnum.REPEAT.getMessage());
        }
    }

    @Override
    public ResultVo removeTrack(TrackBean trackBean) {
        List<String> pids = trackMapper.selectTrackPictureByTid(trackBean.getId());
        pictureMapper.deleteMorePictureById(pids);
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
        return ResultVo.success();
    }

    @Override
    public ResultVo readTrack(TrackBean trackBean) {
        trackMapper.selectTrackById(trackBean.getId());
        return ResultVo.success();
    }
}
