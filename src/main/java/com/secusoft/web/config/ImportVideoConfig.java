package com.secusoft.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 导入视频对接天擎配置config
 * @author ChenDong
 * @company 视在数科
 * @date 2019年6月11日
 */
@Component
@ConfigurationProperties(prefix="import.video.url")
public class ImportVideoConfig {

    public static String createFileClassify;
    public static String modifyFileClassify;
    public static String deleteFileClassify;
    public static String createFileCameraRelation;
    public static String modifyFileCameraRelation;
    public static String deleteOfflineCamera;
    public static String dragOfflineCamera;
    public static String deleteOffline;
    public static String createFileStatus;
    public static String updateFileStatus;
    public static String queryFileStatus;
    public static String deleteFileStatus;
    public static String describeFileClassifys;
    public static String chunk;
    public static String mergeFile;
    public static String getCreateFileClassify() {
        return createFileClassify;
    }
    public static void setCreateFileClassify(String createFileClassify) {
        ImportVideoConfig.createFileClassify = createFileClassify;
    }
    public static String getModifyFileClassify() {
        return modifyFileClassify;
    }
    public static void setModifyFileClassify(String modifyFileClassify) {
        ImportVideoConfig.modifyFileClassify = modifyFileClassify;
    }
    public static String getDeleteFileClassify() {
        return deleteFileClassify;
    }
    public static void setDeleteFileClassify(String deleteFileClassify) {
        ImportVideoConfig.deleteFileClassify = deleteFileClassify;
    }
    public static String getCreateFileCameraRelation() {
        return createFileCameraRelation;
    }
    public static void setCreateFileCameraRelation(String createFileCameraRelation) {
        ImportVideoConfig.createFileCameraRelation = createFileCameraRelation;
    }
    public static String getModifyFileCameraRelation() {
        return modifyFileCameraRelation;
    }
    public static void setModifyFileCameraRelation(String modifyFileCameraRelation) {
        ImportVideoConfig.modifyFileCameraRelation = modifyFileCameraRelation;
    }
    public static String getDeleteOfflineCamera() {
        return deleteOfflineCamera;
    }
    public static void setDeleteOfflineCamera(String deleteOfflineCamera) {
        ImportVideoConfig.deleteOfflineCamera = deleteOfflineCamera;
    }
    public static String getDragOfflineCamera() {
        return dragOfflineCamera;
    }
    public static void setDragOfflineCamera(String dragOfflineCamera) {
        ImportVideoConfig.dragOfflineCamera = dragOfflineCamera;
    }
    public static String getDeleteOffline() {
        return deleteOffline;
    }
    public static void setDeleteOffline(String deleteOffline) {
        ImportVideoConfig.deleteOffline = deleteOffline;
    }
    public static String getCreateFileStatus() {
        return createFileStatus;
    }
    public static void setCreateFileStatus(String createFileStatus) {
        ImportVideoConfig.createFileStatus = createFileStatus;
    }
    public static String getUpdateFileStatus() {
        return updateFileStatus;
    }
    public static void setUpdateFileStatus(String updateFileStatus) {
        ImportVideoConfig.updateFileStatus = updateFileStatus;
    }
    public static String getDeleteFileStatus() {
        return deleteFileStatus;
    }
    public static String getQueryFileStatus() {
        return queryFileStatus;
    }
    public static void setQueryFileStatus(String queryFileStatus) {
        ImportVideoConfig.queryFileStatus = queryFileStatus;
    }
    public static void setDeleteFileStatus(String deleteFileStatus) {
        ImportVideoConfig.deleteFileStatus = deleteFileStatus;
    }
    public static String getDescribeFileClassifys() {
        return describeFileClassifys;
    }
    public static void setDescribeFileClassifys(String describeFileClassifys) {
        ImportVideoConfig.describeFileClassifys = describeFileClassifys;
    }
    public static String getChunk() {
        return chunk;
    }
    public static void setChunk(String chunk) {
        ImportVideoConfig.chunk = chunk;
    }
    public static String getMergeFile() {
        return mergeFile;
    }
    public static void setMergeFile(String mergeFile) {
        ImportVideoConfig.mergeFile = mergeFile;
    }
    
}
