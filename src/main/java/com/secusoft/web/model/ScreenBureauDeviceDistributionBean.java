package com.secusoft.web.model;

import java.io.Serializable;
import java.util.List;

/**
 *蓝色大屏浮框 分局算力分配
 * @author hbxing
 * @date 2019/6/20
 */
public class ScreenBureauDeviceDistributionBean implements Serializable {

    /**
     * 统计各个分局/派出所算力
     */
    List<ScreenSysOrgRoadBean> BureauDeviceTotal;
    /**
     * 统计各个分局/派出所本周使用系统次数
     */
    List<ScreenSystemUseNumber> BureauUseSystemRank;

    public ScreenBureauDeviceDistributionBean() {
    }

    public ScreenBureauDeviceDistributionBean(List<ScreenSysOrgRoadBean> bureauDeviceTotal, List<ScreenSystemUseNumber> bureauUseSystemRank) {
        BureauDeviceTotal = bureauDeviceTotal;
        BureauUseSystemRank = bureauUseSystemRank;
    }

    public List<ScreenSysOrgRoadBean> getBureauDeviceTotal() {
        return BureauDeviceTotal;
    }

    public void setBureauDeviceTotal(List<ScreenSysOrgRoadBean> bureauDeviceTotal) {
        BureauDeviceTotal = bureauDeviceTotal;
    }

    public List<ScreenSystemUseNumber> getBureauUseSystemRank() {
        return BureauUseSystemRank;
    }

    public void setBureauUseSystemRank(List<ScreenSystemUseNumber> bureauUseSystemRank) {
        BureauUseSystemRank = bureauUseSystemRank;
    }

    @Override
    public String toString() {
        return "ScreenBureauDeviceDistributionBean{" +
                "BureauDeviceTotal=" + BureauDeviceTotal +
                ", BureauUseSystemRank=" + BureauUseSystemRank +
                '}';
    }
}
