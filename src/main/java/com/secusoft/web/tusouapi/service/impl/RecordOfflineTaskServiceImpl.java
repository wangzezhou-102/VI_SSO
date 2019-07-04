package com.secusoft.web.tusouapi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.core.exception.BussinessException;
import com.secusoft.web.mapper.RecordOfflineTaskMapper;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.tusouapi.model.RecordOfflineTaskBean;
import com.secusoft.web.tusouapi.model.RecordOfflineTaskRequest;
import com.secusoft.web.tusouapi.service.RecordOfflineTaskService;
import com.secusoft.web.utils.MyHttpClientPool;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.NumberFormat;
import java.util.*;

@Service
public class RecordOfflineTaskServiceImpl implements RecordOfflineTaskService {
    @Value("${vi.service.testurl}")
    private String testurl;
    @Resource
    RecordOfflineTaskMapper recordOfflineTaskMapper;

    @Resource
    MyHttpClientPool myHttpClientPool;
    /**
    *  1.开启离线分析任务
     *  2.下发任务失败，重新下发
    * */
    @Override
    @Transactional
    public ResultVo addRecordOfflineTask(RecordOfflineTaskRequest recordOfflineTaskRequest) {

        RecordOfflineTaskBean recordOfflineTaskBean = new RecordOfflineTaskBean();

        Map<String, String> map = new HashMap<>();

        List<String> deviceIds = recordOfflineTaskRequest.getDeviceIds();
        /*
        * 创建离线任务
        * */
        for(String deviceId : deviceIds){
            //任务id
            String taskId = UUID.randomUUID().toString().replaceAll("-", "");
            recordOfflineTaskBean.setTaskId(taskId);
            recordOfflineTaskBean.setDeviceId(deviceId);
            System.out.println("开始时间");
            recordOfflineTaskBean.setCreateTime(new Date());
            recordOfflineTaskBean.setBeginTime(recordOfflineTaskRequest.getBeginTime());
            recordOfflineTaskBean.setEndTime(recordOfflineTaskRequest.getEndTime());
            System.out.println("创建任务");
            recordOfflineTaskMapper.addRecordOfflineTask(recordOfflineTaskBean);
            map.put(deviceId,taskId);
        }
        /*
        * 开启离线任务
        * */
        for(String deviceId : deviceIds){
            Map<String,String> datamap = new HashMap<>();
            datamap.put("taskId", map.get(deviceId));
            datamap.put("deviceId",deviceId);
            datamap.put("beginTime",recordOfflineTaskRequest.getBeginTime().toString());

            datamap.put("endTime",recordOfflineTaskRequest.getEndTime().toString());

            StringEntity paramsEntity = new StringEntity(JSON.toJSONString(datamap),"UTF-8");

            String fetchByPostMethod = myHttpClientPool.fetchByPostMethod(testurl+"/Start",paramsEntity);

            if( fetchByPostMethod != null){
                JSONObject jsonObject = JSONObject.parseObject(fetchByPostMethod);
                JSONObject data = jsonObject.getJSONObject("data");
                if( data != null ) {
                    String errorCode = data.getString("errorCode");
                    //任务开启失败执行，改变任务状态
                    if(!errorCode.equals("SUCCESS")){
                        RecordOfflineTaskBean recordOfflineTaskBean1 = new RecordOfflineTaskBean();
                        recordOfflineTaskBean1.setTaskId(map.get(deviceId));
                        // "2" - 任务下发失败的状态
                        recordOfflineTaskBean1.setEnable("2");
                        recordOfflineTaskMapper.updateRecordOfflineTaskEnableByTaskId(recordOfflineTaskBean1);
                    }
                }
            }else {
                throw new BussinessException(BizExceptionEnum.PARAM_ERROR);
            }
        }
        return ResultVo.success();
    }
    /**
    * 1.离线分析任务进度查询
     * 2.获取单个任务进度，计算所有任务进度
    * */
    @Override
    public ResultVo getRecordOfflineTaskProgress(RecordOfflineTaskBean recordOfflineTaskBean) {
        List<RecordOfflineTaskBean> offlineTaskProgresses = recordOfflineTaskMapper.getRecordOfflineTaskByEnable(recordOfflineTaskBean);
        //可执行任务列表
        List<Map<String,String>> taskVos = new ArrayList<>();
        /*
        * 获取所有执行离线任务
        * 同步所有离线任务进度
        * */
        for (RecordOfflineTaskBean recordOfflineTaskBean1 : offlineTaskProgresses) {
            Map<String, String> map = new HashMap<>();
            map.put("taskId",recordOfflineTaskBean1.getTaskId());
            map.put("deviceId",recordOfflineTaskBean1.getDeviceId());
            taskVos.add(map);
        }
        StringEntity paramsEntity =new StringEntity(JSON.toJSONString(taskVos),"UTF-8");

        String fetchByPostMethod=myHttpClientPool.fetchByPostMethod(testurl+"/Query",paramsEntity);
        if(fetchByPostMethod != null){//HttpClinet 链接正常
            JSONObject jsonObject = JSONObject.parseObject(fetchByPostMethod);
            JSONObject data = jsonObject.getJSONObject("data");
            if(data != null){
                String errorCode = data.getString("errorCode");
                if(!errorCode.equals("SUCCESS")){ //任务下发 失败状态-3
                    for (RecordOfflineTaskBean recordOfflineTaskBean1 : offlineTaskProgresses) {
                         recordOfflineTaskBean1.setEnable("3");
                         recordOfflineTaskMapper.updateRecordOfflineTaskEnableByTaskId(recordOfflineTaskBean1);
                    }
                } else {
                    //获取离线任务同步数据
                    JSONArray Item = (JSONArray) data.get("data");
                    /*
                     * 同步离线任务进度
                     * */
                    RecordOfflineTaskBean recordOfflineTaskBean2 = new RecordOfflineTaskBean();
                    if(Item.size() > 0) {
                        for(int i=0;i<Item.size();i++){
                            JSONObject jsonObject1 = Item.getJSONObject(i);
                            String taskId = (String)jsonObject1.get("taskId");
                            Integer progress = (Integer)jsonObject1.get("progress");
                            recordOfflineTaskBean2.setTaskId(taskId);
                            recordOfflineTaskBean2.setProgress(progress);

                            recordOfflineTaskMapper.updateRecordOfflineTaskProgressByTaskId(recordOfflineTaskBean2);
                        }
                    }
                }
            }
        }else {
            throw new BussinessException(BizExceptionEnum.PARAM_ERROR);
        }
        //离线任务总进度
        List<RecordOfflineTaskBean> offlineTaskProgresses3 = recordOfflineTaskMapper.getRecordOfflineTaskByEnable(recordOfflineTaskBean);
        int sum = 0;
        int count = 0;
        for (RecordOfflineTaskBean recordOfflineTaskBean3:offlineTaskProgresses3) {
            if(recordOfflineTaskBean3.getProgress() == 100){
                count++;
            }
            sum++;
        }
        /*
        * 离线任务总进度
        * */
        String offlineTaskProgress = null;
        if(sum > 0) {
            NumberFormat numberFormat = NumberFormat.getInstance();

            numberFormat.setMaximumFractionDigits(0);

            String result = numberFormat.format((float)count/(float)sum*100);
            offlineTaskProgress = result + "%";
        }
        System.out.println("离线任务进度："+offlineTaskProgress);
        return ResultVo.success(offlineTaskProgress);
    }
    /**
    * 1.删除（取消）离线任务
    * */
    @Override
    public ResultVo enableRecordOfflineTask(RecordOfflineTaskBean recordOfflineTaskBean) {
        String url = testurl + "/Delete";
        Map<String,String> map = new HashMap<>();
        //获取设备
        RecordOfflineTaskBean offlineTaskByTaskId = recordOfflineTaskMapper.getRecordOfflineTaskByTaskId(recordOfflineTaskBean);

        String deviceId = offlineTaskByTaskId.getDeviceId();

        map.put("deviceId", deviceId);
        map.put("taskId",recordOfflineTaskBean.getTaskId());

        String fetchByPostMethod = myHttpClientPool.fetchByPostMethod(url, new StringEntity(JSON.toJSONString(map), "utf-8"));
        //获取service 返回数据
        if( fetchByPostMethod != null){
            JSONObject jsonObject = JSONObject.parseObject(fetchByPostMethod);
            JSONObject data = jsonObject.getJSONObject("data");
            if( data != null) {
                String errorCode = data.getString("errorCode");
                //任务取消 失败状态-4
                if(!errorCode.equals("SUCCESS")){
                    recordOfflineTaskBean.setEnable("4");
                    recordOfflineTaskMapper.updateRecordOfflineTaskEnableByTaskId(recordOfflineTaskBean);
                } else {
                    //任务取消，设置结束时间
                    recordOfflineTaskBean.setEnable("1");
                    recordOfflineTaskBean.setDestoryTime(new Date());
                    recordOfflineTaskMapper.updateRecordOfflineTaskEnableByTaskId(recordOfflineTaskBean);
                }
            }
        }else {
            throw new BussinessException(BizExceptionEnum.PARAM_ERROR);
        }
        return ResultVo.success();
    }
    /**
     * 开启任务下发失败，重新下发任务
     * */
    public void addRecordOfflineTaskReStart(){
        String url = testurl + "/Start";
        RecordOfflineTaskBean recordOfflineTaskBean = new RecordOfflineTaskBean();

        recordOfflineTaskBean.setEnable("2");

        List<RecordOfflineTaskBean> offlineTaskByEnable = recordOfflineTaskMapper.getRecordOfflineTaskByEnable(recordOfflineTaskBean);

        for (RecordOfflineTaskBean recordOfflineTaskBean1 : offlineTaskByEnable ) {
            Map<String,String> map = new HashMap<>();
            map.put("taskId",recordOfflineTaskBean1.getTaskId());
            map.put("deviceId", recordOfflineTaskBean1.getDeviceId());
            map.put("beginTime", recordOfflineTaskBean1.getBeginTime().toString());
            map.put("endTime", recordOfflineTaskBean1.getEndTime().toString());
            //重新发送
            StringEntity paramsEntity = new StringEntity(JSON.toJSONString(map),"UTF-8");
            String fetchByPostMethod = myHttpClientPool.fetchByPostMethod(url, paramsEntity);
            if(fetchByPostMethod != null){//HttpClinet 连接正常
                JSONObject jsonObject = JSONObject.parseObject(fetchByPostMethod);
                JSONObject data = jsonObject.getJSONObject("data");
                if(data != null){
                    String errorCode = data.getString("errorCode");
                    if(errorCode.equals("SUCCESS")){//任务下发成功
                        RecordOfflineTaskBean recordOfflineTaskBean2 = new RecordOfflineTaskBean();
                        recordOfflineTaskBean2.setEnable("0");

                        recordOfflineTaskMapper.updateRecordOfflineTaskEnableByTaskId(recordOfflineTaskBean2);
                    }
                }
            }
        }
    }
    /**
     * 查询任务下发失败，重新下发任务
     * */
    public void getRecordOfflineTaskProgressReStart(){
        String url = testurl + "/Query";
        RecordOfflineTaskBean recordOfflineTaskBean = new RecordOfflineTaskBean();
        recordOfflineTaskBean.setEnable("3");

        List<RecordOfflineTaskBean> offlineTaskByEnable = recordOfflineTaskMapper.getRecordOfflineTaskByEnable(recordOfflineTaskBean);

        List<Map<String,String>> taskVo = new ArrayList<>();
        for (RecordOfflineTaskBean recordOfflineTaskBean1 : offlineTaskByEnable ) {
            Map<String,String> map = new HashMap<>();
            map.put("taskId",recordOfflineTaskBean1.getTaskId());
            map.put("deviceId", recordOfflineTaskBean1.getDeviceId());
            taskVo.add(map);
            //重新发送
            StringEntity paramsEntity = new StringEntity(JSON.toJSONString(taskVo),"UTF-8");
            String fetchByPostMethod = myHttpClientPool.fetchByPostMethod(url, paramsEntity);
            if(fetchByPostMethod != null){//HttpClinet 连接正常
                JSONObject jsonObject = JSONObject.parseObject(fetchByPostMethod);
                JSONObject data = jsonObject.getJSONObject("data");
                if(data != null){
                    String errorCode = data.getString("errorCode");
                    if(errorCode.equals("SUCCESS")){//任务下发成功
                        //获取离线任务同步数据
                        JSONArray Item = (JSONArray) data.get("data");
                        /*
                         * 同步离线任务进度
                         * */
                        RecordOfflineTaskBean recordOfflineTaskBean2 = new RecordOfflineTaskBean();
                        if(Item.size() > 0) {
                            for(int i=0;i<Item.size();i++){
                                JSONObject jsonObject1 = Item.getJSONObject(i);
                                String taskId = (String)jsonObject1.get("taskId");
                                Integer progress = (Integer)jsonObject1.get("progress");
                                recordOfflineTaskBean2.setTaskId(taskId);
                                recordOfflineTaskBean2.setProgress(progress);
                                recordOfflineTaskBean2.setEnable("0");
                                recordOfflineTaskMapper.updateRecordOfflineTaskEnableByTaskId(recordOfflineTaskBean2);
                                recordOfflineTaskMapper.updateRecordOfflineTaskProgressByTaskId(recordOfflineTaskBean2);
                            }
                        }
                    }
                }
            }
        }
    }
    /**
     * 删除（取消）任务下发失败，重新下发任务
     * */
    public void enableRecordOfflineTaskReStart(){
        String url = testurl + "/Delete";
        RecordOfflineTaskBean recordOfflineTaskBean = new RecordOfflineTaskBean();
        recordOfflineTaskBean.setEnable("4");

        List<RecordOfflineTaskBean> offlineTaskByEnable = recordOfflineTaskMapper.getRecordOfflineTaskByEnable(recordOfflineTaskBean);

        for (RecordOfflineTaskBean recordOfflineTaskBean1 : offlineTaskByEnable ) {
            Map<String,String> map = new HashMap<>();
            map.put("taskId",recordOfflineTaskBean1.getTaskId());
            map.put("deviceId", recordOfflineTaskBean1.getDeviceId());
            //重新发送
            StringEntity paramsEntity = new StringEntity(JSON.toJSONString(map),"UTF-8");
            String fetchByPostMethod = myHttpClientPool.fetchByPostMethod(url, paramsEntity);
            if(fetchByPostMethod != null){//HttpClinet 连接正常
                JSONObject jsonObject = JSONObject.parseObject(fetchByPostMethod);
                JSONObject data = jsonObject.getJSONObject("data");
                if(data != null){
                    String errorCode = data.getString("errorCode");
                    if(errorCode.equals("SUCCESS")){//任务下发成功
                        recordOfflineTaskBean1.setDestoryTime(new Date());
                        recordOfflineTaskBean1.setEnable("1");

                        recordOfflineTaskMapper.updateRecordOfflineTaskEnableByTaskId(recordOfflineTaskBean1);
                    }
                }
            }
        }
    }

}
