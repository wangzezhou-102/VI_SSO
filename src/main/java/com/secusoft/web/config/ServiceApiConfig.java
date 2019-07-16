package com.secusoft.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author chjiang
 * @since 2019/6/11 16:23
 */
@Component
@ConfigurationProperties(prefix = "ServiceApi")
public class ServiceApiConfig {

    /**
     * 布控库信息查询
     */
    public static String pathBkrepoMeta;

    /**
     * 布控库创建
     */
    public static String pathBkrepoCreate;

    /**
     * 布控目标添加
     */
    public static String pathBkmemberAdd;

    /**
     * 布控目标删除
     */
    public static String pathBkmemberDelete;

    /**
     * 布控任务创建、更新
     */
    public static String pathBktaskSubmit;

    /**
     * 布控任务删除
     */
    public static String pathBktaskDelete;

    /**
     * 布控任务启动
     */
    public static String pathBktaskStart;

    /**
     * 布控任务停止
     */
    public static String pathBktaskStop;

    /**
     * 设备启流
     */
    public static String streamStart;

    /**
     * 设备停流
     */
    public static String streamStop;

    /**
     * 获取缓存里的布控告警数据
     */
    public static String getViPsurveyAlarm;
    /**
     * 获取缓存里的巡逻告警数据
     */
    public static String getPatrolAlarm;

    /**
     * 重点人员库
     */
    public static String viewZdryList;

    public static String tusouSearch;

    public static String getGetPatrolAlarm() { return getPatrolAlarm; }

    public static void setGetPatrolAlarm(String getPatrolAlarm) { ServiceApiConfig.getPatrolAlarm = getPatrolAlarm; }

    public static String getPathBkrepoMeta() {
        return pathBkrepoMeta;
    }

    public static void setPathBkrepoMeta(String pathBkrepoMeta) {
        ServiceApiConfig.pathBkrepoMeta = pathBkrepoMeta;
    }

    public static String getPathBkrepoCreate() {
        return pathBkrepoCreate;
    }

    public static void setPathBkrepoCreate(String pathBkrepoCreate) {
        ServiceApiConfig.pathBkrepoCreate = pathBkrepoCreate;
    }

    public static String getPathBkmemberAdd() {
        return pathBkmemberAdd;
    }

    public static void setPathBkmemberAdd(String pathBkmemberAdd) {
        ServiceApiConfig.pathBkmemberAdd = pathBkmemberAdd;
    }

    public static String getPathBkmemberDelete() {
        return pathBkmemberDelete;
    }

    public static void setPathBkmemberDelete(String pathBkmemberDelete) {
        ServiceApiConfig.pathBkmemberDelete = pathBkmemberDelete;
    }

    public static String getPathBktaskSubmit() {
        return pathBktaskSubmit;
    }

    public static void setPathBktaskSubmit(String pathBktaskSubmit) {
        ServiceApiConfig.pathBktaskSubmit = pathBktaskSubmit;
    }

    public static String getPathBktaskDelete() {
        return pathBktaskDelete;
    }

    public static void setPathBktaskDelete(String pathBktaskDelete) {
        ServiceApiConfig.pathBktaskDelete = pathBktaskDelete;
    }

    public static String getPathBktaskStart() {
        return pathBktaskStart;
    }

    public static void setPathBktaskStart(String pathBktaskStart) {
        ServiceApiConfig.pathBktaskStart = pathBktaskStart;
    }

    public static String getPathBktaskStop() {
        return pathBktaskStop;
    }

    public static void setPathBktaskStop(String pathBktaskStop) {
        ServiceApiConfig.pathBktaskStop = pathBktaskStop;
    }

    public static String getStreamStart() {
        return streamStart;
    }

    public static void setStreamStart(String streamStart) {
        ServiceApiConfig.streamStart = streamStart;
    }

    public static String getStreamStop() {
        return streamStop;
    }

    public static void setStreamStop(String streamStop) {
        ServiceApiConfig.streamStop = streamStop;
    }

    public static String getGetViPsurveyAlarm() {
        return getViPsurveyAlarm;
    }

    public static void setGetViPsurveyAlarm(String getViPsurveyAlarm) {
        ServiceApiConfig.getViPsurveyAlarm = getViPsurveyAlarm;
    }

    public static String getViewZdryList() {
        return viewZdryList;
    }

    public static void setViewZdryList(String viewZdryList) {
        ServiceApiConfig.viewZdryList = viewZdryList;
    }

    public static String getTusouSearch() {
        return tusouSearch;
    }

    public static void setTusouSearch(String tusouSearch) {
        ServiceApiConfig.tusouSearch = tusouSearch;
    }
}
