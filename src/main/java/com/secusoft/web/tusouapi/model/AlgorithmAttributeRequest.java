package com.secusoft.web.tusouapi.model;

/**
 * 属性识别request参数
 */
public class AlgorithmAttributeRequest{


    /**
     * [必须] ⽤户名称 平台登录⽤户名
     */
    private String uid; 
    /**
     * [必须] 计算任务的id 在平台计算⻚⾯上
     */
    private String taskId; 
    /**
     * [必须] 算法包名称
     */
    private String algorithmName; 
    /**
     * [必须] 算法版本号
     */
    private String algorithmVersion; 
    /**
     * [必须] 查询类型 根据算法不同查询不同的类型 天擎算法查询类型示例 person(⾏⼈)、bicycle (⾮机动⻋) 、vehicle(机动⻋)
     */
    private String type;
    /**
     * 图像内容; base64 编码;与 imageUrls ⼆选⼀;优先级:imageUrls>contents
     */
    private String contents; 
    /**
     * 图像内容; base64 编码;与 imageUrls ⼆选⼀;优先级:imageUrls>contents
     */
    private String imageUrl;



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

    /**
     * @return the contents
     */
    public String getContents() {
        return contents;
    }

    /**
     * @param contents the contents to set
     */
    public void setContents(String contents) {
        this.contents = contents;
    }

    /**
     * @return the imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * @param imageUrl the imageUrl to set
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    
}