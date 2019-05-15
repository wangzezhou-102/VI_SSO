package com.secusoft.web.tusouapi.model;

/**
 * 停止算法服务request参数
 */
public class ResStopRequest{

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

    
}