package com.secusoft.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.secusoft.web.config.BkrepoConfig;
import com.secusoft.web.config.ServiceApiConfig;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.core.util.UploadUtil;
import com.secusoft.web.mapper.ViPrivateMemberMapper;
import com.secusoft.web.mapper.ViRepoMapper;
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
import org.apache.catalina.servlet4preview.http.HttpServletRequest;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * 布控目标service
 * @author chjiang
 * @since 2019/6/6 14:52
 */
@Service
public class ViPrivateMemberServiceImpl implements ViPrivateMemberService {

    private static Logger log = LoggerFactory.getLogger(ViPrivateMemberServiceImpl.class);

    @Resource
    ViPrivateMemberMapper viPrivateMemberMapper;

    @Resource
    ViRepoMapper viRepoMapper;

    @Autowired
    BkrepoConfig bkrepoConfig;

    @Transactional
    @Override
    public ResultVo insertViPrivateMember(ViPrivateMemberBean viPrivateMemberBean, HttpServletRequest request) {
        log.info("开始创建自定义布控库布控目标");
        if (!StringUtils.hasLength(viPrivateMemberBean.getIdentityId())) {
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_IDENTITYID_NULL.getCode(), BizExceptionEnum.PRIVATEREPO_IDENTITYID_NULL.getMessage());
        }
        if (!StringUtils.hasLength(viPrivateMemberBean.getIdentityName())) {
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_IDENTITYNAME_NULL.getCode(), BizExceptionEnum.PRIVATEREPO_IDENTITYNAME_NULL.getMessage());
        }
        if (!StringUtils.hasLength(viPrivateMemberBean.getImageUrl()) || viPrivateMemberBean.getImageUrl() == null) {
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_IMAGEURL_NULL.getCode(), BizExceptionEnum.PRIVATEREPO_IMAGEURL_NULL.getMessage());
        }
        if (!StringUtils.hasLength(viPrivateMemberBean.getRepoId())) {
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_REPOID_NULL.getCode(), BizExceptionEnum.PRIVATEREPO_REPOID_NULL.getMessage());
        }

        ViPrivateMemberBean viPrivateMemberBean1 = new ViPrivateMemberBean();
        viPrivateMemberBean1.setIdentityId(viPrivateMemberBean.getIdentityId());
        viPrivateMemberBean1.setRepoId(viPrivateMemberBean.getRepoId());
        ViPrivateMemberBean bean1 = viPrivateMemberMapper.getViPrivateMemberByBean(viPrivateMemberBean1);
        if (bean1 != null && bean1.getId() != viPrivateMemberBean.getId() && bean1.getIdentityId().equals(viPrivateMemberBean.getIdentityId())) {
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_ID_REPEATED.getCode(), BizExceptionEnum.PRIVATEREPO_ID_REPEATED.getMessage());
        }

        ViPrivateMemberBean viPrivateMemberByBean = viPrivateMemberMapper.getViPrivateMemberByBean(viPrivateMemberBean);
        if (viPrivateMemberByBean != null) {
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_ID_REPEATED.getCode(), BizExceptionEnum.PRIVATEREPO_ID_REPEATED.getMessage());
        }
        String base64 = viPrivateMemberBean.getImageUrl().split(",")[1];
        viPrivateMemberBean.setObjectId("vi_private_" + UUID.randomUUID().toString().replace("-", "").toLowerCase());
        try {
            viPrivateMemberBean.setImageUrl(UploadUtil.downLoadFromBase64(base64, "Bkmember"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        viPrivateMemberMapper.insertViPrivateMember(viPrivateMemberBean);

        //添加布控目标
        BaseRequest<BKMemberAddRequest> bkMemberAddRequestBaseRequest = new BaseRequest<>();
        bkMemberAddRequestBaseRequest.setRequestId(bkrepoConfig.getRequestId());
        BKMemberAddRequest bkMemberAddRequest = new BKMemberAddRequest();
        bkMemberAddRequest.setObjectId(viPrivateMemberBean.getObjectId());
        bkMemberAddRequest.setContent(base64);
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
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_IDENTITYNAME_NULL.getCode(), BizExceptionEnum.PRIVATEREPO_IDENTITYNAME_NULL.getMessage());
        }
        if (!StringUtils.hasLength(viPrivateMemberBean.getIdentityId())) {
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_IDENTITYID_NULL.getCode(), BizExceptionEnum.PRIVATEREPO_IDENTITYID_NULL.getMessage());
        }
        if (!StringUtils.hasLength(viPrivateMemberBean.getImageUrl())) {
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_IMAGEURL_NULL.getCode(), BizExceptionEnum.PRIVATEREPO_IMAGEURL_NULL.getMessage());
        }
//        if (!StringUtils.hasLength(viPrivateMemberBean.getRepoId())) {
//            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_REPOID_NULL.getCode(),
//                    BizExceptionEnum.PRIVATEREPO_REPOID_NULL.getMessage());
//        }
        if (null == viPrivateMemberBean.getId() && null == viPrivateMemberBean.getObjectId() || viPrivateMemberBean.getId() == 0) {
            return ResultVo.failure(BizExceptionEnum.BKMEMBER_ID_NULL.getCode(), BizExceptionEnum.BKMEMBER_ID_NULL.getMessage());
        }
        ViPrivateMemberBean viPrivateMemberBean1 = new ViPrivateMemberBean();
        viPrivateMemberBean1.setIdentityId(viPrivateMemberBean.getIdentityId());
        viPrivateMemberBean1.setRepoId(viPrivateMemberBean.getRepoId());
        ViPrivateMemberBean bean1 = viPrivateMemberMapper.getViPrivateMemberByBean(viPrivateMemberBean1);
        //判断要修改的库是否有重复的身份证号码
        if (bean1 != null && !bean1.getId().equals(viPrivateMemberBean.getId())) {
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_ID_REPEATED.getCode(), BizExceptionEnum.PRIVATEREPO_ID_REPEATED.getMessage());
        }

        ViPrivateMemberBean bean = viPrivateMemberMapper.getViPrivateMemberById(viPrivateMemberBean);
        if (null == bean) {
            return ResultVo.failure(BizExceptionEnum.BKMEMBER_EXISTED.getCode(), BizExceptionEnum.BKMEMBER_EXISTED.getMessage());
        }


        String oldBase64 = ImageUtils.image2Base64(getRequestPrefix(request)+ bean.getImageUrl());
        String newBase64 = viPrivateMemberBean.getImageUrl().split(",")[1];
        log.info("oldBase64：" + oldBase64);
        log.info("NewBase64：" + newBase64);
        if (!oldBase64.equals(newBase64)) {

            try {
                bean.setImageUrl(UploadUtil.downLoadFromBase64(newBase64, "Bkmember"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            //删除布控目标
            BaseRequest<BKMemberDeleteRequest> bkMemberDeleteRequestBaseRequest = new BaseRequest<>();
            bkMemberDeleteRequestBaseRequest.setRequestId(bkrepoConfig.getRequestId());
            BKMemberDeleteRequest bkMemberDeleteRequest = new BKMemberDeleteRequest();
            bkMemberDeleteRequest.setObjectIds(bean.getObjectId());
            bkMemberDeleteRequest.setBkid(bkrepoConfig.getBkid());
            bkMemberDeleteRequestBaseRequest.setData(bkMemberDeleteRequest);
            BaseResponse baseResponse = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBkmemberDelete(), bkMemberDeleteRequestBaseRequest);
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
                bkMemberAddRequest.setContent(newBase64);
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
        }

        if (!bean.getIdentityId().equals(viPrivateMemberBean.getIdentityId())) {
            bean.setIdentityId(viPrivateMemberBean.getIdentityId());
        }

        if (!bean.getIdentityName().equals(viPrivateMemberBean.getIdentityName())) {
            bean.setIdentityName(viPrivateMemberBean.getIdentityName());
        }

        bean.setModifyTime(new Date());
        bean.setCreateTime(bean.getCreateTime());
        viPrivateMemberMapper.updateViPrivateMember(bean);
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
            BaseResponse baseResponse = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBkmemberDelete(), bkMemberDeleteRequestBaseRequest);
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
    public ResultVo getAllViPrivateMember(ViPrivateMemberVo viPrivateMemberVo, HttpServletRequest request) {

        if (null == viPrivateMemberVo.getRepoId() || viPrivateMemberVo.getRepoId() <= 0) {
            return ResultVo.failure(BizExceptionEnum.PRIVATEREPO_BKREPOID_NULL.getCode(), BizExceptionEnum.PRIVATEREPO_BKREPOID_NULL.getMessage());
        }
        ViRepoBean viRepoBean = new ViRepoBean();
        viRepoBean.setId(viPrivateMemberVo.getRepoId());
        ViRepoBean viRepo = viRepoMapper.getViRepoById(viRepoBean);
        if (viRepo == null) {
            return ResultVo.failure(BizExceptionEnum.NOT_FOUND.getCode(), BizExceptionEnum.NOT_FOUND.getMessage());
        }

        PageHelper.startPage(viPrivateMemberVo.getCurrent(), viPrivateMemberVo.getSize());

        List<ViMemberVo> list = viPrivateMemberMapper.getAllViPrivateMember(viPrivateMemberVo);
        for(ViMemberVo vo:list){
            vo.setContent(ImageUtils.image2Base64(getRequestPrefix(request)+ vo.getImageUrl()));
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



    /**
     * 获取url请求前缀
     *
     * @param request request对象
     * @return
     * @explain http://localhost:8080/test
     */
    public static String getRequestPrefix(HttpServletRequest request) {
        // 网络协议
        String networkProtocol = request.getScheme();
        // 网络ip
        String ip = request.getServerName();
        // 端口号
        int port = request.getServerPort();
        String urlPrefix = networkProtocol + "://" + ip + ":" + port;
        return urlPrefix;
    }
}
