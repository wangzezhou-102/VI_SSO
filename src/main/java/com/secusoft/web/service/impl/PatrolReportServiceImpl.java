package com.secusoft.web.service.impl;

import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.mapper.PatrolReportMapper;
import com.secusoft.web.model.PatrolReportBean;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.PatrolReportService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 巡逻路线service
 * @author Wangzezhou
 * @company 视在数科
 * @date 2019年7月12日
 */
@Service
public class PatrolReportServiceImpl implements PatrolReportService {
    @Resource
    private PatrolReportMapper patrolReportMapper;

    @Override
    public ResultVo selectPatrolReport(PatrolReportBean patrolReportBean) {
        if(patrolReportBean.getTaskId() == null){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(),BizExceptionEnum.PARAM_NULL.getMessage());
        }
        PatrolReportBean patrolReportBean1 = patrolReportMapper.selectPatrolReport(patrolReportBean.getTaskId());
        return ResultVo.success(patrolReportBean1);
    }

    @Override
    public ResultVo updatePatrolReport(PatrolReportBean patrolReportBean) { return null; }
}
