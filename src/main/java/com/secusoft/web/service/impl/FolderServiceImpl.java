package com.secusoft.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.core.util.FileUtil;
import com.secusoft.web.mapper.*;
import com.secusoft.web.model.*;
import com.secusoft.web.service.AreaService;
import com.secusoft.web.service.FolderService;
import com.secusoft.web.service.PictureService;
import com.secusoft.web.service.TrackService;
import com.secusoft.web.tusouapi.model.SearchResponseData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FolderServiceImpl implements FolderService {
	
    private static final String basePath = System.getProperty("user.dir") + "/resources";

    @Resource
    FolderMapper folderMapper;
    @Resource
    AreaMapper areaMapper;
    @Resource
    TrackMapper trackMapper;
    @Resource
    PictureMapper pictureMapper;
    @Resource
    DeviceMapper deviceMapper;

    @Autowired
    PictureService pictureService;
    @Autowired
    TrackService trackService;
    @Autowired
    AreaService areaService;
    @Override
    public ResultVo addFolder(FolderBean folderBean) {

        if(folderBean == null || StringUtils.isEmpty(folderBean.getFolderName())){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        if(folderMapper.selectCountFolderByObj(folderBean)==0){
            folderBean.setStatus(0);
            folderMapper.insertFolder(folderBean);

        }else {
            return ResultVo.failure(BizExceptionEnum.REPEAT.getCode(),BizExceptionEnum.REPEAT.getMessage());
        }
        return ResultVo.success(folderMapper.selectAllFolder());
    }

    @Override
    public ResultVo removeFolder(String id) {
        if(StringUtils.isEmpty(id)){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        folderMapper.deleteFolderById(id);
        pictureService.removePictureByFolderId(id);
        areaService.removeAreaByFolderId(Integer.valueOf(id));
        trackService.removeTrackByFolderId(Integer.valueOf(id));
        String relativePath = "/store/" + id;
        Path folderPath = Paths.get(basePath, relativePath);
        try {
        	FileUtil.deleteDir(folderPath.toFile());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        
        return ResultVo.success();
    }

    @Override
    public ResultVo setFolderStatus(FolderBean folderBean) {
        if(folderBean == null || StringUtils.isEmpty(folderBean.getId())){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        folderBean.setStatus(1);
        folderMapper.updateNonEmptyFolderById(folderBean);
        return ResultVo.success();
    }

    public static void main(String[] args) {
		File folder = new File("D:/个人/项目/VI_WEB/resources/store/24");
		FileUtil.deleteDir(folder);
	}

    @Override
    public ResultVo setFolderName(FolderBean folderBean) {
        if(folderBean == null || StringUtils.isEmpty(folderBean.getFolderName()) 
        		|| StringUtils.isEmpty(folderBean.getId())){
            return ResultVo.failure(BizExceptionEnum.FOLDER_NAME_NULL.getCode(), BizExceptionEnum.FOLDER_NAME_NULL.getMessage());
        }
        FolderBean queryBean = new FolderBean();
        queryBean.setFolderName(folderBean.getFolderName());
        queryBean.setIdn(Integer.valueOf(folderBean.getId()));
        if(folderMapper.selectCountFolderByObj(queryBean)==0){
            folderMapper.updateNonEmptyFolderById(folderBean);
        }else{
            return ResultVo.failure(BizExceptionEnum.FOLDER_REPEAT.getCode(),BizExceptionEnum.FOLDER_REPEAT.getMessage());
        }
        return ResultVo.success();
    }

    @Override
    public ResultVo getFolderByStatus(FolderBean folderBean) {
        if(folderBean != null && folderBean.getPageNumber() != null && folderBean.getPageSize() != null) {
            PageHelper.startPage(folderBean.getPageNumber().intValue(), folderBean.getPageSize());
        }
        Integer status = folderBean.getStatus();
        if(status==null){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        List<FolderBean> folderBeans = folderMapper.selectByStatus(folderBean);
        PageInfo<FolderBean> pageInfo = new PageInfo<FolderBean>(folderBeans);
        return ResultVo.success(pageInfo.getList(),pageInfo.getTotal());
    }

    @Override
    public ResultVo getFolderByName(FolderBean folderBean) {
        List<FolderBean> folderBeans = folderMapper.selectFolderByName(folderBean);
        return ResultVo.success(folderBeans);
    }

    @Override
    public ResultVo getAllFolder() {
        List<FolderBean> folderBeans = folderMapper.selectAllFolder();
        return ResultVo.success(folderBeans);
    }

    @Override
    public ResultVo getFolder(FolderBean folder) {
        if(folder.getPageSize()==null){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(),BizExceptionEnum.PARAM_NULL.getMessage());
        }
        String fid=folder.getId();
        FolderBean folderBean = folderMapper.selectOneFolder(fid);
        List<PictureBean> pictureBeanss = pictureMapper.selectPictureByFid(fid);
        List<TrackBean> trackBeans = trackMapper.selectTrackByFid(fid);
        List<AreaBean> areaBeans = areaMapper.selectAreaByFid(fid);
        if (!pictureBeanss.isEmpty()){
            List<PictureBean> pictureBeans;
            if(pictureBeanss.size()<folder.getPageSize()){
                 pictureBeans =pictureBeanss;
            }else {
                 pictureBeans = pictureBeanss.subList(0, folder.getPageSize());
            }
            ArrayList<SearchResponseData> searchResponseDatas = new ArrayList<>();
            for (PictureBean pictureBean:pictureBeans) {
                SearchResponseData searchResponseData = PictureBean.toSearchResponseDate(pictureBean);
                searchResponseDatas.add(searchResponseData);
            }
            DeviceBean device = new DeviceBean();
            List<DeviceBean> deviceBeans = deviceMapper.readDeviceList(device);
            searchResponseDatas.forEach(searchResponseData -> {
                deviceBeans.forEach(deviceBean ->{
                    if (deviceBean.getDeviceId().equals(searchResponseData.getSource().getCameraId())){
                        searchResponseData.getSource().setDeviceBean(deviceBean);
                    }
                });
                if(searchResponseData.getSource().getDeviceBean()==null){
                    DeviceBean deviceBean = new DeviceBean();
                    deviceBean.setDeviceName("未命名");
                    searchResponseData.getSource().setDeviceBean(deviceBean);
                }
            });
            folderBean.setImageSearchList(searchResponseDatas);
        }
        if (!areaBeans.isEmpty()){
            folderBean.setDeviceArea(areaBeans);
        }
	    if (!trackBeans.isEmpty()){
	        for (TrackBean trackBean:trackBeans) {
	            ArrayList<SearchResponseData> searchResponseDatas = new ArrayList<>();
	            List<PictureBean> pictureBeans1 = trackBean.getPictureBeans();
	            for (PictureBean pictureBean:pictureBeans1) {
	                SearchResponseData searchResponseData = PictureBean.toSearchResponseDate(pictureBean);
	                searchResponseDatas.add(searchResponseData);
	            }
	            DeviceBean device = new DeviceBean();
	            List<DeviceBean> deviceBeans = deviceMapper.readDeviceList(device);
	            searchResponseDatas.forEach(searchResponseData -> {
	                deviceBeans.forEach(deviceBean ->{
	                    if (deviceBean.getDeviceId().equals(searchResponseData.getSource().getCameraId())){
	                        searchResponseData.getSource().setDeviceBean(deviceBean);
	                    }
	                });
	                if(searchResponseData.getSource().getDeviceBean()==null){
	                    DeviceBean deviceBean = new DeviceBean();
	                    deviceBean.setDeviceName("未命名");
	                    searchResponseData.getSource().setDeviceBean(deviceBean);
	                }
	            });
	            trackBean.setPictureList(searchResponseDatas);
	        }
	       folderBean.setTrackList(trackBeans);
	    }
        String responStr = JSON.toJSONString(ResultVo.success(folderBean), new SerializerFeature[] {SerializerFeature.DisableCircularReferenceDetect,
        					SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullListAsEmpty});
        ResultVo resultVo = JSON.parseObject(responStr, new TypeReference<ResultVo>() {
        });
        resultVo.setTotal(Long.valueOf(pictureBeanss.size()));
        return resultVo;
    }


    @Override
    public Map<String, Object> getPicById(String id) {
        return null;
    }
}
