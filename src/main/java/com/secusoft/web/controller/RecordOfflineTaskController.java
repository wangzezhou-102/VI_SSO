package com.secusoft.web.controller;

import com.secusoft.web.core.common.Constants;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.core.exception.BussinessException;
import com.secusoft.web.core.util.ResponseUtil;
import com.secusoft.web.model.RecordOfflineTaskBean;
import com.secusoft.web.model.RecordOfflineTaskRequest;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.RecordOfflineTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 离线视频分析任务
 *
 * @author zzwang
 * @since 2019/6/24 14:47
 */

@CrossOrigin(value = "*", maxAge = 3600)
@RestController
public class RecordOfflineTaskController {


    @Autowired
    RecordOfflineTaskService recordOfflineTaskService;

    @RequestMapping("/addOfflineTask")
    public ResponseEntity<ResultVo> addOfflineTask( @RequestBody RecordOfflineTaskRequest requestOfflineTaskRequest){
       if(!requestOfflineTaskRequest.validate()){
            //没有可执行设备
            throw new BussinessException(BizExceptionEnum.PARAM_NULL);
       }
        ResultVo resultVo = recordOfflineTaskService.addOfflineTask(requestOfflineTaskRequest);

        return ResponseUtil.handle(Constants.OK, resultVo);

    }

    @RequestMapping("/getOfflineTaskProgress")
    public ResponseEntity<ResultVo> getOfflineTaskProgress( @RequestBody RecordOfflineTaskBean recordOfflineTaskBean) {
        if(!recordOfflineTaskBean.getEnable().equals("0")){
            throw new BussinessException(BizExceptionEnum.PARAM_ERROR);
        }
        ResultVo resultVo = recordOfflineTaskService.getOfflineTaskProgress(recordOfflineTaskBean);
        return ResponseUtil.handle(Constants.OK, resultVo);
    }

    @RequestMapping("/enableOfflineTask")
    public ResponseEntity<ResultVo> enableOfflineTask(@RequestBody RecordOfflineTaskBean recordOfflineTaskBean){
        if(!recordOfflineTaskBean.getEnable().equals("1")){
            throw new BussinessException(BizExceptionEnum.PARAM_ERROR);
        }
        ResultVo resultVo = recordOfflineTaskService.enableOfflineTask(recordOfflineTaskBean);
        return ResponseUtil.handle(Constants.OK, resultVo);
    }

}
