package com.secusoft.web.service.impl;

import com.secusoft.web.core.emuns.ViRepoBkTypeEnum;
import com.secusoft.web.mapper.SecurityPlaceMapper;
import com.secusoft.web.mapper.SecurityTaskTypeMapper;
import com.secusoft.web.mapper.ViRepoMapper;
import com.secusoft.web.model.*;
import com.secusoft.web.service.SecurityTaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huanghao
 * @date 2019-07-02
 */
@Service
public class SecurityTaskServiceImpl implements SecurityTaskService {
    @Resource
    ViRepoMapper viRepoMapper;

    @Resource
    SecurityTaskTypeMapper securityTaskTypeMapper;

    @Resource
    SecurityPlaceMapper securityPlaceMapper;

    @Override
    public ResultVo getSecurityTypePlace() {
        List<SecurityTaskTypeBean> securityTaskTypeA = securityTaskTypeMapper.selectsecurityTaskTypeA();
        List<SecurityTaskTypeBean> securityTaskTypeB = securityTaskTypeMapper.selectsecurityTaskTypeB();
        securityTaskTypeA.addAll(securityTaskTypeB);

        List<SecurityPlaceBean> securityPlaceBeans = securityPlaceMapper.selectSecirityTaskPlace();

        List<SecurityTaskTypeBean> securityTaskTypeBeans = securityTaskTypeMapper.selectsecurityFKType();

        ArrayList<List> result = new ArrayList<>();
        result.add(securityTaskTypeA);
        result.add(securityPlaceBeans);
        result.add(securityTaskTypeBeans);
        return ResultVo.success(result);
    }

    @Override
    public ResultVo getSecurityTaskType() {
        List<SecurityTaskTypeBean> securityTaskTypeBeans = securityTaskTypeMapper.selectsecurityTaskType();
        return ResultVo.success(securityTaskTypeBeans);
    }

    @Override
    public ResultVo getAllViRepo() {
        ViRepoBean viRepoBean = new ViRepoBean();
        viRepoBean.setTableName(null);
        viRepoBean.setBktype(null);
        viRepoBean.setType(null);
        List<ViRepoBean> viRepoBeanList = viRepoMapper.getAllViRepo(viRepoBean);
        HashMap<String, Object> map1 = new HashMap<>();
        HashMap<String, Object> map2 = new HashMap<>();
        ArrayList<ViRepoBean> personRepoBeans = new ArrayList<>();
        ArrayList<ViRepoBean> carRepoBeans = new ArrayList<>();
        for (ViRepoBean virepo:viRepoBeanList) {
            if(virepo.getBktype().equals(ViRepoBkTypeEnum.VIREPO_BKTYPE_PEOPLE.getCode()) ){
                personRepoBeans.add(virepo);
            }else if(virepo.getBktype().equals(ViRepoBkTypeEnum.VIREPO_BKTYPE_CAR.getCode())){
                carRepoBeans.add(virepo);
            }
        }
        map1.put("title","人员库");
        map1.put("type","person");
        map1.put("children",personRepoBeans);

        map2.put("title","车辆库");
        map2.put("type","car");
        map2.put("children",carRepoBeans);
        ArrayList<Map> list = new ArrayList<>();
        list.add(map1);
        list.add(map2);
        return ResultVo.success(list);
    }

    @Override
    public ResultVo getSecurityTaskTypeRepo() {
        return ResultVo.success(securityTaskTypeMapper.selectsecurityTaskTypeRepo());
    }

    @Override
    @Transactional
    public ResultVo setSecurityTaskTypeRepo(List<SecurityTaskTypeRepoBean> securityTaskTypeRepoBeans) {
        for (SecurityTaskTypeRepoBean securityTaskTypeRepoBean:securityTaskTypeRepoBeans){
            securityTaskTypeMapper.deletesecurityTaskTypeRepoById(securityTaskTypeRepoBean.getId());
            securityTaskTypeMapper.insertsecurityTaskTypeRepo(securityTaskTypeRepoBean.getRepoIds(),securityTaskTypeRepoBean.getId());
        }
        return ResultVo.success(securityTaskTypeMapper.selectsecurityTaskTypeRepo());
    }
}
