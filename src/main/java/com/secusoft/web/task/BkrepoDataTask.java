package com.secusoft.web.task;

import com.alibaba.fastjson.JSON;
import com.secusoft.web.config.BkrepoConfig;
import com.secusoft.web.config.NormalConfig;
import com.secusoft.web.mapper.SyncZdryLogMapper;
import com.secusoft.web.mapper.ViBasicMemberMapper;
import com.secusoft.web.mapper.ViRepoMapper;
import com.secusoft.web.mapper.ZdryMapper;
import com.secusoft.web.model.*;
import com.secusoft.web.serviceapi.model.BaseResponse;
import com.secusoft.web.tusouapi.model.BKMemberAddRequest;
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
import java.util.Date;
import java.util.List;
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
    @Scheduled(cron = "00 25 19 * * ?")//0 0 */1 * * ?
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
            SyncZdryLogBean syncZdryLogBean = new SyncZdryLogBean();
            BaseResponse baseResponse = null;
            ViRepoBean viRepoBean = null;
            bkrepoTable = NormalConfig.getBkrepoTable2().split(",");
            for (String str : bkrepoTable) {

                log.info("开始同步基础库数据：" + str);
                syncZdryLogBean.setTableName(str);
                SyncZdryLogBean syncBean = syncZdryLogMapper.selectByBean(syncZdryLogBean);
                ZdryVo zdryVo = new ZdryVo();
                zdryVo.setTableName(str);
                //判断是否是首次同步 1首次，0非首次
                log.info("是否是首次同步：" + (null == syncBean));
                zdryVo.setIsFirst(null == syncBean ? 1 : 0);
                zdryVo.setUpdateTime(null == syncBean ? new Date() : syncBean.getLastSyncTime());
                if ("view_qgzt".equals(str)) {
                    if (null == syncBean) {
                        viRepoBean = addViRepo("全国在逃人员布控库", "全国在逃人员布控库", "vi_" + str);
                    } else {
                        List<ViRepoBean> list = viRepoMapper.getAllViRepo(null).stream().filter((ViRepoBean viRepo) -> viRepo.getTableName().equals(
                                "vi_view_qgzt")).collect(Collectors.toList());
                        if (list.size() > 0) {
                            viRepoBean = list.get(0);
                        } else {
                            viRepoBean = addViRepo("全国在逃人员布控库", "全国在逃人员布控库", "vi_" + str);
                        }
                    }
                    boolean result = true;
                    Integer syncNum = 1;
                    while (result) {
                        zdryVo.setSyncNum(syncNum);
                        result = syncQgztPeople(zdryVo, syncBean, syncZdryLogBean, viRepoBean);
                        syncNum++;
                    }
                } else if ("view_sgy".equals(str)) {
//                    baseResponse = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getViewSgyList());
//                    ZdryResponse zdryResponse = JSON.parseObject(JSON.toJSONString(baseResponse.getData()), ZdryResponse.class);

                } else if ("view_lk".equals(str)) {
//                    baseResponse = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getViewLkList());
//                    ZdryResponse zdryResponse = JSON.parseObject(JSON.toJSONString(baseResponse.getData()), ZdryResponse.class);

                } else if ("view_sdts".equals(str)) {
//                    baseResponse = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getViewSdtsList());
//                    ZdryResponse zdryResponse = JSON.parseObject(JSON.toJSONString(baseResponse.getData()), ZdryResponse.class);

                }
            }

            log.info("结束同步基础库数据");
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
        viBasicMemberBean.setRealObjectId(String.valueOf(bean.getId()));
        viBasicMemberBean.setRealTableName(viRepoBean.getTableName());
        viBasicMemberBean.setIdentityId(bean.getPicId());
        viBasicMemberBean.setIdentityName(bean.getHumanName());
        viBasicMemberBean.setContent(ImageUtils.encode(bean.getPic()));
        viBasicMemberBean.setRepoId(viRepoBean.getId());
        viBasicMemberMapper.insertViBasicMember(viBasicMemberBean);
        return viBasicMemberBean;
    }

    /**
     * 下发到天擎
     *
     * @param viBasicMemberBean
     */
    private BaseResponse memberSendToTQ(ViBasicMemberBean viBasicMemberBean) {
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
     * 同步全国在逃人员
     *
     * @param zdryVo
     * @param syncBean
     * @param syncZdryLogBean
     */
    @Transactional
    public boolean syncQgztPeople(ZdryVo zdryVo, SyncZdryLogBean syncBean, SyncZdryLogBean syncZdryLogBean, ViRepoBean viRepoBean) {
        List<ZdryBean> zdryList = getZdryResponse(zdryVo);
        if (null == syncBean) {
            for (ZdryBean bean : zdryList) {
                zdryMapper.insertQgzt(bean);
                ViBasicMemberBean viBasicMemberBean = addViBasicMember(viRepoBean, bean);
                //下发天擎
                BaseResponse baseResponse = memberSendToTQ(viBasicMemberBean);
            }
            syncZdryLogBean.setSyncCount(zdryList.size());
            syncZdryLogBean.setLastSyncTime(zdryList.get(zdryList.size() - 1).getUpdateTime());
            syncZdryLogBean.setLastEndTime(new Date());
            syncZdryLogMapper.insertSyncZdryLog(syncZdryLogBean);
//            for (int i = 2; i < zdryResponse.getPages(); i++) {
//                zdryVo.setCurrent(i);
//                zdryResponse = getZdryResponse(zdryVo);
//                for (ZdryBean bean : zdryResponse.getRecords()) {
//                    // zdryMapper.insertQgzt(bean);
//                    ViBasicMemberBean viBasicMemberBean = addViBasicMember(viRepoBean, bean);
//                    BaseResponse baseResponse = memberSendToTQ(viBasicMemberBean);
//                }
//
//                syncZdryLogBean.setSyncCount(zdryResponse.getRecords().size());
//                syncZdryLogBean.setLastSyncTime(zdryResponse.getRecords().get(zdryResponse.getRecords().size() - 1).getUpdateTime());
//                syncZdryLogBean.setLastEndTime(new Date());
//                //syncZdryLogMapper.updateSyncZdryLog(syncZdryLogBean);
//            }
        } else {
            for (ZdryBean bean : zdryList) {

            }
            syncZdryLogBean.setLastSyncTime(zdryList.get(zdryList.size() - 1).getUpdateTime());
        }
        return zdryList.size() > 0 ? true : false;
    }

    private List<ZdryBean> getZdryResponse(ZdryVo zdryVo) {

        log.info("开始获取基础库数据");
//        String requestStr = JSON.toJSONString(zdryVo);
//        String responseStr = ServiceApiClient.getClientConnectionPool().fetchByPostMethod(ServiceApiConfig.getViewZdryList(), requestStr);
//        JSONObject jsonObjects = (JSONObject) JSONObject.parse(responseStr);
//        String dataJson = jsonObjects.getString("data");
//        ZdryResponse zdryResponse = JSON.parseObject(dataJson, ZdryResponse.class);
        String sql = "select HUMAN_NAME,PIC_ID,STATUS,UPDATE_TIME,PIC from VIEW_QGZT where rownum between " + ((zdryVo.getSyncNum() - 1) * 30 + 1) + " and " + zdryVo.getSyncNum() * 30;

        RowMapper<ZdryBean> rowMapper = new BeanPropertyRowMapper<ZdryBean>(ZdryBean.class);
        List<ZdryBean> users = jdbcTemplate.query(sql, rowMapper);
        log.info("结束获取基础库数据");
        return users;
    }

    private void databaseInsertOrUpdate(String tableName, SyncZdryLogBean syncZdryLogBean) {
        if ("view_qgzt".equals(tableName)) {


        } else if ("view_sgy".equals(tableName)) {


        } else if ("view_lk".equals(tableName)) {


        } else if ("view_sdts".equals(tableName)) {


        }
    }
}
