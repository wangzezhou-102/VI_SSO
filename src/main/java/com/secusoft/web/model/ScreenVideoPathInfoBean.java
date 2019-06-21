package com.secusoft.web.model;
/**
 * 蓝色大屏浮框 视频路数统计
 *
 * @author hbxing
 * @date 2019/6/20
 */
public class ScreenVideoPathInfoBean {
    /**
     *视频监控量
     */
    private Integer VideoMonitorTotal;
    /**
     * 总并发路数
     */
    private Integer concurrentPathTotal;
    /**
     * 当前并发路数
     */
    private Integer CurrentConcurrentPathTotal;

    public ScreenVideoPathInfoBean() {
    }

    public ScreenVideoPathInfoBean(Integer videoMonitorTotal, Integer concurrentPathTotal, Integer currentConcurrentPathTotal) {
        VideoMonitorTotal = videoMonitorTotal;
        this.concurrentPathTotal = concurrentPathTotal;
        CurrentConcurrentPathTotal = currentConcurrentPathTotal;
    }

    public Integer getVideoMonitorTotal() {
        return VideoMonitorTotal;
    }

    public void setVideoMonitorTotal(Integer videoMonitorTotal) {
        VideoMonitorTotal = videoMonitorTotal;
    }

    public Integer getConcurrentPathTotal() {
        return concurrentPathTotal;
    }

    public void setConcurrentPathTotal(Integer concurrentPathTotal) {
        this.concurrentPathTotal = concurrentPathTotal;
    }

    public Integer getCurrentConcurrentPathTotal() {
        return CurrentConcurrentPathTotal;
    }

    public void setCurrentConcurrentPathTotal(Integer currentConcurrentPathTotal) {
        CurrentConcurrentPathTotal = currentConcurrentPathTotal;
    }

    @Override
    public String toString() {
        return "ScreenVideoPathInfoBean{" +
                "VideoMonitorTotal=" + VideoMonitorTotal +
                ", concurrentPathTotal=" + concurrentPathTotal +
                ", CurrentConcurrentPathTotal=" + CurrentConcurrentPathTotal +
                '}';
    }
}
