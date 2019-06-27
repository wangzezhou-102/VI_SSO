package com.secusoft.web.tusouapi.model;

/**
 * 图像搜索request参数
 */
public class SearchRequestData {

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
     * 支持使用多个目标id查询,实现渐进式查询,id与id之间用逗号隔开,如"001,002"查询的结果是与001和002两个目标共同相似度最高的图像
     */
    private String ids;

    /**
     * 图像内容，base64编码,多个⽤","隔开,与ossUrls,features三选⼀,优先级关系:features>ossUrls>contents
     */
    private String contents;

    /**
     * 图像内容，base64编码,多个⽤","隔开,与ossUrls,features三选⼀,优先级关系:features>ossUrls>contents
     */
    private String ossUrls;

    /**
     * 图像内容，base64编码,多个⽤","隔开,与ossUrls,features三选⼀,优先级关系:features>ossUrls>contents
     */
    private String features;

    private Object attribute; //TODO

    /**
     * 查询范围起始时间戳(毫秒) 13位 如1553734800000
     */
    private Long startTime;

    /**
     * 查询范围结束时间戳 (毫秒) 13位 如1553734800000
     */
    private Long endTime;


    /**
     * 摄像头点位id,支持多个查询各点位间用逗号隔开
     */
    private String cameraId;

    /**
     * 特征匹配的score阈值，算法相关，根据公式将相似度转换为score然后取相反数,例如： ⽤户想查找相似度0.5-0.8之间，如果0.5对应计算score是0.7，0.8对应计算score是0. 4, 那么threshold和maxT hresd对应的值是-0.7 、-0.4
     */
    private Double threshold;

    /**
     * 特征匹配的score阈值,算法相关,根据公式将相似度转为score然后取相反数
     */
    private Double maxThrehold;


    /**
     * 局部搜索的时候需要这个参数，是一个浮点数组，每个数字用逗号隔开,取值0或1,数组长度和特征长度一致,例如特征是4维的，那么weights可能的取值类似"1,1,1,0"
     */
    private String weightsString;

    /**
     * 语义搜索文字 ⽤户输⼊⽂字
     */
    private String text;

    public SearchRequestData() {
    }

    public SearchRequestData(String uid, String taskId, String algorithmName, String algorithmVersion, String indexName, String noFeature, String type, String ids, String contents, String ossUrls, String features, Object attribute, Long startTime, Long endTime, String cameraId, Double threshold, Double maxThrehold, String weightsString, String text) {
        this.uid = uid;
        this.taskId = taskId;
        this.algorithmName = algorithmName;
        this.algorithmVersion = algorithmVersion;
        this.indexName = indexName;
        this.noFeature = noFeature;
        this.type = type;
        this.ids = ids;
        this.contents = contents;
        this.ossUrls = ossUrls;
        this.features = features;
        this.attribute = attribute;
        this.startTime = startTime;
        this.endTime = endTime;
        this.cameraId = cameraId;
        this.threshold = threshold;
        this.maxThrehold = maxThrehold;
        this.weightsString = weightsString;
        this.text = text;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public String getAlgorithmVersion() {
        return algorithmVersion;
    }

    public void setAlgorithmVersion(String algorithmVersion) {
        this.algorithmVersion = algorithmVersion;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getNoFeature() {
        return noFeature;
    }

    public void setNoFeature(String noFeature) {
        this.noFeature = noFeature;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getOssUrls() {
        return ossUrls;
    }

    public void setOssUrls(String ossUrls) {
        this.ossUrls = ossUrls;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public Object getAttribute() {
        return attribute;
    }

    public void setAttribute(Object attribute) {
        this.attribute = attribute;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    public Double getThreshold() {
        return threshold;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }

    public Double getMaxThrehold() {
        return maxThrehold;
    }

    public void setMaxThrehold(Double maxThrehold) {
        this.maxThrehold = maxThrehold;
    }

    public String getWeightsString() {
        return weightsString;
    }

    public void setWeightsString(String weightsString) {
        this.weightsString = weightsString;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "SearchRequestData{" +
                "uid='" + uid + '\'' +
                ", taskId='" + taskId + '\'' +
                ", algorithmName='" + algorithmName + '\'' +
                ", algorithmVersion='" + algorithmVersion + '\'' +
                ", indexName='" + indexName + '\'' +
                ", noFeature='" + noFeature + '\'' +
                ", type='" + type + '\'' +
                ", ids='" + ids + '\'' +
                ", contents='" + contents + '\'' +
                ", ossUrls='" + ossUrls + '\'' +
                ", features='" + features + '\'' +
                ", attribute=" + attribute +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", cameraId='" + cameraId + '\'' +
                ", threshold=" + threshold +
                ", maxThrehold=" + maxThrehold +
                ", weightsString='" + weightsString + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}