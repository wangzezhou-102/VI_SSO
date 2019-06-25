package com.secusoft.web.controller;

import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.ViBasicMemberBean;
import com.secusoft.web.model.ViBasicMemberVo;
import com.secusoft.web.service.ViBasicMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chjiang
 * @since 2019/6/10 16:56
 */
@RestController
public class ViBasicMemberController {

    @Autowired
    ViBasicMemberService viBasicMemberService;

    /**
     * 是否关注该布控
     *
     * @param viBasicMemberBean
     * @return
     */
    @RequestMapping("/updatefocusmenber")
    public ResponseEntity<ResultVo> updateFocusMenber(@RequestBody ViBasicMemberBean viBasicMemberBean) {
        ResultVo resultVo = viBasicMemberService.updateFocusMenber(viBasicMemberBean);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    @RequestMapping("/listvibasicmember")
    public ResponseEntity<ResultVo> listViBasicMember(ViBasicMemberVo viBasicMemberVo) {
        return new ResponseEntity<ResultVo>(viBasicMemberService.getPagedViBasicMember(viBasicMemberVo), HttpStatus.OK);
    }
}
