package com.secusoft.web.controller;

import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.ViPrivateMemberBean;
import com.secusoft.web.model.ViPrivateMemberVo;
import com.secusoft.web.service.ViPrivateMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自定义布控库
 *
 * @author chjiang
 * @since 2019/6/6 14:47
 */
@RestController
public class ViPrivateMemberController {

    @Autowired
    ViPrivateMemberService viPrivateMemberService;

    @PostMapping("/insertviprivatemember")
    public ResponseEntity<ResultVo> insertViPrivateMember(@RequestBody ViPrivateMemberBean viPrivateMemberBean) {
        ResultVo resultVo = null;
        try {
            resultVo = viPrivateMemberService.insertViPrivateMember(viPrivateMemberBean);
        } catch (Exception ex) {
            resultVo = ResultVo.failure(BizExceptionEnum.BKMEMBER_ADD_FAIL.getCode(),
                    BizExceptionEnum.BKMEMBER_ADD_FAIL.getMessage());
        }
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    @PostMapping("/updateviprivatemember")
    public ResponseEntity<ResultVo> updateViPrivateMember(@RequestBody ViPrivateMemberBean viPrivateMemberBean) {
        ResultVo resultVo = null;
        try {
            resultVo = viPrivateMemberService.updateViPrivateMember(viPrivateMemberBean);
        } catch (Exception ex) {
            ResultVo.failure(BizExceptionEnum.BKMEMBER_UPDATE_FAIL.getCode(),
                    BizExceptionEnum.BKMEMBER_UPDATE_FAIL.getMessage());
        }
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    @PostMapping(value = "/delviprivatemember")
    public ResponseEntity<ResultVo> delViPrivateMember(@RequestBody ViPrivateMemberVo viPrivateMemberVo) {
        ResultVo resultVo = viPrivateMemberService.delViPrivateMember(viPrivateMemberVo);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    @PostMapping("/listviprivatemember")
    public ResponseEntity<ResultVo> listViPrivateMember(@RequestBody ViPrivateMemberVo viPrivateMemberVo) {
        return new ResponseEntity<ResultVo>(viPrivateMemberService.getAllViPrivateMember(viPrivateMemberVo), HttpStatus.OK);
    }

    @PostMapping("/uploadimg")
    public ResponseEntity<ResultVo> uploadImg(@RequestBody ViPrivateMemberVo viPrivateMemberVo){
        return new ResponseEntity<ResultVo>(viPrivateMemberService.uploadImg(viPrivateMemberVo), HttpStatus.OK);
    }

    @PostMapping("/listinfosearchmember")
    public ResponseEntity<ResultVo> listInfoSearchMember(@RequestBody ViPrivateMemberVo viPrivateMemberVo) {
        return new ResponseEntity<ResultVo>(viPrivateMemberService.getInfoSearchMember(viPrivateMemberVo), HttpStatus.OK);
    }

}
