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
import com.secusoft.web.model.OfflinePointBean;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.OfflinePointService;

/**
 * 离线点位接口
 * @author ChenDong
 * @company 视在数科
 * @date 2019年6月6日
 */
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@RequestMapping("/offlinepoint")
public class OfflinePointController {

    @Autowired
    private OfflinePointService offlinePointService;
    
    /**
     * 增加离线点位
     * @author ChenDong
     * @date 2019年6月6日
     * @param bean
     * @return
     */
    @PostMapping("/addpoint")
    public ResponseEntity<ResultVo> addOfflinePoint(@RequestBody OfflinePointBean bean) {
        if(!bean.validate()) {
            throw new BussinessException(BizExceptionEnum.PARAM_ERROR);
        }
        ResultVo result = new ResultVo();
        result = offlinePointService.addOfflinePoint(bean);
        return ResponseUtil.handle(Constants.SUCCESS, result);
    }
    
    /**
     * 删除离线点位
     * @author ChenDong
     * @date 2019年6月6日
     * @param ids
     * @return
     */
    @PostMapping("/delpoint")
    public ResponseEntity<ResultVo> delOfflinePoint(@RequestBody List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)) {
            throw new BussinessException(BizExceptionEnum.PARAM_NULL);
        }
        ResultVo result = new ResultVo();
        result = offlinePointService.deleteOfflinePoint(ids);
        return ResponseUtil.handle(Constants.SUCCESS, result);
    }
    
    /**
     * 更新离线点位
     * @author ChenDong
     * @date 2019年6月6日
     * @param param
     * @return
     */
    @PostMapping("/updatepoint")
    public ResponseEntity<ResultVo> updateOfflinePoint(@RequestBody OfflinePointBean param) {
        ResultVo result = new ResultVo();
        result = offlinePointService.updateOfflinePoint(param);
        return ResponseUtil.handle(Constants.SUCCESS, result);
    }
    
    /**
     * 获取离线点位列表
     * @author ChenDong
     * @date 2019年6月6日
     * @param queryBean
     * @return
     */
    @PostMapping("/readpointlist")
    public ResponseEntity<ResultVo> readPointList(@RequestBody OfflinePointBean queryBean) {
        ResultVo result = new ResultVo();
        result = offlinePointService.readOfflinePointByParam(queryBean);
        return ResponseUtil.handle(Constants.SUCCESS, result);
    }
}
