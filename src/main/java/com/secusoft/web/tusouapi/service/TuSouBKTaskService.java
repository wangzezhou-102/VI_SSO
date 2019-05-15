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
import com.secusoft.web.tusouapi.model.*;

/**
 * 布控任务管理接⼝ 
 * 针对布控任务的管理接⼝，包括创建、更新、启动、停⽌、删除布控任务，查看布控任务信息等操作
 */
public interface TuSouBKTaskService {

    /**
     * 创建、更新布控任务
     */
    BaseResponse<JSONArray> bktaskSubmit(BaseRequest<BKTaskSubmitRequest> request);

    /**
     * 启动布控任务
     */
    BaseResponse<JSONArray> bktaskStart(BaseRequest<BKTaskDataTaskIdRequest> request);

    /**
     * 停止布控任务
     */
    BaseResponse<JSONArray> bktaskStop(BaseRequest<BKTaskDataTaskIdRequest> request);

    /**
     * 查看某个布控任务信息
     */
    BaseResponse<JSONArray> bktaskGet(BaseRequest<BKTaskDataTaskIdRequest> request);

    /**
     * 查看多个布控任务信息
     */
    BaseResponse<JSONArray> bktaskList(BaseRequest<BKTaskListRequest> request);

    /**
     * 删除某个布控任务
     */
    BaseResponse<JSONArray> bktaskDelete(BaseRequest<BKTaskDeleteRequest> request);
}