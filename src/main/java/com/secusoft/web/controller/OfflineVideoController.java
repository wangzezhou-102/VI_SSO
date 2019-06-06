package com.secusoft.web.controller;

import java.util.List;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.secusoft.web.core.common.Constants;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.core.exception.BussinessException;
import com.secusoft.web.core.util.ResponseUtil;
import com.secusoft.web.model.OfflineVideoBean;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.OfflineVideoService;

/**
 * 离线视频接口
 * @author ChenDong
 * @company 视在数科
 * @date 2019年6月6日
 */
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@RequestMapping("/offlinevideo")
public class OfflineVideoController {

    @Autowired
    private OfflineVideoService offlineVideoService;
    
    /**
     * 增加离线视频
     * @author ChenDong
     * @date 2019年6月6日
     * @param bean
     * @return
     */
    @PostMapping("/addvideo")
    public ResponseEntity<ResultVo> addOfflineVideo(@RequestBody OfflineVideoBean bean) {
        if(!bean.validate()) {
            throw new BussinessException(BizExceptionEnum.PARAM_ERROR);
        }
        ResultVo result = new ResultVo();
        result = offlineVideoService.addOfflineVideo(bean);
        return ResponseUtil.handle(Constants.SUCCESS, result);
    }
    
    /**
     * 删除离线视频
     * @author ChenDong
     * @date 2019年6月6日
     * @param ids
     * @return
     */
    @PostMapping("/delvideo")
    public ResponseEntity<ResultVo> delOfflineVideo(@RequestBody List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)) {
            throw new BussinessException(BizExceptionEnum.PARAM_NULL);
        }
        ResultVo result = new ResultVo();
        result = offlineVideoService.deleteOfflineVideo(ids);
        return ResponseUtil.handle(Constants.SUCCESS, result);
    }
    
    /**
     * 更新离线视频
     * @author ChenDong
     * @date 2019年6月6日
     * @param param
     * @return
     */
    @PostMapping("/updatevideo")
    public ResponseEntity<ResultVo> updateOfflineVideo(@RequestBody OfflineVideoBean param) {
        if(!param.validate()) {
            throw new BussinessException(BizExceptionEnum.PARAM_ERROR);
        }
        ResultVo result = new ResultVo();
        result = offlineVideoService.updateOfflineVideo(param);
        return ResponseUtil.handle(Constants.SUCCESS, result);
    }
    
    /**
     * 获取离线视频列表
     * @author ChenDong
     * @date 2019年6月6日
     * @param queryBean
     * @return
     */
    @PostMapping("/readvideolist")
    public ResponseEntity<ResultVo> readPointList(@RequestBody OfflineVideoBean queryBean) {
        ResultVo result = new ResultVo();
        result = offlineVideoService.readOfflineVideoByParam(queryBean);
        return ResponseUtil.handle(Constants.SUCCESS, result);
    }
}
