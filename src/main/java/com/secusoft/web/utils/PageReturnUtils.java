package com.secusoft.web.utils;

import com.github.pagehelper.PageInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页返回
 * @author jch
 * @version PageReturnUtils.java, v 0.1 上午 15:29 jch Exp $
 */
public class PageReturnUtils {

    public static <T> Map<String,Object> getPageMap(List<T> list, Integer pageNumber, Integer pageSize){
        PageInfo<T> pageInfo = new PageInfo<>(list);
        Map<String, Object> map = new HashMap<>(10);
        map.put("pages", pageInfo.getPages());
        map.put("total", pageInfo.getTotal());
        map.put("current", pageNumber);
        map.put("size", pageSize);
        map.put("records", list);
        return map;
    }
}
