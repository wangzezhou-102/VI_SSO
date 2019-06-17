package com.secusoft.web.core.Interceptor;

import com.secusoft.web.mapper.SysOperationLogMapper;
import com.secusoft.web.model.SysOperationLog;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义拦截器
 */
@Component
public class MyInterceptor implements HandlerInterceptor {
    @Resource
    SysOperationLogMapper sysOperationLogMapper;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        // 所有请求第一个进入的方法
        RequestWrapper request = new RequestWrapper(httpServletRequest);
        String reqURL = request.getRequestURL().toString();
        String ip = request.getRemoteHost ();

        InputStream is = request.getInputStream ();
        StringBuilder responseStrBuilder = new StringBuilder ();
        BufferedReader streamReader = new BufferedReader (new InputStreamReader(is,"UTF-8"));
        String inputStr;
        while ((inputStr = streamReader.readLine ()) != null) {
            responseStrBuilder.append(inputStr);
        }
        String parmeter = responseStrBuilder.toString();
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        if (handler instanceof HandlerMethod) {
            HashMap<String, String> map = new HashMap<>();
            HandlerMethod h = (HandlerMethod) handler;

            //请求的包名
            map.put("controller",h.getBean().getClass().getName());
            //请求的源ip地址
            map.put("ip",ip);
            //请求的URI
            map.put("alluri",reqURL);
            //请求的部分uri
            map.put("uri",request.getRequestURI());
            //请求的方法名
            map.put("method",h.getMethod().getName());
            //请求的参数
            map.put("param",HttpHelper.getBodyString(httpServletRequest));
            //请求的方式
            map.put("requestMethod",request.getMethod());
            request.setAttribute("map",map);
        }


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        int status = httpServletResponse.getStatus();
        Long starttime = (Long) httpServletRequest.getAttribute("startTime");
        Long endtime = System.currentTimeMillis();
        Map<String,String> map = (Map<String, String>)httpServletRequest.getAttribute("map");
        if (map!=null){
            SysOperationLog sysOperationLog = new SysOperationLog();
            // 1成功 0请求失败
            if(status==200){
                sysOperationLog.setType(1);
            }else {
                sysOperationLog.setType(0);
            }
            if(map.get("uri").equals("/tusou_search_sort")){
                sysOperationLog.setTitle("图搜功能");
            }
            if(e!=null){
                sysOperationLog.setException(e.toString());
            }
            sysOperationLog.setRemoteAddr(map.get("ip"));
            sysOperationLog.setRequestUri(map.get("alluri"));
            sysOperationLog.setMethod(map.get("requestMethod"));
            sysOperationLog.setParam(map.get("param"));
            sysOperationLog.setResult("");
            sysOperationLog.setUserId(1);
            sysOperationLog.setTimeout(endtime-starttime);
            sysOperationLogMapper.insert(sysOperationLog);
        }
        System.out.println("success");

    }
}

