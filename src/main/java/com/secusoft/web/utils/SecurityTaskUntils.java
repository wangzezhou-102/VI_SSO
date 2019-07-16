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

//    public static boolean validTaskBeginTime(SecurityTimeMapper securityTimeMapper, SecurityTaskRequest securityTaskRequest) {
//        if (1 == securityTaskRequest.getEnable()) {
//            return true;
//        }
//        List<SecurityTimeBean> securityTimeBeans = securityTimeMapper.selectTimeByTaskId(securityTaskRequest.getTaskId());
//        for (SecurityTimeBean securityTimeBean:securityTimeBeans){
//            if (securityTimeBean.getBeginTime().equals(securityTaskRequest.getBeginTime())){
//                return true;
//            }
//        }
//        long diff = new Date().getTime() - securityTaskRequest.getBeginTime().getTime();
//        long sec = diff % nd % nh % nm / ns;// 计算差多少秒
//        //if(Math.abs(Integer.valueOf(String.valueOf(sec)))>5){
//        //若和当前时间相差时间大于0秒，则不执行
//        if (sec != 0) {
//            return false;
//        }
//        return true;
//    }

    public static boolean validTaskEndTime(ViSurveyTaskMapper viSurveyTaskMapper, ViSurveyTaskBean viSurveyTaskBean) {
        if (1 == viSurveyTaskBean.getEnable()) {
            return true;
        }
        ViSurveyTaskBean viSurveyTask = viSurveyTaskMapper.getViSurveyTaskById(viSurveyTaskBean);
        long diff = new Date().getTime() - viSurveyTask.getEndTime().getTime();
        long sec = diff % nd % nh % nm / ns;// 计算差多少秒
        //if(Math.abs(Integer.valueOf(String.valueOf(sec)))>5){
        //若和当前时间相差时间大于0秒，则不执行
        if (sec != 0) {
            return false;
        }
        return true;
    }

//    public static boolean securityTaskStreamBeginTime(SecurityTimeMapper securityTimeMapper, SecurityTaskRequest securityTaskRequest) {
//        if (1 == securityTaskRequest.getEnable()) {
//            return true;
//        }
//        ViSurveyTaskBean viSurveyTask = viSurveyTaskMapper.getViSurveyTaskById(viSurveyTaskBean);
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(viSurveyTask.getBeginTime());
//        //设备提前5分钟启动码流计划任务
//        calendar.add(Calendar.MINUTE, Integer.parseInt("-" + NormalConfig.getStreamMinute()));
//        long diff = new Date().getTime() - calendar.getTime().getTime();
//        long sec = diff % nd % nh % nm / ns;// 计算差多少秒
//        //if(Math.abs(Integer.valueOf(String.valueOf(sec)))>5){
//        //若和当前时间相差时间大于3秒，则不执行
//        if (sec != 0) {
//            return false;
//        }
//        return true;
//    }


    public static boolean validTaskStreamEndTime(ViSurveyTaskMapper viSurveyTaskMapper, ViSurveyTaskBean viSurveyTaskBean) {
        if (1 == viSurveyTaskBean.getEnable()) {
            return true;
        }
        ViSurveyTaskBean viSurveyTask = viSurveyTaskMapper.getViSurveyTaskById(viSurveyTaskBean);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(viSurveyTask.getEndTime());
        //设备提前5分钟启动码流计划任务
        calendar.add(Calendar.MINUTE, Integer.parseInt("+" + NormalConfig.getStreamMinute()));
        long diff = new Date().getTime() - calendar.getTime().getTime();
        long sec = diff % nd % nh % nm / ns;// 计算差多少秒
        //if(Math.abs(Integer.valueOf(String.valueOf(sec)))>5){
        //若和当前时间相差时间大于0秒，则不执行
        if (sec != 0) {
            return false;
        }
        return true;
    }
}
