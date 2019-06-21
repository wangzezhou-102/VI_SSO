package com.secusoft.web.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.secusoft.web.mapper.*;
import com.secusoft.web.model.*;
import com.secusoft.web.service.ScreenService;
import com.secusoft.web.tusouapi.TuSouClient;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 设备Service
 * @author hbxing
 * @company 视在数科
 * @date 2019年6月18日
 */
@Service
public class ScreenServiceImpl implements ScreenService {
    @Autowired
    private SysOrganizationRoadMapper organizationRoadMapper;
    @Autowired
    private SysOperationLogMapper sysOperationLogMapper;
    @Autowired
    private ViSurveyTaskMapper viSurveyTaskMapper;
    @Autowired
    private ViPsurveyAlaramMapper viPsurveyAlaramMapper;
    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private SysOrganizationMapper sysOrganizationMapper;

    @Autowired
    @Qualifier("secondaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    /**
     * 蓝色大屏相关
     */
    @Override
    @Transactional
    public void readVideoApplication() {
        /**1.获取当前账号天擎支持的并发总算力对应的路数
         *      (1).获得当前系统登录人的组织code
         *      (2).获得该组织的并发总算李对应的路数
         */
        Integer concurrentPathNumber = organizationRoadMapper.selectOrgRoadByOrgCode("3304");
        /**
         * 2.获得目标搜图API调用次数
         *      (1).获得当前系统登录人的组织code
         *      (2).获得该组织code下所有组织的人员id   List<Integer>
         *      (3).去统计该组织所有人员的调用图搜api的数量
         */
        List<Integer> userIds = new ArrayList<>();
        userIds.add(1);
        userIds.add(2);
        Integer tuSouNumber;
        if(userIds!=null&&userIds.size()!=0){
            tuSouNumber = sysOperationLogMapper.selectTuSouNumber(userIds);
        }else{
            tuSouNumber = 0;
        }

        /**
         * 3.当前时间当前账号权限下处于正在执行状态下的布控、安保、巡逻任务个数总和
         *      (1).获得当前系统登录人的组织code
         *      (2).获得该组织code下所有组织的人员id   List<Integer>
         *      (3).统计布控、安保、巡逻任务个数总和
         */
        //布控总数
        Integer surveyTaskNumber;
        if(userIds!=null&&userIds.size()!=0){
             surveyTaskNumber = viSurveyTaskMapper.selectExecutingSurveyTaskNumber(userIds);
        }else {
            surveyTaskNumber = 0;
        }


        //布控、安保、巡逻 任务个数总和(安保、巡逻暂时没有数据)
        Integer taskNumber = surveyTaskNumber;
        /**
         * 4.当日当前账号权限下布控、安保、巡逻报警总数统计
         *      (1).获得当前系统登录人的组织code
         *      (2).获得该组织code下所有组织的人员id   List<Integer>
         *      (3).根据人员id获得布控、安保、巡逻表中的任务id
         *      (4).去对应布控、安保、巡逻报警表中 统计报警数量总和
         */
        //布控报警总数
        List<String> taskIds = new ArrayList<>();
        if(userIds!=null&&userIds.size()!=0){
            taskIds = viSurveyTaskMapper.selectIdByUserIds(userIds);
        }

        Integer surveyAlaramNumber;
        if(taskIds!=null&&taskIds.size()!=0){
            surveyAlaramNumber = viPsurveyAlaramMapper.selectIdNumberBytaskId(taskIds);

        }else{
            surveyAlaramNumber = 0 ;
        }
        //布控、安保、巡逻报警总和(安保、巡逻报警暂时没有数据)
        Integer taskAlaramNumber = surveyAlaramNumber;

    //查到的数据添加至德清mysql数据库
        Date date = new Date();
        String toDay = DateFormatUtils.format(date, "yyyy-MM-dd");

        operationBlueScreen("spzn","右侧-视频智能","bfls","并发路数",concurrentPathNumber.toString(),toDay);
        operationBlueScreen("spzn","右侧-视频智能","mbssdy","目标搜索调用次数",tuSouNumber.toString(),toDay);
        operationBlueScreen("spzn","右侧-视频智能","dqzxrw","当前执行任务个数",taskNumber.toString(),toDay);
        operationBlueScreen("spzn","右侧-视频智能","dqbjzs","当前报警总数",taskAlaramNumber.toString(),toDay);
    }

    /**
     * 浅色大屏相关
     */
    @Transactional
    @Override
    public void updateScreenDateIndicator() {
        Date date = new Date();
        String toDay = DateFormatUtils.format(date, "yyyy-MM-dd");
    //视频巡逻相关
        operation("tdrywyj","社会治安防控扫描仪-特定人员望远镜","spxl","视频巡逻","32563333",toDay);
    //公共区域监控点位相关
        operation("tdrywyj","社会治安防控扫描仪-特定人员望远镜","ggqyjkdw","公共区域监控点位","72187",toDay);
    //人脸抓拍机建设量相关
        operation("tdrywyj","社会治安防控扫描仪-特定人员望远镜","rlzpjjsl","人脸抓拍机建设量","9826",toDay);
    //车辆抓拍机建设量相关
        operation("tdrywyj","社会治安防控扫描仪-特定人员望远镜","ggqyjkgqhbl","车辆抓拍机建设量","6248",toDay);
    //中央雪亮指标
        //上城区分局公共区域监控点位
        operation("zyzb","中央雪亮指标","scqfjggqyjkdw","上城区分局公共区域监控点位","1294",toDay);
        //上城区分局人脸抓拍机建设量
        operation("zyzb","中央雪亮指标","scqfjrlzpjjsl","上城区分局人脸抓拍机建设量","150",toDay);
        //上城区分局公共区域车辆抓拍机建设量
        operation("zyzb","中央雪亮指标","scqfjggqyjkgqhbl","上城区分局公共区域车辆抓拍机建设量","0",toDay);

        //下城区分局公共区域监控点位
        operation("zyzb","中央雪亮指标","xcqfjggqyjkdw","下城区分局公共区域监控点位","2737",toDay);
        //下城区分局人脸抓拍机建设量
        operation("zyzb","中央雪亮指标","xcqfjrlzpjjsl","下城区分局人脸抓拍机建设量","202",toDay);
        //下城区分局公共区域车辆抓拍机建设量
        operation("zyzb","中央雪亮指标","xcqfjggqyjkgqhbl","下城区分局公共区域车辆抓拍机建设量","0",toDay);

        //江干区分局公共区域监控点位
        operation("zyzb","中央雪亮指标","jgqfjggqyjkdw","江干区分局公共区域监控点位","4408",toDay);
        //江干区分局人脸抓拍机建设量
        operation("zyzb","中央雪亮指标","jgqfjrlzpjjsl","江干区分局人脸抓拍机建设量","1323",toDay);
        //江干区分局公共区域车辆抓拍机建设量
        operation("zyzb","中央雪亮指标","jgqfjggqyjkgqhbl","江干区分局公共区域车辆抓拍机建设量","406",toDay);

        //拱墅区分局公共区域监控点位
        operation("zyzb","中央雪亮指标","gsqfjggqyjkdw","拱墅区分局公共区域监控点位","2754",toDay);
        //拱墅区分局人脸抓拍机建设量
        operation("zyzb","中央雪亮指标","gsqfjrlzpjjsl","拱墅区分局人脸抓拍机建设量","804",toDay);
        //拱墅区分局公共区域车辆抓拍机建设量
        operation("zyzb","中央雪亮指标","gsqfjggqyjkgqhbl","拱墅区分局公共区域车辆抓拍机建设量","386",toDay);

        //西湖区分局公共区域监控点位
        operation("zyzb","中央雪亮指标","xhqfjggqyjkdw","西湖区分局公共区域监控点位","4992",toDay);
        //西湖区分局人脸抓拍机建设量
        operation("zyzb","中央雪亮指标","xhqfjrlzpjjsl","西湖区分局人脸抓拍机建设量","305",toDay);
        //西湖区分局公共区域车辆抓拍机建设量
        operation("zyzb","中央雪亮指标","xhqfjggqyjkgqhbl","西湖区分局公共区域车辆抓拍机建设量","894",toDay);

        //滨江区分局公共区域监控点位
        operation("zyzb","中央雪亮指标","bjqfjggqyjkdw","滨江区分局公共区域监控点位","2907",toDay);
        //滨江区分局人脸抓拍机建设量
        operation("zyzb","中央雪亮指标","bjqfjrlzpjjsl","滨江区分局人脸抓拍机建设量","411",toDay);
        //滨江区分局公共区域车辆抓拍机建设量
        operation("zyzb","中央雪亮指标","bjqfjggqyjkgqhbl","滨江区分局公共区域车辆抓拍机建设量","1616",toDay);

        //大江东产业集聚区分局公共区域监控点位
        operation("zyzb","中央雪亮指标","djdcyjjqfjggqyjkdw","大江东产业集聚区分局公共区域监控点位","2325",toDay);
        //大江东产业集聚区分局人脸抓拍机建设量
        operation("zyzb","中央雪亮指标","djdcyjjqfjrlzpjjsl","大江东产业集聚区分局人脸抓拍机建设量","16",toDay);
        //大江东产业集聚区分局公共区域车辆抓拍机建设量
        operation("zyzb","中央雪亮指标","djdcyjjqfjggqyjkgqhbl","大江东产业集聚区分局公共区域车辆抓拍机建设量","120",toDay);

        //经济技术开发区分局公共区域监控点位
        operation("zyzb","中央雪亮指标","jjjskfqfjggqyjkdw","经济技术开发区分局公共区域监控点位","2307",toDay);
        //经济技术开发区分局人脸抓拍机建设量
        operation("zyzb","中央雪亮指标","jjjskfqfjrlzpjjsl","经济技术开发区分局人脸抓拍机建设量","226",toDay);
        //经济技术开发区分局公共区域车辆抓拍机建设量
        operation("zyzb","中央雪亮指标","jjjskfqfjggqyjkgqhbl","经济技术开发区分局公共区域车辆抓拍机建设量","146",toDay);

        //西湖风景名胜区分局公共区域监控点位
        operation("zyzb","中央雪亮指标","xhfjmsqfjggqyjkdw","西湖风景名胜区分局公共区域监控点位","1234",toDay);
        //西湖风景名胜区分局人脸抓拍机建设量
        operation("zyzb","中央雪亮指标","xhfjmsqfjrlzpjjsl","西湖风景名胜区分局人脸抓拍机建设量","86",toDay);
        //西湖风景名胜区分局公共区域车辆抓拍机建设量
        operation("zyzb","中央雪亮指标","xhfjmsqfjggqyjkgqhbl","西湖风景名胜区分局公共区域车辆抓拍机建设量","30",toDay);

        //萧山区分局公共区域监控点位
        operation("zyzb","中央雪亮指标","xsqfjggqyjkdw","萧山区分局公共区域监控点位","7271",toDay);
        //萧山区分局人脸抓拍机建设量
        operation("zyzb","中央雪亮指标","xsqfjrlzpjjsl","萧山区分局人脸抓拍机建设量","1152",toDay);
        //萧山区分局公共区域车辆抓拍机建设量
        operation("zyzb","中央雪亮指标","xsqfjggqyjkgqhbl","萧山区分局公共区域车辆抓拍机建设量","382",toDay);

        //余杭区分局公共区域监控点位
        operation("zyzb","中央雪亮指标","yhqfjggqyjkdw","余杭区分局公共区域监控点位","12052",toDay);
        //余杭区分局人脸抓拍机建设量
        operation("zyzb","中央雪亮指标","yhqfjrlzpjjsl","余杭区分局人脸抓拍机建设量","553",toDay);
        //余杭区分局公共区域车辆抓拍机建设量
        operation("zyzb","中央雪亮指标","yhqfjggqyjkgqhbl","余杭区分局公共区域车辆抓拍机建设量","700",toDay);

        //富阳区分局公共区域监控点位
        operation("zyzb","中央雪亮指标","fyqfjggqyjkdw","富阳区分局公共区域监控点位","5760",toDay);
        //富阳区分局人脸抓拍机建设量
        operation("zyzb","中央雪亮指标","fyqfjrlzpjjsl","富阳区分局人脸抓拍机建设量","565",toDay);
        //富阳区分局公共区域车辆抓拍机建设量
        operation("zyzb","中央雪亮指标","fyqfjggqyjkgqhbl","富阳区分局公共区域车辆抓拍机建设量","253",toDay);

        //临安区分局公共区域监控点位
        operation("zyzb","中央雪亮指标","laqfjggqyjkdw","临安区分局公共区域监控点位","2786",toDay);
        //
        operation("zyzb","中央雪亮指标","laqfjrlzpjjsl","临安区分局人脸抓拍机建设量","102",toDay);
        //临安区分局公共区域车辆抓拍机建设量
        operation("zyzb","中央雪亮指标","laqfjggqyjkgqhbl","临安区分局公共区域车辆抓拍机建设量","520",toDay);

        //桐庐县局公共区域监控点位
        operation("zyzb","中央雪亮指标","tlxjggqyjkdw","桐庐县局公共区域监控点位","2989",toDay);
        //
        operation("zyzb","中央雪亮指标","tlxjrlzpjjsl","桐庐县局人脸抓拍机建设量","208",toDay);
        //桐庐县局公共区域车辆抓拍机建设量
        operation("zyzb","中央雪亮指标","tlxjggqyjkgqhbl","桐庐县局公共区域车辆抓拍机建设量","703",toDay);

        //淳安县局公共区域监控点位
        operation("zyzb","中央雪亮指标","caxjggqyjkdw","淳安县局公共区域监控点位","3036",toDay);
        //淳安县局人脸抓拍机建设量
        operation("zyzb","中央雪亮指标","caxjrlzpjjsl","淳安县局人脸抓拍机建设量","67",toDay);
        //淳安县局公共区域车辆抓拍机建设量
        operation("zyzb","中央雪亮指标","caxjggqyjkgqhbl","淳安县局公共区域车辆抓拍机建设量","216",toDay);

        //建德市局公共区域监控点位
        operation("zyzb","中央雪亮指标","jdsjggqyjkdw","建德市局公共区域监控点位","3620",toDay);
        //建德市局人脸抓拍机建设量
        operation("zyzb","中央雪亮指标","jdsjrlzpjjsl","建德市局人脸抓拍机建设量","27",toDay);
        //建德市局公共区域车辆抓拍机建设量
        operation("zyzb","中央雪亮指标","jdsjggqyjkgqhbl","建德市局公共区域车辆抓拍机建设量","135",toDay);
    //中央防控指标
        //上城区分局视频巡逻
        operation("zyzb","中央防控指标","scqfjspxl","上城区分局视频巡逻","2160203",toDay);
        //下城区分局视频巡逻
        operation("zyzb","中央防控指标","xcqfjspxl","下城区分局视频巡逻","2140207",toDay);
        //江干区分局视频巡逻
        operation("zyzb","中央防控指标","jgqfjspxl","江干区分局视频巡逻","2030204",toDay);
        //拱墅区分局视频巡逻
        operation("zyzb","中央防控指标","gsqfjspxl","拱墅区分局视频巡逻","2060203",toDay);
        //西湖区分局视频巡逻
        operation("zyzb","中央防控指标","xhqfjspxl","西湖区分局视频巡逻","1920155",toDay);
        //滨江区分局视频巡逻
        operation("zyzb","中央防控指标","bjqfjspxl","滨江区分局视频巡逻","1980256",toDay);
        //大江东产业集聚区分局视频巡逻
        operation("zyzb","中央防控指标","djdcyjjqfjspxl","大江东产业集聚区分局视频巡逻","2320216",toDay);
        //经济技术开发区分局视频巡逻
        operation("zyzb","中央防控指标","jjjskfqfjspxl","经济技术开发区分局视频巡逻","1950203",toDay);
        //西湖风景名胜区分局视频巡逻
        operation("zyzb","中央防控指标","xhfjmsqfjspxl","西湖风景名胜区分局视频巡逻","2050205",toDay);
        //萧山区分局视频巡逻
        operation("zyzb","中央防控指标","xsqfjspxl","萧山区分局视频巡逻","1930201",toDay);
        //余杭区分局视频巡逻
        operation("zyzb","中央防控指标","yhqfjspxl","余杭区分局视频巡逻","2020199",toDay);
        //富阳区分局视频巡逻
        operation("zyzb","中央防控指标","fyqfjspxl","富阳区分局视频巡逻","2080198",toDay);
        //临安区分局视频巡逻
        operation("zyzb","中央防控指标","laqfjspxl","临安区分局视频巡逻","2030206",toDay);
        //桐庐县局视频巡逻
        operation("zyzb","中央防控指标","tlxjspxl","桐庐县局视频巡逻","1990207",toDay);
        //淳安县局视频巡逻
        operation("zyzb","中央防控指标","caxjspxl","淳安县局视频巡逻","1940204",toDay);
        //建德市局视频巡逻
        operation("zyzb","中央防控指标","jdsjspxl","建德市局视频巡逻","1960266",toDay);


    }
    /**
     * 蓝色大屏浮框相关
     */

    @Transactional
    @Override
    public void updateBlueScreenFloatingFrame(){
        Date date = new Date();
        String toDay = DateFormatUtils.format(date, "yyyy-MM-dd");
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","mbstsycs","目标搜图使用次数","5000",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","bkzzrws","布控追踪任务数","230",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","abhhrws","安保护航任务数","90",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","qyxlrws","区域巡逻任务数","310",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","jbtxsycs","结伴同行使用次数","20",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","spjkl","视频监控量","123100",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","zbfls","总并发路数","5000",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","dqbfls","当前并发路数","3265",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","jrbjzs","今日报警总数","1632",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","bkbjzs","布控报警总数","1965",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","abbjzs","安保报警总数","3694",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","xlbjzs","今日巡逻报警总数","13889",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","jrxlls","今日巡逻路数","8521",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","jrsjbjzs","今日事件报警总数","3625",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","jrrybjzs","今日人员报警总数","2369",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","jrclbjzs","今日车辆报警总数","7895",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","drzptj","当日抓拍行人总数","452369",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","drzpfjdczs","当日抓拍非机动车总数","995642",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","drzpclzs","当日抓拍车辆总数","362514",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","drzpxrnzs","当日抓拍行人男总数","238946",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","drzpxrnzs","当日抓拍行人女总数","213423",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","drzpyfyshz","抓拍衣服颜色汇总","90",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","dqbjzs","区域巡逻任务数","310",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","scqfjslls","上城区分局算力路数","560",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","xcqfjslls","下城区分局算力路数","700",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","jgqfjslls","江干区分局算力路数","600",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","gsqfjslls","拱墅区分局算力路数","932",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","xhqfjslls","西湖区分局算力路数","563",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","bjqfjslls","滨江区分局算力路数","123",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","djdcyjjqfjslls","大江东产业集聚区分局算力路数","546",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","jjjskfqfjslls","经济技术开发区分局算力路数","214",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","xhfjmsqfjslls","西湖风景名胜区分局算力路数","300",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","xsqfjslls","萧山区分局算力路数","1000",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","yhqfjslls","余杭区分局算力路数","1500",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","fyqfjslls","富阳区分局算力路数","360",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","laqfjslls","临安区分局算力路数","400",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","tlqfjslls","桐庐县局算力路数","471",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","caqfjslls","淳安县局算力路数","231",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","jdqfjslls","建德市局算力路数","500",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","fjpcsbzsyxt1","分局/派出所本周使用系统Top1","100",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","fjpcsbzsyxt2","分局/派出所本周使用系统Top2","99",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","fjpcsbzsyxt3","分局/派出所本周使用系统Top3","82",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","fjpcsbzsyxt4","分局/派出所本周使用系统Top4","65",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","fjpcsbzsyxt5","分局/派出所本周使用系统Top5","63",toDay);
        operationBlueScreenFloatingFrame("spzn","浮框-视频智能","fjpcsbzsyxt6","分局/派出所本周使用系统Top6","42",toDay);
    }

    /**
     * 操作白屏数据库封装的方法
     */
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
    /**
     * 操作蓝屏数据库封装的方法
     */
    void operationBlueScreen(String type_code,String type_name,String indicator_code,String indicator_name,String indicator_value,String toDay){
        DataIndicatorBean dataIndicatorBean = new DataIndicatorBean();
        jdbcTemplate.query("select indicator_code,dt from data_lp_indicator where indicator_code=? and dt=?",new Object[]{indicator_code,toDay},(rs)->{
            dataIndicatorBean.setIndicatorCode(rs.getString("indicator_code"));
        });
        if(dataIndicatorBean.getIndicatorCode()==null){
            jdbcTemplate.update("insert into data_lp_indicator(type_code,type_name,indicator_code,indicator_name,indicator_value,dt) value(?,?,?,?,?,?)",type_code,type_name,indicator_code,indicator_name,indicator_value,toDay);
        }else{
            jdbcTemplate.update("update data_lp_indicator set indicator_value=? where indicator_code=? and dt=?",indicator_value,indicator_code,toDay);
        }
    }
    /**
     * 操作蓝屏浮框数据库封装的方法
     */
    void operationBlueScreenFloatingFrame(String type_code,String type_name,String indicator_code,String indicator_name,String indicator_value,String toDay){
        DataIndicatorBean dataIndicatorBean = new DataIndicatorBean();
        jdbcTemplate.query("select indicator_code,dt from data_lpp_indicator where indicator_code=? and dt=?",new Object[]{indicator_code,toDay},(rs)->{
            dataIndicatorBean.setIndicatorCode(rs.getString("indicator_code"));
        });
        if(dataIndicatorBean.getIndicatorCode()==null){
            jdbcTemplate.update("insert into data_lpp_indicator(type_code,type_name,indicator_code,indicator_name,indicator_value,dt) value(?,?,?,?,?,?)",type_code,type_name,indicator_code,indicator_name,indicator_value,toDay);
        }else{
            jdbcTemplate.update("update data_lpp_indicator set indicator_value=? where indicator_code=? and dt=?",indicator_value,indicator_code,toDay);
        }
    }


    /**
     * 浅色大屏相关(调用阿里接口获取视频巡逻的个数)
     */
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

    /**
     * 蓝色大屏浮框相关 功能模块使用统计
     */
    @Override
    public ResultVo functionUseNumber() {
        /**
         * 1.获得当前登录用户的所属分局
         * 2.获得该分局下所用的用户编码
         * 3.统计这些用户使用各个功能模块的次数
         */
        List<Integer> userIds = new ArrayList<>();
        userIds.add(1);
        userIds.add(2);
        ScreenFunctionUserInfoBean screenFunctionUserInfoBean = new ScreenFunctionUserInfoBean();
        if(userIds!=null&&userIds.size()!=0){
            //图搜使用次数
            screenFunctionUserInfoBean.setSeachUsedTotal(sysOperationLogMapper.selectTuSouNumber(userIds));
            //布控追踪任务个数
            screenFunctionUserInfoBean.setSurveyTaskTotal(viSurveyTaskMapper.selectExecutingSurveyTaskNumber(userIds));
            //安保护航任务个数(暂时没有数据,暂时设置为0)
            screenFunctionUserInfoBean.setSecurityTaskTotal(0);
            //结伴同行使用次数
            screenFunctionUserInfoBean.setPeerGroupTotal(0);
            //区域巡逻执行任务个数
            screenFunctionUserInfoBean.setAreaPatrollingTaskTotal(0);
        }else{
            screenFunctionUserInfoBean.setSeachUsedTotal(0);
            screenFunctionUserInfoBean.setSurveyTaskTotal(0);
            screenFunctionUserInfoBean.setSecurityTaskTotal(0);
            screenFunctionUserInfoBean.setPeerGroupTotal(0);
            screenFunctionUserInfoBean.setAreaPatrollingTaskTotal(0);
        }
        return ResultVo.success(screenFunctionUserInfoBean);
    }
    /**
     * 蓝色大屏浮框相关 视频路数统计
     */
    @Override
    public ResultVo videoPathNumber() {


        ScreenVideoPathInfoBean screenVideoPathInfoBean = new ScreenVideoPathInfoBean();
        /**
         * 1.当前账号拥有权限的视频监控路数
         *      (1).获得当前登录用户所在的组织code
         *      (2).获得该组织权限下所用的组织codes
         *      (3).从设备表中查询这些组织的设备数量
         */
        List<String> orgIds = new ArrayList<>();
        orgIds.add("001001");
        orgIds.add("001001001");
        orgIds.add("001");
        if(orgIds!=null&&orgIds.size()!=0){
            screenVideoPathInfoBean.setVideoMonitorTotal(deviceMapper.selectDeviceNumberByCivilCodes(orgIds));
        }else {
            screenVideoPathInfoBean.setVideoMonitorTotal(0);
        }
        /**
         * 2.当前账号权限拥有算力对应的最大并发路数/正在使用的并发数
         *      (1).获得当前登录用户所在的组织code
         *      (2).获取总并发数/正在使用的并发数
         */
        String orgCode = "3304";
        if(orgCode!=null&&!"".equals(orgCode)){
            screenVideoPathInfoBean.setConcurrentPathTotal(organizationRoadMapper.selectOrgRoadByOrgCode(orgCode));
            screenVideoPathInfoBean.setCurrentConcurrentPathTotal(organizationRoadMapper.selectOrgUseRoadByOrgCode(orgCode));
        }else {
            screenVideoPathInfoBean.setConcurrentPathTotal(0);
            screenVideoPathInfoBean.setCurrentConcurrentPathTotal(0);
        }
        return ResultVo.success(screenVideoPathInfoBean);
    }
    /**
     * 蓝色大屏浮框相关 报警统计
     */
    @Override
    public ResultVo alaramNumber() {
        ScreenAlaramInfoBean screenAlaramInfoBean = new ScreenAlaramInfoBean();
        /**
         * 4.当日当前账号权限下布控、安保、巡逻报警总数统计
         *      (1).获得当前系统登录人的组织code
         *      (2).获得该组织code下所有组织的人员id   List<Integer>
         *      (3).根据人员id获得布控、安保、巡逻表中的任务id
         *      (4).去对应布控、安保、巡逻报警表中 统计报警数量总和(安保、巡逻暂时没有数据)
         */
        //布控报警总数
        List<Integer> userIds = new ArrayList<>();
        userIds.add(1);
        userIds.add(2);
        List<String> taskIds = new ArrayList<>();
        if(userIds!=null&&userIds.size()!=0){
            taskIds = viSurveyTaskMapper.selectIdByUserIds(userIds);
        }
        if(taskIds!=null&&taskIds.size()!=0){
            screenAlaramInfoBean.setSurveyAlarmTotal(viPsurveyAlaramMapper.selectIdNumberBytaskId(taskIds));
        }else{
            screenAlaramInfoBean.setSurveyAlarmTotal(0);
        }
        screenAlaramInfoBean.setSecurityAlarmTotal(0);
        screenAlaramInfoBean.setPatrolAlarmTotal(0);
        screenAlaramInfoBean.setAlarmTotal(screenAlaramInfoBean.getPatrolAlarmTotal()+screenAlaramInfoBean.getSecurityAlarmTotal()+screenAlaramInfoBean.getSurveyAlarmTotal());

        return ResultVo.success(screenAlaramInfoBean);
    }

    /**
     * 蓝色大屏浮框相关  分局算力分配
     */
    @Override
    public ResultVo bureauDeviceDistribution() {
        ScreenBureauDeviceDistributionBean screenBureauDeviceDistributionBean = new ScreenBureauDeviceDistributionBean();
        /**
         * 1.分局/派出所算力分配
         *      (1).获得当前登录的用户的组织code(三种情况: 市局 分局 派出所)
         *      (2).市局查看所有分局的算力分配占比；分局展示各自下属派出所的算力分配占比；派出所展示同分局下所有派出所的算力分配占比（派出所级的此项和所属分局的展示项相同）
         */
        String patentCode = "3304";
        List<ScreenSysOrgRoadBean> screenSysOrgRoadBeans = organizationRoadMapper.selectSysOrgRoadBean(patentCode);
        screenBureauDeviceDistributionBean.setBureauDeviceTotal(screenSysOrgRoadBeans);
        /**
         * 2.分局/派出所使用系统 本周使用系统次数：统计对应账号管辖的所有账号使用功能调用API接口的总次数
         *    市局查看本周所有分局使用系统次数最多的前六分局及对应使用系统次数；分局展示各自下属派出所的使用top6；
         *      (1).获得当前登录的用户的组织所属级别(三种情况: 市局 分局 派出所)
         *      (2).找出该级别下的的所有组织code,如果是派出所找出兄弟派出所的组织code  List<String>
         *      (3).找出这些组织下所对应的用户id   List<Integer>  ====》   可以转为Map<String,List<Integer> key为组织code,键为该组织下的用户
         *      (4).去操作日志表中找出这些用户(本周)的操作日志记录  List<Operation>
         *      (5).将List<Operation>中的用户id  换成对应的组织code
         *      (6).按照组织code分组,取出前top6
         */

        Map<String,List<Integer>> map = new HashMap<>();

        int m = 1;
        for (int i = 1;i<8;i++){
            List<Integer> userIds = new ArrayList<>();
            userIds.add(m);
            userIds.add(m+1);
            map.put("00100"+i,userIds);
            m=m+2;
        }

        List<ScreenSystemUseNumber> bureauUseSystemRanks = new ArrayList<>();

        map.forEach((orgCode,userIds)->{
            SysOrganizationBean sysOrganizationBean = new SysOrganizationBean();
            sysOrganizationBean.setOrgId(orgCode);
            bureauUseSystemRanks.add(new ScreenSystemUseNumber(sysOrganizationMapper.readOrgNameByOrgCode(orgCode),sysOperationLogMapper.selectUseNumberByUserIds(userIds)));
        });
        bureauUseSystemRanks.sort(Comparator.comparing(ScreenSystemUseNumber::getNumber));
        screenBureauDeviceDistributionBean.setBureauUseSystemRank(bureauUseSystemRanks);
        return ResultVo.success(screenBureauDeviceDistributionBean);
    }

    /**
     * 蓝色大屏浮框相关  视频巡逻统计
     */
    @Override
    public ResultVo videoPatrolNumber() {
        ScreenVideoPatrolNumber screenVideoPatrolNumber = new ScreenVideoPatrolNumber(1000,1000,78,102,99);
        return ResultVo.success(screenVideoPatrolNumber);
    }
    /**
     * 蓝色大屏浮框相关  当日抓拍统计
     */
    @Override
    public ResultVo snapNumberToday() {
        Map<String,Object> map = new HashMap<>();
        map.put("pedestrian",7120000);
        map.put("noVehicle",7213000);
        map.put("vehicle",7103000);
        map.put("male",5675765);
        map.put("female",7615712);
        map.put("color",new String[]{"粉色","红色","紫色","黑色","灰色","绿色","橘色"});
        return ResultVo.success(map);
    }


}
