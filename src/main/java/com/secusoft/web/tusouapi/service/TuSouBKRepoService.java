/**
 * 布控服务接⼝
 * 布控服务的⽬的是，将⽤户录⼊布控库的⽬标，与视频流结构化分析得到的⽬标进⾏实时⽐对，对于符合⼀定条件的⽐对结果，
 * 将向消息中间件（datahub、rocketmq）写⼊对应结果，通知上游处理。
 * 
 * 基本的布控业务操作流程是：1、创建布控库 2、添加布控⽬标 3、创建布控任务 4、启动布控任务
 * 
 * 当前布控服务⽀持三类接⼝ :
 * 布控库管理接⼝: ⽀持创建、更新、删除、获取布控库
 * 布控⽬标管理接⼝: ⽀持创建、更新、删除、获取布控任务
 * 布控任务管理接⼝: ⽀持布控库中布控⽬标的增删改查操作
 * 
 * 在布控业务中，上游业务的⽤户需指定如下消息中间件地址：
 * 输⼊：业务层指定视频分析结构化数据源消息中间件（datahub、rocketmq）地址
 * 输出：业务层指定分析匹配的结果输出消息中间件（datahub、rocketmq）地址
 */
package com.secusoft.web.tusouapi.service;

import com.alibaba.fastjson.JSONArray;
import com.secusoft.web.tusouapi.model.BKRepoCreateRequest;
import com.secusoft.web.tusouapi.model.BKRepoDeleteRequest;
import com.secusoft.web.tusouapi.model.BKRepoMetaRequest;
import com.secusoft.web.tusouapi.model.BKRepoUpdateRequest;
import com.secusoft.web.tusouapi.model.BaseRequest;
import com.secusoft.web.tusouapi.model.BaseResponse;

/**
 * 布控库管理接⼝
 * 对布控库的管理操作API，包括创建、更新、删除布控库和获取布控哭信息的操作。
 */
public interface TuSouBKRepoService{


    /**
     * 创建布控库
     * @return
     */
    BaseResponse<JSONArray> bkrepoCreate(BaseRequest<BKRepoCreateRequest> request);
    /**
     * 更新布控库信息（⽬前不⽀持修改算法名称、算法版本以及布控库id）
     */
    BaseResponse<JSONArray> bkrepoUpdate(BaseRequest<BKRepoUpdateRequest> request);

    /**
     * 删除布控库
     * @return
     */
    BaseResponse<JSONArray> bkrepoDelete(BaseRequest<BKRepoDeleteRequest> request);

    /**
     * 获取布控库的meta信息
     * @return
     */
    BaseResponse<JSONArray> bkrepoMeta(BaseRequest<BKRepoMetaRequest> request);
}