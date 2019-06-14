package com.secusoft.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.mapper.AreaMapper;
import com.secusoft.web.mapper.PictureMapper;
import com.secusoft.web.mapper.TrackMapper;
import com.secusoft.web.model.*;
import com.secusoft.web.service.FolderService;
import com.secusoft.web.mapper.FolderMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
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
        return ResultVo.success();
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
    public ResultVo getFolderByName(String name,Integer status) {
        List<FolderBean> folderBeans = folderMapper.selectFolderByName(name,status);
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
        folderBean.setImageSearchList(pictureBeans);
        folderBean.setDeviceArea(areaBeans);
        folderBean.setTrackList(trackBeans);

        String responStr = JSON.toJSONString(ResultVo.success(folderBean), SerializerFeature.DisableCircularReferenceDetect);
        ResultVo resultVo = JSON.parseObject(responStr, new TypeReference<ResultVo>() {
        });
        return resultVo;
    }


    @Override
    public Map<String, Object> getPicById(String id) {
        return null;
    }
}
