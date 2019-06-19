package com.secusoft.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.secusoft.web.config.ServiceApiConfig;
import com.secusoft.web.core.base.controller.BaseController;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.mapper.*;
import com.secusoft.web.model.*;
import com.secusoft.web.serviceapi.ServiceApiClient;
import com.secusoft.web.tusouapi.model.SearchData;
import com.secusoft.web.tusouapi.model.SearchResponse;
import com.secusoft.web.tusouapi.model.SearchSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class TestController extends BaseController {
    @Resource
    private PictureMapper pictureMapper;
    @Resource
    private TrackMapper trackMapper;
    @Resource
    private AreaMapper areaMapper;
    @Resource
    private FolderMapper folderMapper;
    @Resource
    private DeviceMapper deviceMapper;

    @Resource
    private ViPsurveyAlaramMapper viPsurveyAlaramMapper;

    @Resource
    private ViPsurveyAlaramDetailMapper viPsurveyAlaramDetailMapper;

    @RequestMapping("/testalaram")
    public Object testAlaram() {
        String responseStr = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getGetViPsurveyAlaram(), "");

        JSONObject jsonObject= (JSONObject) JSONObject.parse(responseStr);
        String code=jsonObject.getString("code");
        String data=jsonObject.getString("data");
        if(String.valueOf(BizExceptionEnum.OK.getCode()).equals(code)&&(!data.isEmpty()||!"null".equals(data))) {
            JSONArray jsonArrayData = (JSONArray) JSONArray.parse(data);
            JSONObject jsonData = (JSONObject) JSONObject.parse(jsonArrayData.getString(0));
            String taskId = jsonData.getString("taskId");

            //布控报警
            String src = jsonData.getString("src");
            ViPsurveyAlaramBean viPsurveyAlaramBean = (ViPsurveyAlaramBean) JSONObject.parseObject(src, ViPsurveyAlaramBean.class);
            viPsurveyAlaramBean.setTaskId(taskId);
            viPsurveyAlaramMapper.insertViPsurveyAlaram(viPsurveyAlaramBean);
            //人员报警布控图比对
            String similar = jsonData.getString("similar");
            List<ViPsurveyAlaramDetailBean> detailBeanList = (List<ViPsurveyAlaramDetailBean>) JSONObject.parseArray(similar, ViPsurveyAlaramDetailBean.class);
            for (ViPsurveyAlaramDetailBean bean: detailBeanList) {
                bean.setAlarmId(viPsurveyAlaramBean.getId());
                bean.setTaskId(taskId);
                bean.setAlarmType("测试");
                bean.setAlarmStatus(1);
            }
            viPsurveyAlaramDetailMapper.insertBatch(detailBeanList);

        }
        return null;
    }

    @RequestMapping("/test")
    public Object test() {
//        List<PictureBean> pictureBeans = pictureMapper.selectPictureByTid("2");
//        String s = JSON.toJSONString(pictureBeans, SerializerFeature.DisableCircularReferenceDetect);
//        JSONArray objects = JSON.parseArray(s);
//        return objects;
//        List<TrackBean> trackBeans = trackMapper.selectTrackByFid("2");
//        String s = JSON.toJSONString(trackBeans, SerializerFeature.DisableCircularReferenceDetect);
//        JSONArray objects = JSON.parseArray(s);
//        return objects;
        String fid = "2";
        FolderBean folderBean = folderMapper.selectOneFolder(fid);
        List<PictureBean> pictureBeans = pictureMapper.selectPictureByFid(fid);
        List<TrackBean> trackBeans = trackMapper.selectTrackByFid(fid);
        List<AreaBean> areaBeans = areaMapper.selectAreaByFid(fid);
        folderBean.setImageSearchList(pictureBeans);
        folderBean.setDeviceArea(areaBeans);
        folderBean.setTrackList(trackBeans);
        String s = JSON.toJSONString(folderBean, SerializerFeature.DisableCircularReferenceDetect);
        JSONObject object = JSON.parseObject(s);
        return object;

    }

    @RequestMapping("/testSeach")
    public Object testSeach() {
        String akun = "{\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"../img/img/b1.jpg\",\n" +
                "        \"oriImageSigned\": \"../img/img/b1.jpg\",\n" +
                "        \"hairScore\": 0.85698605,\n" +
                "        \"cropImage\": \"../img/img/s1.jpg\",\n" +
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
                "        \"cameraId\": \"1\",\n" +
                "        \"objId\": \"a37484d85d9e45a781c7325f0f2e580a\",\n" +
                "        \"objRight\": 271,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"../img/img/s1.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"a37484d85d9e45a781c7325f0f2e580a\",\n" +
                "      \"_score\": 0.88738585,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"../img/img/b2.jpg\",\n" +
                "        \"oriImageSigned\": \"../img/img/b2.jpg\",\n" +
                "        \"hairScore\": 0.56812334,\n" +
                "        \"cropImage\": \"../img/img/s2.jpg\",\n" +
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
                "        \"cameraId\": \"2\",\n" +
                "        \"objId\": \"56fd6e8d73ad43918c66dc0f345c495d\",\n" +
                "        \"objRight\": 1592,\n" +
                "        \"upper_color\": 2,\n" +
                "        \"cropImageSigned\": \"../img/img/s2.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"56fd6e8d73ad43918c66dc0f345c495d\",\n" +
                "      \"_score\": 0.8828314,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"../img/img/b3.jpg\",\n" +
                "        \"oriImageSigned\": \"../img/img/b3.jpg\",\n" +
                "        \"hairScore\": 0.8539175,\n" +
                "        \"cropImage\": \"../img/img/b3.jpg\",\n" +
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
                "        \"cameraId\": \"3\",\n" +
                "        \"objId\": \"7c355210fee54806ab246aef91ed9dfc\",\n" +
                "        \"objRight\": 1100,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"../img/img/s3.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"7c355210fee54806ab246aef91ed9dfc\",\n" +
                "      \"_score\": 0.88123775,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"../img/img/b4.jpg\",\n" +
                "        \"oriImageSigned\": \"../img/img/b4.jpg\",\n" +
                "        \"hairScore\": 0.82993346,\n" +
                "        \"cropImage\": \"../img/img/b4.jpg\",\n" +
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
                "        \"cameraId\": \"3\",\n" +
                "        \"objId\": \"44ac48d8106b4e46b4a3dd000b39465b\",\n" +
                "        \"objRight\": 457,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"../img/img/b4.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"44ac48d8106b4e46b4a3dd000b39465b\",\n" +
                "      \"_score\": 0.88067305,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"../img/img/b5.jpg\",\n" +
                "        \"oriImageSigned\": \"../img/img/b5.jpg\",\n" +
                "        \"hairScore\": 0.6653193,\n" +
                "        \"cropImage\": \"../img/img/s5.jpg\",\n" +
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
                "        \"cameraId\": \"3\",\n" +
                "        \"objId\": \"bfd2f5b71bf8445baa64d0d4456ce46c\",\n" +
                "        \"objRight\": 245,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"../img/img/s5.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"bfd2f5b71bf8445baa64d0d4456ce46c\",\n" +
                "      \"_score\": 0.8770586,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"../img/img/b6.jpg\",\n" +
                "        \"oriImageSigned\": \"../img/img/b6.jpg\",\n" +
                "        \"hairScore\": 0.9665299,\n" +
                "        \"cropImage\": \"../img/img/s6.jpg\",\n" +
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
                "        \"cameraId\": \"4\",\n" +
                "        \"objId\": \"a3beb8369f4441b1a857df2ce48c18fa\",\n" +
                "        \"objRight\": 1906,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"../img/img/s6.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"a3beb8369f4441b1a857df2ce48c18fa\",\n" +
                "      \"_score\": 0.87405056,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"../img/img/b7.jpg\",\n" +
                "        \"oriImageSigned\": \"../img/img/b7.jpg\",\n" +
                "        \"hairScore\": 0.4807687,\n" +
                "        \"cropImage\": \"../img/img/s7.jpg\",\n" +
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
                "        \"cameraId\": \"5\",\n" +
                "        \"objId\": \"c700d983837e419f833d93c0f8b91e52\",\n" +
                "        \"objRight\": 1908,\n" +
                "        \"upper_color\": 12,\n" +
                "        \"cropImageSigned\": \"../img/img/s7.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"c700d983837e419f833d93c0f8b91e52\",\n" +
                "      \"_score\": 0.87394196,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"../img/img/b8.jpg\",\n" +
                "        \"oriImageSigned\": \"../img/img/b8.jpg\",\n" +
                "        \"hairScore\": 0.9527993,\n" +
                "        \"cropImage\": \"../img/img/s8.jpg\",\n" +
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
                "        \"cameraId\": \"2\",\n" +
                "        \"objId\": \"77342791e0fc43819b83fb8a7dcd0c09\",\n" +
                "        \"objRight\": 647,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"../img/img/s8.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"77342791e0fc43819b83fb8a7dcd0c09\",\n" +
                "      \"_score\": 0.87332696,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"../img/img/b9.jpg\",\n" +
                "        \"oriImageSigned\": \"../img/img/b9.jpg\",\n" +
                "        \"hairScore\": 0.9329962,\n" +
                "        \"cropImage\": \"../img/img/s9.jpg\",\n" +
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
                "        \"cameraId\": \"4\",\n" +
                "        \"objId\": \"f68683208466445da6a1db9eaa5697a6\",\n" +
                "        \"objRight\": 1220,\n" +
                "        \"upper_color\": 7,\n" +
                "        \"cropImageSigned\": \"../img/img/s9.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"f68683208466445da6a1db9eaa5697a6\",\n" +
                "      \"_score\": 0.8723895,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"../img/img/b10.jpg\",\n" +
                "        \"oriImageSigned\": \"../img/img/b10.jpg\",\n" +
                "        \"hairScore\": 0.6101619,\n" +
                "        \"cropImage\": \"../img/img/s10.jpg\",\n" +
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
                "        \"cameraId\": \"5\",\n" +
                "        \"objId\": \"94dcece4062141089d71e4a5d670f049\",\n" +
                "        \"objRight\": 285,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"../img/img/s10.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"94dcece4062141089d71e4a5d670f049\",\n" +
                "      \"_score\": 0.8723822,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"../img/img/b11.jpg\",\n" +
                "        \"oriImageSigned\": \"../img/img/b11.jpg\",\n" +
                "        \"hairScore\": 0.9686641,\n" +
                "        \"cropImage\": \"../img/img/s11.jpg\",\n" +
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
                "        \"cameraId\": \"1\",\n" +
                "        \"objId\": \"ed048018a6ab47eab06a7f6ba3a4e376\",\n" +
                "        \"objRight\": 1761,\n" +
                "        \"upper_color\": 9,\n" +
                "        \"cropImageSigned\": \"../img/img/s11.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"ed048018a6ab47eab06a7f6ba3a4e376\",\n" +
                "      \"_score\": 0.8717074,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"../img/img/b12.jpg\",\n" +
                "        \"oriImageSigned\": \"../img/img/b12.jpg\",\n" +
                "        \"hairScore\": 0.640658,\n" +
                "        \"cropImage\": \"../img/img/s12.jpg\",\n" +
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
                "        \"cameraId\": \"2\",\n" +
                "        \"objId\": \"aa0008e893db4dddbc16dd5871875192\",\n" +
                "        \"objRight\": 344,\n" +
                "        \"upper_color\": 2,\n" +
                "        \"cropImageSigned\": \"../img/img/s12.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"aa0008e893db4dddbc16dd5871875192\",\n" +
                "      \"_score\": 0.8716339,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"../img/img/b13.jpg\",\n" +
                "        \"oriImageSigned\": \"../img/img/b13.jpg\",\n" +
                "        \"hairScore\": 0.94429225,\n" +
                "        \"cropImage\": \"../img/img/s13.jpg\",\n" +
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
                "        \"cameraId\": \"3\",\n" +
                "        \"objId\": \"721180650a9b47d0923e09e5fd3aac24\",\n" +
                "        \"objRight\": 1808,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"../img/img/b13.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"721180650a9b47d0923e09e5fd3aac24\",\n" +
                "      \"_score\": 0.8716172,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"../img/img/b14.jpg\",\n" +
                "        \"oriImageSigned\": \"../img/img/b14.jpg\",\n" +
                "        \"hairScore\": 0.48905444,\n" +
                "        \"cropImage\": \"../img/img/s14.jpg\",\n" +
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
                "        \"cameraId\": \"3\",\n" +
                "        \"objId\": \"3b594608003e4634807237033b97e103\",\n" +
                "        \"objRight\": 628,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"../img/img/s14.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"3b594608003e4634807237033b97e103\",\n" +
                "      \"_score\": 0.8714435,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"../img/img/b15.jpg\",\n" +
                "        \"oriImageSigned\": \"../img/img/b15.jpg\",\n" +
                "        \"hairScore\": 0.929774,\n" +
                "        \"cropImage\": \"../img/img/s15.jpg\",\n" +
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
                "        \"cameraId\": \"4\",\n" +
                "        \"objId\": \"d1c81c27f4fa4b17834db81988c3c4c4\",\n" +
                "        \"objRight\": 714,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"../img/img/s15.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"d1c81c27f4fa4b17834db81988c3c4c4\",\n" +
                "      \"_score\": 0.87116855,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"../img/img/b16.jpg\",\n" +
                "        \"oriImageSigned\": \"../img/img/b16.jpg\",\n" +
                "        \"hairScore\": 0.9571443,\n" +
                "        \"cropImage\": \"../img/img/s16.jpg\",\n" +
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
                "        \"cameraId\": \"5\",\n" +
                "        \"objId\": \"d3f7bbdc031e4437ac1a07c54f94452b\",\n" +
                "        \"objRight\": 228,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"../img/img/s16.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"d3f7bbdc031e4437ac1a07c54f94452b\",\n" +
                "      \"_score\": 0.8710928,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"../img/img/b17.jpg\",\n" +
                "        \"oriImageSigned\": \"../img/img/b17.jpg\",\n" +
                "        \"hairScore\": 0.52144307,\n" +
                "        \"cropImage\": \"../img/img/s17.jpg\",\n" +
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
                "        \"cameraId\": \"6\",\n" +
                "        \"objId\": \"6904f1748cc74f238f4da3010f79ea8c\",\n" +
                "        \"objRight\": 426,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"../img/img/s17.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"6904f1748cc74f238f4da3010f79ea8c\",\n" +
                "      \"_score\": 0.87032753,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"../img/img/b18.jpg\",\n" +
                "        \"oriImageSigned\": \"../img/img/b18.jpg\",\n" +
                "        \"hairScore\": 0.9777385,\n" +
                "        \"cropImage\": \"../img/img/s18.jpg\",\n" +
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
                "        \"cameraId\": \"5\",\n" +
                "        \"objId\": \"e20aaf6d0b6c46178c273450a18fa71e\",\n" +
                "        \"objRight\": 696,\n" +
                "        \"upper_color\": 9,\n" +
                "        \"cropImageSigned\": \"../img/img/s18.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"e20aaf6d0b6c46178c273450a18fa71e\",\n" +
                "      \"_score\": 0.8701077,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"../img/img/b19.jpg\",\n" +
                "        \"oriImageSigned\": \"../img/img/b19.jpg\",\n" +
                "        \"hairScore\": 0.5422348,\n" +
                "        \"cropImage\": \"../img/img/s19.jpg\",\n" +
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
                "        \"cameraId\": \"4\",\n" +
                "        \"objId\": \"e299112a6978419f928712abb9883904\",\n" +
                "        \"objRight\": 361,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"../img/img/s19.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"e299112a6978419f928712abb9883904\",\n" +
                "      \"_score\": 0.8700134,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"../img/img/b20.jpg\",\n" +
                "        \"oriImageSigned\": \"../img/img/b20.jpg\",\n" +
                "        \"hairScore\": 0.9266526,\n" +
                "        \"cropImage\": \"../img/img/s20.jpg\",\n" +
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
                "        \"cameraId\": \"1\",\n" +
                "        \"objId\": \"eb84f76d4a41498aa943790374fd3a75\",\n" +
                "        \"objRight\": 1568,\n" +
                "        \"upper_color\": 9,\n" +
                "        \"cropImageSigned\": \"../img/img/s20.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"eb84f76d4a41498aa943790374fd3a75\",\n" +
                "      \"_score\": 0.86958915,\n" +
                "      \"_ext\": null\n" +
                "    }\n" +
                "  ],\n" +
                "  \"errorCode\": \"SUCCESS\",\n" +
                "  \"totalCount\": 20,\n" +
                "  \"errorMsg\": \"\"\n" +
                "}\n";

        JSONObject ss = (JSONObject) JSON.parse(akun);
        return ss;
    }

    @RequestMapping("/testDevice")
    public Object testDevice() {
        HashMap<String, String> device1 = new HashMap<>();
        device1.put("cameraId", "1");
        device1.put("deviceName", "爱国路");
        HashMap<String, String> device2 = new HashMap<>();
        device2.put("cameraId", "2");
        device2.put("deviceName", "富强路");
        HashMap<String, String> device3 = new HashMap<>();
        device3.put("cameraId", "3");
        device3.put("deviceName", "民主路");
        HashMap<String, String> device4 = new HashMap<>();
        device4.put("cameraId", "4");
        device4.put("deviceName", "和谐路");
        HashMap<String, String> device5 = new HashMap<>();
        device5.put("cameraId", "5");
        device5.put("deviceName", "友善路");
        HashMap<String, String> device6 = new HashMap<>();
        device6.put("cameraId", "6");
        device6.put("deviceName", "Akun路");

        ArrayList<HashMap> deviceBeans = new ArrayList<>();

        deviceBeans.add(device1);
        deviceBeans.add(device2);
        deviceBeans.add(device3);
        deviceBeans.add(device4);
        deviceBeans.add(device5);
        deviceBeans.add(device6);
        return deviceBeans;
    }

    @RequestMapping("/testSort")
    public Object testSort(@RequestBody JSONObject jsonObject) {
        //跳过多少个
        Integer from = Integer.parseInt(jsonObject.get("from").toString());
        //请求多少个
        Integer size = Integer.parseInt(jsonObject.get("size").toString());
        String akun = "{\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"../img/img/b1.jpg\",\n" +
                "        \"oriImageSigned\": \"../img/img/b1.jpg\",\n" +
                "        \"hairScore\": 0.85698605,\n" +
                "        \"cropImage\": \"../img/img/s1.jpg\",\n" +
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
                "        \"cameraId\": \"1\",\n" +
                "        \"objId\": \"a37484d85d9e45a781c7325f0f2e580a\",\n" +
                "        \"objRight\": 271,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"../img/img/s1.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"a37484d85d9e45a781c7325f0f2e580a\",\n" +
                "      \"_score\": 0.88738585,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"../img/img/b2.jpg\",\n" +
                "        \"oriImageSigned\": \"../img/img/b2.jpg\",\n" +
                "        \"hairScore\": 0.56812334,\n" +
                "        \"cropImage\": \"../img/img/s2.jpg\",\n" +
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
                "        \"cameraId\": \"2\",\n" +
                "        \"objId\": \"56fd6e8d73ad43918c66dc0f345c495d\",\n" +
                "        \"objRight\": 1592,\n" +
                "        \"upper_color\": 2,\n" +
                "        \"cropImageSigned\": \"../img/img/s2.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"56fd6e8d73ad43918c66dc0f345c495d\",\n" +
                "      \"_score\": 0.8828314,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"tt_person_index-20190602\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"../img/img/b3.jpg\",\n" +
                "        \"oriImageSigned\": \"../img/img/b3.jpg\",\n" +
                "        \"hairScore\": 0.8539175,\n" +
                "        \"cropImage\": \"../img/img/b3.jpg\",\n" +
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
                "        \"cameraId\": \"3\",\n" +
                "        \"objId\": \"7c355210fee54806ab246aef91ed9dfc\",\n" +
                "        \"objRight\": 1100,\n" +
                "        \"upper_color\": 10,\n" +
                "        \"cropImageSigned\": \"../img/img/s3.jpg\"\n" +
                "      },\n" +
                "      \"_id\": \"7c355210fee54806ab246aef91ed9dfc\",\n" +
                "      \"_score\": 0.88123775,\n" +
                "      \"_ext\": null\n" +
                "    }" +
                "  ],\n" +
                "  \"errorCode\": \"SUCCESS\",\n" +
                "  \"totalCount\": 20,\n" +
                "  \"errorMsg\": \"\"\n" +
                "}\n";

        SearchResponse searchResponse = JSON.parseObject(akun, new TypeReference<SearchResponse>() {
        });
        List<SearchData> olddatas = searchResponse.getData();
        //获取全部的设备列表
        DeviceBean device = new DeviceBean();
        List<DeviceBean> deviceBeans = deviceMapper.readDeviceList(device);

        olddatas.forEach(searchData -> {
            deviceBeans.forEach(deviceBean -> {
                if (deviceBean.getDeviceId().equals(searchData.getSource().getCameraId())) {
                    searchData.getSource().setDeviceBean(deviceBean);
                }
            });
        });

        List<SearchData> olddata = olddatas.subList(from, from + size);

        //相似度排序
        Collections.sort(olddata, new Comparator<SearchData>() {
            @Override
            public int compare(SearchData o1, SearchData o2) {
                Double score1 = o1.getScore();
                Double score2 = o2.getScore();
                if (score1 < score2) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        ArrayList<SearchData> data = new ArrayList<>();
        data.addAll(olddata);

        // 时间戳排序
        if (data != null && data.size() > 1) {
            Collections.sort(data, new Comparator<SearchData>() {
                @Override
                public int compare(SearchData o1, SearchData o2) {
                    Long o1Value = o1.getSource().getTimestamp();
                    Long o2Value = o2.getSource().getTimestamp();
                    if (o1Value < o2Value) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            });
        }

        //设备分组排序
        List<SearchData> dataList = olddata;
        ArrayList<String> deviceIds = new ArrayList<>();
        Set<String> set = new HashSet<String>();
        Map<String, List> resultMap = new LinkedHashMap<String, List>();
        dataList.forEach(searchData -> {
            SearchSource source = searchData.getSource();
            String cameraId = source.getCameraId();
            if (set.contains(cameraId)) {
                resultMap.get(cameraId).add(searchData);
            } else {
                set.add(cameraId);
                List<SearchData> list1 = new ArrayList<>();
                list1.add(searchData);
                resultMap.put(cameraId, list1);
            }
        });
        HashMap<String, Object> stringSearchDataHashMap = new HashMap<>();
        stringSearchDataHashMap.put("score", olddata);
        stringSearchDataHashMap.put("timestamp", data);
        stringSearchDataHashMap.put("device", resultMap);

        Long totalCount = searchResponse.getTotalCount();

        String responStr = JSON.toJSONString(ResultVo.success(stringSearchDataHashMap, totalCount), SerializerFeature.DisableCircularReferenceDetect);
        ResultVo resultVo = JSON.parseObject(responStr, new TypeReference<ResultVo>() {
        });
        ResponseEntity<ResultVo> resultVoResponseEntity = new ResponseEntity<>(resultVo, HttpStatus.OK);

        //String responStr = JSON.toJSONString(resultVoResponseEntity, SerializerFeature.DisableCircularReferenceDetect);
        //JSONObject object = JSON.parseObject(responStr );
        return resultVoResponseEntity;


    }

    @RequestMapping("/timetasktest")
    public Object timeTaskTest() throws ParseException {
        // 设置传入的时间格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 指定一个日期
        Date date = dateFormat.parse("2019-06-13 15:44:32");

//         Timer timer = new Timer();
//         timer.schedule(new SurveyStartTask(), date);
//
//        date = dateFormat.parse("2019-06-13 15:44:34");
//        timer.schedule(new SurveyStartTask(), date);

        return null;
    }
//        SearchResponse searchResponse = JSON.parseObject(akun, new TypeReference<SearchResponse>() {
//        });
//        List<SearchData> olddata = searchResponse.getData();
//        //相似度排序
//        Collections.sort(olddata , new Comparator<SearchData>() {
//            @Override
//            public int compare(SearchData o1, SearchData o2) {
//                Double score1 = o1.getScore();
//                Double score2 = o2.getScore();
//                if (score1<score2){
//                    return 1;
//                }else{
//                    return -1;
//                }
//            }
//        });
//        ArrayList<SearchData> data = new ArrayList<>();
//        data.addAll(olddata);
//
//       // 时间戳排序
//        if (data !=null && data.size()>1){
//            Collections.sort(data , new Comparator<SearchData>() {
//                @Override
//                public int compare(SearchData o1, SearchData o2) {
//                    Long o1Value = o1.getSource().getTimestamp();
//                    Long o2Value = o2.getSource().getTimestamp();
//                    if (o1Value<o2Value){
//                        return 1;
//                    }else{
//                        return -1;
//                    }
//                }
//            });
//        }
//
//        //设备分组排序
//        List<SearchData> dataList =olddata;
//        ArrayList<String> deviceIds = new ArrayList<>();
//        Set<String> set = new HashSet<String>();
//        Map<String,List> resultMap = new LinkedHashMap<String,List>();
//        dataList.forEach(searchData -> {
//            SearchSource source = searchData.getSource();
//            String cameraId = source.getCameraId();
//            if (set.contains(cameraId)){
//                resultMap.get(cameraId).add(searchData);
//            }else {
//                set.add(cameraId);
//                List<SearchData> list1 = new ArrayList<>();
//                list1.add(searchData);
//                resultMap.put(cameraId,list1);
//            }
//        });
//        HashMap<String, Object> stringSearchDataHashMap = new HashMap<>();
//        stringSearchDataHashMap.put("score",olddata);
//        stringSearchDataHashMap.put("timestamp",data);
//        stringSearchDataHashMap.put("device",resultMap);
//
//        Long totalCount = searchResponse.getTotalCount();
//        String s = JSON.toJSONString(stringSearchDataHashMap, SerializerFeature.DisableCircularReferenceDetect);
//        JSONObject object = JSON.parseObject(s);

    @RequestMapping("test33")
    public Object test33(){
        List<PictureBean> pictureBeans = pictureMapper.selectPictureByFid("6");
        return pictureBeans;
    }
//    @Resource SysOperationLogMapper sysOperationLogMapper;
//    @RequestMapping("testSys")
//    public  void testSys(){
//        SysOperationLog sys = sysOperationLogMapper.selectById("1");
//        System.out.println("sys");
//    }

}