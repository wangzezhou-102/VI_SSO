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
import com.secusoft.web.model.OfflineFolderBean;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.OfflineFolderService;

/**
 * 离线文件接口
 * @author ChenDong
 * @company 视在数科
 * @date 2019年6月6日
 */
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@RequestMapping("/offlinefolder")
public class OfflineFolderController {

    @Autowired
    private OfflineFolderService offlineFolderService;
    
    /**
     * 增加离线文件夹
     * @author ChenDong
     * @date 2019年6月6日
     * @param bean
     * @return
     */
    @PostMapping("/addfolder")
    public ResponseEntity<ResultVo> addOfflineFolder(@RequestBody OfflineFolderBean bean) {
        if(!bean.validate()) {
            throw new BussinessException(BizExceptionEnum.PARAM_ERROR);
        }
        ResultVo result = new ResultVo();
        result = offlineFolderService.addOfflineFolder(bean);
        return ResponseUtil.handle(Constants.SUCCESS, result);
    }
    
    /**
     * 删除离线文件夹
     * @author ChenDong
     * @date 2019年6月6日
     * @param ids
     * @return
     */
    @PostMapping("/delfolder")
    public ResponseEntity<ResultVo> delOfflineFolder(@RequestBody List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)) {
            throw new BussinessException(BizExceptionEnum.PARAM_NULL);
        }
        ResultVo result = new ResultVo();
        result = offlineFolderService.deleteOfflineFolder(ids);
        return ResponseUtil.handle(Constants.SUCCESS, result);
    }
    
    /**
     * 更新离线文件夹
     * @author ChenDong
     * @date 2019年6月6日
     * @param param
     * @return
     */
    @PostMapping("/updatefolder")
    public ResponseEntity<ResultVo> updateOfflineFolder(@RequestBody OfflineFolderBean param) {
        ResultVo result = new ResultVo();
        result = offlineFolderService.updateOfflineFolder(param);
        return ResponseUtil.handle(Constants.SUCCESS, result);
    }
    
    /**
     * 获取离线文件夹列表
     * @author ChenDong
     * @date 2019年6月6日
     * @param queryBean
     * @return
     */
    @PostMapping("/readfolderlist")
    public ResponseEntity<ResultVo> readFolderList(@RequestBody OfflineFolderBean queryBean) {
        ResultVo result = new ResultVo();
        result = offlineFolderService.readOfflineFolderByParam(queryBean);
        return ResponseUtil.handle(Constants.SUCCESS, result);
    }
    
    /**
     * 获取离线文件夹列表(含下级关联列表)
     * @author ChenDong
     * @date 2019年6月6日
     * @param queryBean
     * @return
     */
    @PostMapping("/readfolder")
    public ResponseEntity<ResultVo> readFolder(@RequestBody OfflineFolderBean queryBean) {
        ResultVo result = new ResultVo();
        result = offlineFolderService.readOfflineFolder(queryBean);
        return ResponseUtil.handle(Constants.SUCCESS, result);
    }
}
