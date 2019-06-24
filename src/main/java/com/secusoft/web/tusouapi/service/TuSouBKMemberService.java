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
import com.secusoft.web.tusouapi.model.BKMemberAddRequest;
import com.secusoft.web.tusouapi.model.BKMemberDeleteRequest;
import com.secusoft.web.tusouapi.model.BKMemberListRequest;
import com.secusoft.web.tusouapi.model.BaseRequest;
import com.secusoft.web.tusouapi.model.BaseResponse;

/**
 * 布控⽬标接⼝
 * 在指定的布控库中, 添加、删除、查询布控⽬标的信息。
 */
public interface TuSouBKMemberService{


    /**
     * 添加⼀个或多个布控⽬标
     * @return
     */
    BaseResponse<JSONArray> bkmemberAdd(BaseRequest<BKMemberAddRequest> request);

    /**
     * 删除布控⽬标
     */
    BaseResponse<JSONArray> bkmemberDelete(BaseRequest<BKMemberDeleteRequest> request);

    /**
     * 获取当前⽬标库的布控⽬标
     */
    BaseResponse<JSONArray> bkmemberList(BaseRequest<BKMemberListRequest> request);

}