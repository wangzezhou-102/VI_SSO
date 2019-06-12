package com.secusoft.web.serviceapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.secusoft.web.config.NormalConfig;
import com.secusoft.web.tusouapi.model.BaseRequest;
import com.secusoft.web.tusouapi.model.BaseResponse;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.net.ConnectException;

@Component
@Configurable
public class ServiceClient {
    private static Logger log = LoggerFactory.getLogger(ServiceClient.class);

    private String addrApiService = NormalConfig.getAddrApiService();

//    //API 资源路径
//    //布控库信息查询
//    public static final String Path_BKREPO_META = serviceApiConfig.getPathBkrepoMeta();
//    //布控库创建
//    public static final String Path_BKREPO_CREATE = "/bkrepocreate";
//    //添加布控目标
//    public static final String Path_BKMEMBER_ADD = "/bkmemberadd";
//    //删除布控目标
//    public static final String Path_BKMEMBER_DELETE = "/bkmemberdelete";

    private volatile static ServiceClient HttpClientConnectionPool;

    private static final String USERAGENT = "SZ-JAVA";
    private static final String CHARSET = "UTF-8";
    private static final int MAX_TOTAL_CONNECTIONS = 50;
    private static final int MAX_ROUTE_CONNECTIONS = 20;
    private static final int CONNECT_TIMEOUT = 5000; // 连接时间
    private static final int SOCKET_TIMEOUT = 60000; // 获取内容时间

    private static PoolingHttpClientConnectionManager cm = null;
    private static CloseableHttpClient httpclient;


    /**
     * 初始化连接池
     */
    static {
        try {
            cm = new PoolingHttpClientConnectionManager();
            cm.setMaxTotal(MAX_TOTAL_CONNECTIONS);
            cm.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);// 默认设置为2

            // 客户端请求的默认设置
            RequestConfig defaultRequestConfig = RequestConfig.custom()
                    .setSocketTimeout(SOCKET_TIMEOUT)
                    .setConnectTimeout(CONNECT_TIMEOUT)
                    .setConnectionRequestTimeout(CONNECT_TIMEOUT)
                    .setRedirectsEnabled(false)
                    .setCookieSpec(CookieSpecs.STANDARD_STRICT)
                    .build();

            // 请求重试处理
            HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
                public boolean retryRequest(IOException exception,
                                            int executionCount, HttpContext context) {
                    if (executionCount >= 2) {// 如果超过最大重试次数，那么就不要继续了
                        return false;
                    }

                    if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                        return true;
                    }

                    if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                        return false;
                    }
                    HttpRequest request = (HttpRequest) context.getAttribute(HttpClientContext.HTTP_REQUEST);
                    boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                    if (idempotent) {// 如果请求被认为是幂等的，那么就重试
                        return true;
                    }

                    return false;
                }

            };

            httpclient = HttpClients.custom()
                    .setConnectionManager(cm)
                    .setDefaultRequestConfig(defaultRequestConfig)
                    .setRetryHandler(httpRequestRetryHandler)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ServiceClient() {
    }

    /**
     * 获取HttpClientConnectionPool对象，这是单例方法
     *
     * @return
     */
    public static ServiceClient getClientConnectionPool() {
        if (HttpClientConnectionPool == null) {
            synchronized (ServiceClient.class) {
                if (HttpClientConnectionPool == null) {
                    HttpClientConnectionPool = new ServiceClient();
                }
            }
        }
        return HttpClientConnectionPool;
    }

    /**
     * 获取HttpClient。在获取之前，确保HttpClientConnectionPool对象已创建。
     *
     * @return
     */
    public static CloseableHttpClient getHttpClient() {
        return httpclient;
    }


    /**
     * 关闭整个连接池
     */
    public static void close() {
        if (cm != null) {
            cm.shutdown();
        }
        if (httpclient != null) {
            try {
                httpclient.close();
            } catch (IOException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }


    /**
     * Post方法封装，发送post请求，获取响应内容
     *
     * @param url
     * @param jsonStr
     * @return
     */
    public String fetchByPostMethod(String url, String jsonStr) {
        System.out.println("addrApiService:" + addrApiService);
        String resultStr = null;
        HttpPost httpPost = new HttpPost(addrApiService + url);
        httpPost.setEntity(new StringEntity(jsonStr, ContentType.APPLICATION_JSON));
        httpPost.addHeader(HttpHeaders.USER_AGENT, USERAGENT);
//		httpPost.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
//		httpPost.addHeader(HttpHeaders.ACCEPT,"Application/json");
//		httpPost.addHeader(HttpHeaders.CONNECTION,"keep-alive");

        HttpResponse response;
        try {
            response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            resultStr = EntityUtils.toString(entity, CHARSET);
            EntityUtils.consume(entity);
        } catch (ConnectException ce) {//服务器请求失败
            log.error(ce.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            httpPost.abort();
        }
        return resultStr;
    }

    public BaseResponse fetchByPostMethod(String url, BaseRequest request) {
        String requestStr = JSON.toJSONString(request);
        String responseStr = fetchByPostMethod(url, requestStr);
        BaseResponse<JSONArray> response = new BaseResponse<>();
        response = JSON.parseObject(responseStr, (Class<BaseResponse<JSONArray>>) response.getClass());
        return response;

    }
}
