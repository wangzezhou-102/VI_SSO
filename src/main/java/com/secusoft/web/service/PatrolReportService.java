package com.secusoft.web.service;

import com.secusoft.web.model.PatrolReportBean;
import com.secusoft.web.model.ResultVo;

import javax.servlet.http.HttpServletResponse;

/**
 * 巡逻路线service
 * @author Wangzezhou
 * @company 视在数科
 * @date 2019年7月12日
 */
public interface PatrolReportService {
    //查询一条巡逻任务报告
    ResultVo selectPatrolReport(PatrolReportBean patrolReportBean);
    //修改巡逻任务报告
    ResultVo updatePatrolReport(PatrolReportBean patrolReportBean);
    //添加报告
    ResultVo insertPatrolReport(HttpServletResponse response, PatrolReportBean patrolReportBean);
}
