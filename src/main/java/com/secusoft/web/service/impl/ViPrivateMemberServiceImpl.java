package com.secusoft.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.secusoft.web.config.BkrepoConfig;
import com.secusoft.web.config.ServiceApiConfig;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.core.util.UploadUtil;
import com.secusoft.web.mapper.ViBasicMemberMapper;
import com.secusoft.web.mapper.ViPrivateMemberMapper;
import com.secusoft.web.model.*;
import com.secusoft.web.service.ViPrivateMemberService;
import com.secusoft.web.serviceapi.ServiceApiClient;
import com.secusoft.web.serviceapi.model.BaseResponse;
import com.secusoft.web.tusouapi.model.BKMemberAddRequest;
import com.secusoft.web.tusouapi.model.BKMemberDeleteRequest;
import com.secusoft.web.tusouapi.model.BaseRequest;
import com.secusoft.web.utils.ImageUtils;
import com.secusoft.web.utils.PageReturnUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author chjiang
 * @since 2019/6/6 14:52
 */
@Service
public class ViPrivateMemberServiceImpl implements ViPrivateMemberService {

    private static Logger log = LoggerFactory.getLogger(ViPrivateMemberServiceImpl.class);

    @Resource
    ViPrivateMemberMapper viPrivateMemberMapper;

    @Resource
    ViBasicMemberMapper viBasicMemberMapper;

    @Autowired
    BkrepoConfig bkrepoConfig;

    @Transactional
    @Override
    public ResultVo insertViPrivateMember(ViPrivateMemberBean viPrivateMemberBean, HttpServletRequest request) {
        log.info("开始创建自定义布控库布控目标");
        if (!StringUtils.hasLength(viPrivateMemberBean.getIdentityId())) {
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_IDENTITYID_NULL.getCode(),
                    BizExceptionEnum.PRIVATEREPO_IDENTITYID_NULL.getMessage());
        }
        if (!StringUtils.hasLength(viPrivateMemberBean.getIdentityName())) {
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_IDENTITYNAME_NULL.getCode(),
                    BizExceptionEnum.PRIVATEREPO_IDENTITYNAME_NULL.getMessage());
        }
        if (!StringUtils.hasLength(viPrivateMemberBean.getImageUrl())) {
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_IMAGEURL_NULL.getCode(),
                    BizExceptionEnum.PRIVATEREPO_IMAGEURL_NULL.getMessage());
        }
        if (!StringUtils.hasLength(viPrivateMemberBean.getRepoId())) {
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_REPOID_NULL.getCode(),
                    BizExceptionEnum.PRIVATEREPO_REPOID_NULL.getMessage());
        }
        String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();//访问路径
        String base64=viPrivateMemberBean.getImageUrl();
        viPrivateMemberBean.setObjectId("vi_private_" + viPrivateMemberBean.getIdentityId());
        try {
            viPrivateMemberBean.setImageUrl(UploadUtil.downLoadFromBase64(viPrivateMemberBean.getImageUrl().split(",")[1], "Bkmember"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        viPrivateMemberMapper.insertViPrivateMember(viPrivateMemberBean);

        //添加布控目标
        BaseRequest<BKMemberAddRequest> bkMemberAddRequestBaseRequest = new BaseRequest<>();
        bkMemberAddRequestBaseRequest.setRequestId(bkrepoConfig.getRequestId());
        BKMemberAddRequest bkMemberAddRequest = new BKMemberAddRequest();
        bkMemberAddRequest.setObjectId(viPrivateMemberBean.getObjectId());
        bkMemberAddRequest.setContent(base64.split(",")[1]);
        bkMemberAddRequest.setBkid(bkrepoConfig.getBkid());
        bkMemberAddRequestBaseRequest.setData(bkMemberAddRequest);

        String requestStr = JSON.toJSONString(bkMemberAddRequestBaseRequest);
        System.out.println(requestStr);
        BaseResponse baseResponse = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBkmemberAdd(), bkMemberAddRequestBaseRequest);
//        BaseResponse baseResponse = new BaseResponse();
//        baseResponse.setCode(String.valueOf(BizExceptionEnum.OK.getCode()));
        String code = baseResponse.getCode();
        if (!String.valueOf(BizExceptionEnum.OK.getCode()).equals(code)) {
            log.info("创建自定义布控库布控目标失败");
            throw new RuntimeException("布控目标创建失败");
        }

        log.info("结束创建自定义布控库布控目标");
        return ResultVo.success();
    }

    @Override
    public ResultVo updateViPrivateMember(ViPrivateMemberBean viPrivateMemberBean, HttpServletRequest request) {
        log.info("开始布控目标更新");
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
            return ResultVo.failure(BizExceptionEnum.BKMEMBER_ID_NULL.getCode(), BizExceptionEnum.BKMEMBER_ID_NULL.getMessage());
        }
        ViPrivateMemberBean bean = viPrivateMemberMapper.getViPrivateMemberByBean(viPrivateMemberBean);

        if (null == bean) {
            return ResultVo.failure(BizExceptionEnum.BKMEMBER_EXISTED.getCode(), BizExceptionEnum.BKMEMBER_EXISTED.getMessage());
        }

        String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();//访问路径
        String oldBase64 = ImageUtils.image2Base64(returnUrl + bean.getImageUrl());

        log.info("oldBase64：" + oldBase64);
        log.info("NewBase64：" + viPrivateMemberBean.getImageUrl().split(",")[1]);
        if (!oldBase64.equals(viPrivateMemberBean.getImageUrl().split(",")[1])) {

            try {
                viPrivateMemberBean.setImageUrl(UploadUtil.downLoadFromBase64(viPrivateMemberBean.getImageUrl(), "Bkmember"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            //删除布控目标
            BaseRequest<BKMemberDeleteRequest> bkMemberDeleteRequestBaseRequest = new BaseRequest<>();
            bkMemberDeleteRequestBaseRequest.setRequestId(bkrepoConfig.getRequestId());
            BKMemberDeleteRequest bkMemberDeleteRequest = new BKMemberDeleteRequest();
            bkMemberDeleteRequest.setObjectIds(viPrivateMemberBean.getObjectId());
            bkMemberDeleteRequest.setBkid(bkrepoConfig.getBkid());
            bkMemberDeleteRequestBaseRequest.setData(bkMemberDeleteRequest);
            BaseResponse baseResponse = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBkmemberDelete(),bkMemberDeleteRequestBaseRequest);
//            BaseResponse baseResponse = new BaseResponse();
//            baseResponse.setCode(String.valueOf(BizExceptionEnum.OK.getCode()));
            String code = baseResponse.getCode();
            if (String.valueOf(BizExceptionEnum.OK.getCode()).equals(code)) {
                //添加布控目标
                BaseRequest<BKMemberAddRequest> bkMemberAddRequestBaseRequest = new BaseRequest<>();
                bkMemberAddRequestBaseRequest.setRequestId(bkrepoConfig.getRequestId());
                BKMemberAddRequest bkMemberAddRequest = new BKMemberAddRequest();
                bkMemberAddRequest.setObjectId(bean.getObjectId());
                bkMemberAddRequest.setBkid(bkrepoConfig.getBkid());
                bkMemberAddRequest.setContent(viPrivateMemberBean.getImageUrl());
                bkMemberAddRequestBaseRequest.setData(bkMemberAddRequest);

                baseResponse = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBkmemberAdd(), bkMemberAddRequestBaseRequest);
                //baseResponse.setCode(String.valueOf(BizExceptionEnum.OK.getCode()));
                code = baseResponse.getCode();
                if (!String.valueOf(BizExceptionEnum.OK.getCode()).equals(code)) {
                    throw new RuntimeException("新布控目标创建失败");
                }
            } else {
                throw new RuntimeException("删除布控目标失败");
            }
        }else {
            viPrivateMemberBean.setImageUrl(bean.getImageUrl());
        }
        viPrivateMemberBean.setObjectId(bean.getObjectId());
        viPrivateMemberBean.setId(bean.getId());
        viPrivateMemberBean.setModifyTime(new Date());
        viPrivateMemberBean.setCreateTime(bean.getCreateTime());
        viPrivateMemberBean.setRepoId(bean.getRepoId());
        viPrivateMemberMapper.updateViPrivateMember(viPrivateMemberBean);
        log.info("结束布控目标更新");
        return ResultVo.success();
    }

    @Override
    public ResultVo delViPrivateMember(ViPrivateMemberVo viPrivateMemberVo) {
        log.info("开始布控目标删除");
        if (viPrivateMemberVo.getDelIds().length <= 0) {
            return ResultVo.failure(BizExceptionEnum.BKMEMBER_ID_NULL.getCode(), BizExceptionEnum.BKMEMBER_ID_NULL.getMessage());
        }

        for (String str : viPrivateMemberVo.getDelIds()) {
            ViPrivateMemberBean viPrivateMemberBean = new ViPrivateMemberBean();
            viPrivateMemberBean.setId(Integer.parseInt(str));
            viPrivateMemberBean = viPrivateMemberMapper.getViPrivateMemberByBean(viPrivateMemberBean);

            //删除布控目标
            BaseRequest<BKMemberDeleteRequest> bkMemberDeleteRequestBaseRequest = new BaseRequest<>();
            bkMemberDeleteRequestBaseRequest.setRequestId(bkrepoConfig.getRequestId());
            BKMemberDeleteRequest bkMemberDeleteRequest = new BKMemberDeleteRequest();
            bkMemberDeleteRequest.setObjectIds(viPrivateMemberBean.getObjectId());
            bkMemberDeleteRequest.setBkid(bkrepoConfig.getBkid());
            bkMemberDeleteRequestBaseRequest.setData(bkMemberDeleteRequest);

            String requestStr = JSON.toJSONString(bkMemberDeleteRequestBaseRequest);
            System.out.println("bkMemberDeleteRequestBaseRequest：" + requestStr);
            BaseResponse baseResponse = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBkmemberDelete() , bkMemberDeleteRequestBaseRequest);
//            BaseResponse baseResponse = new BaseResponse();
//            baseResponse.setCode(String.valueOf(BizExceptionEnum.OK.getCode()));
            Object dataJson = baseResponse.getData();
            String errorCode = baseResponse.getCode();
            String errorMsg = baseResponse.getMessage();
            if (String.valueOf(BizExceptionEnum.OK.getCode()).equals(errorCode)) {
                log.info("布控目标删除成功");
                viPrivateMemberMapper.delViPrivateMember(viPrivateMemberBean.getId());
                Path path1 = Paths.get(UploadUtil.basePath, viPrivateMemberBean.getImageUrl());
                try {
                    Files.deleteIfExists(path1);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else {
                log.info("布控目标删除失败");
                return ResultVo.failure(BizExceptionEnum.BKMEMBER_DELETE_FAIL.getCode(), BizExceptionEnum.BKMEMBER_DELETE_FAIL.getMessage());
            }
        }
        log.info("结束布控目标删除");
        return ResultVo.success();
    }

    @Override
    public ResultVo getAllViPrivateMember(ViPrivateMemberVo viPrivateMemberVo) {
        List<ViMemberVo> list = null;
        PageHelper.startPage(viPrivateMemberVo.getCurrent(), viPrivateMemberVo.getSize());
        if (viPrivateMemberVo.getType() == 1) {
            list = viPrivateMemberMapper.getAllViPrivateMember(viPrivateMemberVo);
        } else {
            ViBasicMemberBean viBasicMemberBean = new ViBasicMemberBean();
            viBasicMemberBean.setRepoId(viPrivateMemberVo.getRepoId());
            viBasicMemberBean.setIdentityName(viPrivateMemberVo.getSearchValue());
            list = viBasicMemberMapper.getAllViBasicMember(viBasicMemberBean);
        }
        return ResultVo.success(PageReturnUtils.getPageMap(list, viPrivateMemberVo.getCurrent(), viPrivateMemberVo.getSize()));
    }

    @Override
    public ResultVo uploadImg(ViPrivateMemberVo viPrivateMemberVo) {

        //获取文件名加后缀
        if (!StringUtils.hasLength(viPrivateMemberVo.getFile())) {
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }

        //通过base64存储图片
        String fileName = null;
        try {
            fileName = UploadUtil.downLoadFromBase64(viPrivateMemberVo.getFile(), "Bkmember");
        } catch (IOException e) {
            e.printStackTrace();
            return ResultVo.failure(BizExceptionEnum.SERVER_ERROR);
        }

        if (!StringUtils.hasLength(fileName)) {
            return ResultVo.failure(BizExceptionEnum.SERVER_ERROR);
        }

        Map<String, String> map = new HashMap<>();
        map.put("fileName", fileName);
        return ResultVo.success(map);
    }

    @Override
    public ResultVo getInfoSearchMember(ViPrivateMemberVo viPrivateMemberVo) {
        if (null == viPrivateMemberVo) {
            return ResultVo.failure(BizExceptionEnum.PARAM_NULL.getCode(), BizExceptionEnum.PARAM_NULL.getMessage());
        }

        PageHelper.startPage(viPrivateMemberVo.getCurrent(), viPrivateMemberVo.getSize());
        List<ViMemberVo> infoSearchMemberList = viPrivateMemberMapper.getInfoSearchMember(viPrivateMemberVo);

        return ResultVo.success(PageReturnUtils.getPageMap(infoSearchMemberList, viPrivateMemberVo.getCurrent(), viPrivateMemberVo.getSize()));
    }


}
