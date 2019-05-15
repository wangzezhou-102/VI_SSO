package com.secusoft.web.tusouapi.model;
/**
 * 布控请求Meta参数
 */
public class BKRepoMeta{

    /**
     * [必须] 布控库描述信息
     */
    private String bkdesc;
    /**
     * [必须] 布控库名称
     */
    private String bkname;
    /**
     * [必须] 该布控库使⽤的算法名称
     */
    private String algorithmName;
    /**
     * [必须] 该布控库使⽤的算法版本号
     */
    private String algorithmVersion;
    /**
     * [必须] 该布控库使⽤的算法类型
     */
    private String algorithmType;
    /**
     * [必须] oss的访问权限信息
     */
    private OSSInfo ossInfo;

    /**
     * @return the bkdesc
     */
    public String getBkdesc() {
        return bkdesc;
    }

    /**
     * @param bkdesc the bkdesc to set
     */
    public void setBkdesc(String bkdesc) {
        this.bkdesc = bkdesc;
    }

    /**
     * @return the bkname
     */
    public String getBkname() {
        return bkname;
    }

    /**
     * @param bkname the bkname to set
     */
    public void setBkname(String bkname) {
        this.bkname = bkname;
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
     * @return the algorithmType
     */
    public String getAlgorithmType() {
        return algorithmType;
    }

    /**
     * @param algorithmType the algorithmType to set
     */
    public void setAlgorithmType(String algorithmType) {
        this.algorithmType = algorithmType;
    }

    /**
     * @return the ossInfo
     */
    public OSSInfo getOssInfo() {
        return ossInfo;
    }

    /**
     * @param ossInfo the ossInfo to set
     */
    public void setOssInfo(OSSInfo ossInfo) {
        this.ossInfo = ossInfo;
    }

    
}