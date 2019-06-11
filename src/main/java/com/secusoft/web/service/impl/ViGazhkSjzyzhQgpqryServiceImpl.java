package com.secusoft.web.service.impl;

import com.secusoft.web.mapper.ViBasicMemberMapper;
import com.secusoft.web.mapper.ViGazhkSjzyzhQgpqryMapper;
import com.secusoft.web.mapper.ViRepoMapper;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.ViBasicMemberBean;
import com.secusoft.web.model.ViRepoBean;
import com.secusoft.web.model.gazhk.ViGazhkSjzyzhQgpqryBean;
import com.secusoft.web.service.ViGazhkSjzyzhQgpqryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * code is far away from bug with the animal protecting
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 *
 * @Description : 全国扒窃人员Impl
 * ---------------------------------
 * @author chjiang
 * @since 2019/6/10 19:30
 */
@Service
public class ViGazhkSjzyzhQgpqryServiceImpl implements ViGazhkSjzyzhQgpqryService {

    @Resource
    ViGazhkSjzyzhQgpqryMapper viGazhkSjzyzhQgpqryMapper;

    @Resource
    ViRepoMapper viRepoMapper;

    @Resource
    ViBasicMemberMapper viBasicMemberMapper;


    @Override
    public ResultVo addViGazhkSjzyzhQgpqry(ViGazhkSjzyzhQgpqryBean viGazhkSjzyzhQgpqryBean,String tableName) {
        ViRepoBean viRepoBean=new ViRepoBean();
        viRepoBean.setBkname("全国扒窃人员");
        viRepoBean.setType(0);
        viRepoBean.setTableName("vi_gazhk_sjzyzh_qgpqry");
        List<ViRepoBean> list = viRepoMapper.getAllViRepo(viRepoBean);
        //判断布控库是否存在
        if(list.size()>0){
            viRepoBean=list.get(0);
        }else{
            viRepoMapper.insertViRepo(viRepoBean);
        }
        viGazhkSjzyzhQgpqryMapper.truncateTable();
        viGazhkSjzyzhQgpqryMapper.addViGazhkSjzyzhQgpqry(viGazhkSjzyzhQgpqryBean);

        ViBasicMemberBean viBasicMemberBean = new ViBasicMemberBean();
        viBasicMemberBean.setObjectId("vi_gazhk_sjzyzh_qgpqry" + viGazhkSjzyzhQgpqryBean.getXh());
        viBasicMemberBean.setRepoId(viRepoBean.getId());
        viBasicMemberBean.setRealObjectId(viGazhkSjzyzhQgpqryBean.getXh().toString());
        viBasicMemberBean.setRealTableName("vi_gazhk_sjzyzh_qgpqry");
        viBasicMemberBean.setIdentityId(viGazhkSjzyzhQgpqryBean.getSfzh());
        viBasicMemberBean.setIdentityName(viGazhkSjzyzhQgpqryBean.getXm().trim());
        viBasicMemberBean.setStatus(1);

        List<ViBasicMemberBean> allViBasicMember = viBasicMemberMapper.getAllViBasicMember(viBasicMemberBean.getObjectId());
        if(allViBasicMember.size()==0) {
            viBasicMemberMapper.insertViBasicMember(viBasicMemberBean);
        }else{
            System.out.println("已存在"+viBasicMemberBean.getIdentityName());
        }
        return ResultVo.success();
    }

    @Override
    public ResultVo truncateTable() {
        viGazhkSjzyzhQgpqryMapper.truncateTable();
        return ResultVo.success();
    }
}
