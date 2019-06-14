package com.secusoft.web.tusouapi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.tusouapi.TuSouClient;
import com.secusoft.web.tusouapi.model.*;
import com.secusoft.web.tusouapi.service.TuSouSearchService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TuSouSearchServiceImpl implements TuSouSearchService {

    @Override
    public BaseResponse<JSONArray> search(BaseRequest<SearchRequest> request) {
        return TuSouClient.getClientConnectionPool().fetchByPostMethod(TuSouClient.Path_SEARCH,request);
    }

    @Override
    public ResultVo sortsearch(JSONObject request) {
        String requestStr = JSON.toJSONString(request);
        String responseStr = TuSouClient.getClientConnectionPool().fetchByPostMethod(TuSouClient.Path_SEARCH, requestStr);

        SearchResponse searchResponse = JSON.parseObject(responseStr, new TypeReference<SearchResponse>() {
        });
        List<SearchData> olddata = searchResponse.getData();
        //相似度排序
        Collections.sort(olddata , new Comparator<SearchData>() {
            @Override
            public int compare(SearchData o1, SearchData o2) {
                Double score1 = o1.getScore();
                Double score2 = o2.getScore();
                if (score1<score2){
                    return 1;
                }else{
                    return -1;
                }
            }
        });
        ArrayList<SearchData> data = new ArrayList<>();
        data.addAll(olddata);

        // 时间戳排序
        if (data !=null && data.size()>1){
            Collections.sort(data , new Comparator<SearchData>() {
                @Override
                public int compare(SearchData o1, SearchData o2) {
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
        List<SearchData> dataList =olddata;
        ArrayList<String> deviceIds = new ArrayList<>();
        Set<String> set = new HashSet<String>();
        Map<String,List> resultMap = new LinkedHashMap<String,List>();
        dataList.forEach(searchData -> {
            SearchSource source = searchData.getSource();
            String cameraId = source.getCameraId();
            if (set.contains(cameraId)){
                resultMap.get(cameraId).add(searchData);
            }else {
                set.add(cameraId);
                List<SearchData> list1 = new ArrayList<>();
                list1.add(searchData);
                resultMap.put(cameraId,list1);
            }
        });
        HashMap<String, Object> stringSearchDataHashMap = new HashMap<>();
        stringSearchDataHashMap.put("score",olddata);
        stringSearchDataHashMap.put("timestamp",data);
        stringSearchDataHashMap.put("device",resultMap);

        Long totalCount = searchResponse.getTotalCount();
        return ResultVo.success(stringSearchDataHashMap,totalCount);
    }
}
