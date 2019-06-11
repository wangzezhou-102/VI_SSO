package com.secusoft.web.utils;

import com.aliyun.odps.TableSchema;
import com.aliyun.odps.data.Record;
import com.secusoft.web.model.gazhk.ViGazhkDfkWffzryBean;
import com.secusoft.web.model.gazhk.ViGazhkSjzyzhQgpqryBean;
import org.springframework.stereotype.Component;

/**
 * @author chjiang
 * @since 2019/6/10 9:21
 */
@Component
public class GazhkUntils {

    /**
     * 打防控人员数据赋值
     *
     * @param record
     * @param schema
     */
    public static ViGazhkDfkWffzryBean setViGazhkDfkWffzry(Record record, TableSchema schema) {
        ViGazhkDfkWffzryBean viGazhkDfkWffzryBean = new ViGazhkDfkWffzryBean();
        viGazhkDfkWffzryBean.setPkId(record.getString("pkId"));
        viGazhkDfkWffzryBean.setObjectId(record.getString("pkId"));
        viGazhkDfkWffzryBean.setJgSf(record.getString("jgSf"));
        viGazhkDfkWffzryBean.setJgSs(record.getString("jgSs"));
        viGazhkDfkWffzryBean.setrAjbh(record.getString("rAjbh"));
        viGazhkDfkWffzryBean.setrRybh(record.getString("rRybh"));
        viGazhkDfkWffzryBean.setrXlh(record.getString("rXlh"));
        viGazhkDfkWffzryBean.setrAjlb(record.getString("rAjlb"));
        viGazhkDfkWffzryBean.setrRyly(record.getString("rRyly"));
        viGazhkDfkWffzryBean.setrZhrq(record.getDatetime("rZhrq"));
        viGazhkDfkWffzryBean.setrZhdw(record.getString("rZhdw"));
        viGazhkDfkWffzryBean.setrLxr(record.getString("rLxr"));
        viGazhkDfkWffzryBean.setrLxdh(record.getString("rLxdh"));
        viGazhkDfkWffzryBean.setrZyaq(record.getString("rZyaq"));
        viGazhkDfkWffzryBean.setrCldw(record.getString("rCldw"));
        viGazhkDfkWffzryBean.setrClrq(record.getDatetime("rClrq"));
        viGazhkDfkWffzryBean.setrCllx(record.getString("rCllx"));
        viGazhkDfkWffzryBean.setrCljg(record.getString("rCljg"));
        viGazhkDfkWffzryBean.setrCfrq(record.getDatetime("rCfrq"));
        viGazhkDfkWffzryBean.setrXxzk(record.getString("rXxzk"));
        viGazhkDfkWffzryBean.setrZxd(record.getString("rZxd"));
        viGazhkDfkWffzryBean.setrZxdxz(record.getString("rZxdxz"));
        viGazhkDfkWffzryBean.setrSfrq(record.getDatetime("rSfrq"));
        viGazhkDfkWffzryBean.setrSfly(record.getString("rSfly"));
        viGazhkDfkWffzryBean.setrRylx(record.getString("rRylx"));
        viGazhkDfkWffzryBean.setrBz(record.getString("rBz"));
        viGazhkDfkWffzryBean.setrJbqkjsxbx(record.getString("rJbqkjsxbx"));
        viGazhkDfkWffzryBean.setrGlcs(record.getString("rGlcs"));
        viGazhkDfkWffzryBean.setrJgxzcy(record.getString("rJgxzcy"));
        viGazhkDfkWffzryBean.setrBgxj(record.getString("rBgxj"));
        viGazhkDfkWffzryBean.setrZxyy(record.getString("rZxyy"));
        viGazhkDfkWffzryBean.setrZxrq(record.getDatetime("rZxrq"));
        viGazhkDfkWffzryBean.setrZxr(record.getString("rZxr"));
        viGazhkDfkWffzryBean.setrTbdw(record.getString("rTbdw"));
        viGazhkDfkWffzryBean.setrTbr(record.getString("rTbr"));
        viGazhkDfkWffzryBean.setrTbsj(record.getDatetime("rTbsj"));
        viGazhkDfkWffzryBean.setLrdw(record.getString("lrdw"));
        viGazhkDfkWffzryBean.setLrr(record.getString("lrr"));
        viGazhkDfkWffzryBean.setLrsj(record.getDatetime("lrsj"));
        viGazhkDfkWffzryBean.setCsbz(record.getString("csbz"));
        String scbz = record.getString("scbz");
        if (null != scbz) {
            viGazhkDfkWffzryBean.setScbz(Integer.valueOf(scbz));
        }
        viGazhkDfkWffzryBean.setXgsj(record.getDatetime("xgsj"));
        viGazhkDfkWffzryBean.setCsspdw(record.getString("csspdw"));
        viGazhkDfkWffzryBean.setCsscspdw(record.getString("csscspdw"));
        viGazhkDfkWffzryBean.setScyy(record.getString("scyy"));
        viGazhkDfkWffzryBean.setSbsj(record.getDatetime("sbsj"));
        viGazhkDfkWffzryBean.setrYwxtfl(record.getString("rYwxtfl"));
        viGazhkDfkWffzryBean.setrCfjsrq(record.getDatetime("rCfjsrq"));
        viGazhkDfkWffzryBean.setrYpxq(record.getString("rYpxq"));
        viGazhkDfkWffzryBean.setrAjlb1(record.getString("rAjlb1"));
        viGazhkDfkWffzryBean.setrAjlb2(record.getString("rAjlb2"));
        viGazhkDfkWffzryBean.setrZrdd(record.getString("rZrdd"));
        viGazhkDfkWffzryBean.setrZhddxz(record.getString("rZhddxz"));
        viGazhkDfkWffzryBean.setrZhfs(record.getString("rZhfs"));
        viGazhkDfkWffzryBean.setScbzFormat(record.getString("scbzFormat"));
        viGazhkDfkWffzryBean.setrCldwCode6(record.getString("rCldwCode6"));
        viGazhkDfkWffzryBean.setrRybhJgcode(record.getString("rRybhJgcode"));
        viGazhkDfkWffzryBean.setrRybhMzcode(record.getString("rRybhMzcode"));
        viGazhkDfkWffzryBean.setrRybhXm(record.getString("rRybhXm"));
        viGazhkDfkWffzryBean.setrRybhXb(record.getString("rRybhXb"));
        viGazhkDfkWffzryBean.setrRybhSfzh(record.getString("rRybhSfzh"));
        viGazhkDfkWffzryBean.setrRybhMz(record.getString("rRybhMz"));
        viGazhkDfkWffzryBean.setrRybhJg(record.getString("rRybhJg"));
        viGazhkDfkWffzryBean.setrAjlbFormat(record.getString("rAjlbFormat"));
        viGazhkDfkWffzryBean.setrRylyFormat(record.getString("rRylyFormat"));
        viGazhkDfkWffzryBean.setrZhdwFormat(record.getString("rZhdwFormat"));
        viGazhkDfkWffzryBean.setrCldwFormat(record.getString("rCldwFormat"));
        viGazhkDfkWffzryBean.setrCllxFormat(record.getString("rCllxFormat"));
        viGazhkDfkWffzryBean.setrXxzkFormat(record.getString("rXxzkFormat"));
        viGazhkDfkWffzryBean.setrZxdFormat(record.getString("rZxdFormat"));
        viGazhkDfkWffzryBean.setrSflyFormat(record.getString("rSflyFormat"));
        viGazhkDfkWffzryBean.setrRylxFormat(record.getString("rRylxFormat"));
        viGazhkDfkWffzryBean.setrTbdwFormat(record.getString("rTbdwFormat"));
        viGazhkDfkWffzryBean.setLrdwFormat(record.getString("lrdwFormat"));
        viGazhkDfkWffzryBean.setCsspdwFormat(record.getString("csspdwFormat"));
        viGazhkDfkWffzryBean.setCsscspdwFormat(record.getString("csscspdwFormat"));
        viGazhkDfkWffzryBean.setrYwxtflFormat(record.getString("rYwxtflFormat"));
        viGazhkDfkWffzryBean.setrAjlb1Format(record.getString("rAjlb1Format"));
        viGazhkDfkWffzryBean.setrAjlb2Format(record.getString("rAjlb2Format"));

        return viGazhkDfkWffzryBean;
    }

    /**
     * 全国扒窃人员数据赋值
     *
     * @param record
     * @param schema
     * @return
     */
    public static ViGazhkSjzyzhQgpqryBean setViGazhkSjzyzhQgpqry(Record record, TableSchema schema) {
        ViGazhkSjzyzhQgpqryBean viGazhkSjzyzhQgpqryBean = new ViGazhkSjzyzhQgpqryBean();
        viGazhkSjzyzhQgpqryBean.setObjectId(record.getBigint("xh").toString());
        Long xh = record.getBigint("xh");
        if (null != xh) {
            viGazhkSjzyzhQgpqryBean.setXh(xh.intValue());
        }

        viGazhkSjzyzhQgpqryBean.setXm(record.getString("xm"));
        viGazhkSjzyzhQgpqryBean.setXb(record.getString("xb"));
        viGazhkSjzyzhQgpqryBean.setSfzh(record.getString("sfzh"));
        viGazhkSjzyzhQgpqryBean.setJg(record.getString("jg"));

        return viGazhkSjzyzhQgpqryBean;
    }
}
