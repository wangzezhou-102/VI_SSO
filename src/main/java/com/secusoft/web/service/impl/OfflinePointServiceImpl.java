package com.secusoft.web.service.impl;

import java.util.List;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.mapper.OfflinePointMapper;
import com.secusoft.web.model.OfflinePointBean;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.OfflinePointService;

/**
 * 离线点位
 * @author ChenDong
 * @company 视在数科
 * @date 2019年6月6日
 */
@Service
public class OfflinePointServiceImpl implements OfflinePointService{

    @Autowired
    private OfflinePointMapper offlinePointMapper;
    
    @Override
    public ResultVo addOfflinePoint(OfflinePointBean offlinePointBean) {
        OfflinePointBean queryBean = new OfflinePointBean();
        queryBean.setPointName(offlinePointBean.getPointName());
        long count = offlinePointMapper.readOfflinePointCountByParam(queryBean);
        if(count>0) {
            return ResultVo.failure(BizExceptionEnum.REPEAT);
        }
        offlinePointMapper.insert(offlinePointBean);
        return ResultVo.success();
    }

    @Override
    public ResultVo deleteOfflinePoint(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)) {
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL);
        }
        // 单数据直接删除一条数据
        if(ids.size() == 1) {
            offlinePointMapper.deleteById(ids.get(0));
        } else {
            offlinePointMapper.deleteBatchIds(ids);
        }
        return ResultVo.success();
    }

    @Override
    public ResultVo updateOfflinePoint(OfflinePointBean offlinePointBean) {
        OfflinePointBean queryBean = new OfflinePointBean();
        queryBean.setIdn(offlinePointBean.getId());
        queryBean.setPointName(offlinePointBean.getPointName());
        long count = offlinePointMapper.readOfflinePointCountByParam(queryBean);
        if(count>0) {
            return ResultVo.failure(BizExceptionEnum.REPEAT);
        }
        offlinePointMapper.updateById(offlinePointBean);
        return ResultVo.success();
    }

    @Override
    public ResultVo readOfflinePointByParam(OfflinePointBean queryBean) {
        List<OfflinePointBean> list = offlinePointMapper.readOfflinePointListByParam(queryBean);
        return ResultVo.success(list);
    }
    
}
