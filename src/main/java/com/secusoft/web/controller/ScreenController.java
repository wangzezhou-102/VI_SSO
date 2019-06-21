package com.secusoft.web.controller;

import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.ScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 蓝色大屏相关接口
 *
 * @author hbxing
 * @date 2019/6/18
 */
@RestController
@CrossOrigin(value = "*", maxAge = 3600)
public class ScreenController {
    @Autowired
    private ScreenService screenService;

    /**
     * 查询蓝色大屏浮框 功能模块使用统计 相关数据
     * @author hbxing
     * @date 2019年6月20日
     */
    @PostMapping("/screenFloatingFunctionUseNumber")
    public ResponseEntity<ResultVo> screenFloatingFunctionUseNumber() {
        return new ResponseEntity<>(screenService.functionUseNumber(), HttpStatus.OK);
    }
    /**
     * 查询蓝色大屏浮框 视频路数统计 相关数据
     * @author hbxing
     * @date 2019年6月20日
     */
    @PostMapping("/screenFloatingVideoPathNumber")
    public ResponseEntity<ResultVo> screenFloatingVideoPathNumber(){
        return new ResponseEntity<>(screenService.videoPathNumber(),HttpStatus.OK);
    }
    /**
     * 查询蓝色大屏浮框 报警统计 相关数据
     * @author hbxing
     * @date 2019年6月20日
     */
    @PostMapping("/screenFloatingAlaramNumber")
    public ResponseEntity<ResultVo> screenFloatingAlaramNumber(){
        return new ResponseEntity<>(screenService.alaramNumber(),HttpStatus.OK);
    }
    /**
     * 查询蓝色大屏浮框 分局算力分配 相关数据
     * @author hbxing
     * @date 2019年6月20日
     */
    @PostMapping("/screenFloatingCureauDeviceDistribution")
    public ResponseEntity<ResultVo> screenFloatingCureauDeviceDistribution(){
        return new ResponseEntity<>(screenService.bureauDeviceDistribution(),HttpStatus.OK);
    }
    /**
     * 查询蓝色大屏浮框 视频巡逻统计 相关数据
     * @author hbxing
     * @date 2019年6月20日
     */
    @PostMapping("/screenFloatingVideoPatrolNumber")
    public ResponseEntity<ResultVo> screenFloatingVideoPatrolNumber(){
        return new ResponseEntity<>(screenService.videoPatrolNumber(),HttpStatus.OK);
    }
    /**
     * 查询蓝色大屏浮框 当日抓拍统计 相关数据
     * @author hbxing
     * @date 2019年6月20日
     */
    @PostMapping("/screenFloatingSnapNumberToday")
    public ResponseEntity<ResultVo> screenFloatingSnapNumberToday(){
        return new ResponseEntity<>(screenService.snapNumberToday(),HttpStatus.OK);
    }

}
