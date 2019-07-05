package com.secusoft.web.model;

import java.util.List;

/**
 * 安保任务实体
 * @author huanghao
 * @date 2019-07-03
 */
public class SecurityTaskBean {
    private Integer id;
    private String taskId;
    /**
     *  安保任务名称
     */
    private String securityName;
    /**
     * 安保任务类型
     */
    private String securityType;
    /**
     * 安保任务地点
     */
    private List<SecurityTaskPlaceBean> securityTaskPlaceBeans;
    /**
     * 安保任务时间
     */
    private List<SecurityTimeStamp> timeStamps;
    /**
     * 安保任务说明
     */
    private String explain;
    /**
     * 是否开启 0-结束 1-开启
     */
    private Integer enable;
    private Integer securityStatus;
    private String topic;
    /**
     * 操作人员编号
     */
    private String userId;
    private String orgCode;
    private List<CodeplacesBean> codeplacesBeans;



}
