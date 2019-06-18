package com.secusoft.web.tusouapi.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.tusouapi.model.BaseRequest;
import com.secusoft.web.tusouapi.model.BaseResponse;
import com.secusoft.web.tusouapi.model.SearchRequest;
/**
 * 图像搜索（图搜）服务接⼝
 * 
 * 图像搜索（图搜）服务的功能是，通过传⼊⽬标图像，并指定相应的搜索⽬标类型(⾏⼈、⾮机动⻋或机动⻋)，调⽤天擎不同的特征
 * 提取和搜索算法，根据特征相似度匹配的得分，从⾼到低依次返回对应的搜索结果(也就是越相似的图⽚越排在前⾯)。在上述特征搜
 * 索的基础上，也可指定详细的搜索⽬标的属性，如⾏⼈的⾐服类型、⾮机动⻋的颜⾊、机动⻋的⻋型等，返回更准确的搜索结果。
 * 图搜接⼝⽬前⽀持的典型场景如下：
 * 1. 图像搜索（以图搜图）：输⼊单张或多张图像，提取特征进⾏搜索；
 * 2. 图像+属性搜索：在上述特征搜索基础上，联合属性搜索；
 * 3. 纯属性搜索：不需要输⼊图像，直接指定详细的搜索⽬标的属性，进⾏搜索；
 * 4. 渐进式搜索：单张图像+多个⽬标id, 或只有多个⽬标id；(这⾥的id是接⼝返回⾥的id)
 */
public interface TuSouSearchService{

    /**
     * 图像搜索
     * @param request
     * @return
     */
    BaseResponse<JSONArray> search(BaseRequest<SearchRequest> request);

    ResultVo sortsearch(JSONObject request);

    ResultVo testsearch(JSONObject request);
}