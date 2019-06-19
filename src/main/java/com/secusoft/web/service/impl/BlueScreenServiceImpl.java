package com.secusoft.web.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.secusoft.web.core.util.DateUtils;
import com.secusoft.web.mapper.SysOperationLogMapper;
import com.secusoft.web.mapper.SysOrganizationRoadMapper;
import com.secusoft.web.mapper.ViSurveyTaskMapper;
import com.secusoft.web.model.DataIndicatorBean;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.BlueScreenService;
import com.secusoft.web.tusouapi.TuSouClient;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 设备Service
 * @author hbxing
 * @company 视在数科
 * @date 2019年6月18日
 */
@Service
public class BlueScreenServiceImpl implements BlueScreenService {
    @Autowired
    private SysOrganizationRoadMapper organizationRoadMapper;
    @Autowired
    private SysOperationLogMapper sysOperationLogMapper;
    @Autowired
    private ViSurveyTaskMapper viSurveyTaskMapper;

    @Autowired
    @Qualifier("secondaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    public ResultVo readVideoApplication() {
        /**1.获取当前账号天擎支持的并发总算力对应的路数
         *      (1).获得当前系统登录人的组织code
         *      (2).获得该组织的并发总算李对应的路数
         */
        Integer ConcurrentPathNumber = organizationRoadMapper.selectOrgRoadByOrgCode("3304");
        /**
         * 2.获得目标搜图API调用次数
         *      (1).获得当前系统登录人的组织code
         *      (2).获得该组织code下所有组织的人员id   List<Integer>
         *      (3).去统计该组织所有人员的调用图搜api的数量
         */
        List<Integer> userIds = new ArrayList<>();
        userIds.add(1);
        userIds.add(2);
        Integer tuSouNumber = sysOperationLogMapper.selectTuSouNumber(userIds);
        /**
         * 3.当前时间当前账号权限下处于正在执行状态下的布控、安保、巡逻任务个数总和
         *      (1).获得当前系统登录人的组织code
         *      (2).获得该组织code下所有组织的人员id   List<Integer>
         *      (3).统计布控、安保、巡逻任务个数总和
         */
        Integer surveyTaskNumber = viSurveyTaskMapper.selectExecutingSurveyTaskNumber(userIds);
        /**
         * 4.当日当前账号权限下布控、安保、巡逻报警总数统计
         *      (1).获得当前系统登录人的组织code
         *      (2).获得该组织code下所有组织的人员id   List<Integer>
         *      (3).根据人员id获得布控、安保、巡逻表中的任务id
         *      (4).去对应布控、安保、巡逻报警表中 统计报警数量总和
         */


        return null;
    }

    //@Transactional
    @Override
    public void updateScreenDateIndicator() {
        Date date = new Date();
        String toDay = DateFormatUtils.format(date, "yyyy-MM-dd");
        //公共区域监控点位相关
        DataIndicatorBean publicAreaPointNumber = new DataIndicatorBean();
        jdbcTemplate.query("select indicator_code,dt from data_indicator where indicator_code=? and dt=?",new Object[]{"ggqyjkdw",toDay},(rs)->{
            publicAreaPointNumber.setIndicatorCode(rs.getString("indicator_code"));
        });
        if(publicAreaPointNumber.getIndicatorCode()==null){
            //添加一条公共区域监控点位数据
            jdbcTemplate.update("insert into data_indicator(type_code,type_name,indicator_code,indicator_name,indicator_value,dt) value(?,?,?,?,?,?)","tdrywyj","社会治安防控扫描仪-特定人员望远镜","ggqyjkdw","公共区域监控点位","6347",toDay);
        }else{
            //修改此条公共区域监控点位数据
            jdbcTemplate.update("update data_indicator set indicator_value=? where indicator_code=? and dt=?","6347","ggqyjkdw",toDay);
        }


        //人脸抓拍机建设量相关
        DataIndicatorBean faceCapturePointNumber = new DataIndicatorBean();
        jdbcTemplate.query("select indicator_code,dt from data_indicator where indicator_code=? and dt=?",new Object[]{"rlzpjjsl",toDay},(rs)->{
            faceCapturePointNumber.setIndicatorCode(rs.getString("indicator_code"));
        });
        if(faceCapturePointNumber.getIndicatorCode()==null){
            //添加一条人脸抓拍机建设量数据
            jdbcTemplate.update("insert into data_indicator(type_code,type_name,indicator_code,indicator_name,indicator_value,dt) value(?,?,?,?,?,?)","tdrywyj","社会治安防控扫描仪-特定人员望远镜","rlzpjjsl","人脸抓拍机建设量","2345",toDay);
        }else {
            //修改此条人脸抓拍机建设量数据
            jdbcTemplate.update("update data_indicator set indicator_value=? where indicator_code=? and dt=?", "2345", "rlzpjjsl", toDay);
        }

        //视频巡逻相关
        DataIndicatorBean videoPatrolData = new DataIndicatorBean();
        //获取当天时间视频巡逻数据
        jdbcTemplate.query("select indicator_code,dt from data_indicator where indicator_code=? and dt=?",new Object[]{"spxl",toDay},(rs)->{
            videoPatrolData.setIndicatorCode(rs.getString("indicator_code"));
        });
        String totalCount = getVideoPatrolNumber();
        //存在更新当天数据,不存在插入一条数据
        if(videoPatrolData.getIndicatorCode()==null){
            //插入一条视频巡逻数据
            jdbcTemplate.update("insert into data_indicator(type_code,type_name,indicator_code,indicator_name,indicator_value,dt) value(?,?,?,?,?,?)","tdrywyj","社会治安防控扫描仪-特定人员望远镜","spxl","视频巡逻",totalCount,toDay);//数量从图搜接口获取数据
        }else{
            //更新一条视频巡逻数据
            jdbcTemplate.update("update data_indicator set indicator_value=? where indicator_code=? and dt=?", totalCount, "spxl", toDay);//数量从图搜接口获取数据
        }
    }
    //调用阿里接口获取视频巡逻的个数
    String getVideoPatrolNumber(){
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        Date time = todayStart.getTime();
        long time1 = time.getTime();
        String requestStr = "{\"size\":0,\"data\":{\"noFeature\":\"1\",\"uid\":\"hangzhou\",\"type\":\"person\",\"taskId\":\"512041492240442db7462770e968e785\","+"\"startTime\":"+time1+"},\"requestId\":\"request42321\"}";
        String responseStr = TuSouClient.getClientConnectionPool().fetchByPostMethod(TuSouClient.Path_SEARCH, requestStr);
        System.out.println("调用阿里接口参数:"+requestStr);
       /* String test = "{\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"_index\": \"str_person_index-20190618\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"http://33.95.245.183:50011/hzga/origImage/20190618/330106530001915760/13/1560837168282.jpg\",\n" +
                "        \"oriImageSigned\": \"http://33.95.245.183:50011/hzga/origImage/20190618/330106530001915760/13/1560837168282.jpg?Expires=1560861849&OSSAccessKeyId=MC1RVZ06EQ0D42BT4XVH&Signature=T165vwkeh32ckXkRNH9UZ32olZk%3D\",\n" +
                "        \"hairScore\": 0.5225835,\n" +
                "        \"cropImage\": \"http://33.95.245.183:50011/hzga/cropImage/20190618/330106530001915760/13/1560837168303.jpg\",\n" +
                "        \"sexScore\": 0.7278464,\n" +
                "        \"upper_typeScore\": 0.65060145,\n" +
                "        \"hair\": \"短发\",\n" +
                "        \"objLeft\": 1236,\n" +
                "        \"lower_type\": \"长裤\",\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 348,\n" +
                "        \"objUUId\": \"330106530001915760_1560837282979_0_21515\",\n" +
                "        \"timestamp\": 1560837282979,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": \"短袖\",\n" +
                "        \"objBottom\": 470,\n" +
                "        \"lower_color\": \"蓝\",\n" +
                "        \"upper_colorScore\": 0.41650817,\n" +
                "        \"sex\": \"女\",\n" +
                "        \"lower_typeScore\": 0.77931094,\n" +
                "        \"entryTime\": 1560837282979,\n" +
                "        \"lower_colorScore\": 0.5721449,\n" +
                "        \"cameraId\": \"330106530001915760\",\n" +
                "        \"objId\": \"56ca18443a824365b21aeec80418a085\",\n" +
                "        \"objRight\": 1293,\n" +
                "        \"upper_color\": \"白\",\n" +
                "        \"cropImageSigned\": \"http://33.95.245.183:50011/hzga/cropImage/20190618/330106530001915760/13/1560837168303.jpg?Expires=1560861849&OSSAccessKeyId=MC1RVZ06EQ0D42BT4XVH&Signature=mlbfM7NYDoimW7ycraeJMWHplJg%3D\"\n" +
                "      },\n" +
                "      \"_id\": \"56ca18443a824365b21aeec80418a085\",\n" +
                "      \"sort\": [\n" +
                "        1560837282979\n" +
                "      ],\n" +
                "      \"_score\": null,\n" +
                "      \"_ext\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"_index\": \"str_person_index-20190618\",\n" +
                "      \"_type\": \"default\",\n" +
                "      \"_source\": {\n" +
                "        \"origImage\": \"http://33.95.245.183:50011/hzga/origImage/20190618/330106530047282760/13/1560837173930.jpg\",\n" +
                "        \"oriImageSigned\": \"http://33.95.245.183:50011/hzga/origImage/20190618/330106530047282760/13/1560837173930.jpg?Expires=1560861849&OSSAccessKeyId=MC1RVZ06EQ0D42BT4XVH&Signature=JkzG1OLvvt%2BT4U0udvOUen%2Fa0L8%3D\",\n" +
                "        \"hairScore\": 0.83574134,\n" +
                "        \"cropImage\": \"http://33.95.245.183:50011/hzga/cropImage/20190618/330106530047282760/13/1560837173941.jpg\",\n" +
                "        \"sexScore\": 0.81702894,\n" +
                "        \"upper_typeScore\": 0.9840149,\n" +
                "        \"hair\": \"短发\",\n" +
                "        \"objLeft\": 1488,\n" +
                "        \"lower_type\": \"中裤\",\n" +
                "        \"objType\": \"person\",\n" +
                "        \"objTop\": 496,\n" +
                "        \"objUUId\": \"330106530047282760_1560837288765_0_44598\",\n" +
                "        \"timestamp\": 1560837288765,\n" +
                "        \"leaveTime\": -1,\n" +
                "        \"upper_type\": \"短袖\",\n" +
                "        \"objBottom\": 820,\n" +
                "        \"lower_color\": \"黑\",\n" +
                "        \"upper_colorScore\": 0.30953032,\n" +
                "        \"sex\": \"男\",\n" +
                "        \"lower_typeScore\": 0.52509266,\n" +
                "        \"entryTime\": 1560837282957,\n" +
                "        \"lower_colorScore\": 0.89378726,\n" +
                "        \"cameraId\": \"330106530047282760\",\n" +
                "        \"objId\": \"e2f1c6bb2a3b4d91bc7c816864b5eb24\",\n" +
                "        \"objRight\": 1625,\n" +
                "        \"upper_color\": \"绿\",\n" +
                "        \"cropImageSigned\": \"http://33.95.245.183:50011/hzga/cropImage/20190618/330106530047282760/13/1560837173941.jpg?Expires=1560861849&OSSAccessKeyId=MC1RVZ06EQ0D42BT4XVH&Signature=TUGDBmWUyjqqsygJupAaYPlayZk%3D\"\n" +
                "      },\n" +
                "      \"_id\": \"e2f1c6bb2a3b4d91bc7c816864b5eb24\",\n" +
                "      \"sort\": [\n" +
                "        1560837282957\n" +
                "      ],\n" +
                "      \"_score\": null,\n" +
                "      \"_ext\": null\n" +
                "    }\n" +
                "  ],\n" +
                "  \"errorCode\": \"SUCCESS\",\n" +
                "  \"totalCount\": 568586,\n" +
                "  \"errorMsg\": \"\"\n" +
                "}";*/

        JSONObject jsonObject = JSONArray.parseObject(responseStr);
        String totalCount = jsonObject.getString("totalCount");
        return  totalCount;
    }
}
