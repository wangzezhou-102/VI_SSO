package com.secusoft.web.service.impl;

import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.core.exception.BussinessException;
import com.secusoft.web.mapper.PatrolRouteDeviceMapper;
import com.secusoft.web.mapper.PatrolRouteMapper;
import com.secusoft.web.model.DeviceBean;
import com.secusoft.web.model.PatrolRouteBean;
import com.secusoft.web.model.PatrolRouteDeviceBean;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.PatrolRouteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 巡逻路线service
 * @author Wangzezhou
 * @company 视在数科
 * @date 2019年7月2日
 */
@Service
public class PatrolRouteServiceImpl implements PatrolRouteService {

    @Resource
    private PatrolRouteMapper patrolRouteMapper;
    @Resource
    private PatrolRouteDeviceMapper patrolRouteDeviceMapper;

    //查询不为删除态的所有路线 删除—3
    @Override
    public ResultVo selectPatrolRouteByStatus(PatrolRouteBean patrolRouteBean) {
        List<PatrolRouteBean> patrolRouteBeans = patrolRouteMapper.selectPatrolRouteByStatus(patrolRouteBean);
        return ResultVo.success(patrolRouteBeans);
    }
    //查询一条路线信息
    public ResultVo selectPatrolRouteById(PatrolRouteBean patrolRouteBean){
        PatrolRouteBean patrolRouteBean1 = patrolRouteMapper.selectPatrolRouteByPrimaryKey(patrolRouteBean.getId());
        return ResultVo.success(patrolRouteBean1);
    }
    //新增路线
    @Override
    @Transactional
    public ResultVo insertPatroRoute(PatrolRouteBean patrolRouteBean) {
        List<DeviceBean> devices = patrolRouteBean.getDevices();
        //查询非删除状态下的路线
        patrolRouteBean.setStatus(3);
        List<PatrolRouteBean> patrolRouteBeans = patrolRouteMapper.selectPatrolRouteByStatus(patrolRouteBean);
        boolean isSame = false;
        for (PatrolRouteBean patrolRouteBean1: patrolRouteBeans) {
            //路线名称
            if(patrolRouteBean1.getRouteName().equals(patrolRouteBean.getRouteName())){
               isSame = true;
               throw new BussinessException(BizExceptionEnum.REPEAT);
            }
        }
        if(!isSame){
            //添加路线
            patrolRouteBean.setCreateTime(new Date());
            patrolRouteBean.setUpdateTime(new Date());
            patrolRouteBean.setUpdateId(patrolRouteBean.getCreateId());
            patrolRouteBean.setStatus(0);
            patrolRouteMapper.insertPatrolRoute(patrolRouteBean);
        }
        //根据路线名称获取路线id
        PatrolRouteBean patrolRouteBean1 = patrolRouteMapper.selectPatrolRouteByRouteName(patrolRouteBean);
        //添加路线对应设备
        for (DeviceBean deviceBean: devices) {
            PatrolRouteDeviceBean patrolRouteDeviceBean = new PatrolRouteDeviceBean();
            patrolRouteDeviceBean.setRouteId(patrolRouteBean1.getId());
            patrolRouteDeviceBean.setDeviceId(deviceBean.getDeviceId());
            patrolRouteDeviceMapper.insertPatrolRouteDevice(patrolRouteDeviceBean);
        }
        return ResultVo.success();
    }
    //删除路线
    @Override
    public ResultVo deletePatrolRoute(PatrolRouteBean patrolRouteBean){
        //根据Id修改路线状态
        patrolRouteMapper.updatePatrolRouteByPrimaryKey(patrolRouteBean);
        return ResultVo.success();
    }
    //修改路线信息
    @Override
    @Transactional
    public ResultVo updatePatroRoute(PatrolRouteBean patrolRouteBean){
        //查询非删除状态下的路线
        patrolRouteBean.setStatus(3);
        List<PatrolRouteBean> patrolRouteBeans = patrolRouteMapper.selectPatrolRouteByStatus(patrolRouteBean);
        boolean isUp = true;
        for (PatrolRouteBean patrolRouteBean1: patrolRouteBeans) {
            //路线名称
            if(patrolRouteBean1.getRouteName().equals(patrolRouteBean.getRouteName())){
                //路线名称没有修改情况
                if(patrolRouteBean.getId() == patrolRouteBean1.getId()){
                    isUp = false;
                }else{
                    throw new BussinessException(BizExceptionEnum.REPEAT);
                }
            }
        }
        if(isUp){
            patrolRouteMapper.updatePatrolRouteByPrimaryKey(patrolRouteBean);
        }
        List<DeviceBean> devices = patrolRouteBean.getDevices();
        if(devices.isEmpty()){
            PatrolRouteDeviceBean patrolRouteDeviceBean = new PatrolRouteDeviceBean();
            patrolRouteDeviceBean.setRouteId(patrolRouteBean.getId());
            patrolRouteDeviceMapper.deletePatrolRouteDevice(patrolRouteDeviceBean);
        }else{
            PatrolRouteDeviceBean patrolRouteDeviceBean = new PatrolRouteDeviceBean();
            patrolRouteDeviceBean.setRouteId(patrolRouteBean.getId());
            patrolRouteDeviceMapper.deletePatrolRouteDevice(patrolRouteDeviceBean);
            for (DeviceBean deviceBean:devices) {
                patrolRouteDeviceBean.setDeviceId(deviceBean.getDeviceId());
                patrolRouteDeviceMapper.insertPatrolRouteDevice(patrolRouteDeviceBean);
            }
        }
        return ResultVo.success();
    }
}
