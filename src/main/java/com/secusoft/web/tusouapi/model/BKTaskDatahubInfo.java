package com.secusoft.web.tusouapi.model;

public class BKTaskDatahubInfo extends BKTaskChannel{

    /**
     * oss Endpoint信息
     */
    private String ossEndpoint;
    /**
     * oss AccessKeyId信息
     */
    private String ossAccessKeyId;
    /**
     * oss AccessKeySecret信息
     */
    private String ossAccessKeySecret;


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

    
}