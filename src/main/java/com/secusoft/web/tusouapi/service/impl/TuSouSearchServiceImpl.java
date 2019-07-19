package com.secusoft.web.tusouapi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.secusoft.web.core.common.Constants;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.core.util.StringUtils;
import com.secusoft.web.mapper.DeviceMapper;
import com.secusoft.web.mapper.PictureMapper;
import com.secusoft.web.mapper.SysOperationLogMapper;
import com.secusoft.web.model.DeviceBean;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.SysOperationLog;
import com.secusoft.web.tusouapi.SemanticSearchClient;
import com.secusoft.web.tusouapi.TuSouClient;
import com.secusoft.web.tusouapi.model.*;
import com.secusoft.web.tusouapi.service.TuSouSearchService;
import com.secusoft.web.utils.SearchSortUtils;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class TuSouSearchServiceImpl implements TuSouSearchService {
    @Resource
    DeviceMapper deviceMapper;

    @Resource
    SysOperationLogMapper sysOperationLogMapper;

    @Autowired
    TuSouSearchService tuSouSearchService;

    @Resource
    PictureMapper pictureMapper;
    
    @Override
    public BaseResponse<JSONArray> search(BaseRequest<SearchRequestData> request) {
        return TuSouClient.getClientConnectionPool().fetchByPostMethod(TuSouClient.Path_SEARCH,request);
    }

    @Override
    public ResultVo sortsearch(JSONObject request) {
        String requestStr = JSON.toJSONString(request);
//        System.out.println("前台发送的数据"+requestStr);

        //判断是否是语义搜索
        BaseRequest<SearchRequestData> searchRequestBaseRequest = JSON.parseObject(request.toJSONString(), new TypeReference<BaseRequest<SearchRequestData>>() {
        });
        String responseStr=null;
        if(searchRequestBaseRequest.getData().getText()!=null){
            responseStr = SemanticSearchClient.getClientConnectionPool().fetchByPostMethod(SemanticSearchClient.Path_SEARCH, requestStr);
        }else {
            responseStr = TuSouClient.getClientConnectionPool().fetchByPostMethod(TuSouClient.Path_SEARCH, requestStr);
        }
        SearchResponse searchResponse = JSON.parseObject(responseStr, new TypeReference<SearchResponse>() {
        });
        //真实给阿里返回的接口

        if(searchResponse == null){
            return ResultVo.failure(BizExceptionEnum.TQ_SERVER_ERROR.getCode(),BizExceptionEnum.TQ_SERVER_ERROR.getMessage());
        }
        //如果返回的状态码为failed，就返回阿里的Msg
        if(Constants.FAILED.equals(searchResponse.getErrorCode())){
            return ResultVo.failure(BizExceptionEnum.PARAM_ERROR.getCode(),searchResponse.getErrorMsg());
        }
        if(StringUtils.isEmpty(searchResponse.getErrorCode())) {
            return ResultVo.failure(BizExceptionEnum.PARAM_ERROR.getCode(),BizExceptionEnum.PARAM_ERROR.getMessage());
        }
        //如果查询结果为空并且请求成功
        if(CollectionUtils.isEmpty(searchResponse.getData())&&Constants.SUCCESS.equals(searchResponse.getErrorCode())){
            return ResultVo.success();
        }
        List<SearchResponseData> olddata = searchResponse.getData();
        // 注入状态
        List<String> pictureIds = pictureMapper.readPictureIds();
        olddata.forEach(obj -> {
        	if(CollectionUtils.isNotEmpty(pictureIds)) {
        		for(String picId:pictureIds) {
        			if(picId.equals(obj.getId())) {
        				obj.setStatus(1);
        				break;
        			}
        		}
        	}
        });
        //获取全部的设备列表 与data里进行映射
        //如果设备列表为空
        DeviceBean device = new DeviceBean();
        List<DeviceBean> deviceBeans = deviceMapper.readDeviceList(device);
        olddata.forEach(searchResponseData -> {
            deviceBeans.forEach(deviceBean ->{
                if (deviceBean.getDeviceId().equals(searchResponseData.getSource().getCameraId())){
                    searchResponseData.getSource().setDeviceBean(deviceBean);
                }
            });
            if(searchResponseData.getSource().getDeviceBean()==null){
                DeviceBean deviceBean = new DeviceBean();
                deviceBean.setDeviceName("未命名");
                searchResponseData.getSource().setDeviceBean(deviceBean);
            }
        });
        //如果返回的属性里没有相似度 就按时间戳排序
        if(olddata.get(0).getScore()==null){
            // 时间戳排序
            List<SearchResponseData> timeStampData = SearchSortUtils.timeStampSort(olddata);

            HashMap<String, Object> stringSearchDataHashMap = new HashMap<>();
            stringSearchDataHashMap.put("timestamp",timeStampData);
            Long totalCount = searchResponse.getTotalCount();
            String responStr = JSON.toJSONString(ResultVo.success(stringSearchDataHashMap,totalCount), SerializerFeature.DisableCircularReferenceDetect);
            ResultVo resultVo = JSON.parseObject(responStr, new TypeReference<ResultVo>() {
            });
            return resultVo;
        }
        //相似度排序
        List<SearchResponseData> scoreData = new ArrayList<SearchResponseData>();
        scoreData.addAll(SearchSortUtils.scoreSort(olddata));

        // 时间戳排序
        List<SearchResponseData> timeStampData = new ArrayList<SearchResponseData>();
        timeStampData.addAll(SearchSortUtils.timeStampSort(olddata));

        //设备分组排序
        List<SearchResponseData> sortData = SearchSortUtils.scoreSort(olddata);
        Map<String, List> resultMap = SearchSortUtils.deviceSort(sortData);
        //把Map中的value取出来放入List返回
        ArrayList<List> resultList = new ArrayList<>();
        resultMap.forEach((k,v)->{
            resultList.add(v);
        });
        HashMap<String, Object> stringSearchDataHashMap = new HashMap<>();
        
        stringSearchDataHashMap.put("score",scoreData);
        stringSearchDataHashMap.put("timestamp",timeStampData);
        stringSearchDataHashMap.put("device",resultList);

        //FastJSon中 重复引用需要关闭
        Long totalCount = searchResponse.getTotalCount();
        String responStr = JSON.toJSONString(ResultVo.success(stringSearchDataHashMap,totalCount), SerializerFeature.DisableCircularReferenceDetect);
        ResultVo resultVo = JSON.parseObject(responStr, new TypeReference<ResultVo>() {
        });
        return resultVo;
    }

    @Override
    public ResultVo testsearch(JSONObject request) {
        String requestStr = JSON.toJSONString(request);
        System.out.println("前台发送的数据"+requestStr);
        //跳过多少个
        //Integer from = Integer.parseInt(request.get("from").toString());
        //请求多少个
        //Integer size = Integer.parseInt(request.get("size").toString());
        Integer from=0;
        Integer size=35;

        System.out.println(from+" -- "+size);
        String responseStr="{\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691489_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691489_s.jpg\",\n" +
                "        \"hairScore\": 0.85698605,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691489_s.jpg\",\n" +
                "        \"sexScore\": 0.7459802,\n" +
                "        \"upper_typeScore\": 0.6697427,\n" +
                "        \"hair\": 1,\n" +
                "        \"objLeft\": 82,\n" +
                "        \"lower_type\": 6,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 553,\n" +
                "        \"objUUId\": \"330119520001025303_1559440276664_0_22241\",\n" +
                "        \"timestamp\": 1559440276664,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 0,\n" +
                "        \"objBottom\": 716,\n" +
                "        \"lower_color\": 13,\n" +
                "        \"upper_colorScore\": 0.8952415,\n" +
                "        \"sex\": 0,\n" +
                "        \"lower_typeScore\": 0.6810614,\n" +
                "        \"entryTime\": 1559440276664,\n" +
                "        \"lower_colorScore\": 0.66150177,\n" +
                "        \"cameraId\": \"330119520001066103\",\n" +
                "        \"objId\": \"a37484d85d9e45a781c7325f0f2e580a\",\n" +
                "        \"objRight\": 271,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691489_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"a37484d85d9e45a781c7325f0f2e580a\",\n" +
                "      \"_score\": 0.88738585,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691488_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691488_s.jpg\",\n" +
                "        \"hairScore\": 0.56812334,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691488_s.jpg\",\n" +
                "        \"sexScore\": 0.6741696,\n" +
                "        \"upper_typeScore\": 0.4701406,\n" +
                "        \"hair\": 1,\n" +
                "        \"objLeft\": 1452,\n" +
                "        \"lower_type\": 2,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 897,\n" +
                "        \"objUUId\": \"330102540001131760_1559439009385_0_12433\",\n" +
                "        \"timestamp\": 1559439009385,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 0,\n" +
                "        \"objBottom\": 1077,\n" +
                "        \"lower_color\": 9,\n" +
                "        \"upper_colorScore\": 0.4740565,\n" +
                "        \"sex\": 1,\n" +
                "        \"lower_typeScore\": 0.57026124,\n" +
                "        \"entryTime\": 1559439008210,\n" +
                "        \"lower_colorScore\": 0.41560984,\n" +
                "        \"cameraId\": \"330102540001006960\",\n" +
                "        \"objId\": \"56fd6e8d73ad43918c66dc0f345c495d\",\n" +
                "        \"objRight\": 1592,\n" +
                "        \"upper_color\": 2,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691488_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"56fd6e8d73ad43918c66dc0f345c495d\",\n" +
                "      \"_score\": 0.8828314,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691487_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691487_s.jpg\",\n" +
                "        \"hairScore\": 0.8539175,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691487_s.jpg\",\n" +
                "        \"sexScore\": 0.5970783,\n" +
                "        \"upper_typeScore\": 0.4410029,\n" +
                "        \"hair\": 1,\n" +
                "        \"objLeft\": 942,\n" +
                "        \"lower_type\": 2,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 899,\n" +
                "        \"objUUId\": \"330117019900010000_1559439828042_1_7020\",\n" +
                "        \"timestamp\": 1559439828042,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 2,\n" +
                "        \"objBottom\": 1076,\n" +
                "        \"lower_color\": 9,\n" +
                "        \"upper_colorScore\": 0.4169154,\n" +
                "        \"sex\": 0,\n" +
                "        \"lower_typeScore\": 0.43089944,\n" +
                "        \"entryTime\": 1559439828042,\n" +
                "        \"lower_colorScore\": 0.46018422,\n" +
                "        \"cameraId\": \"330102540001076760\",\n" +
                "        \"objId\": \"7c355210fee54806ab246aef91ed9dfc\",\n" +
                "        \"objRight\": 1100,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691487_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"7c355210fee54806ab246aef91ed9dfc\",\n" +
                "      \"_score\": 0.88123775,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691486_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691486_s.jpg\",\n" +
                "        \"hairScore\": 0.82993346,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691486_s.jpg\",\n" +
                "        \"sexScore\": 0.8038736,\n" +
                "        \"upper_typeScore\": 0.39617947,\n" +
                "        \"hair\": 1,\n" +
                "        \"objLeft\": 283,\n" +
                "        \"lower_type\": 6,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 571,\n" +
                "        \"objUUId\": \"330119520001025303_1559440476767_0_17699\",\n" +
                "        \"timestamp\": 1559440476767,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 2,\n" +
                "        \"objBottom\": 718,\n" +
                "        \"lower_color\": 13,\n" +
                "        \"upper_colorScore\": 0.8371094,\n" +
                "        \"sex\": 0,\n" +
                "        \"lower_typeScore\": 0.49887255,\n" +
                "        \"entryTime\": 1559440476767,\n" +
                "        \"lower_colorScore\": 0.50681835,\n" +
                "        \"cameraId\": \"330119520001076203\",\n" +
                "        \"objId\": \"44ac48d8106b4e46b4a3dd000b39465b\",\n" +
                "        \"objRight\": 457,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691486_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"44ac48d8106b4e46b4a3dd000b39465b\",\n" +
                "      \"_score\": 0.88067305,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691485_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691485_s.jpg\",\n" +
                "        \"hairScore\": 0.6653193,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691485_s.jpg\",\n" +
                "        \"sexScore\": 0.6064582,\n" +
                "        \"upper_typeScore\": 0.54809207,\n" +
                "        \"hair\": 1,\n" +
                "        \"objLeft\": 137,\n" +
                "        \"lower_type\": 2,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 647,\n" +
                "        \"objUUId\": \"330119520001025303_1559439693710_1_10393\",\n" +
                "        \"timestamp\": 1559439693710,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 0,\n" +
                "        \"objBottom\": 718,\n" +
                "        \"lower_color\": 9,\n" +
                "        \"upper_colorScore\": 0.25725934,\n" +
                "        \"sex\": 1,\n" +
                "        \"lower_typeScore\": 0.6301192,\n" +
                "        \"entryTime\": 1559439693710,\n" +
                "        \"lower_colorScore\": 0.689523,\n" +
                "        \"cameraId\": \"330119520001197703\",\n" +
                "        \"objId\": \"bfd2f5b71bf8445baa64d0d4456ce46c\",\n" +
                "        \"objRight\": 245,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691485_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"bfd2f5b71bf8445baa64d0d4456ce46c\",\n" +
                "      \"_score\": 0.8770586,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a76914484_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a76914484_s.jpg\",\n" +
                "        \"hairScore\": 0.9665299,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691484_s.jpg\",\n" +
                "        \"sexScore\": 0.85608107,\n" +
                "        \"upper_typeScore\": 0.768965,\n" +
                "        \"hair\": 1,\n" +
                "        \"objLeft\": 1653,\n" +
                "        \"lower_type\": 2,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 409,\n" +
                "        \"objUUId\": \"1440011_1559437330274_0_36622\",\n" +
                "        \"timestamp\": 1559437330274,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 0,\n" +
                "        \"objBottom\": 774,\n" +
                "        \"lower_color\": 9,\n" +
                "        \"upper_colorScore\": 0.91046464,\n" +
                "        \"sex\": 0,\n" +
                "        \"lower_typeScore\": 0.88955146,\n" +
                "        \"entryTime\": 1559437330274,\n" +
                "        \"lower_colorScore\": 0.9537687,\n" +
                "        \"cameraId\": \"330102540001513166\",\n" +
                "        \"objId\": \"a3beb8369f4441b1a857df2ce48c18fa\",\n" +
                "        \"objRight\": 1906,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a76914484_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"a3beb8369f4441b1a857df2ce48c18fa\",\n" +
                "      \"_score\": 0.87405056,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691483_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691483_s.jpg\",\n" +
                "        \"hairScore\": 0.4807687,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691483_s.jpg\",\n" +
                "        \"sexScore\": 0.6856863,\n" +
                "        \"upper_typeScore\": 0.44372064,\n" +
                "        \"hair\": 1,\n" +
                "        \"objLeft\": 1725,\n" +
                "        \"lower_type\": 2,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 695,\n" +
                "        \"objUUId\": \"330102540001583860_1559437543235_3_204\",\n" +
                "        \"timestamp\": 1559437543235,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 0,\n" +
                "        \"objBottom\": 1063,\n" +
                "        \"lower_color\": 9,\n" +
                "        \"upper_colorScore\": 0.4246667,\n" +
                "        \"sex\": 0,\n" +
                "        \"lower_typeScore\": 0.8088778,\n" +
                "        \"entryTime\": 1559437543235,\n" +
                "        \"lower_colorScore\": 0.51156026,\n" +
                "        \"cameraId\": \"330102540001513167\",\n" +
                "        \"objId\": \"c700d983837e419f833d93c0f8b91e52\",\n" +
                "        \"objRight\": 1908,\n" +
                "        \"upper_color\": 12,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691483_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"c700d983837e419f833d93c0f8b91e52\",\n" +
                "      \"_score\": 0.87394196,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691482_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691482_s.jpg\",\n" +
                "        \"hairScore\": 0.9527993,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691482_s.jpg\",\n" +
                "        \"sexScore\": 0.94330597,\n" +
                "        \"upper_typeScore\": 0.73482895,\n" +
                "        \"hair\": 1,\n" +
                "        \"objLeft\": 470,\n" +
                "        \"lower_type\": 2,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 590,\n" +
                "        \"objUUId\": \"330119520001025303_1559438557614_0_31916\",\n" +
                "        \"timestamp\": 1559438557614,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 0,\n" +
                "        \"objBottom\": 714,\n" +
                "        \"lower_color\": 9,\n" +
                "        \"upper_colorScore\": 0.54856217,\n" +
                "        \"sex\": 0,\n" +
                "        \"lower_typeScore\": 0.7041001,\n" +
                "        \"entryTime\": 1559438557614,\n" +
                "        \"lower_colorScore\": 0.3734564,\n" +
                "        \"cameraId\": \"330102540001513170\",\n" +
                "        \"objId\": \"77342791e0fc43819b83fb8a7dcd0c09\",\n" +
                "        \"objRight\": 647,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691482_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"77342791e0fc43819b83fb8a7dcd0c09\",\n" +
                "      \"_score\": 0.87332696,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691482_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691482_s.jpg\",\n" +
                "        \"hairScore\": 0.9329962,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691481_s.jpg\",\n" +
                "        \"sexScore\": 0.6425652,\n" +
                "        \"upper_typeScore\": 0.64209145,\n" +
                "        \"hair\": 1,\n" +
                "        \"objLeft\": 1099,\n" +
                "        \"lower_type\": 1,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 383,\n" +
                "        \"objUUId\": \"330102540001583860_1559439183343_0_49533\",\n" +
                "        \"timestamp\": 1559439183343,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 0,\n" +
                "        \"objBottom\": 670,\n" +
                "        \"lower_color\": 9,\n" +
                "        \"upper_colorScore\": 0.29750535,\n" +
                "        \"sex\": 0,\n" +
                "        \"lower_typeScore\": 0.40595126,\n" +
                "        \"entryTime\": 1559439181974,\n" +
                "        \"lower_colorScore\": 0.38861415,\n" +
                "        \"cameraId\": \"330106530001752060\",\n" +
                "        \"objId\": \"f68683208466445da6a1db9eaa5697a6\",\n" +
                "        \"objRight\": 1220,\n" +
                "        \"upper_color\": 7,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691482_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"f68683208466445da6a1db9eaa5697a6\",\n" +
                "      \"_score\": 0.8723895,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691480_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691480_s.jpg\",\n" +
                "        \"hairScore\": 0.6101619,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691480_s.jpg\",\n" +
                "        \"sexScore\": 0.6798965,\n" +
                "        \"upper_typeScore\": 0.61606055,\n" +
                "        \"hair\": 1,\n" +
                "        \"objLeft\": 131,\n" +
                "        \"lower_type\": 2,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 597,\n" +
                "        \"objUUId\": \"330119520001025303_1559438395181_0_10808\",\n" +
                "        \"timestamp\": 1559438395181,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 0,\n" +
                "        \"objBottom\": 718,\n" +
                "        \"lower_color\": 9,\n" +
                "        \"upper_colorScore\": 0.3861273,\n" +
                "        \"sex\": 1,\n" +
                "        \"lower_typeScore\": 0.34709913,\n" +
                "        \"entryTime\": 1559438395181,\n" +
                "        \"lower_colorScore\": 0.43180212,\n" +
                "        \"cameraId\": \"330102540001513170\",\n" +
                "        \"objId\": \"94dcece4062141089d71e4a5d670f049\",\n" +
                "        \"objRight\": 285,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691480_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"94dcece4062141089d71e4a5d670f049\",\n" +
                "      \"_score\": 0.8723822,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691489_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691489_s.jpg\",\n" +
                "        \"hairScore\": 0.9686641,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691489_s.jpg\",\n" +
                "        \"sexScore\": 0.6111966,\n" +
                "        \"upper_typeScore\": 0.9075603,\n" +
                "        \"hair\": 1,\n" +
                "        \"objLeft\": 1676,\n" +
                "        \"lower_type\": 2,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 406,\n" +
                "        \"objUUId\": \"330102540001513219_1559440564520_0_8470\",\n" +
                "        \"timestamp\": 1559440564520,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 0,\n" +
                "        \"objBottom\": 622,\n" +
                "        \"lower_color\": 9,\n" +
                "        \"upper_colorScore\": 0.27087748,\n" +
                "        \"sex\": 0,\n" +
                "        \"lower_typeScore\": 0.8365562,\n" +
                "        \"entryTime\": 1559440564520,\n" +
                "        \"lower_colorScore\": 0.9060415,\n" +
                "        \"cameraId\": \"330102540001513187\",\n" +
                "        \"objId\": \"ed048018a6ab47eab06a7f6ba3a4e376\",\n" +
                "        \"objRight\": 1761,\n" +
                "        \"upper_color\": 9,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691489_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"ed048018a6ab47eab06a7f6ba3a4e376\",\n" +
                "      \"_score\": 0.8717074,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"hairScore\": 0.640658,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"sexScore\": 0.7636918,\n" +
                "        \"upper_typeScore\": 0.5326139,\n" +
                "        \"hair\": 1,\n" +
                "        \"objLeft\": 40,\n" +
                "        \"lower_type\": 2,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 936,\n" +
                "        \"objUUId\": \"330102540001513223_1559438479894_0_15583\",\n" +
                "        \"timestamp\": 1559438479894,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 2,\n" +
                "        \"objBottom\": 1076,\n" +
                "        \"lower_color\": 9,\n" +
                "        \"upper_colorScore\": 0.16038781,\n" +
                "        \"sex\": 1,\n" +
                "        \"lower_typeScore\": 0.59525996,\n" +
                "        \"entryTime\": 1559438479894,\n" +
                "        \"lower_colorScore\": 0.34516272,\n" +
                "        \"cameraId\": \"330102540001513187\",\n" +
                "        \"objId\": \"aa0008e893db4dddbc16dd5871875192\",\n" +
                "        \"objRight\": 344,\n" +
                "        \"upper_color\": 2,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"aa0008e893db4dddbc16dd5871875192\",\n" +
                "      \"_score\": 0.8716339,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"hairScore\": 0.94429225,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691501_s.jpg\",\n" +
                "        \"sexScore\": 0.53937894,\n" +
                "        \"upper_typeScore\": 0.8223879,\n" +
                "        \"hair\": 1,\n" +
                "        \"objLeft\": 1688,\n" +
                "        \"lower_type\": 1,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 410,\n" +
                "        \"objUUId\": \"330102540001513219_1559440564885_0_15697\",\n" +
                "        \"timestamp\": 1559440564885,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 0,\n" +
                "        \"objBottom\": 705,\n" +
                "        \"lower_color\": 9,\n" +
                "        \"upper_colorScore\": 0.30349064,\n" +
                "        \"sex\": 1,\n" +
                "        \"lower_typeScore\": 0.37291217,\n" +
                "        \"entryTime\": 1559440557522,\n" +
                "        \"lower_colorScore\": 0.8297801,\n" +
                "        \"cameraId\": \"330102540001513223\",\n" +
                "        \"objId\": \"721180650a9b47d0923e09e5fd3aac24\",\n" +
                "        \"objRight\": 1808,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"721180650a9b47d0923e09e5fd3aac24\",\n" +
                "      \"_score\": 0.8716172,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"hairScore\": 0.48905444,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691501_s.jpg\",\n" +
                "        \"sexScore\": 0.78384227,\n" +
                "        \"upper_typeScore\": 0.6327143,\n" +
                "        \"hair\": 1,\n" +
                "        \"objLeft\": 421,\n" +
                "        \"lower_type\": 2,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 875,\n" +
                "        \"objUUId\": \"330102540001513223_1559439083438_0_24963\",\n" +
                "        \"timestamp\": 1559439083438,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 0,\n" +
                "        \"objBottom\": 1075,\n" +
                "        \"lower_color\": 9,\n" +
                "        \"upper_colorScore\": 0.8333851,\n" +
                "        \"sex\": 1,\n" +
                "        \"lower_typeScore\": 0.35940742,\n" +
                "        \"entryTime\": 1559439083438,\n" +
                "        \"lower_colorScore\": 0.3787323,\n" +
                "        \"cameraId\": \"330102540001513223\",\n" +
                "        \"objId\": \"3b594608003e4634807237033b97e103\",\n" +
                "        \"objRight\": 628,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"3b594608003e4634807237033b97e103\",\n" +
                "      \"_score\": 0.8714435,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"hairScore\": 0.929774,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691501_s.jpg\",\n" +
                "        \"sexScore\": 0.76415086,\n" +
                "        \"upper_typeScore\": 0.662874,\n" +
                "        \"hair\": 1,\n" +
                "        \"objLeft\": 550,\n" +
                "        \"lower_type\": 2,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 893,\n" +
                "        \"objUUId\": \"330117019900010000_1559439827391_1_2594\",\n" +
                "        \"timestamp\": 1559439827391,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 0,\n" +
                "        \"objBottom\": 1074,\n" +
                "        \"lower_color\": 9,\n" +
                "        \"upper_colorScore\": 0.2709052,\n" +
                "        \"sex\": 0,\n" +
                "        \"lower_typeScore\": 0.67862815,\n" +
                "        \"entryTime\": 1559439827391,\n" +
                "        \"lower_colorScore\": 0.56764174,\n" +
                "        \"cameraId\": \"330102540001634760\",\n" +
                "        \"objId\": \"d1c81c27f4fa4b17834db81988c3c4c4\",\n" +
                "        \"objRight\": 714,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"d1c81c27f4fa4b17834db81988c3c4c4\",\n" +
                "      \"_score\": 0.87116855,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"hairScore\": 0.9571443,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691501_s.jpg\",\n" +
                "        \"sexScore\": 0.95762694,\n" +
                "        \"upper_typeScore\": 0.85720795,\n" +
                "        \"hair\": 1,\n" +
                "        \"objLeft\": 40,\n" +
                "        \"lower_type\": 2,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 574,\n" +
                "        \"objUUId\": \"330119520001025303_1559440005485_1_3126\",\n" +
                "        \"timestamp\": 1559440005485,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 0,\n" +
                "        \"objBottom\": 715,\n" +
                "        \"lower_color\": 9,\n" +
                "        \"upper_colorScore\": 0.34993902,\n" +
                "        \"sex\": 0,\n" +
                "        \"lower_typeScore\": 0.84178066,\n" +
                "        \"entryTime\": 1559440005485,\n" +
                "        \"lower_colorScore\": 0.4785236,\n" +
                "        \"cameraId\": \"330102540001714360\",\n" +
                "        \"objId\": \"d3f7bbdc031e4437ac1a07c54f94452b\",\n" +
                "        \"objRight\": 228,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"d3f7bbdc031e4437ac1a07c54f94452b\",\n" +
                "      \"_score\": 0.8710928,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"hairScore\": 0.52144307,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691501_s.jpg\",\n" +
                "        \"sexScore\": 0.9195852,\n" +
                "        \"upper_typeScore\": 0.63467854,\n" +
                "        \"hair\": 2,\n" +
                "        \"objLeft\": 130,\n" +
                "        \"lower_type\": 6,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 900,\n" +
                "        \"objUUId\": \"330102540001513223_1559439501488_0_20264\",\n" +
                "        \"timestamp\": 1559439501488,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 0,\n" +
                "        \"objBottom\": 1070,\n" +
                "        \"lower_color\": 9,\n" +
                "        \"upper_colorScore\": 0.48184937,\n" +
                "        \"sex\": 1,\n" +
                "        \"lower_typeScore\": 0.3653439,\n" +
                "        \"entryTime\": 1559439501488,\n" +
                "        \"lower_colorScore\": 0.33376402,\n" +
                "        \"cameraId\": \"330106530001830060\",\n" +
                "        \"objId\": \"6904f1748cc74f238f4da3010f79ea8c\",\n" +
                "        \"objRight\": 426,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691501_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"6904f1748cc74f238f4da3010f79ea8c\",\n" +
                "      \"_score\": 0.87032753,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"hairScore\": 0.9777385,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691501_s.jpg\",\n" +
                "        \"sexScore\": 0.9073448,\n" +
                "        \"upper_typeScore\": 0.672501,\n" +
                "        \"hair\": 1,\n" +
                "        \"objLeft\": 563,\n" +
                "        \"lower_type\": 2,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 583,\n" +
                "        \"objUUId\": \"330102540083470260_1559437805391_1_3559\",\n" +
                "        \"timestamp\": 1559437805391,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 0,\n" +
                "        \"objBottom\": 865,\n" +
                "        \"lower_color\": 9,\n" +
                "        \"upper_colorScore\": 0.53582484,\n" +
                "        \"sex\": 0,\n" +
                "        \"lower_typeScore\": 0.92632914,\n" +
                "        \"entryTime\": 1559437805391,\n" +
                "        \"lower_colorScore\": 0.520542,\n" +
                "        \"cameraId\": \"330106530001915760\",\n" +
                "        \"objId\": \"e20aaf6d0b6c46178c273450a18fa71e\",\n" +
                "        \"objRight\": 696,\n" +
                "        \"upper_color\": 9,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"e20aaf6d0b6c46178c273450a18fa71e\",\n" +
                "      \"_score\": 0.8701077,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"hairScore\": 0.5422348,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691501_s.jpg\",\n" +
                "        \"sexScore\": 0.93943167,\n" +
                "        \"upper_typeScore\": 0.4578791,\n" +
                "        \"hair\": 2,\n" +
                "        \"objLeft\": 162,\n" +
                "        \"lower_type\": 2,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 856,\n" +
                "        \"objUUId\": \"330102540001513223_1559438480095_1_16920\",\n" +
                "        \"timestamp\": 1559438480095,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 0,\n" +
                "        \"objBottom\": 1077,\n" +
                "        \"lower_color\": 9,\n" +
                "        \"upper_colorScore\": 0.19661461,\n" +
                "        \"sex\": 1,\n" +
                "        \"lower_typeScore\": 0.4188326,\n" +
                "        \"entryTime\": 1559438480095,\n" +
                "        \"lower_colorScore\": 0.6738354,\n" +
                "        \"cameraId\": \"330106530045281260\",\n" +
                "        \"objId\": \"e299112a6978419f928712abb9883904\",\n" +
                "        \"objRight\": 361,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691501_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"e299112a6978419f928712abb9883904\",\n" +
                "      \"_score\": 0.8700134,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"hairScore\": 0.9266526,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691501_s.jpg\",\n" +
                "        \"sexScore\": 0.72620076,\n" +
                "        \"upper_typeScore\": 0.9268774,\n" +
                "        \"hair\": 1,\n" +
                "        \"objLeft\": 1495,\n" +
                "        \"lower_type\": 2,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 493,\n" +
                "        \"objUUId\": \"330102540001131760_1559439099612_0_13162\",\n" +
                "        \"timestamp\": 1559439099612,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 0,\n" +
                "        \"objBottom\": 671,\n" +
                "        \"lower_color\": 9,\n" +
                "        \"upper_colorScore\": 0.37962916,\n" +
                "        \"sex\": 0,\n" +
                "        \"lower_typeScore\": 0.323678,\n" +
                "        \"entryTime\": 1559439099612,\n" +
                "        \"lower_colorScore\": 0.37475058,\n" +
                "        \"cameraId\": \"330106530045281260\",\n" +
                "        \"objId\": \"eb84f76d4a41498aa943790374fd3a75\",\n" +
                "        \"objRight\": 1568,\n" +
                "        \"upper_color\": 9,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691501_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"eb84f76d4a41498aa943790374fd3a75\",\n" +
                "      \"_score\": 0.86958915,\n" +
                "      \"_ext\": null\n" +
                "    }\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691489_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691489_s.jpg\",\n" +
                "        \"hairScore\": 0.85698605,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691489_s.jpg\",\n" +
                "        \"sexScore\": 0.7459802,\n" +
                "        \"upper_typeScore\": 0.6697427,\n" +
                "        \"hair\": 1,\n" +
                "        \"objLeft\": 82,\n" +
                "        \"lower_type\": 6,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 553,\n" +
                "        \"objUUId\": \"330119520001025303_1559440276664_0_22241\",\n" +
                "        \"timestamp\": 1559440276664,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 0,\n" +
                "        \"objBottom\": 716,\n" +
                "        \"lower_color\": 13,\n" +
                "        \"upper_colorScore\": 0.8952415,\n" +
                "        \"sex\": 0,\n" +
                "        \"lower_typeScore\": 0.6810614,\n" +
                "        \"entryTime\": 1559440276664,\n" +
                "        \"lower_colorScore\": 0.66150177,\n" +
                "        \"cameraId\": \"330106530211146660\",\n" +
                "        \"objId\": \"a37484d85d9e45a781c7325f0f2e580a\",\n" +
                "        \"objRight\": 271,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691489_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"a37484d85d9e45a781c7325f0f2e580a\",\n" +
                "      \"_score\": 0.88738585,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691488_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691488_s.jpg\",\n" +
                "        \"hairScore\": 0.56812334,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691488_s.jpg\",\n" +
                "        \"sexScore\": 0.6741696,\n" +
                "        \"upper_typeScore\": 0.4701406,\n" +
                "        \"hair\": 1,\n" +
                "        \"objLeft\": 1452,\n" +
                "        \"lower_type\": 2,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 897,\n" +
                "        \"objUUId\": \"330102540001131760_1559439009385_0_12433\",\n" +
                "        \"timestamp\": 1559439009385,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 0,\n" +
                "        \"objBottom\": 1077,\n" +
                "        \"lower_color\": 9,\n" +
                "        \"upper_colorScore\": 0.4740565,\n" +
                "        \"sex\": 1,\n" +
                "        \"lower_typeScore\": 0.57026124,\n" +
                "        \"entryTime\": 1559439008210,\n" +
                "        \"lower_colorScore\": 0.41560984,\n" +
                "        \"cameraId\": \"330106530211146660\",\n" +
                "        \"objId\": \"56fd6e8d73ad43918c66dc0f345c495d\",\n" +
                "        \"objRight\": 1592,\n" +
                "        \"upper_color\": 2,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691488_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"56fd6e8d73ad43918c66dc0f345c495d\",\n" +
                "      \"_score\": 0.8828314,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691487_s.jpg\",\n" +
                "        \"oriImageSigned\": \"../img/img/b3.jpg\",\n" +
                "        \"hairScore\": 0.8539175,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691487_s.jpg\",\n" +
                "        \"sexScore\": 0.5970783,\n" +
                "        \"upper_typeScore\": 0.4410029,\n" +
                "        \"hair\": 1,\n" +
                "        \"objLeft\": 942,\n" +
                "        \"lower_type\": 2,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 899,\n" +
                "        \"objUUId\": \"330117019900010000_1559439828042_1_7020\",\n" +
                "        \"timestamp\": 1559439828042,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 2,\n" +
                "        \"objBottom\": 1076,\n" +
                "        \"lower_color\": 9,\n" +
                "        \"upper_colorScore\": 0.4169154,\n" +
                "        \"sex\": 0,\n" +
                "        \"lower_typeScore\": 0.43089944,\n" +
                "        \"entryTime\": 1559439828042,\n" +
                "        \"lower_colorScore\": 0.46018422,\n" +
                "        \"cameraId\": \"330106710011244460\",\n" +
                "        \"objId\": \"7c355210fee54806ab246aef91ed9dfc\",\n" +
                "        \"objRight\": 1100,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691486_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"7c355210fee54806ab246aef91ed9dfc\",\n" +
                "      \"_score\": 0.88123775,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691486_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691486_s.jpg\",\n" +
                "        \"hairScore\": 0.82993346,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691486_s.jpg\",\n" +
                "        \"sexScore\": 0.8038736,\n" +
                "        \"upper_typeScore\": 0.39617947,\n" +
                "        \"hair\": 1,\n" +
                "        \"objLeft\": 283,\n" +
                "        \"lower_type\": 6,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 571,\n" +
                "        \"objUUId\": \"330119520001025303_1559440476767_0_17699\",\n" +
                "        \"timestamp\": 1559440476767,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 2,\n" +
                "        \"objBottom\": 718,\n" +
                "        \"lower_color\": 13,\n" +
                "        \"upper_colorScore\": 0.8371094,\n" +
                "        \"sex\": 0,\n" +
                "        \"lower_typeScore\": 0.49887255,\n" +
                "        \"entryTime\": 1559440476767,\n" +
                "        \"lower_colorScore\": 0.50681835,\n" +
                "        \"cameraId\": \"330119520001026503\",\n" +
                "        \"objId\": \"44ac48d8106b4e46b4a3dd000b39465b\",\n" +
                "        \"objRight\": 457,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691486_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"44ac48d8106b4e46b4a3dd000b39465b\",\n" +
                "      \"_score\": 0.88067305,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691485_s.jpg\",\n" +
                "        \"oriImageSigned\": \"../img/img/b5.jpg\",\n" +
                "        \"hairScore\": 0.6653193,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691485_s.jpg\",\n" +
                "        \"sexScore\": 0.6064582,\n" +
                "        \"upper_typeScore\": 0.54809207,\n" +
                "        \"hair\": 1,\n" +
                "        \"objLeft\": 137,\n" +
                "        \"lower_type\": 2,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 647,\n" +
                "        \"objUUId\": \"330119520001025303_1559439693710_1_10393\",\n" +
                "        \"timestamp\": 1559439693710,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 0,\n" +
                "        \"objBottom\": 718,\n" +
                "        \"lower_color\": 9,\n" +
                "        \"upper_colorScore\": 0.25725934,\n" +
                "        \"sex\": 1,\n" +
                "        \"lower_typeScore\": 0.6301192,\n" +
                "        \"entryTime\": 1559439693710,\n" +
                "        \"lower_colorScore\": 0.689523,\n" +
                "        \"cameraId\": \"330119520001026503\",\n" +
                "        \"objId\": \"bfd2f5b71bf8445baa64d0d4456ce46c\",\n" +
                "        \"objRight\": 245,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691489_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"bfd2f5b71bf8445baa64d0d4456ce46c\",\n" +
                "      \"_score\": 0.8770586,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691489_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691489_s.jpg\",\n" +
                "        \"hairScore\": 0.9686641,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691489_s.jpg\",\n" +
                "        \"sexScore\": 0.6111966,\n" +
                "        \"upper_typeScore\": 0.9075603,\n" +
                "        \"hair\": 1,\n" +
                "        \"objLeft\": 1676,\n" +
                "        \"lower_type\": 2,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 406,\n" +
                "        \"objUUId\": \"330102540001513219_1559440564520_0_8470\",\n" +
                "        \"timestamp\": 1559440564520,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 0,\n" +
                "        \"objBottom\": 622,\n" +
                "        \"lower_color\": 9,\n" +
                "        \"upper_colorScore\": 0.27087748,\n" +
                "        \"sex\": 0,\n" +
                "        \"lower_typeScore\": 0.8365562,\n" +
                "        \"entryTime\": 1559440564520,\n" +
                "        \"lower_colorScore\": 0.9060415,\n" +
                "        \"cameraId\": \"330106710011316260\",\n" +
                "        \"objId\": \"ed048018a6ab47eab06a7f6ba3a4e376\",\n" +
                "        \"objRight\": 1761,\n" +
                "        \"upper_color\": 9,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691489_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"ed048018a6ab47eab06a7f6ba3a4e376\",\n" +
                "      \"_score\": 0.8717074,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"hairScore\": 0.640658,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"sexScore\": 0.7636918,\n" +
                "        \"upper_typeScore\": 0.5326139,\n" +
                "        \"hair\": 1,\n" +
                "        \"objLeft\": 40,\n" +
                "        \"lower_type\": 2,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 936,\n" +
                "        \"objUUId\": \"330102540001513223_1559438479894_0_15583\",\n" +
                "        \"timestamp\": 1559438479894,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 2,\n" +
                "        \"objBottom\": 1076,\n" +
                "        \"lower_color\": 9,\n" +
                "        \"upper_colorScore\": 0.16038781,\n" +
                "        \"sex\": 1,\n" +
                "        \"lower_typeScore\": 0.59525996,\n" +
                "        \"entryTime\": 1559438479894,\n" +
                "        \"lower_colorScore\": 0.34516272,\n" +
                "        \"cameraId\": \"330106710011316260\",\n" +
                "        \"objId\": \"aa0008e893db4dddbc16dd5871875192\",\n" +
                "        \"objRight\": 344,\n" +
                "        \"upper_color\": 2,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"aa0008e893db4dddbc16dd5871875192\",\n" +
                "      \"_score\": 0.8716339,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"hairScore\": 0.94429225,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691501_s.jpg\",\n" +
                "        \"sexScore\": 0.53937894,\n" +
                "        \"upper_typeScore\": 0.8223879,\n" +
                "        \"hair\": 1,\n" +
                "        \"objLeft\": 1688,\n" +
                "        \"lower_type\": 1,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 410,\n" +
                "        \"objUUId\": \"330102540001513219_1559440564885_0_15697\",\n" +
                "        \"timestamp\": 1559440564885,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 0,\n" +
                "        \"objBottom\": 705,\n" +
                "        \"lower_color\": 9,\n" +
                "        \"upper_colorScore\": 0.30349064,\n" +
                "        \"sex\": 1,\n" +
                "        \"lower_typeScore\": 0.37291217,\n" +
                "        \"entryTime\": 1559440557522,\n" +
                "        \"lower_colorScore\": 0.8297801,\n" +
                "        \"cameraId\": \"330106710011316260\",\n" +
                "        \"objId\": \"721180650a9b47d0923e09e5fd3aac24\",\n" +
                "        \"objRight\": 1808,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"721180650a9b47d0923e09e5fd3aac24\",\n" +
                "      \"_score\": 0.8716172,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"hairScore\": 0.48905444,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691501_s.jpg\",\n" +
                "        \"sexScore\": 0.78384227,\n" +
                "        \"upper_typeScore\": 0.6327143,\n" +
                "        \"hair\": 1,\n" +
                "        \"objLeft\": 421,\n" +
                "        \"lower_type\": 2,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 875,\n" +
                "        \"objUUId\": \"330102540001513223_1559439083438_0_24963\",\n" +
                "        \"timestamp\": 1559439083438,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 0,\n" +
                "        \"objBottom\": 1075,\n" +
                "        \"lower_color\": 9,\n" +
                "        \"upper_colorScore\": 0.8333851,\n" +
                "        \"sex\": 1,\n" +
                "        \"lower_typeScore\": 0.35940742,\n" +
                "        \"entryTime\": 1559439083438,\n" +
                "        \"lower_colorScore\": 0.3787323,\n" +
                "        \"cameraId\": \"330106710018439260\",\n" +
                "        \"objId\": \"3b594608003e4634807237033b97e103\",\n" +
                "        \"objRight\": 628,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"3b594608003e4634807237033b97e103\",\n" +
                "      \"_score\": 0.8714435,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"hairScore\": 0.929774,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691501_s.jpg\",\n" +
                "        \"sexScore\": 0.76415086,\n" +
                "        \"upper_typeScore\": 0.662874,\n" +
                "        \"hair\": 1,\n" +
                "        \"objLeft\": 550,\n" +
                "        \"lower_type\": 2,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 893,\n" +
                "        \"objUUId\": \"330117019900010000_1559439827391_1_2594\",\n" +
                "        \"timestamp\": 1559439827391,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 0,\n" +
                "        \"objBottom\": 1074,\n" +
                "        \"lower_color\": 9,\n" +
                "        \"upper_colorScore\": 0.2709052,\n" +
                "        \"sex\": 0,\n" +
                "        \"lower_typeScore\": 0.67862815,\n" +
                "        \"entryTime\": 1559439827391,\n" +
                "        \"lower_colorScore\": 0.56764174,\n" +
                "        \"cameraId\": \"330102540001634760\",\n" +
                "        \"objId\": \"d1c81c27f4fa4b17834db81988c3c4c4\",\n" +
                "        \"objRight\": 714,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"d1c81c27f4fa4b17834db81988c3c4c4\",\n" +
                "      \"_score\": 0.87116855,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"hairScore\": 0.9571443,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691501_s.jpg\",\n" +
                "        \"sexScore\": 0.95762694,\n" +
                "        \"upper_typeScore\": 0.85720795,\n" +
                "        \"hair\": 1,\n" +
                "        \"objLeft\": 40,\n" +
                "        \"lower_type\": 2,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 574,\n" +
                "        \"objUUId\": \"330119520001025303_1559440005485_1_3126\",\n" +
                "        \"timestamp\": 1559440005485,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 0,\n" +
                "        \"objBottom\": 715,\n" +
                "        \"lower_color\": 9,\n" +
                "        \"upper_colorScore\": 0.34993902,\n" +
                "        \"sex\": 0,\n" +
                "        \"lower_typeScore\": 0.84178066,\n" +
                "        \"entryTime\": 1559440005485,\n" +
                "        \"lower_colorScore\": 0.4785236,\n" +
                "        \"cameraId\": \"330119520001016003\",\n" +
                "        \"objId\": \"d3f7bbdc031e4437ac1a07c54f94452b\",\n" +
                "        \"objRight\": 228,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"d3f7bbdc031e4437ac1a07c54f94452b\",\n" +
                "      \"_score\": 0.8710928,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"hairScore\": 0.52144307,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691501_s.jpg\",\n" +
                "        \"sexScore\": 0.9195852,\n" +
                "        \"upper_typeScore\": 0.63467854,\n" +
                "        \"hair\": 2,\n" +
                "        \"objLeft\": 130,\n" +
                "        \"lower_type\": 6,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 900,\n" +
                "        \"objUUId\": \"330102540001513223_1559439501488_0_20264\",\n" +
                "        \"timestamp\": 1559439501488,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 0,\n" +
                "        \"objBottom\": 1070,\n" +
                "        \"lower_color\": 9,\n" +
                "        \"upper_colorScore\": 0.48184937,\n" +
                "        \"sex\": 1,\n" +
                "        \"lower_typeScore\": 0.3653439,\n" +
                "        \"entryTime\": 1559439501488,\n" +
                "        \"lower_colorScore\": 0.33376402,\n" +
                "        \"cameraId\": \"330119520001016003\",\n" +
                "        \"objId\": \"6904f1748cc74f238f4da3010f79ea8c\",\n" +
                "        \"objRight\": 426,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691501_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"6904f1748cc74f238f4da3010f79ea8c\",\n" +
                "      \"_score\": 0.87032753,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"hairScore\": 0.9777385,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691501_s.jpg\",\n" +
                "        \"sexScore\": 0.9073448,\n" +
                "        \"upper_typeScore\": 0.672501,\n" +
                "        \"hair\": 1,\n" +
                "        \"objLeft\": 563,\n" +
                "        \"lower_type\": 2,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 583,\n" +
                "        \"objUUId\": \"330102540083470260_1559437805391_1_3559\",\n" +
                "        \"timestamp\": 1559437805391,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 0,\n" +
                "        \"objBottom\": 865,\n" +
                "        \"lower_color\": 9,\n" +
                "        \"upper_colorScore\": 0.53582484,\n" +
                "        \"sex\": 0,\n" +
                "        \"lower_typeScore\": 0.92632914,\n" +
                "        \"entryTime\": 1559437805391,\n" +
                "        \"lower_colorScore\": 0.520542,\n" +
                "        \"cameraId\": \"330119520001975903\",\n" +
                "        \"objId\": \"e20aaf6d0b6c46178c273450a18fa71e\",\n" +
                "        \"objRight\": 696,\n" +
                "        \"upper_color\": 9,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"e20aaf6d0b6c46178c273450a18fa71e\",\n" +
                "      \"_score\": 0.8701077,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"hairScore\": 0.5422348,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691501_s.jpg\",\n" +
                "        \"sexScore\": 0.93943167,\n" +
                "        \"upper_typeScore\": 0.4578791,\n" +
                "        \"hair\": 2,\n" +
                "        \"objLeft\": 162,\n" +
                "        \"lower_type\": 2,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 856,\n" +
                "        \"objUUId\": \"330102540001513223_1559438480095_1_16920\",\n" +
                "        \"timestamp\": 1559438480095,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 0,\n" +
                "        \"objBottom\": 1077,\n" +
                "        \"lower_color\": 9,\n" +
                "        \"upper_colorScore\": 0.19661461,\n" +
                "        \"sex\": 1,\n" +
                "        \"lower_typeScore\": 0.4188326,\n" +
                "        \"entryTime\": 1559438480095,\n" +
                "        \"lower_colorScore\": 0.6738354,\n" +
                "        \"cameraId\": \"330102540003134860\",\n" +
                "        \"objId\": \"e299112a6978419f928712abb9883904\",\n" +
                "        \"objRight\": 361,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691501_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"e299112a6978419f928712abb9883904\",\n" +
                "      \"_score\": 0.8700134,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"hairScore\": 0.9266526,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691501_s.jpg\",\n" +
                "        \"sexScore\": 0.72620076,\n" +
                "        \"upper_typeScore\": 0.9268774,\n" +
                "        \"hair\": 1,\n" +
                "        \"objLeft\": 1495,\n" +
                "        \"lower_type\": 2,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 493,\n" +
                "        \"objUUId\": \"330102540001131760_1559439099612_0_13162\",\n" +
                "        \"timestamp\": 1559439099612,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 0,\n" +
                "        \"objBottom\": 671,\n" +
                "        \"lower_color\": 9,\n" +
                "        \"upper_colorScore\": 0.37962916,\n" +
                "        \"sex\": 0,\n" +
                "        \"lower_typeScore\": 0.323678,\n" +
                "        \"entryTime\": 1559439099612,\n" +
                "        \"lower_colorScore\": 0.37475058,\n" +
                "        \"cameraId\": \"330119570020050084\",\n" +
                "        \"objId\": \"eb84f76d4a41498aa943790374fd3a75\",\n" +
                "        \"objRight\": 1568,\n" +
                "        \"upper_color\": 9,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691501_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"eb84f76d4a41498aa943790374fd3a75\",\n" +
                "      \"_score\": 0.86958915,\n" +
                "      \"_ext\": null\n" +
                "    }\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"hairScore\": 0.52144307,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691501_s.jpg\",\n" +
                "        \"sexScore\": 0.9195852,\n" +
                "        \"upper_typeScore\": 0.63467854,\n" +
                "        \"hair\": 2,\n" +
                "        \"objLeft\": 130,\n" +
                "        \"lower_type\": 6,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 900,\n" +
                "        \"objUUId\": \"330102540001513223_1559439501488_0_20264\",\n" +
                "        \"timestamp\": 1559439501488,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 0,\n" +
                "        \"objBottom\": 1070,\n" +
                "        \"lower_color\": 9,\n" +
                "        \"upper_colorScore\": 0.48184937,\n" +
                "        \"sex\": 1,\n" +
                "        \"lower_typeScore\": 0.3653439,\n" +
                "        \"entryTime\": 1559439501488,\n" +
                "        \"lower_colorScore\": 0.33376402,\n" +
                "        \"cameraId\": \"330106710011331560\",\n" +
                "        \"objId\": \"6904f1748cc74f238f4da3010f79ea8c\",\n" +
                "        \"objRight\": 426,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"6904f1748cc74f238f4da3010f79ea8c\",\n" +
                "      \"_score\": 0.87032753,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"hairScore\": 0.9777385,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691501_s.jpg\",\n" +
                "        \"sexScore\": 0.9073448,\n" +
                "        \"upper_typeScore\": 0.672501,\n" +
                "        \"hair\": 1,\n" +
                "        \"objLeft\": 563,\n" +
                "        \"lower_type\": 2,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 583,\n" +
                "        \"objUUId\": \"330102540083470260_1559437805391_1_3559\",\n" +
                "        \"timestamp\": 1559437805391,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 0,\n" +
                "        \"objBottom\": 865,\n" +
                "        \"lower_color\": 9,\n" +
                "        \"upper_colorScore\": 0.53582484,\n" +
                "        \"sex\": 0,\n" +
                "        \"lower_typeScore\": 0.92632914,\n" +
                "        \"entryTime\": 1559437805391,\n" +
                "        \"lower_colorScore\": 0.520542,\n" +
                "        \"cameraId\": \"330106530001619760\",\n" +
                "        \"objId\": \"e20aaf6d0b6c46178c273450a18fa71e\",\n" +
                "        \"objRight\": 696,\n" +
                "        \"upper_color\": 9,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691501_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"e20aaf6d0b6c46178c273450a18fa71e\",\n" +
                "      \"_score\": 0.8701077,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"hairScore\": 0.5422348,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691501_s.jpg\",\n" +
                "        \"sexScore\": 0.93943167,\n" +
                "        \"upper_typeScore\": 0.4578791,\n" +
                "        \"hair\": 2,\n" +
                "        \"objLeft\": 162,\n" +
                "        \"lower_type\": 2,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 856,\n" +
                "        \"objUUId\": \"330102540001513223_1559438480095_1_16920\",\n" +
                "        \"timestamp\": 1559438480095,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 0,\n" +
                "        \"objBottom\": 1077,\n" +
                "        \"lower_color\": 9,\n" +
                "        \"upper_colorScore\": 0.19661461,\n" +
                "        \"sex\": 1,\n" +
                "        \"lower_typeScore\": 0.4188326,\n" +
                "        \"entryTime\": 1559438480095,\n" +
                "        \"lower_colorScore\": 0.6738354,\n" +
                "        \"cameraId\": \"330106530001619760\",\n" +
                "        \"objId\": \"e299112a6978419f928712abb9883904\",\n" +
                "        \"objRight\": 361,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691501_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"e299112a6978419f928712abb9883904\",\n" +
                "      \"_score\": 0.8700134,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"oriImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\",\n" +
                "        \"hairScore\": 0.9266526,\n" +
                "        \"cropImage\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691501_s.jpg\",\n" +
                "        \"sexScore\": 0.72620076,\n" +
                "        \"upper_typeScore\": 0.9268774,\n" +
                "        \"hair\": 1,\n" +
                "        \"objLeft\": 1495,\n" +
                "        \"lower_type\": 2,\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 493,\n" +
                "        \"objUUId\": \"330102540001131760_1559439099612_0_13162\",\n" +
                "        \"timestamp\": 1559439099612,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": 0,\n" +
                "        \"objBottom\": 671,\n" +
                "        \"lower_color\": 9,\n" +
                "        \"upper_colorScore\": 0.37962916,\n" +
                "        \"sex\": 0,\n" +
                "        \"lower_typeScore\": 0.323678,\n" +
                "        \"entryTime\": 1559439099612,\n" +
                "        \"lower_colorScore\": 0.37475058,\n" +
                "        \"cameraId\": \"330106710011300560\",\n" +
                "        \"objId\": \"eb84f76d4a41498aa943790374fd3a75\",\n" +
                "        \"objRight\": 1568,\n" +
                "        \"upper_color\": 9,\n" +
                "        \"cropImageSigned\": \"https://k.zol-img.com.cn/sjbbs/7692/a7691400_s.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"eb84f76d4a41498aa943790374fd3a75\",\n" +
                "      \"_score\": 0.86958915,\n" +
                "      \"_ext\": null\n" +
                "    }\n" +
                "  ],\n" +
                "  \"errorCode\": \"SUCCESS\",\n" +
                "  \"totalCount\": 40,\n" +
                "  \"errorMsg\": \"\"\n" +
                "}\n";
        SearchResponse searchResponse = JSON.parseObject(responseStr, new TypeReference<SearchResponse>() {
        });
        if(searchResponse.getData()==null){
            return ResultVo.failure(BizExceptionEnum.PARAM_ERROR.getCode(),searchResponse.getErrorMsg());
        }
        List<SearchResponseData> olddatas = searchResponse.getData();
        //获取全部的设备列表
        //如果设备列表为空
        DeviceBean device = new DeviceBean();
        List<DeviceBean> deviceBeans = deviceMapper.readDeviceList(device);
        olddatas.forEach(searchResponseData -> {
            deviceBeans.forEach(deviceBean ->{
                if (deviceBean.getDeviceId().equals(searchResponseData.getSource().getCameraId())){
                    searchResponseData.getSource().setDeviceBean(deviceBean);
                }
            });
            if(searchResponseData.getSource().getDeviceBean()==null){
                DeviceBean deviceBean = new DeviceBean();
                deviceBean.setDeviceName("未命名");
                searchResponseData.getSource().setDeviceBean(deviceBean);
            }
        });
        List<SearchResponseData> olddata = olddatas.subList(from, from + size);
        System.out.println(olddata.size());


        //相似度排序
        Collections.sort(olddata , new Comparator<SearchResponseData>() {
            @Override
            public int compare(SearchResponseData o1, SearchResponseData o2) {
                Double score1 = o1.getScore();
                Double score2 = o2.getScore();
                if (score1<score2){
                    return 1;
                }else{
                    return -1;
                }
            }
        });
        ArrayList<SearchResponseData> data = new ArrayList<>();
        data.addAll(olddata);

        // 时间戳排序
        if (data !=null && data.size()>1){
            Collections.sort(data , new Comparator<SearchResponseData>() {
                @Override
                public int compare(SearchResponseData o1, SearchResponseData o2) {
                    Long o1Value = o1.getSource().getTimestamp();
                    Long o2Value = o2.getSource().getTimestamp();
                    if (o1Value<o2Value){
                        return 1;
                    }else{
                        return -1;
                    }
                }
            });
        }

        //设备分组排序
        List<SearchResponseData> dataList =olddata;
        ArrayList<String> deviceIds = new ArrayList<>();
        Set<String> set = new HashSet<String>();
        Map<String,List> resultMap = new LinkedHashMap<String,List>();
        dataList.forEach(searchResponseData -> {
            SearchSource source = searchResponseData.getSource();
            String cameraId = source.getCameraId();
            if (set.contains(cameraId)){
                resultMap.get(cameraId).add(searchResponseData);
            }else {
                set.add(cameraId);
                List<SearchResponseData> list1 = new ArrayList<>();
                list1.add(searchResponseData);
                resultMap.put(cameraId,list1);
            }
        });
        //把Map中的value取出来放入List返回
        ArrayList<List> resultList = new ArrayList<>();
        resultMap.forEach((k,v)->{
            resultList.add(v);
        });
        HashMap<String, Object> stringSearchDataHashMap = new HashMap<>();
        stringSearchDataHashMap.put("score",olddata);
        stringSearchDataHashMap.put("timestamp",data);
        stringSearchDataHashMap.put("device",resultList);

        Long totalCount = searchResponse.getTotalCount();
        String responStr = JSON.toJSONString(ResultVo.success(stringSearchDataHashMap,totalCount), SerializerFeature.DisableCircularReferenceDetect);
        ResultVo resultVo = JSON.parseObject(responStr, new TypeReference<ResultVo>() {
        });
        return resultVo;
    }

    @Override
    public ResultVo cacheSearch(SysOperationLog sysOperationLogg) {
        //返回时 需要将ids里面图片转化为OssUrl
        List<SysOperationLog> sysOperationLogs = sysOperationLogMapper.selectThreeLog(sysOperationLogg);
        List<ArrayList<SearchRequestData>> searchRequests =new ArrayList<>();
        int j=0;
        HashMap<Integer, StringBuilder> map = new HashMap<Integer, StringBuilder>();
        for (SysOperationLog sysOperationLog:sysOperationLogs) {
            ArrayList<String> strings = new ArrayList<>();
            String param = sysOperationLog.getParam();
            BaseRequest<SearchRequestData> searchRequestBaseRequest = JSON.parseObject(param, new TypeReference<BaseRequest<SearchRequestData>>() {
            });
            if(searchRequestBaseRequest!=null&&searchRequestBaseRequest.getData()!=null&&StringUtils.isNotEmpty(searchRequestBaseRequest.getData().getIds())){
                String ids = searchRequestBaseRequest.getData().getIds();
                String[] split = ids.split(",");
                //重新查询id对应的 ossurl
                for (int i = 0; i <split.length ; i++) {
                    BaseRequest<SearchRequestData> searchDataBaseRequest = new BaseRequest<>();
                    SearchRequestData searchRequestData = new SearchRequestData();
                    searchRequestData.setUid("hangzhou");
                    searchRequestData.setTaskId("512041492240442db7462770e968e785");
                    searchRequestData.setType("person");
                    searchRequestData.setNoFeature("1");
                    searchRequestData.setIds(split[i]);
                    searchDataBaseRequest.setData(searchRequestData);
                    //返回参数取出oss
                    BaseResponse<JSONArray> search = tuSouSearchService.search(searchDataBaseRequest);
                    JSONArray data = search.getData();
                    ArrayList<SearchResponseData> searchResponseData = JSON.parseObject(JSON.toJSONString(data), new TypeReference<ArrayList<SearchResponseData>>() {
                    });
                    strings.add(searchResponseData.get(0).getSource().getCropImageSigned());
                }
                StringBuilder sb=new StringBuilder();
                for (String cropurl:strings) {
                    if(sb.length()==0){
                        sb.append(cropurl);
                    }else{
                        sb.append(",");
                        sb.append(cropurl);
                    }
                }
                map.put(j,sb);
            }
            j++;
        };
        ArrayList<String> params = new ArrayList<>();
        sysOperationLogs.forEach(sysOperationLog -> {
            BaseRequest<SearchRequestData> searchRequestBaseRequest = JSON.parseObject(sysOperationLog.getParam(), new TypeReference<BaseRequest<SearchRequestData>>() {
            });
            params.add(JSON.toJSONString(searchRequestBaseRequest.getData()));
        });
        //把有ids的参数增加Ossurls参数
        if(!map.isEmpty()){
            for (Integer in : map.keySet()) {
                String param = sysOperationLogs.get(in).getParam();
                BaseRequest<SearchRequestData> searchRequestBaseRequest = JSON.parseObject(param, new TypeReference<BaseRequest<SearchRequestData>>() {
                });
                searchRequestBaseRequest.getData().setOssUrls(map.get(in).toString());
                params.set(in,JSON.toJSONString(searchRequestBaseRequest.getData()));
            }
        }
        JSONArray jsonObject = JSON.parseArray(params.toString());
        return ResultVo.success(jsonObject);
    }
}
