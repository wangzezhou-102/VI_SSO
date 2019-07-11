package com.secusoft.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.mapper.*;
import com.secusoft.web.model.*;
import com.secusoft.web.service.FolderService;
import com.secusoft.web.tusouapi.model.SearchResponse;
import com.secusoft.web.tusouapi.model.SearchResponseData;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FolderServiceImpl implements FolderService {
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

    @Override
    public ResultVo setFolderName(FolderBean folderBean) {
        if(folderBean == null || StringUtils.isEmpty(folderBean.getFolderName())){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        if(folderMapper.selectCountFolderByObj(folderBean)==0){
            folderMapper.updateNonEmptyFolderById(folderBean);
        }else{
            return ResultVo.failure(BizExceptionEnum.REPEAT.getCode(),BizExceptionEnum.REPEAT.getMessage());
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
    public ResultVo getFolder(String fid) {
        FolderBean folderBean = folderMapper.selectOneFolder(fid);
        List<PictureBean> pictureBeans = pictureMapper.selectPictureByFid(fid);

        List<TrackBean> trackBeans = trackMapper.selectTrackByFid(fid);
        List<AreaBean> areaBeans = areaMapper.selectAreaByFid(fid);
        if (!pictureBeans.isEmpty()){
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
        return resultVo;
    }


    @Override
    public Map<String, Object> getPicById(String id) {
        return null;
    }
}
