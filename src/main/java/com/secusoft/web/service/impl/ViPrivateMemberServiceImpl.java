package com.secusoft.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.secusoft.web.config.BkrepoConfig;
import com.secusoft.web.config.ServiceApiConfig;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.mapper.ViPrivateMemberMapper;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.ViPrivateMemberBean;
import com.secusoft.web.model.ViPrivateMemberVo;
import com.secusoft.web.service.ViPrivateMemberService;
import com.secusoft.web.serviceapi.ServiceApiClient;
import com.secusoft.web.serviceapi.model.BaseResponse;
import com.secusoft.web.tusouapi.model.BKMemberAddRequest;
import com.secusoft.web.tusouapi.model.BKMemberDeleteRequest;
import com.secusoft.web.tusouapi.model.BaseRequest;
import com.secusoft.web.utils.PageReturnUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    @Override
    public ResultVo insertViPrivateMember(ViPrivateMemberBean viPrivateMemberBean) {
        if (!StringUtils.hasLength(viPrivateMemberBean.getIdentityName())) {
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_IDENTITYNAME_NULL.getCode(),
                    BizExceptionEnum.PRIVATEREPO_IDENTITYNAME_NULL.getMessage());
        }
        if (!StringUtils.hasLength(viPrivateMemberBean.getIdentityId())) {
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_IDENTITYID_NULL.getCode(),
                    BizExceptionEnum.PRIVATEREPO_IDENTITYID_NULL.getMessage());
        }
        if (!StringUtils.hasLength(viPrivateMemberBean.getImageUrl())) {
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_IMAGEURL_NULL.getCode(),
                    BizExceptionEnum.PRIVATEREPO_IMAGEURL_NULL.getMessage());
        }
        if (!StringUtils.hasLength(viPrivateMemberBean.getRepoId())) {
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_REPOID_NULL.getCode(),
                    BizExceptionEnum.PRIVATEREPO_REPOID_NULL.getMessage());
        }
        //由于object_id唯一，先放置一个随机数区别
        int randomNumber = (int) Math.round(Math.random() * (25000 - 1) + 1);
        viPrivateMemberBean.setObjectId("vi_private_" + randomNumber);

        viPrivateMemberMapper.insertViPrivateMember(viPrivateMemberBean);
        viPrivateMemberBean.setObjectId("vi_private_" + viPrivateMemberBean.getId());

        viPrivateMemberMapper.updateViPrivateMember(viPrivateMemberBean);

        //添加布控目标
        BaseRequest<BKMemberAddRequest> bkMemberAddRequestBaseRequest = new BaseRequest<>();
        bkMemberAddRequestBaseRequest.setRequestId(bkrepoConfig.getRequestId());
        BKMemberAddRequest bkMemberAddRequest = new BKMemberAddRequest();
        bkMemberAddRequest.setObjectId(viPrivateMemberBean.getObjectId());
        bkMemberAddRequest.setBkid(bkrepoConfig.getBkid());
        bkMemberAddRequest.setOssUrl(viPrivateMemberBean.getImageUrl());
        bkMemberAddRequestBaseRequest.setData(bkMemberAddRequest);

        String requestStr = JSON.toJSONString(bkMemberAddRequestBaseRequest);
        System.out.println(requestStr);
        BaseResponse baseResponse =
                ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBkmemberAdd(),
                        bkMemberAddRequestBaseRequest);

        String code = baseResponse.getCode();
        if (!String.valueOf(BizExceptionEnum.OK.getCode()).equals(code)) {
            throw new RuntimeException("布控目标创建失败");
        }
        return ResultVo.success();
    }

    @Override
    public ResultVo updateViPrivateMember(ViPrivateMemberBean viPrivateMemberBean) {
        if (null == viPrivateMemberBean) {
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        if (!StringUtils.hasLength(viPrivateMemberBean.getIdentityName())) {
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_IDENTITYNAME_NULL.getCode(),
                    BizExceptionEnum.PRIVATEREPO_IDENTITYNAME_NULL.getMessage());
        }
        if (!StringUtils.hasLength(viPrivateMemberBean.getIdentityId())) {
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_IDENTITYID_NULL.getCode(),
                    BizExceptionEnum.PRIVATEREPO_IDENTITYID_NULL.getMessage());
        }
        if (!StringUtils.hasLength(viPrivateMemberBean.getImageUrl())) {
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_IMAGEURL_NULL.getCode(),
                    BizExceptionEnum.PRIVATEREPO_IMAGEURL_NULL.getMessage());
        }
//        if (!StringUtils.hasLength(viPrivateMemberBean.getRepoId())) {
//            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_REPOID_NULL.getCode(),
//                    BizExceptionEnum.PRIVATEREPO_REPOID_NULL.getMessage());
//        }
        if (null == viPrivateMemberBean.getId() && null == viPrivateMemberBean.getObjectId() || viPrivateMemberBean.getId() == 0) {
            return ResultVo.failure(BizExceptionEnum.BKMEMBER_DELETE_NULL.getCode(), BizExceptionEnum.BKMEMBER_DELETE_NULL.getMessage());
        }
        ViPrivateMemberBean bean = null;
        if (0 != viPrivateMemberBean.getId()) {
            bean = viPrivateMemberMapper.selectById(viPrivateMemberBean.getId());
        } else {
            bean = viPrivateMemberMapper.getViPrivateMemberByObjectId(viPrivateMemberBean.getObjectId());
        }
        if (null == bean) {
            return ResultVo.failure(BizExceptionEnum.BKMEMBER_EXISTED.getCode(), BizExceptionEnum.BKMEMBER_EXISTED.getMessage());
        }
        if (!bean.getImageUrl().equals(viPrivateMemberBean.getImageUrl())) {
            //删除布控目标
            BaseRequest<BKMemberDeleteRequest> bkMemberDeleteRequestBaseRequest = new BaseRequest<>();
            bkMemberDeleteRequestBaseRequest.setRequestId(bkrepoConfig.getRequestId());
            BKMemberDeleteRequest bkMemberDeleteRequest = new BKMemberDeleteRequest();
            bkMemberDeleteRequest.setObjectIds(viPrivateMemberBean.getObjectId());
            bkMemberDeleteRequest.setBkid(bkrepoConfig.getBkid());
            bkMemberDeleteRequestBaseRequest.setData(bkMemberDeleteRequest);
            BaseResponse baseResponse =
                    ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBkmemberDelete(), bkMemberDeleteRequestBaseRequest);
            String code = baseResponse.getCode();
            if (String.valueOf(BizExceptionEnum.OK.getCode()).equals(code)) {
                //添加布控目标
                BaseRequest<BKMemberAddRequest> bkMemberAddRequestBaseRequest = new BaseRequest<>();
                bkMemberAddRequestBaseRequest.setRequestId(bkrepoConfig.getRequestId());
                BKMemberAddRequest bkMemberAddRequest = new BKMemberAddRequest();
                bkMemberAddRequest.setObjectId(bean.getObjectId());
                bkMemberAddRequest.setBkid(bkrepoConfig.getBkid());
                bkMemberAddRequest.setOssUrl(viPrivateMemberBean.getImageUrl());
                bkMemberAddRequestBaseRequest.setData(bkMemberAddRequest);

                baseResponse =
                        ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBkmemberAdd(), bkMemberAddRequestBaseRequest);

                code = baseResponse.getCode();
                if (!String.valueOf(BizExceptionEnum.OK.getCode()).equals(code)) {
                    throw new RuntimeException("新布控目标创建失败");
                }
            } else {
                throw new RuntimeException("删除布控目标失败");
            }
        }
        viPrivateMemberBean.setObjectId(bean.getObjectId());
        viPrivateMemberBean.setId(bean.getId());
        viPrivateMemberBean.setModifyTime(new Date());
        viPrivateMemberBean.setCreateTime(bean.getCreateTime());
        viPrivateMemberBean.setRepoId(bean.getRepoId());
        viPrivateMemberMapper.updateViPrivateMember(viPrivateMemberBean);
        return ResultVo.success();
    }

    @Override
    public ResultVo delViPrivateMember(ViPrivateMemberBean viPrivateMemberBean) {
        if (null == viPrivateMemberBean) {
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }
        if (null == viPrivateMemberBean.getId() && null == viPrivateMemberBean.getObjectId() || viPrivateMemberBean.getId() == 0) {
            return ResultVo.failure(BizExceptionEnum.BKMEMBER_DELETE_NULL.getCode(),
                    BizExceptionEnum.BKMEMBER_DELETE_NULL.getMessage());
        }
        if (0 != viPrivateMemberBean.getId()) {
            viPrivateMemberBean = viPrivateMemberMapper.selectById(viPrivateMemberBean.getId());
        } else {
            viPrivateMemberBean = viPrivateMemberMapper.getViPrivateMemberByObjectId(viPrivateMemberBean.getObjectId());
        }
        //删除布控目标
        BaseRequest<BKMemberDeleteRequest> bkMemberDeleteRequestBaseRequest = new BaseRequest<>();
        bkMemberDeleteRequestBaseRequest.setRequestId(bkrepoConfig.getRequestId());
        BKMemberDeleteRequest bkMemberDeleteRequest = new BKMemberDeleteRequest();
        bkMemberDeleteRequest.setObjectIds(viPrivateMemberBean.getObjectId());
        bkMemberDeleteRequest.setBkid(bkrepoConfig.getBkid());
        bkMemberDeleteRequestBaseRequest.setData(bkMemberDeleteRequest);

        String requestStr = JSON.toJSONString(bkMemberDeleteRequestBaseRequest);
        System.out.println("bkMemberDeleteRequestBaseRequest：" + requestStr);
        BaseResponse baseResponse =
                ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBkmemberDelete()
                        , bkMemberDeleteRequestBaseRequest);

        String code = baseResponse.getCode();
        if (String.valueOf(BizExceptionEnum.OK.getCode()).equals(code)) {
            System.out.println("布控目标删除成功");
            viPrivateMemberMapper.delViPrivateMember(viPrivateMemberBean.getId());
        } else {
            System.out.println("布控目标删除失败");
            return ResultVo.failure(BizExceptionEnum.BKMEMBER_DELETE_FAIL.getCode(),
                    BizExceptionEnum.BKMEMBER_DELETE_FAIL.getMessage());
        }

        return ResultVo.success();
    }

    @Override
    public ResultVo getAllViPrivateMember(ViPrivateMemberVo viPrivateMemberVo) {
        PageHelper.startPage(viPrivateMemberVo.getCurrent(), viPrivateMemberVo.getSize());

        List<ViPrivateMemberBean> list = viPrivateMemberMapper.getAllViPrivateMember();

        return ResultVo.success(PageReturnUtils.getPageMap(list, viPrivateMemberVo.getCurrent(),
                viPrivateMemberVo.getSize()));
    }
}
