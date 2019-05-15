package com.secusoft.web.tusouapi.service;

import com.alibaba.fastjson.JSONArray;
import com.secusoft.web.tusouapi.model.AlgorithmAttributeRequest;
import com.secusoft.web.tusouapi.model.AlgorithmDetectRequest;
import com.secusoft.web.tusouapi.model.AlgorithmFeatureRequest;
import com.secusoft.web.tusouapi.model.BaseRequest;
import com.secusoft.web.tusouapi.model.BaseResponse;

/**
 * 图像算法服务接⼝
 * 图像算法服务的⽬标是，给定图像，调⽤不同的算法对图像进⾏处理，返回对应的结果。
 * 图像算法服务⽬前⽀持属性识别服务(attribute)、特征提取服务(f eature)和⽬标检测服务 (detect)：
 * 1. 属性识别：传⼊⽬标图像(⾏⼈、⾮机动⻋或机动⻋)，属性服务返回对应⽬标的属性信息，如⾏⼈的上⾐⻓度、⾮机动⻋的⻋型、机动⻋的颜⾊等;
 * 2. 特征提取：传⼊⽬标图像(⾏⼈、⾮机动⻋或机动⻋)，返回对应⽬标图像的⾼维特征向量;
 * 3. ⽬标检测：传⼊图像，返回对应图像中所有⽬标的位置和类别信息等
 */
public interface TuSouAlgorithmService{

    
    /**
     * 属性识别
     * 
     * @return
     */
    BaseResponse<JSONArray> algorithmAttribute(BaseRequest<AlgorithmAttributeRequest> request);

    /**
     * 特征提取
     * 
     * @return
     */
    BaseResponse<JSONArray> algorithmFeature(BaseRequest<AlgorithmFeatureRequest> request);

    /**
     * ⽬标检测
     * 
     * @return
     */
    BaseResponse<JSONArray> algorithmDetect(BaseRequest<AlgorithmDetectRequest> request);
    
}