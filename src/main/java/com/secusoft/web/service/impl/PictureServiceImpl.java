package com.secusoft.web.service.impl;

import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.mapper.DeviceMapper;
import com.secusoft.web.mapper.PictureMapper;
import com.secusoft.web.model.Device;
import com.secusoft.web.model.Picture;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.PictureService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Service
public class PictureServiceImpl implements PictureService {
    @Resource
    PictureMapper pictureMapper;

    @Resource
    DeviceMapper deviceMapper;

    @Override
    public ResultVo addPicture(Picture picture) {
        if (picture==null){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        //图片收藏type为0 轨迹内的图type为1
        picture.setPicType(0);
        pictureMapper.insertPicture(picture);

        return ResultVo.success();
    }

    @Override
    public ResultVo removePicture(String id) {
        if(StringUtils.isEmpty(id)){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        pictureMapper.deletePictureById(id);
        return ResultVo.success();
    }

    @Override
    public ResultVo getPictureById(String id) {
        if(StringUtils.isEmpty(id)){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        Picture picture = pictureMapper.selectPictureById(id);
        Device device = deviceMapper.selectDeviceById(picture.getDeviceId());
        picture.setDevice(device);
        return ResultVo.success(picture);
    }
}
