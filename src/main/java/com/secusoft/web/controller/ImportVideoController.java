package com.secusoft.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.secusoft.web.core.common.Constants;
import com.secusoft.web.config.ImportVideoConfig;
import com.secusoft.web.core.util.ResponseUtil;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.ImportVideoService;

/**
 * 导入视频对接Controller
 * @author ChenDong
 * @company 视在数科
 * @date 2019年6月11日
 */
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
public class ImportVideoController {

    @Autowired
    private ImportVideoService importVideoService;
    
    /**
     * 上传文件切片
     * @author ChenDong
     * @date 2019年6月11日
     * @return
     */
    @RequestMapping("/chunk")
    public ResponseEntity<ResultVo> chunk() {
        System.out.println(ImportVideoConfig.chunk);
        ResultVo result = new ResultVo();
        result = importVideoService.chunk();
        return ResponseUtil.handle(Constants.OK, result);
    }
    
    /**
     * 切片合并
     * @author ChenDong
     * @date 2019年6月11日
     * @param param
     * @return
     */
    @PostMapping("/mergefile")
    public ResponseEntity<ResultVo> mergeFile(@RequestBody Map<String, Object> param) {
        ResultVo result = new ResultVo();
        result = importVideoService.mergeFile(param);
        return ResponseUtil.handle(Constants.OK, result);
    }
    
    /**
     * 创建文件夹
     * @author ChenDong
     * @date 2019年6月11日
     * @param param
     * @return
     */
    @PostMapping("/createfileclassify")
    public ResponseEntity<ResultVo> createFileClassify(@RequestBody Map<String,Object> param) {
        ResultVo result = new ResultVo();
        result = importVideoService.createFileClassify(param);
        return ResponseUtil.handle(Constants.OK, result);
    }
    
    /**
     * 修改文件夹
     * @author ChenDong
     * @date 2019年6月11日
     * @param param
     * @return
     */
    @PostMapping("/modifyfileclassify")
    public ResponseEntity<ResultVo> modifyFileClassify(@RequestBody Map<String,Object> param) {
        ResultVo result = new ResultVo();
        result = importVideoService.modifyFileClassify(param);
        return ResponseUtil.handle(Constants.OK, result);
    }
    
    /**
     * 删除文件夹
     * @author ChenDong
     * @date 2019年6月11日
     * @param param
     * @return
     */
    @PostMapping("/deletefileclassify")
    public ResponseEntity<ResultVo> deleteFileClassify(@RequestBody Map<String,Object> param) {
        ResultVo result = new ResultVo();
        result = importVideoService.deleteFileClassify(param);
        return ResponseUtil.handle(Constants.OK, result);
    }
    
    /**
     * 创建点位
     * @author ChenDong
     * @date 2019年6月11日
     * @param param
     * @return
     */
    @PostMapping("/createfilecamerarelation")
    public ResponseEntity<ResultVo> createFileCameraRelation(@RequestBody Map<String,Object> param) {
        ResultVo result = new ResultVo();
        result = importVideoService.createFileCameraRelation(param);
        return ResponseUtil.handle(Constants.OK, result);
    }
    
    /**
     * 修改点位
     * @author ChenDong
     * @date 2019年6月11日
     * @param param
     * @return
     */
    @PostMapping("/modifyfilecamerarelation")
    public ResponseEntity<ResultVo> modifyFileCameraRelation(@RequestBody Map<String,Object> param) {
        ResultVo result = new ResultVo();
        result = importVideoService.modifyFileCameraRelation(param);
        return ResponseUtil.handle(Constants.OK, result);
    }
    
    /**
     * 拖拽点位文件位置
     * @author ChenDong
     * @date 2019年6月11日
     * @param param
     * @return
     */
    @PostMapping("/dragofflinecamera")
    public ResponseEntity<ResultVo> dragOfflineCamera(@RequestBody Map<String,Object> param) {
        ResultVo result = new ResultVo();
        result = importVideoService.dragOfflineCamera(param);
        return ResponseUtil.handle(Constants.OK, result);
    }
    
    /**
     * 删除点位
     * @author ChenDong
     * @date 2019年6月11日
     * @param param
     * @return
     */
    @PostMapping("/deleteofflinecamera")
    public ResponseEntity<ResultVo> deleteOfflineCamera(Map<String,Object> param) {
        ResultVo result = new ResultVo();
        result = importVideoService.deleteOfflineCamera(param);
        return ResponseUtil.handle(Constants.OK, result);
    }
    
    /**
     * 删除视频及其结构化数据
     * @author ChenDong
     * @date 2019年6月11日
     * @param param
     * @return
     */
    @PostMapping("/deleteoffline")
    public ResponseEntity<ResultVo> deleteOffline(Map<String,Object> param) {
        ResultVo result = new ResultVo();
        result = importVideoService.deleteOffline(param);
        return ResponseUtil.handle(Constants.OK, result);
    }
    
    /**
     * 创建视频文件
     * @author ChenDong
     * @date 2019年6月11日
     * @param param
     * @return
     */
    @PostMapping("/createfilestatus")
    public ResponseEntity<ResultVo> createFileStatus(Map<String,Object> param) {
        ResultVo result = new ResultVo();
        result = importVideoService.createFileStatus(param);
        return ResponseUtil.handle(Constants.OK, result);
    }
    
    /**
     * 更新视频文件（名称和开始时间）
     * @author ChenDong
     * @date 2019年6月11日
     * @param param
     * @return
     */
    @PostMapping("/updatefilestatus")
    public ResponseEntity<ResultVo> updateFileStatus(Map<String,Object> param) {
        ResultVo result = new ResultVo();
        result = importVideoService.updateFileStatus(param);
        return ResponseUtil.handle(Constants.OK, result);
    }
    
    /**
     * 分页查询离线视频文件
     * @author ChenDong
     * @date 2019年6月11日
     * @param param
     * @return
     */
    @PostMapping("/queryfilestatus")
    public ResponseEntity<ResultVo> queryFileStatus(Map<String,Object> param) {
        ResultVo result = new ResultVo();
        result = importVideoService.queryFileStatus(param);
        return ResponseUtil.handle(Constants.OK, result);
    }
    
    /**
     * 单个删除离线文件
     * @author ChenDong
     * @date 2019年6月11日
     * @param param
     * @return
     */
    @PostMapping("/deletefilestatus")
    public ResponseEntity<ResultVo> deleteFileStatus(Map<String,Object> param) {
        ResultVo result = new ResultVo();
        result = importVideoService.deleteFileStatus(param);
        return ResponseUtil.handle(Constants.OK, result);
    }
    
    /**
     * 视频分析查询文件夹下所有点位，嵌套返回格式
     * @author ChenDong
     * @date 2019年6月11日
     * @param param
     * @return
     */
    @PostMapping("/describefileclassifys")
    public ResponseEntity<ResultVo> describeFileClassifys(Map<String,Object> param) {
        ResultVo result = new ResultVo();
        result = importVideoService.describeFileClassifys(param);
        return ResponseUtil.handle(Constants.OK, result);
    }
}
