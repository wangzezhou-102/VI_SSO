package com.secusoft.web.websocket;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@EnableScheduling
public class Task {


    @Autowired
    WebSock webSock;

    //@Scheduled(cron="0/20 * * * * ?")
    private void sendtoClient() throws IOException {
//        TCarSurveyAlarm tCarSurveyAlarm=new TCarSurveyAlarm();
//        tCarSurveyAlarm.setVehicleResultId("802393");
//        tCarSurveyAlarm.setImageUrl("www.baidu.com");
//        tCarSurveyAlarm.setLocaltionName("00010002003");
//        tCarSurveyAlarm.setCarSurveyId(1);
//        tCarSurveyAlarm.setProcess_state(1);
//        tCarSurveyAlarm.setRemark("弯道超速");
//        tCarSurveyAlarmService.insert(tCarSurveyAlarm);
//        SocketServer.sendAll(JSON.toJSONString(trafficEvent));


//        TrafficEvent trafficEvent = new TrafficEvent();
//        trafficEvent.setId(UUID.randomUUID().toString());
//        trafficEvent.setTitle(list.get(0));
//        trafficEvent.setLocalDesc(list.get(1));
//        trafficEvent.setReportTime(DateUtil.parseTime(list.get(2)));
//        trafficEvent.setImageUrl(list.get(3));
//        trafficEvent.setDeviceId(list.get(4));

        /**
         *   select id,
         * 			 deal_status_id dealStatus,
         * 			 title,
         * 			 local_desc as localDesc,
         * 			 report_time as reportTime
         *       from
         *           t_traffic_event
         *           order by reportTime desc limit 0,1
         */

        Map<String,Object> map = new HashMap<>();
        map.put("dealStatus", "123");
        map.put("title","路面异常事件");
        map.put("reportTime","2019-05-27 15:15:15");
        map.put("drivewayType", "3123");
        map.put("imgUrl","www.baidu.com");
        map.put("deviceId","33011701001310700005");
        map.put("roadType", "#2112");
        map.put("local_desc","滨江区浦沿路");
//
        Map<String,Object> map1=new HashMap<>();
        map1.put("id","121222");
        map1.put("carid","浙ALT593");
        map1.put("Type","两客一危");
        map1.put("localtion","滨江区浦沿路");
        map1.put("deviceid","11010101001310700008");
        map1.put("remark","违规停放");
        map1.put("starttime","2019-05-19 15:15:15");

        WebSocketMessageVO webSocketMessageVO=new WebSocketMessageVO();
        webSocketMessageVO.setData(map);
        webSocketMessageVO.setType(1);

        WebSocketMessageVO webSocketMessageVO1=new WebSocketMessageVO();
        webSocketMessageVO1.setData(map1);
        webSocketMessageVO1.setType(2);

        webSock.sendMessage(JSON.toJSONString(webSocketMessageVO));
        webSock.sendMessage(JSON.toJSONString(webSocketMessageVO1));
//        trafficEventService.insert(trafficEvent);
    }






}
