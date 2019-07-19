package com.secusoft.web.utils;

import com.secusoft.web.config.NormalConfig;
import com.secusoft.web.mapper.SecurityTimeMapper;
import com.secusoft.web.mapper.ViSurveyTaskMapper;
import com.secusoft.web.model.SecurityTaskRequest;
import com.secusoft.web.model.SecurityTimeBean;
import com.secusoft.web.model.ViSurveyTaskBean;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SecurityTaskUntils {

    private static long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
    private static long nh = 1000 * 60 * 60;// 一小时的毫秒数
    private static long nm = 1000 * 60;// 一分钟的毫秒数
    private static long ns = 1000;// 一秒钟的毫秒数

    public static boolean securityTaskBeginTime(SecurityTimeMapper securityTimeMapper, SecurityTaskRequest securityTaskRequest) {
        List<SecurityTimeBean> securityTimeBeans = securityTimeMapper.selectTimeByTaskId(securityTaskRequest.getTaskId());

        long timeInMillis = Calendar.getInstance().getTimeInMillis();
        //时间与库中时间一致
        for (SecurityTimeBean securityTimeBean:securityTimeBeans){
            if(securityTimeBean.getId().equals(securityTaskRequest.getId())){
                Long beginTime = securityTimeBean.getBeginTime();
                Long time=beginTime-timeInMillis;
                if(time == 0){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean securityTaskEndTime(SecurityTimeMapper securityTimeMapper, SecurityTaskRequest securityTaskRequest) {
        List<SecurityTimeBean> securityTimeBeans = securityTimeMapper.selectTimeByTaskId(securityTaskRequest.getTaskId());

        long timeInMillis = Calendar.getInstance().getTimeInMillis();
        //时间与库中时间一致
        for (SecurityTimeBean securityTimeBean:securityTimeBeans){
            if(securityTimeBean.getId().equals(securityTaskRequest.getId())){
                Long endTime = securityTimeBean.getEndTime();
                Long time=endTime-timeInMillis;
                if(time == 0){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean securityTaskStreamBeginTime(SecurityTimeMapper securityTimeMapper, SecurityTaskRequest securityTaskRequest) {

        List<SecurityTimeBean> securityTimeBeans = securityTimeMapper.selectTimeByTaskId(securityTaskRequest.getTaskId());

        long timeInMillis = Calendar.getInstance().getTimeInMillis();
        //时间与库中时间一致
        for (SecurityTimeBean securityTimeBean:securityTimeBeans){
            if(securityTimeBean.getId().equals(securityTaskRequest.getId())){
                Long beginTime = securityTimeBean.getBeginTime();
                Long time=beginTime-timeInMillis-5000;
                if(time == 0){
                    return true;
                }
            }
        }
        return false;
    }


    public static boolean securityTaskStreamEndTime(SecurityTimeMapper securityTimeMapper, SecurityTaskRequest securityTaskRequest) {
        List<SecurityTimeBean> securityTimeBeans = securityTimeMapper.selectTimeByTaskId(securityTaskRequest.getTaskId());

        long timeInMillis = Calendar.getInstance().getTimeInMillis();
        //时间与库中时间一致
        for (SecurityTimeBean securityTimeBean:securityTimeBeans){
            if(securityTimeBean.getId().equals(securityTaskRequest.getId())){
                Long endTime = securityTimeBean.getEndTime();
                Long time=endTime-timeInMillis-5000;
                if(time == 0){
                    return true;
                }
            }
        }
        return false;
    }
}
