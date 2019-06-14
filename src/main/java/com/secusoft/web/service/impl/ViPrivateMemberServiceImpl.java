package com.secusoft.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.secusoft.web.config.BkrepoConfig;
import com.secusoft.web.config.ServiceApiConfig;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.mapper.ViPrivateMemberMapper;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.ViPrivateMemberBean;
import com.secusoft.web.model.ViPrivateMemberVo;
import com.secusoft.web.service.ViPrivateMemberService;
import com.secusoft.web.serviceapi.ServiceClient;
import com.secusoft.web.tusouapi.model.BKMemberAddRequest;
import com.secusoft.web.tusouapi.model.BKMemberDeleteRequest;
import com.secusoft.web.tusouapi.model.BaseRequest;
import com.secusoft.web.utils.PageReturnUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author chjiang
 * @since 2019/6/6 14:52
 */
@Service
public class ViPrivateMemberServiceImpl implements ViPrivateMemberService {

    @Resource
    ViPrivateMemberMapper viPrivateMemberMapper;

    @Autowired
    BkrepoConfig bkrepoConfig;

    @Override
    public ResultVo insertViPrivateMember(ViPrivateMemberBean viPrivateMemberBean) {
        if(!StringUtils.hasLength(viPrivateMemberBean.getIdentityName())){
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_IDENTITYNAME_NULL.getCode(), BizExceptionEnum.PRIVATEREPO_IDENTITYNAME_NULL.getMessage());
        }
        if(!StringUtils.hasLength(viPrivateMemberBean.getIdentityId())){
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_IDENTITYID_NULL.getCode(), BizExceptionEnum.PRIVATEREPO_IDENTITYID_NULL.getMessage());
        }
        if(!StringUtils.hasLength(viPrivateMemberBean.getIdentityId())){
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_IMAGEURL_NULL.getCode(), BizExceptionEnum.PRIVATEREPO_IMAGEURL_NULL.getMessage());
        }
        if(!StringUtils.hasLength(viPrivateMemberBean.getIdentityId())){
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_REPOID_NULL.getCode(), BizExceptionEnum.PRIVATEREPO_REPOID_NULL.getMessage());
        }
        //由于object_id唯一，先放置一个随机数区别
        int randomNumber = (int) Math.round(Math.random()*(25000-1)+1);
        viPrivateMemberBean.setObjectId("vi_private_"+randomNumber);

        viPrivateMemberMapper.insertViPrivateMember(viPrivateMemberBean);
        viPrivateMemberBean.setObjectId("vi_private_"+viPrivateMemberBean.getId());

        viPrivateMemberMapper.updateViPrivateMember(viPrivateMemberBean);

        //添加布控目标
        BaseRequest<BKMemberAddRequest> bkMemberAddRequestBaseRequest=new BaseRequest<>();
        bkMemberAddRequestBaseRequest.setRequestId(bkrepoConfig.getRequestId());
        BKMemberAddRequest bkMemberAddRequest=new BKMemberAddRequest();
        bkMemberAddRequest.setObjectId(viPrivateMemberBean.getObjectId());
        bkMemberAddRequest.setBkid(bkrepoConfig.getBkid());
        bkMemberAddRequestBaseRequest.setData(bkMemberAddRequest);

        String requestStr = JSON.toJSONString(bkMemberAddRequestBaseRequest);
        String responseBkrepoCreateStr = ServiceClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBkmemberAdd(), requestStr);
        //解析json
        JSONObject jsonObject= (JSONObject) JSONObject.parse(responseBkrepoCreateStr);
        String code=jsonObject.getString("code");
        if(String.valueOf(BizExceptionEnum.OK.getCode()).equals(code)){
            System.out.println("布控目标创建成功");
        }
        return ResultVo.success();
    }

    @Override
    public ResultVo updateViPrivateMember(ViPrivateMemberBean viPrivateMemberBean) {
        if(!StringUtils.hasLength(viPrivateMemberBean.getIdentityName())){
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_IDENTITYNAME_NULL.getCode(), BizExceptionEnum.PRIVATEREPO_IDENTITYNAME_NULL.getMessage());
        }
        if(!StringUtils.hasLength(viPrivateMemberBean.getIdentityId())){
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_IDENTITYID_NULL.getCode(), BizExceptionEnum.PRIVATEREPO_IDENTITYID_NULL.getMessage());
        }
        if(!StringUtils.hasLength(viPrivateMemberBean.getIdentityId())){
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_IMAGEURL_NULL.getCode(), BizExceptionEnum.PRIVATEREPO_IMAGEURL_NULL.getMessage());
        }
        if(!StringUtils.hasLength(viPrivateMemberBean.getIdentityId())){
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_REPOID_NULL.getCode(), BizExceptionEnum.PRIVATEREPO_REPOID_NULL.getMessage());
        }
        viPrivateMemberBean.setModifyTime(new Date());
        viPrivateMemberMapper.updateViPrivateMember(viPrivateMemberBean);
        return ResultVo.success();
    }

    @Override
    public ResultVo delViPrivateMember(String objectId) {
        if(StringUtils.hasLength(objectId)){
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }

        //删除布控目标
        BaseRequest<BKMemberDeleteRequest> bkMemberDeleteRequestBaseRequest=new BaseRequest<>();
        bkMemberDeleteRequestBaseRequest.setRequestId(bkrepoConfig.getRequestId());
        BKMemberDeleteRequest bkMemberDeleteRequest=new BKMemberDeleteRequest();
        bkMemberDeleteRequest.setObjectIds(objectId);
        bkMemberDeleteRequest.setBkid(bkrepoConfig.getBkid());
        bkMemberDeleteRequestBaseRequest.setData(bkMemberDeleteRequest);

        String requestStr = JSON.toJSONString(bkMemberDeleteRequestBaseRequest);
        String responseBkrepoCreateStr = ServiceClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBkmemberDelete(), requestStr);
        //解析json
        JSONObject jsonObject= (JSONObject) JSONObject.parse(responseBkrepoCreateStr);
        String code=jsonObject.getString("code");
        if(String.valueOf(BizExceptionEnum.OK.getCode()).equals(code)){
            System.out.println("布控目标删除成功");
            viPrivateMemberMapper.delViPrivateMember(objectId);
        }else{
            System.out.println("布控目标删除失败");
            return ResultVo.failure(BizExceptionEnum.BKMEMBER_FAIL.getCode(), BizExceptionEnum.BKMEMBER_FAIL.getMessage());
        }

        return ResultVo.success();
    }

    @Override
    public ResultVo getAllViPrivateMember(ViPrivateMemberVo viPrivateMemberVo) {
        PageHelper.startPage(viPrivateMemberVo.getCurrent(),viPrivateMemberVo.getSize());

        List<ViPrivateMemberBean> list=viPrivateMemberMapper.getAllViPrivateMember();

        return ResultVo.success(PageReturnUtils.getPageMap(list,viPrivateMemberVo.getCurrent(),viPrivateMemberVo.getSize()));
    }
}
