package com.secusoft.web.task;

import com.alibaba.fastjson.JSON;
import com.secusoft.web.config.BkrepoConfig;
import com.secusoft.web.config.NormalConfig;
import com.secusoft.web.mapper.SyncZdryLogMapper;
import com.secusoft.web.mapper.ViBasicMemberMapper;
import com.secusoft.web.mapper.ViRepoMapper;
import com.secusoft.web.mapper.ZdryMapper;
import com.secusoft.web.model.*;
import com.secusoft.web.service.ZdryService;
import com.secusoft.web.serviceapi.model.BaseResponse;
import com.secusoft.web.tusouapi.model.BKMemberAddRequest;
import com.secusoft.web.tusouapi.model.BKMemberDeleteRequest;
import com.secusoft.web.tusouapi.model.BaseRequest;
import com.secusoft.web.utils.ImageUtils;
import com.secusoft.web.utils.OdpsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 烽火数据同步Controller
 */
@Component
@Configurable
@EnableScheduling
public class BkrepoDataTask {
    private static Logger log = LoggerFactory.getLogger(BkrepoDataTask.class);

    @Resource
    SyncZdryLogMapper syncZdryLogMapper;

    @Resource
    ViRepoMapper viRepoMapper;

    @Resource
    ZdryMapper zdryMapper;

    @Resource
    ViBasicMemberMapper viBasicMemberMapper;

    @Autowired
    ZdryService zdryService;

    @Autowired
    BkrepoConfig bkrepoConfig;

    @Autowired
    @Qualifier("threedaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    /**
     * 烽火定时请求数据
     *
     * @throws ParseException
     * @throws InterruptedException
     */
    //0 0 */1 * * ? 每小时执行一次
    //0 0/1 * * * ? 每分钟执行一次
    //0 15 10 ? * * 每天10点15分触发
    @Async
    @Scheduled(cron = "30 33 17 * * ?")//0 0 */1 * * ?
    public void BkrepoData() throws ParseException, InterruptedException {
        log.info("开始同步基础库数据");
        String[] bkrepoTable = null;
        log.info(NormalConfig.getBkrepoType() == 1 ? "从烽火取数据" : "从视频专网取数据");
        if (NormalConfig.getBkrepoType() == 1) {
            bkrepoTable = NormalConfig.getBkrepoTable1().split(",");
            ViRepoBean viRepoBean = null;
            for (String str : bkrepoTable) {
                OdpsUtils.table = "vi_" + str + "_linshi";
                OdpsUtils.sql = "select * from vi_" + str + ";";
                OdpsUtils.runSql();
                OdpsUtils.tunnel();
                return;
            }
        } else if (NormalConfig.getBkrepoType() == 2) {
            BaseResponse baseResponse = null;
            bkrepoTable = NormalConfig.getBkrepoTable2().split(",");
            for (String str : bkrepoTable) {
                SyncZdryLogBean syncZdryLogBean = new SyncZdryLogBean();
                log.info("开始同步基础库数据：" + str);
                syncZdryLogBean.setTableName(str);
                SyncZdryLogBean syncBean = syncZdryLogMapper.selectByBean(syncZdryLogBean);
                ZdryVo zdryVo = new ZdryVo();
                zdryVo.setTableName(str);
                zdryVo.setUpdateTime(new Date());
                zdryVo.setIsFirst(null == syncBean ? 1 : 0);
                //判断是否是首次同步 1首次，0非首次
                log.info("是否是首次同步：" + (null == syncBean));
                if ("view_qgzt".equals(str)) {
                    //asycBkrepo("全国在逃人员布控库", zdryVo, syncBean, syncZdryLogBean);
                } else if ("view_sgy".equals(str)) {
                    //asycBkrepo("省高院布控库", zdryVo, syncBean, syncZdryLogBean);

                } else if ("view_sdts".equals(str)) {
                    asycBkrepo("涉毒脱失人员布控库", zdryVo, syncBean, syncZdryLogBean);
                }
            }
            log.info("结束同步基础库数据");
        }
    }

    /**
     * 同步数据总方法
     */
    private void asycBkrepo(String bkname, ZdryVo zdryVo, SyncZdryLogBean syncBean, SyncZdryLogBean syncZdryLogBean) {

        ViRepoBean viRepoBean = null;
        if (null == syncBean) {
            viRepoBean = addViRepo(bkname, bkname, "vi_" + zdryVo.getTableName());
        } else {
            List<ViRepoBean> list = viRepoMapper.getAllViRepo(null).stream().filter((ViRepoBean viRepo) -> viRepo.getTableName().equals(
                    "vi_" + zdryVo.getTableName())).collect(Collectors.toList());
            if (list.size() > 0) {
                viRepoBean = list.get(0);
            } else {
                viRepoBean = addViRepo(bkname, bkname, "vi_" + zdryVo.getTableName());
            }
        }
        boolean result = true;
        Integer syncNum = 1;
        while (result) {
            zdryVo.setSyncNum(syncNum);
            result = syncPeople(zdryVo, syncBean, syncZdryLogBean, viRepoBean);
            syncNum++;
            syncZdryLogBean = syncZdryLogMapper.selectByBean(syncZdryLogBean);
        }
        if (null == syncBean) {
            orderbyPartitionData(zdryVo, viRepoBean);
        }
    }

    /**
     * 创建布控库
     *
     * @param bkname    布控库名称
     * @param bkdesc    布控库描述信息
     * @param tableName 表名,基础库的话，vi+源表名
     * @return
     */
    private ViRepoBean addViRepo(String bkname, String bkdesc, String tableName) {
        ViRepoBean viRepoBean = new ViRepoBean();
        viRepoBean.setBkname(bkdesc);
        viRepoBean.setBkdesc(bkname);
        viRepoBean.setPoliceStationId("0");
        viRepoBean.setTableName(tableName);
        viRepoBean.setType(0);
        viRepoBean.setBktype(1);
        viRepoMapper.insertViRepo(viRepoBean);
        return viRepoBean;
    }

    /**
     * 添加基础布控库
     *
     * @param viRepoBean
     * @param bean
     * @return
     */
    private ViBasicMemberBean addViBasicMember(ViRepoBean viRepoBean, ZdryBean bean) {
        ViBasicMemberBean viBasicMemberBean = new ViBasicMemberBean();
        viBasicMemberBean.setObjectId(viRepoBean.getTableName() + "_" + bean.getPicId());
        viBasicMemberBean.setRepoId(viRepoBean.getId());
        viBasicMemberBean.setRealObjectId(bean.getPicId());
        viBasicMemberBean.setRealTableName(viRepoBean.getTableName());
        viBasicMemberBean.setIdentityId(bean.getPicId());
        viBasicMemberBean.setIdentityName(bean.getHumanName());
        viBasicMemberBean.setContent(ImageUtils.encode(bean.getPic()));
        viBasicMemberBean.setRepoId(viRepoBean.getId());
        viBasicMemberMapper.insertViBasicMember(viBasicMemberBean);
        memberSendToTQAdd(viBasicMemberBean);
        return viBasicMemberBean;
    }

    /**
     * 下发到天擎添加布控目标
     *
     * @param viBasicMemberBean
     */
    private BaseResponse memberSendToTQAdd(ViBasicMemberBean viBasicMemberBean) {
        //添加布控目标
        BaseRequest<BKMemberAddRequest> bkMemberAddRequestBaseRequest = new BaseRequest<>();
        bkMemberAddRequestBaseRequest.setRequestId(bkrepoConfig.getRequestId());
        BKMemberAddRequest bkMemberAddRequest = new BKMemberAddRequest();
        bkMemberAddRequest.setObjectId(viBasicMemberBean.getObjectId());
        bkMemberAddRequest.setBkid(bkrepoConfig.getBkid());
        bkMemberAddRequest.setContent(viBasicMemberBean.getContent());
        bkMemberAddRequestBaseRequest.setData(bkMemberAddRequest);

        String requestStr = JSON.toJSONString(bkMemberAddRequestBaseRequest);
        log.info(requestStr);
        //BaseResponse baseResponse = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBkmemberAdd(), bkMemberAddRequestBaseRequest);
        BaseResponse baseResponse = new BaseResponse();

        return baseResponse;
    }

    /**
     * 下发到天擎删除
     *
     * @param viBasicMemberBean
     */
    private BaseResponse memberSendToTQDelete(ViBasicMemberBean viBasicMemberBean) {
        //删除布控目标
        BaseRequest<BKMemberDeleteRequest> bkMemberDeleteRequestBaseRequest = new BaseRequest<>();
        bkMemberDeleteRequestBaseRequest.setRequestId(bkrepoConfig.getRequestId());
        BKMemberDeleteRequest bkMemberDeleteRequest = new BKMemberDeleteRequest();
        bkMemberDeleteRequest.setObjectIds(viBasicMemberBean.getObjectId());
        bkMemberDeleteRequest.setBkid(bkrepoConfig.getBkid());
        bkMemberDeleteRequestBaseRequest.setData(bkMemberDeleteRequest);

        String requestStr = JSON.toJSONString(bkMemberDeleteRequestBaseRequest);
        log.info(requestStr);
        //BaseResponse baseResponse = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getPathBkmemberDelete(), bkMemberDeleteRequestBaseRequest);
        BaseResponse baseResponse = new BaseResponse();

        return baseResponse;
    }

    /**
     * 同步全国在逃人员
     *
     * @param zdryVo
     * @param syncBean
     * @param syncZdryLogBean
     */
    @Transactional
    public boolean syncPeople(ZdryVo zdryVo, SyncZdryLogBean syncBean, SyncZdryLogBean syncZdryLogBean, ViRepoBean viRepoBean) {
        List<ZdryBean> zdryList = null;
        String tableName = zdryVo.getTableName();
        zdryList = getZdryResponse(zdryVo, syncBean);
        if (null == syncBean) {
            for (ZdryBean bean : zdryList) {
                if (tableName.equals("view_qgzt")) {
                    zdryMapper.insertQgzt(bean);
                } else if (tableName.equals("view_sgy")) {
                    zdryMapper.insertSgy(bean);
                } else if (tableName.equals("view_sdts")) {
                    zdryMapper.insertSdts(bean);
                }

            }
            if (syncZdryLogBean.getId() == null) {
                if (zdryList.size() > 0) {
                    syncZdryLogBean.setSyncCount(zdryList.size());
                    syncZdryLogBean.setLastSyncTime(zdryList.get(zdryList.size() - 1).getUpdateTime());
                    syncZdryLogBean.setLastEndTime(new Date());
                    syncZdryLogMapper.insertSyncZdryLog(syncZdryLogBean);
                }
            } else {
                if (zdryList.size() > 0) {
                    updateSyncZdryLog(syncZdryLogBean, zdryList);
                }
            }
        } else {
            for (ZdryBean bean : zdryList) {
                ViBasicMemberBean viBasicMemberBean = new ViBasicMemberBean();
                if (tableName.equals("view_qgzt")) {
                    viBasicMemberBean.setObjectId("view_qgzt_" + bean.getPicId());
                    zdryMapper.insertQgzt(bean);
                } else if (tableName.equals("view_sgy")) {
                    viBasicMemberBean.setObjectId("view_sgy_" + bean.getPicId());
                    zdryMapper.insertSgy(bean);
                } else if (tableName.equals("view_sdts")) {
                    viBasicMemberBean.setObjectId("view_sdts_" + bean.getPicId());
                    zdryMapper.insertSdts(bean);
                }
                ViBasicMemberBean viBasicMemberByObjectId = viBasicMemberMapper.getViBasicMemberByObjectId(viBasicMemberBean);
                if (bean.getStatus().equals("1")) {
                    memberSendToTQDelete(viBasicMemberByObjectId);
                    viBasicMemberMapper.delViBasicMember(viBasicMemberByObjectId.getId());
                } else {
                    addViBasicMember(viRepoBean,bean);
                }
            }
            if (zdryList.size() > 0) {
                updateSyncZdryLog(syncZdryLogBean, zdryList);
            }
        }
        return zdryList.size() > 0 ? true : false;
    }

    /**
     * 首次更新过滤后数据处理1
     *
     * @param zdryVo
     * @param viRepoBean
     */
    private void orderbyPartitionData(ZdryVo zdryVo, ViRepoBean viRepoBean) {
        String tableName = zdryVo.getTableName();
        log.info("开始过滤：" + tableName);

        ZdryBeanVo zdryBeanVo = new ZdryBeanVo();
        zdryBeanVo.setCurrent(1);
        zdryBeanVo.setSize(50);
        Map<String, Object> stringObjectMap = null;
        if (tableName.equals("view_qgzt")) {
            stringObjectMap = zdryService.orderbyPartitionQgzt(zdryBeanVo);
            basicMemberDue(stringObjectMap, zdryBeanVo, viRepoBean, tableName);
        } else if (tableName.equals("view_sgy")) {
            stringObjectMap = zdryService.orderbyPartitionSgy(zdryBeanVo);
            basicMemberDue(stringObjectMap, zdryBeanVo, viRepoBean, tableName);
        } else if (tableName.equals("view_sdts")) {
            stringObjectMap = zdryService.orderbyPartitionSdzt(zdryBeanVo);
            basicMemberDue(stringObjectMap, zdryBeanVo, viRepoBean, tableName);
        }
    }

    /**
     * 首次更新过滤后数据处理2
     *
     * @param stringObjectMaps
     * @param zdryBeanVo
     * @param viRepoBean
     * @param tableName
     */
    private void basicMemberDue(Map<String, Object> stringObjectMaps, ZdryBeanVo zdryBeanVo, ViRepoBean viRepoBean, String tableName) {
        Map<String, Object> stringObjectMap = null;
        Integer total = null;
        Integer pages = null;

        List<ZdryBean> list = null;
        total = Integer.parseInt(String.valueOf(stringObjectMaps.get("total")));
        log.info(tableName + "过滤后数量：" + total);
        pages = Integer.parseInt(String.valueOf(stringObjectMaps.get("pages")));
        log.info(tableName + "过滤后需执行次数：" + pages);
        list = (ArrayList<ZdryBean>) stringObjectMaps.get("records");
        for (ZdryBean zdryBean : list) {
            //本地新增
            addViBasicMember(viRepoBean, zdryBean);
        }
        for (int i = 2; i <= pages; i++) {
            zdryBeanVo.setCurrent(i);
            stringObjectMap = zdryService.orderbyPartitionQgzt(zdryBeanVo);
            list = (ArrayList<ZdryBean>) stringObjectMap.get("records");
            for (ZdryBean zdryBean : list) {

                //本地新增
                addViBasicMember(viRepoBean, zdryBean);
            }
        }
    }

    /**
     * 更新同步日志
     *
     * @param syncZdryLogBean
     * @param zdryList
     */
    private void updateSyncZdryLog(SyncZdryLogBean syncZdryLogBean, List<ZdryBean> zdryList) {
        syncZdryLogBean.setSyncCount(zdryList.size());
        syncZdryLogBean.setLastSyncTime(zdryList.get(zdryList.size() - 1).getUpdateTime());
        syncZdryLogBean.setLastEndTime(new Date());
        syncZdryLogMapper.updateSyncZdryLog(syncZdryLogBean);
    }

    /**
     * 获取基础库数据信息
     * @param zdryVo
     * @param syncZdryLogBean
     * @return
     */
    private List<ZdryBean> getZdryResponse(ZdryVo zdryVo, SyncZdryLogBean syncZdryLogBean) {
        log.info("开始获取基础库数据：" + zdryVo.getTableName() + "，" + zdryVo.getSyncNum());
//        String requestStr = JSON.toJSONString(zdryVo);
//        String responseStr = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getViewZdryList(), requestStr);
//        JSONObject jsonObjects = (JSONObject) JSONObject.parse(responseStr);
//        String dataJson = jsonObjects.getString("data");
//        ZdryResponse zdryResponse = JSON.parseObject(dataJson, ZdryResponse.class);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(zdryVo.getIsFirst() == 1 ? zdryVo.getUpdateTime() : syncZdryLogBean.getLastSyncTime());
        String sql = "select * from (select ROWNUM AS num,HUMAN_NAME,PIC_ID,STATUS,UPDATE_TIME,PIC from  " + zdryVo.getTableName() + "  where rownum <= " + (zdryVo.getSyncNum() * 40) + " and UPDATE_TIME>=to_date('" + dateString + "','yyyy-MM-dd HH24:mi:ss')  order by UPDATE_TIME) a where a.num>=" + ((zdryVo.getSyncNum() - 1) * 40 + 1);
        log.info(sql);
        RowMapper<ZdryBean> rowMapper = new BeanPropertyRowMapper<ZdryBean>(ZdryBean.class);
        List<ZdryBean> zdryList = jdbcTemplate.query(sql, rowMapper);
        log.info("结束获取基础库数据：" + zdryVo.getTableName() + "，数量：" + zdryList.size());
        return zdryList;
    }
}
