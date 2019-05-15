package com.secusoft.web.tusouapi.model;

/**
 * 图像搜索request参数
 */
public class SearchRequest {

    /**
     * [必须] ⽤户名称 平台登录⽤户名
     */
    private String uid;
    /**
     * [必须] 计算任务的id 在平台计算⻚⾯上
     */
    private String taskId;
    /**
     * 算法名称 算法⽤于提取特征,⼀般指定uid和taskId即可
     */
    private String algorithmName;
    /**
     * 算法版本
     */
    private String algorithmVersion;
    /**
     * 索引名称
     */
    private String indexName;
    /**
     * "1"表示不⽤特征匹配 查询 纯属性或id查询 不传或其他值表示使⽤ 特征查询
     */
    private String noFeature;
    /**
     * [必须] 查询类型 根据算法不同查询不同的类型 天擎算法查询类型示例 person(⾏⼈)、bicycle (⾮机动⻋) 、vehicle(机动⻋)
     */
    private String type;


    

    /**
     * @return the uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * @param uid the uid to set
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * @return the taskId
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * @param taskId the taskId to set
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * @return the algorithmName
     */
    public String getAlgorithmName() {
        return algorithmName;
    }

    /**
     * @param algorithmName the algorithmName to set
     */
    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    /**
     * @return the algorithmVersion
     */
    public String getAlgorithmVersion() {
        return algorithmVersion;
    }

    /**
     * @param algorithmVersion the algorithmVersion to set
     */
    public void setAlgorithmVersion(String algorithmVersion) {
        this.algorithmVersion = algorithmVersion;
    }

    /**
     * @return the indexName
     */
    public String getIndexName() {
        return indexName;
    }

    /**
     * @param indexName the indexName to set
     */
    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    /**
     * @return the noFeature
     */
    public String getNoFeature() {
        return noFeature;
    }

    /**
     * @param noFeature the noFeature to set
     */
    public void setNoFeature(String noFeature) {
        this.noFeature = noFeature;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }


    
}