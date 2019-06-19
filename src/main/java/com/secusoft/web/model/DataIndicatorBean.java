package com.secusoft.web.model;

public class DataIndicatorBean {
    private String typeCode;
    private String typeName;
    private String indicatorCode;
    private String indicatorName;
    private String indicatorValue;
    private String dt;

    public DataIndicatorBean() {
    }

    public DataIndicatorBean(String typeCode, String typeName, String indicatorCode, String indicatorName, String indicatorValue, String dt) {
        this.typeCode = typeCode;
        this.typeName = typeName;
        this.indicatorCode = indicatorCode;
        this.indicatorName = indicatorName;
        this.indicatorValue = indicatorValue;
        this.dt = dt;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getIndicatorCode() {
        return indicatorCode;
    }

    public void setIndicatorCode(String indicatorCode) {
        this.indicatorCode = indicatorCode;
    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    public String getIndicatorValue() {
        return indicatorValue;
    }

    public void setIndicatorValue(String indicatorValue) {
        this.indicatorValue = indicatorValue;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    @Override
    public String toString() {
        return "DataIndicatorBean{" +
                "typeCode='" + typeCode + '\'' +
                ", typeName='" + typeName + '\'' +
                ", indicatorCode='" + indicatorCode + '\'' +
                ", indicatorName='" + indicatorName + '\'' +
                ", indicatorValue='" + indicatorValue + '\'' +
                ", dt='" + dt + '\'' +
                '}';
    }
}
