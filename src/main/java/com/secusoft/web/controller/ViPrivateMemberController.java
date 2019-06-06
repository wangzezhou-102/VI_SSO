package com.secusoft.web.controller;

import com.secusoft.web.Service.ViPrivateMemberService;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.ViPrivateMemberBean;
import com.secusoft.web.model.ViPrivateMemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自定义布控库
 * @author chjiang
 * @since 2019/6/6 14:47
 */
@RestController
public class ViPrivateMemberController {

    @Autowired
    ViPrivateMemberService viPrivateMemberService;

    @RequestMapping("/insertviprivatemember")
    public ResponseEntity<ResultVo> insertViPrivateMember(@RequestBody ViPrivateMemberBean viPrivateMemberBean){
        ResultVo resultVo = viPrivateMemberService.insertViPrivateMember(viPrivateMemberBean);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    @RequestMapping("/updateviprivatemember")
    public ResponseEntity<ResultVo> updateViPrivateMember(@RequestBody ViPrivateMemberBean viPrivateMemberBean){
        ResultVo resultVo = viPrivateMemberService.updateViPrivateMember(viPrivateMemberBean);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    @RequestMapping("/delviprivatemember")
    public ResponseEntity<ResultVo> delViPrivateMember(@RequestBody ViPrivateMemberBean viPrivateMemberBean){
        ResultVo resultVo = viPrivateMemberService.delViPrivateMember(viPrivateMemberBean.getId());
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    @RequestMapping("/listviprivatemember")
    public ResponseEntity<ResultVo> listViPrivateMember(@RequestBody ViPrivateMemberVo viPrivateMemberVo){
        return  new ResponseEntity<ResultVo>(viPrivateMemberService.getAllViPrivateMember(viPrivateMemberVo), HttpStatus.OK);
    }
}
