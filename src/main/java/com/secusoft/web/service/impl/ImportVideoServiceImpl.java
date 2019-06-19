package com.secusoft.web.service.impl;

import com.secusoft.web.config.ImportVideoConfig;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.ImportVideoService;
import com.secusoft.web.serviceapi.ServiceApiClient;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ImportVideoServiceImpl implements ImportVideoService {

    /**
     * 创建文件夹
     */
    @Override
    public ResultVo createFileClassify(Map<String, Object> param) {
        ResultVo resultVo = new ResultVo();
        String result = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ImportVideoConfig.createFileClassify, param);
        resultVo.setData(result);
        return resultVo;
    }

    /**
     * 修改文件夹
     */
    @Override
    public ResultVo modifyFileClassify(Map<String, Object> param) {
        ResultVo resultVo = new ResultVo();
        String result = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ImportVideoConfig.modifyFileClassify, param);
        resultVo.setData(result);
        return resultVo;
    }

    /**
     * 删除文件夹
     */
    @Override
    public ResultVo deleteFileClassify(Map<String, Object> param) {
        ResultVo resultVo = new ResultVo();
        String result = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ImportVideoConfig.deleteFileClassify, param);
        resultVo.setData(result);
        return resultVo;
    }

    /**
     * 创建点位
     */
    @Override
    public ResultVo createFileCameraRelation(Map<String, Object> param) {
        ResultVo resultVo = new ResultVo();
        String result = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ImportVideoConfig.createFileCameraRelation, param);
        resultVo.setData(result);
        return resultVo;
    }

    /**
     * 修改点位
     */
    @Override
    public ResultVo modifyFileCameraRelation(Map<String, Object> param) {
        ResultVo resultVo = new ResultVo();
        String result = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ImportVideoConfig.modifyFileCameraRelation, param);
        resultVo.setData(result);
        return resultVo;
    }

    /**
     * 删除点位，含下级视频
     */
    @Override
    public ResultVo deleteOfflineCamera(Map<String, Object> param) {
        ResultVo resultVo = new ResultVo();
        String result = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ImportVideoConfig.deleteOfflineCamera, param);
        resultVo.setData(result);
        return resultVo;
    }

    /**
     * 更换点位所属文件夹
     */
    @Override
    public ResultVo dragOfflineCamera(Map<String, Object> param) {
        ResultVo resultVo = new ResultVo();
        String result = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ImportVideoConfig.dragOfflineCamera, param);
        resultVo.setData(result);
        return resultVo;
    }
    
    /**
     * 删除离线视频、删除切片  删除结构化数据、删除es数据
     */
    @Override
    public ResultVo deleteOffline(Map<String, Object> param) {
        ResultVo resultVo = new ResultVo();
        String result = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ImportVideoConfig.deleteOffline, param);
        resultVo.setData(result);
        return resultVo;
    }

    /**
     * 创建离线文件信息
     */
    @Override
    public ResultVo createFileStatus(Map<String, Object> param) {
        ResultVo resultVo = new ResultVo();
        String result = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ImportVideoConfig.createFileStatus, param);
        resultVo.setData(result);
        return resultVo;
    }

    /**
     * 修改文件开始时间/文件名称
     */
    @Override
    public ResultVo updateFileStatus(Map<String, Object> param) {
        ResultVo resultVo = new ResultVo();
        String result = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ImportVideoConfig.updateFileStatus, param);
        resultVo.setData(result);
        return resultVo;
    }

    /**
     * 分页查询离线文件
     */
    @Override
    public ResultVo queryFileStatus(Map<String, Object> param) {
        ResultVo resultVo = new ResultVo();
        String result = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ImportVideoConfig.queryFileStatus, param);
        resultVo.setData(result);
        return resultVo;
    }
    
    /**
     * 删除文件夹，不含结构化数据
     */
    @Override
    public ResultVo deleteFileStatus(Map<String, Object> param) {
        ResultVo resultVo = new ResultVo();
        String result = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ImportVideoConfig.deleteFileStatus, param);
        resultVo.setData(result);
        return resultVo;
    }

    /**
     * 视频分析查询文件夹下所有点位，嵌套返回格式
     */
    @Override
    public ResultVo describeFileClassifys(Map<String, Object> param) {
        ResultVo resultVo = new ResultVo();
        String result = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ImportVideoConfig.describeFileClassifys, param);
        resultVo.setData(result);
        return resultVo;
    }

    /**
     * 上传文件切片
     */
    @Override
    public ResultVo chunk() {
        ResultVo resultVo = new ResultVo();
        Map<String,Object> param = new HashMap<String,Object>();
        String result = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ImportVideoConfig.chunk, param);
        resultVo.setData(result);
        return resultVo;
    }

    /**
     * 切片合并
     */
    @Override
    public ResultVo mergeFile(Map<String,Object> param) {
        ResultVo resultVo = new ResultVo();
        String result = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ImportVideoConfig.mergeFile, param);
        resultVo.setData(result);
        return resultVo;
    }

}
