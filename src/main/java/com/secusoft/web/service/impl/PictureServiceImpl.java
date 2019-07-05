package com.secusoft.web.service.impl;

import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.core.util.UUIDUtil;
import com.secusoft.web.core.util.UploadUtil;
import com.secusoft.web.mapper.DeviceMapper;
import com.secusoft.web.mapper.PictureMapper;
import com.secusoft.web.model.DeviceBean;
import com.secusoft.web.model.PictureBean;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.PictureService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class PictureServiceImpl implements PictureService {
    @Resource
    PictureMapper pictureMapper;

    @Resource
    DeviceMapper deviceMapper;

    @Override
    public ResultVo addPicture(PictureBean pictureBean) {
        if (pictureBean ==null){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        //图片收藏需要下载到本地
        String basePath = System.getProperty("user.dir");
        String folderName = basePath+"/"+"pic"+UploadUtil.getFolder();
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
        //图片收藏type为1 轨迹内的图type为2
        pictureBean.setPicType(1);
        pictureMapper.insertPicture(pictureBean);
        return ResultVo.success();
    }

    @Override
    public ResultVo removePicture(String id) {
        if(StringUtils.isEmpty(id)){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        String basePath = System.getProperty("user.dir");
        String oriFileName =pictureMapper.selectPictureById(id).getLocalOriImageUrl();
        String cropFileName = pictureMapper.selectPictureById(id).getLocalCropImageUrl();

        System.out.println(oriFileName);
        System.out.println(cropFileName);

        Path path1 = Paths.get(basePath,"pic",oriFileName);
        Path path2 = Paths.get(basePath,"pic",cropFileName);
        //先在文件夹中删除  再在数据库中删除
        try {
            Files.deleteIfExists(path1);
            Files.deleteIfExists(path2);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        pictureMapper.deletePictureById(id);
        return ResultVo.success();
    }

    @Override
    public ResultVo getPictureById(String id) {
        if(StringUtils.isEmpty(id)){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        PictureBean pictureBean = pictureMapper.selectPictureById(id);
        if(pictureBean==null){
            return ResultVo.failure(BizExceptionEnum.PARAM_ERROR.getCode(),BizExceptionEnum.PARAM_ERROR.getMessage());
        }
        DeviceBean deviceBean = deviceMapper.selectDeviceById(pictureBean.getDeviceId());
        pictureBean.setDeviceBean(deviceBean);
        return ResultVo.success(pictureBean);
    }
}
