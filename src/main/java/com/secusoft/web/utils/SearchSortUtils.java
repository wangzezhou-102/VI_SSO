package com.secusoft.web.utils;

import com.secusoft.web.tusouapi.model.SearchResponseData;
import com.secusoft.web.tusouapi.model.SearchSource;

import java.util.*;

/**
 * @author huanghao
 * @date 2019-06-28
 */
public class SearchSortUtils {
    /**
     * 时间戳排序
     * @param olddata
     * @return
     */
    public static List<SearchResponseData> timeStampSort(List<SearchResponseData> olddata){
        if (olddata !=null && olddata.size()>1){
            Collections.sort(olddata , new Comparator<SearchResponseData>() {
                @Override
                public int compare(SearchResponseData o1, SearchResponseData o2) {
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
        return olddata;
    }

    /**
     * 相似度排序
     * @param olddata
     * @return
     */
    public static List<SearchResponseData> scoreSort(List<SearchResponseData> olddata){
        Collections.sort(olddata , new Comparator<SearchResponseData>() {
            @Override
            public int compare(SearchResponseData o1, SearchResponseData o2) {
                Double score1 = o1.getScore();
                Double score2 = o2.getScore();
                if (score1<score2){
                    return 1;
                }else{
                    return -1;
                }
            }
        });
        return olddata;
    }

    /**
     * 设备分组排序
     * @param olddata
     * @return
     */
    public static Map<String,List> deviceSort(List<SearchResponseData> olddata){
        List<SearchResponseData> dataList =olddata;
        ArrayList<String> deviceIds = new ArrayList<>();
        Set<String> set = new HashSet<String>();
        Map<String,List> resultMap = new LinkedHashMap<String,List>();
        dataList.forEach(searchResponseData -> {
            SearchSource source = searchResponseData.getSource();
            String cameraId = source.getCameraId();
            if (set.contains(cameraId)){
                resultMap.get(cameraId).add(searchResponseData);
            }else {
                set.add(cameraId);
                List<SearchResponseData> list1 = new ArrayList<>();
                list1.add(searchResponseData);
                resultMap.put(cameraId,list1);
            }
        });
        return resultMap;
    }


}
