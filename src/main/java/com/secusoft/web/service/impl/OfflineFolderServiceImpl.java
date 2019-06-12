package com.secusoft.web.service.impl;

import java.util.List;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.mapper.OfflineFolderMapper;
import com.secusoft.web.model.OfflineFolderBean;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.OfflineFolderService;

/**
 * 离线文件夹
 * @author ChenDong
 * @company 视在数科
 * @date 2019年6月6日
 */
@Service
public class OfflineFolderServiceImpl implements OfflineFolderService{

    @Autowired
    private OfflineFolderMapper offlineFolderMapper;
    
    @Override
    public ResultVo addOfflineFolder(OfflineFolderBean offlineFolderBean) {
        OfflineFolderBean queryBean = new OfflineFolderBean();
        queryBean.setClassifyName(offlineFolderBean.getClassifyName());
        long count = offlineFolderMapper.readOfflineFolderCountByParam(queryBean);
        if(count>0) {
            return ResultVo.failure(BizExceptionEnum.REPEAT);
        }
        offlineFolderMapper.insert(offlineFolderBean);
        return ResultVo.success();
    }

    @Override
    public ResultVo deleteOfflineFolder(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)) {
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL);
        }
        // 单数据直接删除一条数据
        if(ids.size() == 1) {
            offlineFolderMapper.deleteById(ids.get(0));
        } else {
            offlineFolderMapper.deleteBatchIds(ids);
        }
        return ResultVo.success();
    }

    @Override
    public ResultVo updateOfflineFolder(OfflineFolderBean offlineFolderBean) {
        OfflineFolderBean queryBean = new OfflineFolderBean();
        queryBean.setIdn(offlineFolderBean.getId());
        queryBean.setClassifyName(offlineFolderBean.getClassifyName());
        long count = offlineFolderMapper.readOfflineFolderCountByParam(queryBean);
        if(count>0) {
            return ResultVo.failure(BizExceptionEnum.REPEAT);
        }
        offlineFolderMapper.updateById(offlineFolderBean);
        return ResultVo.success();
    }

    @Override
    public ResultVo readOfflineFolderByParam(OfflineFolderBean queryBean) {
        List<OfflineFolderBean> list = offlineFolderMapper.readOfflineFolderListByParam(queryBean);
        return ResultVo.success(list);
    }

    /**
     * 初始化查询关联下级列表
     */
    @Override
    public ResultVo readOfflineFolder(OfflineFolderBean queryBean) {
        List<OfflineFolderBean> list = offlineFolderMapper.readOfflineFolder(queryBean);
        return ResultVo.success(list);
    }

}
