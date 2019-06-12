package com.secusoft.web.service;

import com.secusoft.web.model.ResultVo;

import java.util.Map;

/**
 * 
 * @author ChenDong
 * @company 视在数科
 * @date 2019年6月11日
 */
public interface ImportVideoService {

    ResultVo chunk();
    ResultVo mergeFile(Map<String, Object> param);
    ResultVo createFileClassify(Map<String,Object> param);
    ResultVo modifyFileClassify(Map<String,Object> param);
    ResultVo deleteFileClassify(Map<String,Object> param);
    ResultVo createFileCameraRelation(Map<String,Object> param);
    ResultVo modifyFileCameraRelation(Map<String,Object> param);
    ResultVo dragOfflineCamera(Map<String,Object> param);
    ResultVo deleteOfflineCamera(Map<String,Object> param);
    ResultVo deleteOffline(Map<String,Object> param);
    ResultVo createFileStatus(Map<String,Object> param);
    ResultVo updateFileStatus(Map<String,Object> param);
    ResultVo queryFileStatus(Map<String,Object> param);
    ResultVo deleteFileStatus(Map<String,Object> param);
    ResultVo describeFileClassifys(Map<String,Object> param);
}
