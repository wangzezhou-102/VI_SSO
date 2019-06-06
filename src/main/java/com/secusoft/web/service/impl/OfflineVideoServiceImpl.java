package com.secusoft.web.service.impl;

import java.util.List;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.mapper.OfflineVideoMapper;
import com.secusoft.web.model.OfflineVideoBean;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.OfflineVideoService;

/**
 * 离线视频
 * @author ChenDong
 * @company 视在数科
 * @date 2019年6月6日
 */
@Service
public class OfflineVideoServiceImpl implements OfflineVideoService{

    @Autowired
    private OfflineVideoMapper offlineVideoMapper;
    
    @Override
    public ResultVo addOfflineVideo(OfflineVideoBean offlineVideoBean) {
        OfflineVideoBean queryBean = new OfflineVideoBean();
        queryBean.setVideoName(offlineVideoBean.getVideoName());
        long count = offlineVideoMapper.readOfflineVideoCountByParam(queryBean);
        if(count>0) {
            return ResultVo.failure(BizExceptionEnum.REPEAT);
        }
        offlineVideoMapper.insert(offlineVideoBean);
        return ResultVo.success();
    }

    @Override
    public ResultVo deleteOfflineVideo(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)) {
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL);
        }
        // 单数据直接删除一条数据
        if(ids.size() == 1) {
            offlineVideoMapper.deleteById(ids.get(0));
        } else {
            offlineVideoMapper.deleteBatchIds(ids);
        }
        return ResultVo.success();
    }

    @Override
    public ResultVo updateOfflineVideo(OfflineVideoBean offlineVideoBean) {
        OfflineVideoBean queryBean = new OfflineVideoBean();
        queryBean.setIdn(offlineVideoBean.getId());
        queryBean.setVideoName(offlineVideoBean.getVideoName());
        long count = offlineVideoMapper.readOfflineVideoCountByParam(queryBean);
        if(count>0) {
            return ResultVo.failure(BizExceptionEnum.REPEAT);
        }
        offlineVideoMapper.updateById(offlineVideoBean);
        return ResultVo.success();
    }

    @Override
    public ResultVo readOfflineVideoByParam(OfflineVideoBean queryBean) {
        List<OfflineVideoBean> list = offlineVideoMapper.readOfflineVideoListByParam(queryBean);
        return ResultVo.success(list);
    }
    
}
