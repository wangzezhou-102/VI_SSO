package com.secusoft.web.controller;

import com.secusoft.web.core.common.Constants;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.core.exception.BussinessException;
import com.secusoft.web.core.util.PdfUtil;
import com.secusoft.web.core.util.ResponseUtil;
import com.secusoft.web.model.PatrolAlarmVo;
import com.secusoft.web.model.PatrolReportBean;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.PatrolReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * 巡逻任务
 * @author Wangzezhou
 * @company 视在数科
 * @date 2019年7月12日
 */
@RestController
@CrossOrigin(value = "*",maxAge = 3600)
public class PatrolReportController {
    @Autowired
    private PatrolReportService patrolReportService;
    //获取巡逻报告信息
    @PostMapping("/getpatrolreport")
    public ResponseEntity getPatrolReport(@RequestBody PatrolReportBean patrolReportBean){//巡逻任务id taskId
        System.out.println(patrolReportBean.getTaskId());
        ResultVo resultVo = patrolReportService.selectPatrolReport(patrolReportBean);
        System.out.println(resultVo);
        return ResponseUtil.handle(Constants.OK, resultVo);
    }
    //生成巡逻报告
    @GetMapping("/createpatrolreport")
    public ResponseEntity createPatrolReport(HttpServletResponse response, PatrolAlarmVo patrolAlarmVo){
        PdfUtil pdfUtil = new PdfUtil();
        try{
            pdfUtil.turnToPdf(response, patrolAlarmVo);
            System.out.println("生成巡逻报告");
        }catch(Exception e) {
            throw new BussinessException(BizExceptionEnum.SERVER_ERROR);
        }
        return ResponseUtil.handle(Constants.OK, null);
    }

    @PostMapping("/updatepatrolreport")
    public ResponseEntity updatePatrolReport(@RequestBody PatrolReportBean patrolReportBean){

        return null;
    }

    @PostMapping("addpatrolreport")
    public ResponseEntity addPatrolReport(@RequestBody PatrolReportBean patrolReportBean){

        return null;
    }

}
