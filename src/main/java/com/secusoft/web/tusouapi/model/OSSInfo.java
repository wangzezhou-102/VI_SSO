package com.secusoft.web.tusouapi.model;

/**
 * ossInfo参数
 */
public class OSSInfo{
    
    /**
     * [必须] oss Endpoint信息
     */
    private String ossEndpoint;
    /**
     * [必须] oss AccessKeyId信息
     */
    private String ossAccessKeyId;
    /**
     * [必须] oss AccessKeySecret信息
     */
    private String ossAccessKeySecret;
    /**
     * [必须] oss的Bucket信息
     */
    private String ossBucket;

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



    
}