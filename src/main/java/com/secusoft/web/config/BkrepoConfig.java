package com.secusoft.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author chjiang
 * @since 2019/6/10 13:26
 */
@Component
@ConfigurationProperties(prefix = "bkrepo")
public class BkrepoConfig {

    private String requestId;

    private String bkid;

    private Double threshold;

    private Meta meta;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getBkid() {
        return bkid;
    }

    public void setBkid(String bkid) {
        this.bkid = bkid;
    }

    public Double getThreshold() {
        return threshold;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public static class Meta{

        private String bkdesc;

        private String bkname;

        private String algorithmName;

        private String algorithmVersion;

        private String algorithmType;

        private OssInfo ossInfo;

        public String getBkdesc() {
            return bkdesc;
        }

        public void setBkdesc(String bkdesc) {
            this.bkdesc = bkdesc;
        }

        public String getBkname() {
            return bkname;
        }

        public void setBkname(String bkname) {
            this.bkname = bkname;
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

        public String getAlgorithmType() {
            return algorithmType;
        }

        public void setAlgorithmType(String algorithmType) {
            this.algorithmType = algorithmType;
        }

        public OssInfo getOssInfo() {
            return ossInfo;
        }

        public void setOssInfo(OssInfo ossInfo) {
            this.ossInfo = ossInfo;
        }
    }

    public static class OssInfo{

        private String endpoint;

        private String access_id;

        private String access_key;

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
}
