package com.secusoft.web.model;

/**
 * @author huanghao
 * @date 2019-06-25
 */
public class TotalInfoVo {
    private String title;
    private Integer total;
    private String unit;
    private Integer alarmStatistics;
    private Boolean isShow;

    public TotalInfoVo() {
    }

    public TotalInfoVo(String title, Integer total, String unit, Integer alarmStatistics, Boolean isShow) {
        this.title = title;
        this.total = total;
        this.unit = unit;
        this.alarmStatistics = alarmStatistics;
        this.isShow = isShow;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getAlarmStatistics() {
        return alarmStatistics;
    }

    public void setAlarmStatistics(Integer alarmStatistics) {
        this.alarmStatistics = alarmStatistics;
    }

    public Boolean getShow() {
        return isShow;
    }

    public void setShow(Boolean show) {
        isShow = show;
    }

    @Override
    public String toString() {
        return "TotalInfoVo{" +
                "title='" + title + '\'' +
                ", total=" + total +
                ", unit='" + unit + '\'' +
                ", alarmStatistics=" + alarmStatistics +
                ", isShow=" + isShow +
                '}';
    }
}
