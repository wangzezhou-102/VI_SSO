package com.secusoft.web.controller;

import com.secusoft.web.model.ResultVo;
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
public class BlueScreenController {

    /**
     * 查询蓝色大屏视频应用相关数据
     * @author hbxing
     * @date 2019年6月18日
     */
    @PostMapping("/readVideoApplication")
    public ResponseEntity<ResultVo> readVideoApplication() {
        return null;
    }
}
