package com.secusoft.web.tusouapi.model;

/**
 * ossInfo参数
 */
public class OSSInfo{
    
    /**
     * [必须] oss Endpoint信息
     */
    private String endpoint;
    /**
     * [必须] oss AccessKeyId信息
     */
    private String access_id;
    /**
     * [必须] oss AccessKeySecret信息
     */
    private String access_key;
    /**
     * [必须] oss的Bucket信息
     */
    private String bucket_name;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccess_id() {
        return access_id;
    }

    public void setAccess_id(String access_id) {
        this.access_id = access_id;
    }

    public String getAccess_key() {
        return access_key;
    }

    public void setAccess_key(String access_key) {
        this.access_key = access_key;
    }

    public String getBucket_name() {
        return bucket_name;
    }

    public void setBucket_name(String bucket_name) {
        this.bucket_name = bucket_name;
    }
}