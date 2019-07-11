package com.secusoft.web.service.impl;

import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.core.util.UUIDUtil;
import com.secusoft.web.core.util.UploadUtil;
import com.secusoft.web.mapper.DeviceMapper;
import com.secusoft.web.mapper.FolderMapper;
import com.secusoft.web.mapper.PictureMapper;
import com.secusoft.web.model.DeviceBean;
import com.secusoft.web.model.FolderBean;
import com.secusoft.web.model.PictureBean;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.PictureService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PictureServiceImpl implements PictureService {
	
	private static final String basePath = System.getProperty("user.dir") + "/resources";
    @Resource
    PictureMapper pictureMapper;

    @Resource
    DeviceMapper deviceMapper;

    @Autowired
    FolderMapper folderMapper;
    
    @Override
    public ResultVo addPicture(PictureBean pictureBean) throws UnsupportedEncodingException {
        if (pictureBean ==null){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        //图片收藏需要下载到本地
        String relativePath = "/store/" + pictureBean.getFolderId() + "/pic/" ;
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
        pictureBean.setLocalCropImageUrl("/static" + relativePath + cropFileName);
        pictureBean.setLocalOriImageUrl("/static" + relativePath+ oriFileName);
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
        PictureBean picBean = pictureMapper.selectPictureById(id);
        String oriFileName = picBean.getLocalOriImageUrl();
        String cropFileName = picBean.getLocalCropImageUrl();

        System.out.println(oriFileName);
        System.out.println(cropFileName);

        Path path1 = Paths.get(basePath, oriFileName.replaceFirst("/static/", ""));
        Path path2 = Paths.get(basePath, cropFileName.replaceFirst("/static/", ""));
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

    /**
     * 删除数据库对应文件夹下所有图片，仅限数据库
     */
	@Override
	public ResultVo removePictureByFolderId(String folderId) {
		if(StringUtils.isEmpty(folderId)) {
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
		}
		PictureBean paramBean = new PictureBean();
		paramBean.setFolderId(folderId);
		paramBean.setPicType(1);
		pictureMapper.deletePicByBean(paramBean);
		return ResultVo.success();
	}
}
