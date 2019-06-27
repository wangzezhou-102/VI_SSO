package com.secusoft.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.secusoft.web.mapper.RecordOfflineTaskMapper;
import com.secusoft.web.model.RecordOfflineTaskBean;
import com.secusoft.web.model.RecordOfflineTaskRequest;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.RecordOfflineTaskService;
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

    @Override
    @Transactional
    public ResultVo addOfflineTask(RecordOfflineTaskRequest recordOfflineTaskRequest) {

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

            recordOfflineTaskBean.setBeginTime(new Date());

            recordOfflineTaskMapper.addOfflineTask(recordOfflineTaskBean);

            map.put(deviceId,taskId);
        }
        Map<String,String> datamap = new HashMap<>();
        /*
        * 开启离线任务
        * */
        for(String deviceId : deviceIds){
            datamap.put("TaskId", map.get(deviceId));
            datamap.put("DeviceId",deviceId);
            datamap.put("BeginTime",recordOfflineTaskRequest.getBeginTime());
            datamap.put("EndTime",recordOfflineTaskRequest.getEndTime());

            StringEntity paramsEntity = new StringEntity(JSON.toJSON(datamap).toString(),"UTF-8");

            String fetchByPostMethod = myHttpClientPool.fetchByPostMethod(testurl+"/Start",paramsEntity);

            JSONObject jsonObject = JSONObject.parseObject(fetchByPostMethod);

            JSONObject data = jsonObject.getJSONObject("data");

            String errorCode = data.getString("ErrorCode");
            //任务开启失败执行
            if(!errorCode.equals("0")){

            }
        }
        return ResultVo.success();
    }

    @Override
    public ResultVo getOfflineTaskProgress(RecordOfflineTaskBean recordOfflineTaskBean) {
        List<RecordOfflineTaskBean> offlineTaskProgresses = recordOfflineTaskMapper.getOfflineTaskProgress(recordOfflineTaskBean);
        Map<String, List<String>> map = new HashMap<>();

        List<String> TaskId = new ArrayList<>();
        /*
        * 获取所有执行离线任务
        * 同步所有离线任务进度
        * */
        for (RecordOfflineTaskBean recordOfflineTaskBean1 : offlineTaskProgresses) {
            TaskId.add(recordOfflineTaskBean1.getTaskId());
        }
        map.put("TaskId",TaskId);
        StringEntity paramsEntity =new StringEntity(JSON.toJSON(map).toString(),"UTF-8");

        String fetchByPostMethod=myHttpClientPool.fetchByPostMethod(testurl+"/Query",paramsEntity);

        JSONObject jsonObject = JSONObject.parseObject(fetchByPostMethod);

        JSONObject data = jsonObject.getJSONObject("data");

        String errorCode = data.getString("ErrorCode");
        //任务查询失败执行
        if(!errorCode.equals("0")) {

        }
        //获取离线任务同步数据
        JSONArray Item = (JSONArray) data.get("Item");
        /*
        * 同步离线任务进度
        * */
        RecordOfflineTaskBean recordOfflineTaskBean2 = new RecordOfflineTaskBean();
        if(Item.size() > 0) {
            for(int i=0;i<Item.size();i++){
                JSONObject jsonObject1 = Item.getJSONObject(i);
                String taskId = (String)jsonObject1.get("TaskId");
                Integer progress = (Integer)jsonObject1.get("Progress");
                recordOfflineTaskBean2.setTaskId(taskId);
                recordOfflineTaskBean2.setProgress(progress);

                recordOfflineTaskMapper.updateOfflineTaskProgressById(recordOfflineTaskBean2);
            }
        }
        List<RecordOfflineTaskBean> offlineTaskProgresses3 = recordOfflineTaskMapper.getOfflineTaskProgress(recordOfflineTaskBean);
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

            numberFormat.setMaximumFractionDigits(2);

            String result = numberFormat.format((float)count/(float)sum*100);
            offlineTaskProgress = result + "%";
        }
        //System.out.println("离线任务进度："+offlineTaskProgress);
        return ResultVo.success(offlineTaskProgress);
    }

    @Override
    public ResultVo enableOfflineTask(RecordOfflineTaskBean recordOfflineTaskBean) {
        Map<String,String> map = new HashMap<>();

        map.put("TaskId",recordOfflineTaskBean.getTaskId());

        String fetchByPostMethod = myHttpClientPool.fetchByPostMethod(testurl+"/Delete", new StringEntity(JSON.toJSON(map).toString(), "utf-8"));
        //获取service 返回数据
        JSONObject jsonObject = JSONObject.parseObject(fetchByPostMethod);

        JSONObject data = jsonObject.getJSONObject("data");

        String errorCode = data.getString("ErrorCode");
        //任务取消失败执行
        if(!errorCode.equals("0")){


        }
        //任务取消，设置结束时间
        recordOfflineTaskBean.setEndTime(new Date());

        recordOfflineTaskMapper.updateOfflineTaskEnableById(recordOfflineTaskBean);

        return ResultVo.success(jsonObject.get("data"));

    }
}
