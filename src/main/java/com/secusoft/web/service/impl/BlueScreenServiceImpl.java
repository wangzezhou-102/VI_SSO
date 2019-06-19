package com.secusoft.web.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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

import javax.annotation.Resource;
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

    @Transactional
    @Override
    public void updateScreenDateIndicator() {
        Date date = new Date();
        String toDay = DateFormatUtils.format(date, "yyyy-MM-dd");
    //视频巡逻相关
        operation("tdrywyj","社会治安防控扫描仪-特定人员望远镜","spxl","视频巡逻",getVideoPatrolNumber(),toDay);
    //公共区域监控点位相关
        operation("tdrywyj","社会治安防控扫描仪-特定人员望远镜","ggqyjkdw","公共区域监控点位","634700",toDay);
    //人脸抓拍机建设量相关
        operation("tdrywyj","社会治安防控扫描仪-特定人员望远镜","rlzpjjsl","人脸抓拍机建设量","23450",toDay);
    //车辆抓拍机建设量相关
        operation("tdrywyj","社会治安防控扫描仪-特定人员望远镜","ggqyjkgqhbl","车辆抓拍机建设量","21010",toDay);
    //中央雪亮指标
        //上城区分局公共区域监控点位
        operation("zyzb","中央雪亮指标","scqfjggqyjkdw","上城区分局公共区域监控点位","1365",toDay);
        //上城区分局人脸抓拍机建设量
        operation("zyzb","中央雪亮指标","scqfjrlzpjjsl","上城区分局人脸抓拍机建设量","2134",toDay);
        //上城区分局公共区域车辆抓拍机建设量
        operation("zyzb","中央雪亮指标","scqfjggqyjkgqhbl","上城区分局公共区域车辆抓拍机建设量","1254",toDay);

        //下城区分局公共区域监控点位
        operation("zyzb","中央雪亮指标","xcqfjggqyjkdw","下城区分局公共区域监控点位","1365",toDay);
        //下城区分局人脸抓拍机建设量
        operation("zyzb","中央雪亮指标","xcqfjrlzpjjsl","下城区分局人脸抓拍机建设量","2134",toDay);
        //下城区分局公共区域车辆抓拍机建设量
        operation("zyzb","中央雪亮指标","xcqfjggqyjkgqhbl","下城区分局公共区域车辆抓拍机建设量","1254",toDay);

        //江干区分局公共区域监控点位
        operation("zyzb","中央雪亮指标","jgqfjggqyjkdw","江干区分局公共区域监控点位","1365",toDay);
        //江干区分局人脸抓拍机建设量
        operation("zyzb","中央雪亮指标","jgqfjrlzpjjsl","江干区分局人脸抓拍机建设量","2134",toDay);
        //江干区分局公共区域车辆抓拍机建设量
        operation("zyzb","中央雪亮指标","jgqfjggqyjkgqhbl","江干区分局公共区域车辆抓拍机建设量","1254",toDay);

        //拱墅区分局公共区域监控点位
        operation("zyzb","中央雪亮指标","gsqfjggqyjkdw","拱墅区分局公共区域监控点位","1365",toDay);
        //拱墅区分局人脸抓拍机建设量
        operation("zyzb","中央雪亮指标","gsqfjrlzpjjsl","拱墅区分局人脸抓拍机建设量","2134",toDay);
        //拱墅区分局公共区域车辆抓拍机建设量
        operation("zyzb","中央雪亮指标","gsqfjggqyjkgqhbl","拱墅区分局公共区域车辆抓拍机建设量","1254",toDay);

        //西湖区分局公共区域监控点位
        operation("zyzb","中央雪亮指标","xhqfjggqyjkdw","西湖区分局公共区域监控点位","1365",toDay);
        //西湖区分局人脸抓拍机建设量
        operation("zyzb","中央雪亮指标","xhqfjrlzpjjsl","西湖区分局人脸抓拍机建设量","2134",toDay);
        //西湖区分局公共区域车辆抓拍机建设量
        operation("zyzb","中央雪亮指标","xhqfjggqyjkgqhbl","西湖区分局公共区域车辆抓拍机建设量","1254",toDay);

        //滨江区分局公共区域监控点位
        operation("zyzb","中央雪亮指标","bjqfjggqyjkdw","滨江区分局公共区域监控点位","1365",toDay);
        //滨江区分局人脸抓拍机建设量
        operation("zyzb","中央雪亮指标","bjqfjrlzpjjsl","滨江区分局人脸抓拍机建设量","2134",toDay);
        //滨江区分局公共区域车辆抓拍机建设量
        operation("zyzb","中央雪亮指标","bjqfjggqyjkgqhbl","滨江区分局公共区域车辆抓拍机建设量","1254",toDay);

        //大江东产业集聚区分局公共区域监控点位
        operation("zyzb","中央雪亮指标","djdcyjjqfjggqyjkdw","大江东产业集聚区分局公共区域监控点位","1365",toDay);
        //大江东产业集聚区分局人脸抓拍机建设量
        operation("zyzb","中央雪亮指标","djdcyjjqfjrlzpjjsl","大江东产业集聚区分局人脸抓拍机建设量","2134",toDay);
        //大江东产业集聚区分局公共区域车辆抓拍机建设量
        operation("zyzb","中央雪亮指标","djdcyjjqfjggqyjkgqhbl","大江东产业集聚区分局公共区域车辆抓拍机建设量","1254",toDay);

        //经济技术开发区分局公共区域监控点位
        operation("zyzb","中央雪亮指标","jjjskfqfjggqyjkdw","经济技术开发区分局公共区域监控点位","1365",toDay);
        //经济技术开发区分局人脸抓拍机建设量
        operation("zyzb","中央雪亮指标","jjjskfqfjrlzpjjsl","经济技术开发区分局人脸抓拍机建设量","2134",toDay);
        //经济技术开发区分局公共区域车辆抓拍机建设量
        operation("zyzb","中央雪亮指标","jjjskfqfjggqyjkgqhbl","经济技术开发区分局公共区域车辆抓拍机建设量","1254",toDay);

        //西湖风景名胜区分局公共区域监控点位
        operation("zyzb","中央雪亮指标","xhfjmsqfjggqyjkdw","西湖风景名胜区分局公共区域监控点位","1365",toDay);
        //西湖风景名胜区分局人脸抓拍机建设量
        operation("zyzb","中央雪亮指标","xhfjmsqfjrlzpjjsl","西湖风景名胜区分局人脸抓拍机建设量","2134",toDay);
        //西湖风景名胜区分局公共区域车辆抓拍机建设量
        operation("zyzb","中央雪亮指标","xhfjmsqfjggqyjkgqhbl","西湖风景名胜区分局公共区域车辆抓拍机建设量","1254",toDay);

        //萧山区分局公共区域监控点位
        operation("zyzb","中央雪亮指标","xsqfjggqyjkdw","萧山区分局公共区域监控点位","1365",toDay);
        //萧山区分局人脸抓拍机建设量
        operation("zyzb","中央雪亮指标","xsqfjrlzpjjsl","萧山区分局人脸抓拍机建设量","2134",toDay);
        //萧山区分局公共区域车辆抓拍机建设量
        operation("zyzb","中央雪亮指标","xsqfjggqyjkgqhbl","萧山区分局公共区域车辆抓拍机建设量","1254",toDay);

        //余杭区分局公共区域监控点位
        operation("zyzb","中央雪亮指标","yhqfjggqyjkdw","余杭区分局公共区域监控点位","1365",toDay);
        //余杭区分局人脸抓拍机建设量
        operation("zyzb","中央雪亮指标","yhqfjrlzpjjsl","余杭区分局人脸抓拍机建设量","2134",toDay);
        //余杭区分局公共区域车辆抓拍机建设量
        operation("zyzb","中央雪亮指标","yhqfjggqyjkgqhbl","余杭区分局公共区域车辆抓拍机建设量","1254",toDay);

        //富阳区分局公共区域监控点位
        operation("zyzb","中央雪亮指标","fyqfjggqyjkdw","富阳区分局公共区域监控点位","1365",toDay);
        //富阳区分局人脸抓拍机建设量
        operation("zyzb","中央雪亮指标","fyqfjrlzpjjsl","富阳区分局人脸抓拍机建设量","2134",toDay);
        //富阳区分局公共区域车辆抓拍机建设量
        operation("zyzb","中央雪亮指标","fyqfjggqyjkgqhbl","富阳区分局公共区域车辆抓拍机建设量","1254",toDay);

        //临安区分局公共区域监控点位
        operation("zyzb","中央雪亮指标","laqfjggqyjkdw","临安区分局公共区域监控点位","1365",toDay);
        //
        operation("zyzb","中央雪亮指标","laqfjrlzpjjsl","临安区分局人脸抓拍机建设量","2134",toDay);
        //临安区分局公共区域车辆抓拍机建设量
        operation("zyzb","中央雪亮指标","laqfjggqyjkgqhbl","临安区分局公共区域车辆抓拍机建设量","1254",toDay);

        //桐庐县局公共区域监控点位
        operation("zyzb","中央雪亮指标","tlxjggqyjkdw","桐庐县局公共区域监控点位","1365",toDay);
        //
        operation("zyzb","中央雪亮指标","tlxjrlzpjjsl","桐庐县局人脸抓拍机建设量","2134",toDay);
        //桐庐县局公共区域车辆抓拍机建设量
        operation("zyzb","中央雪亮指标","tlxjggqyjkgqhbl","桐庐县局公共区域车辆抓拍机建设量","1254",toDay);

        //淳安县局公共区域监控点位
        operation("zyzb","中央雪亮指标","caxjggqyjkdw","淳安县局公共区域监控点位","1365",toDay);
        //淳安县局人脸抓拍机建设量
        operation("zyzb","中央雪亮指标","caxjrlzpjjsl","淳安县局人脸抓拍机建设量","2134",toDay);
        //淳安县局公共区域车辆抓拍机建设量
        operation("zyzb","中央雪亮指标","caxjggqyjkgqhbl","淳安县局公共区域车辆抓拍机建设量","1254",toDay);

        //建德市局公共区域监控点位
        operation("zyzb","中央雪亮指标","jdsjggqyjkdw","建德市局公共区域监控点位","1365",toDay);
        //
        operation("zyzb","中央雪亮指标","jdsjrlzpjjsl","建德市局人脸抓拍机建设量","2134",toDay);
        //建德市局公共区域车辆抓拍机建设量
        operation("zyzb","中央雪亮指标","jdsjggqyjkgqhbl","建德市局公共区域车辆抓拍机建设量","1254",toDay);
    }
    //操作阿里提供的数据库
    void operation(String type_code,String type_name,String indicator_code,String indicator_name,String indicator_value,String toDay){
        DataIndicatorBean dataIndicatorBean = new DataIndicatorBean();
        jdbcTemplate.query("select indicator_code,dt from data_indicator where indicator_code=? and dt=?",new Object[]{indicator_code,toDay},(rs)->{
            dataIndicatorBean.setIndicatorCode(rs.getString("indicator_code"));
        });
        if(dataIndicatorBean.getIndicatorCode()==null){
            jdbcTemplate.update("insert into data_indicator(type_code,type_name,indicator_code,indicator_name,indicator_value,dt) value(?,?,?,?,?,?)",type_code,type_name,indicator_code,indicator_name,indicator_value,toDay);
        }else{
            jdbcTemplate.update("update data_indicator set indicator_value=? where indicator_code=? and dt=?",indicator_value,indicator_code,toDay);
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
        String requestStr = "{\"size\":1,\"data\":{\"noFeature\":\"1\",\"uid\":\"hangzhou\",\"type\":\"person\",\"taskId\":\"512041492240442db7462770e968e785\","+"\"startTime\":"+time1+"},\"requestId\":\"request42321\"}";
        String responseStr = TuSouClient.getClientConnectionPool().fetchByPostMethod(TuSouClient.Path_SEARCH, requestStr);
        System.out.println("调用阿里接口参数:"+requestStr);

        JSONObject jsonObject = JSONArray.parseObject(responseStr);
        System.out.println(jsonObject);
        String totalCount = jsonObject.getString("totalCount");
        return  totalCount;
    }
}
