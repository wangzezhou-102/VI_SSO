package com.secusoft.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.secusoft.web.config.BkrepoConfig;
import com.secusoft.web.config.ServiceApiConfig;
import com.secusoft.web.core.util.StringUtils;
import com.secusoft.web.mapper.ViBasicMemberMapper;
import com.secusoft.web.mapper.ViGazhkSjzyzhQgpqryMapper;
import com.secusoft.web.mapper.ViRepoMapper;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.ViBasicMemberBean;
import com.secusoft.web.model.ViRepoBean;
import com.secusoft.web.model.gazhk.ViGazhkSjzyzhQgpqryBean;
import com.secusoft.web.service.ViGazhkSjzyzhQgpqryService;
import com.secusoft.web.serviceapi.ServiceApiClient;
import com.secusoft.web.tusouapi.model.BKMemberAddRequest;
import com.secusoft.web.tusouapi.model.BKMemberDeleteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
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
 * @author chjiang
 * @Description : 全国扒窃人员Impl
 * ---------------------------------
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


    private ServiceApiConfig serviceApiConfig;
    private BkrepoConfig bkrepoConfig;

    @Autowired
    public ViGazhkSjzyzhQgpqryServiceImpl(ServiceApiConfig serviceApiConfig,
                         BkrepoConfig bkrepoConfig){
        this.serviceApiConfig = serviceApiConfig;
        this.bkrepoConfig = bkrepoConfig;
    }


    @Override
    public ResultVo addViGazhkSjzyzhQgpqry(ViGazhkSjzyzhQgpqryBean viGazhkSjzyzhQgpqryBean, String tableName) {
        ViRepoBean viRepoBean = new ViRepoBean();
        viRepoBean.setBkname("全国扒窃人员");
        viRepoBean.setType(0);
        viRepoBean.setTableName("vi_gazhk_sjzyzh_qgpqry");
        List<ViRepoBean> list = viRepoMapper.getAllViRepo(viRepoBean);
        //判断布控库是否存在
        if (list.size() > 0) {
            viRepoBean = list.get(0);
        } else {
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

        ViBasicMemberBean bean = viBasicMemberMapper.getViBasicMemberByObjectId(viBasicMemberBean);
        if (null==bean) {
            viBasicMemberMapper.insertViBasicMember(viBasicMemberBean);
        } else {
            //判断身份证号是否有变化
            if (bean.getIdentityId() != viBasicMemberBean.getIdentityId()) {
                bean.setIdentityId(viBasicMemberBean.getIdentityId());
            }
            //判断姓名是否有变化
            if (bean.getIdentityName() != viBasicMemberBean.getIdentityName()) {
                bean.setIdentityName(viBasicMemberBean.getIdentityName());
            }
            viBasicMemberMapper.updateViBasicMember(bean);
            //删除布控人员接口
            BKMemberDeleteRequest bkMemberDeleteRequest=new BKMemberDeleteRequest();
            bkMemberDeleteRequest.setBkid(bkrepoConfig.getBkid());
            bkMemberDeleteRequest.setObjectIds(bean.getObjectId());
            String requestStr = JSON.toJSONString(bkMemberDeleteRequest);
            ServiceApiClient.getClientConnectionPool().fetchByPostMethod(serviceApiConfig.getPathBkmemberDelete(), requestStr);
        }
        BKMemberAddRequest bkMemberAddRequest=new BKMemberAddRequest();
        bkMemberAddRequest.setBkid(bkrepoConfig.getBkid());
        bkMemberAddRequest.setBkid(viBasicMemberBean.getObjectId());
        bkMemberAddRequest.setContent(viBasicMemberBean.getContent());
        bkMemberAddRequest.setOssUrl(viBasicMemberBean.getImageUrl());
        bkMemberAddRequest.setFeature(viBasicMemberBean.getFeature());
        if(StringUtils.isNotEmpty(viBasicMemberBean.getAttribute())) {
            bkMemberAddRequest.setAttribute((JSONObject) JSONObject.parse(viBasicMemberBean.getAttribute()));
        }
        String requestStr = JSON.toJSONString(bkMemberAddRequest);
        ServiceApiClient.getClientConnectionPool().fetchByPostMethod(serviceApiConfig.getPathBkmemberAdd(), requestStr);
        return ResultVo.success();
    }

    @Override
    public ResultVo truncateTable() {
        viGazhkSjzyzhQgpqryMapper.truncateTable();
        return ResultVo.success();
    }
}
