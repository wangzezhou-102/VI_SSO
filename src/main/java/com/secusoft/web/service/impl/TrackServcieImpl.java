package com.secusoft.web.service.impl;

import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.core.util.FileUtil;
import com.secusoft.web.core.util.StringUtils;
import com.secusoft.web.core.util.UUIDUtil;
import com.secusoft.web.core.util.UploadUtil;
import com.secusoft.web.mapper.PictureMapper;
import com.secusoft.web.mapper.TrackMapper;
import com.secusoft.web.model.PictureBean;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.TrackBean;
import com.secusoft.web.service.PictureService;
import com.secusoft.web.service.TrackService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private PictureService pictureService;
    
    private static String basePath = System.getProperty("user.dir") + "/resources/";

    @Override
    @Transactional
    public ResultVo addTrack(TrackBean trackBean, List<PictureBean> pictureBeans) {
        if(trackMapper.selectCountTrackByName(trackBean)==0){
            trackMapper.insertTrack(trackBean);
            ArrayList<String> picIds = new ArrayList<>();
            System.out.println(pictureBeans.size());
            String relativePath = "/store/" + trackBean.getFolderId() + "/track/" + trackBean.getId() +"/";
            for (PictureBean pictureBean:pictureBeans) {
                //收藏图片类型是1  轨迹是2
                pictureBean.setPicType(2);
                //图片收藏需要下载到本地

                String folderName = basePath + relativePath;
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
                pictureBean.setLocalCropImageUrl("/static"+ relativePath + cropFileName);
                pictureBean.setLocalOriImageUrl("/static" + relativePath + oriFileName);
                pictureMapper.insertPicture(pictureBean);
                picIds.add(pictureBean.getId());

            }
            trackMapper.insertTrackPicture(picIds, trackBean.getId());
            return ResultVo.success();
        }else {
            return ResultVo.failure(BizExceptionEnum.REPEAT.getCode(),BizExceptionEnum.REPEAT.getMessage());
        }
    }

    @Override
    @Transactional
    public ResultVo coveraddTrack(TrackBean trackBean, List<PictureBean> pictureBeans) {
        //先把这个轨迹找出来  找到他的id 找到与其对应的 picture 删除（表以及文件）  重新添加图片  更新中间表
        TrackBean track = trackMapper.selectTrackByObj(trackBean);

        List<String> pids = trackMapper.selectTrackPictureByTid(track.getId());
        for (String id:pids) {

        	PictureBean picBean = pictureMapper.selectPictureById(id);
            String oriFileName = picBean.getLocalOriImageUrl();
            String cropFileName = picBean.getLocalCropImageUrl();

            Path path1 = Paths.get(basePath, oriFileName.replace("/static/", ""));
            Path path2 = Paths.get(basePath, cropFileName.replace("/static/", ""));
            //先在文件夹中删除  再在数据库中删除
            try {
                Files.deleteIfExists(path1);
                Files.deleteIfExists(path2);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        //删除图片表
        if (!pids.isEmpty()){
            pictureMapper.deleteMorePictureById(pids);
        }
        //删除中间表
        trackMapper.deleteTrackPictureById(track.getId());
        //下载最新的图片到本地
        for (PictureBean pictureBean : pictureBeans) {
            pictureBean.setPicType(2);
            //图片收藏需要下载到本地
            String relativePath = "/store/" + track.getFolderId() + "/track/" + track.getId() +"/";

            String folderName = basePath + relativePath;
            //创建文件名称  类似 org_ehWbXqMCZkg6KwRKsU31Cs.jpg
            String oriFileName = UUIDUtil.getUid("org_") +".jpg";
            String cropFileName = UUIDUtil.getUid("crop_") +".jpg";
            try {
                //创建并下载到相应的文件夹
                Files.createDirectories(Paths.get(folderName));
                UploadUtil.downLoadFromUrl(pictureBean.getCropImageSignedUrl(),cropFileName,folderName);
                UploadUtil.downLoadFromUrl(pictureBean.getOriImageSignedUrl(),oriFileName,folderName);
                System.out.println("更新成功");
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
            pictureBean.setLocalCropImageUrl("/static" + relativePath + cropFileName);
            pictureBean.setLocalOriImageUrl("/static" + relativePath + oriFileName);
        }
        ArrayList<String> picIds = new ArrayList<>();
        for (PictureBean pictureBean : pictureBeans) {
            pictureMapper.insertPicture(pictureBean);
            picIds.add(pictureBean.getId());
        }
        //重新插入中间表
        trackMapper.insertTrackPicture(picIds, track.getId());
        return ResultVo.success();
    }

    @Override
    public ResultVo removeTrack(TrackBean trackBean) {
    	TrackBean track = trackMapper.selectTrackById(trackBean.getId());
        String relativePath = "/store/" + track.getFolderId() + "/track/" + track.getId() ;
        Path path = Paths.get(basePath, relativePath);
        try {
        	FileUtil.deleteDir(path.toFile());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
//        for (String id:pids) {
//            String basePath = System.getProperty("user.dir");
//            String oriFileName =pictureMapper.selectPictureById(id).getLocalOriImageUrl();
//            String cropFileName = pictureMapper.selectPictureById(id).getLocalCropImageUrl();
//
//            Path path1 = Paths.get(basePath,"pic",oriFileName);
//            Path path2 = Paths.get(basePath,"pic",cropFileName);
//            //先在文件夹中删除  再在数据库中删除
//            try {
//                Files.deleteIfExists(path1);
//                Files.deleteIfExists(path2);
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//        }
//        if(!pids.isEmpty()){
//            pictureMapper.deleteMorePictureById(pids);
//        }
        pictureMapper.deletePictureByTrackId(trackBean.getId());
        trackMapper.deleteTrackPictureById(trackBean.getId());
        trackMapper.deleteTrackById(trackBean.getId());
        return ResultVo.success();
    }

    @Override
    public ResultVo updateTrackName(TrackBean trackBean) {
    	if(StringUtils.isEmpty(trackBean.getId())||StringUtils.isEmpty(trackBean.getTrackName())) {
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(),BizExceptionEnum.PARAM_NULL.getMessage());
    	}
    	TrackBean queryBean = new TrackBean();
    	queryBean.setIdn(Integer.valueOf(trackBean.getId()));
    	queryBean.setTrackName(trackBean.getTrackName());
    	Integer count = trackMapper.selectTrackCountByBean(queryBean);
    	if(count > 0) {
            return ResultVo.failure(BizExceptionEnum.TRACK_REPEAT.getCode(),BizExceptionEnum.TRACK_REPEAT.getMessage());
    	}
        trackMapper.updateTrackNameById(trackBean);
        return ResultVo.success();
    }


    @Override
    public ResultVo updateTrack(TrackBean trackBean, List<PictureBean> pictureBeans) {
        List<String> pids = trackMapper.selectTrackPictureByTid(trackBean.getId());
        pictureMapper.deleteMorePictureById(pids);
        trackMapper.deleteTrackPictureById(trackBean.getId());
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

    /**
     * 删除轨迹下的图片信息
     */
    @Transactional
	@Override
	public ResultVo removeTrackPic(PictureBean pictureBean) {
    	String picId = pictureBean.getId();
    	if(StringUtils.isEmpty(picId)) {
            return ResultVo.failure(BizExceptionEnum.PARAM_ERROR.getCode(), BizExceptionEnum.PARAM_ERROR.getMessage());
    	}
    	pictureService.removePicture(picId);
    	trackMapper.deleteTrackPicByPicId(picId);
		return ResultVo.success();
	}
    
    /**
     * 根据文件夹删除轨迹信息，仅数据库
     */
    @Override
    public ResultVo removeTrackByFolderId(Integer folderId) {
    	if(folderId == null) {
            return ResultVo.failure(BizExceptionEnum.PARAM_ERROR.getCode(), BizExceptionEnum.PARAM_ERROR.getMessage());
    	}
    	pictureMapper.deleteTrackPicByFolderId(folderId);
    	trackMapper.deleteTrackPicByFolderId(folderId);
    	TrackBean bean = new TrackBean();
    	bean.setFolderId(String.valueOf(folderId));
    	trackMapper.deleteTrackByBean(bean);
		return ResultVo.success();

    }
}
