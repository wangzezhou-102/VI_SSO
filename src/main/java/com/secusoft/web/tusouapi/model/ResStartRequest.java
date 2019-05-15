package com.secusoft.web.tusouapi.model;

/**
 * 启动算法服务request参数
 */
public class ResStartRequest {

    /**
     * [必须] ⽤户名称 平台登录⽤户名
     */
    private String uid;
    /**
     * [必须] 算法包名称
     */
    private String algorithmName;
    /**
     * [必须] 算法版本号
     */
    private String algorithmVersion;
    /**
     * [必须] 算法类型 0:CPU，1:GPU，2:CPU和GPU
     */
    private int algoType;
    /**
     * [必须] 运⾏类型;0:CPU, 1:GPU
     */
    private int runType;
    /**
     * [必须] oss accessKeyID
     */
    private String ossAccessKeyId;
    /**
     * [必须] oss endpoint
     */
    private String ossEndpoint;
    /**
     * [必须] oss accessKeySecret
     */
    private String ossAccessKeySecret;
    /**
     * [必须] oss bucket name
     */
    private String ossBucket;
    /**
     * [必须] oss 算法包路径
     */
    private String ossPath;

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
     * @return the algoType
     */
    public int getAlgoType() {
        return algoType;
    }

    /**
     * @param algoType the algoType to set
     */
    public void setAlgoType(int algoType) {
        this.algoType = algoType;
    }

    /**
     * @return the runType
     */
    public int getRunType() {
        return runType;
    }

    /**
     * @param runType the runType to set
     */
    public void setRunType(int runType) {
        this.runType = runType;
    }

    /**
     * @return the ossAccessKeyId
     */
    public String getOssAccessKeyId() {
        return ossAccessKeyId;
    }

    /**
     * @param ossAccessKeyId the ossAccessKeyId to set
     */
    public void setOssAccessKeyId(String ossAccessKeyId) {
        this.ossAccessKeyId = ossAccessKeyId;
    }

    /**
     * @return the ossEndpoint
     */
    public String getOssEndpoint() {
        return ossEndpoint;
    }

    /**
     * @param ossEndpoint the ossEndpoint to set
     */
    public void setOssEndpoint(String ossEndpoint) {
        this.ossEndpoint = ossEndpoint;
    }

    /**
     * @return the ossAccessKeySecret
     */
    public String getOssAccessKeySecret() {
        return ossAccessKeySecret;
    }

    /**
     * @param ossAccessKeySecret the ossAccessKeySecret to set
     */
    public void setOssAccessKeySecret(String ossAccessKeySecret) {
        this.ossAccessKeySecret = ossAccessKeySecret;
    }

    /**
     * @return the ossBucket
     */
    public String getOssBucket() {
        return ossBucket;
    }

    /**
     * @param ossBucket the ossBucket to set
     */
    public void setOssBucket(String ossBucket) {
        this.ossBucket = ossBucket;
    }

    /**
     * @return the ossPath
     */
    public String getOssPath() {
        return ossPath;
    }

    /**
     * @param ossPath the ossPath to set
     */
    public void setOssPath(String ossPath) {
        this.ossPath = ossPath;
    }

}