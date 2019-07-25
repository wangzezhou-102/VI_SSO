package com.secusoft.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.idsmanager.dingdang.jwt.DingdangUserRetriever;
import com.secusoft.web.core.support.FingerTookit;
import com.secusoft.web.core.util.StringUtils;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.SSOService;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Value;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.InvalidParameterException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


public class SSOServiceImpl implements SSOService {
    @Value("${spzn.appid}")
    private String appid;
    @Value("${spzn.appkey}")
    private String appkey;
    private FingerTookit fingerTookit;
    //保存用户信息，解析idToken
    @Override
    public ResultVo SSO(JSONObject jsonObject, HttpSession session) throws JoseException,IOException {
        String publicKey = "{\"kty\":\"RSA\",\"kid\":\"1346614912326510837\",\"alg\":\"RS256\",\"n\":\"hOdf08cku1cEddGWHjOxalfqqmrMJ5LotXT28r0pgsw82uZiSNhi4kr1qVB7z3vUeqh0TffekWxsxGc0VXGoYrPYRkkS08old8CNZQjl7AbnY179kwPilburFuMXioYO55UgvXm2mpCBL8RKGiDSORlVXruBYhxGxZ8yAaloIPVZMTIBjhKtq_fc9K1fygjR7Q3BJJkDcLU92P1Jb8_EbpvRhkHzjKi-FcXbflPWY8dMQpksInp9c-AUByVvYQD3me94yVpyOcwVNUhT5sDUOHhbWjs0gkllY86GRqIHMpNk8VDI7BiXTny-etm7AGyU0_AJlwn4JcsERCqozH7n6w\",\"e\":\"AQAB\"}";
        String idToken = JSON.toJSONString(jsonObject);
        //解析id_token
        DingdangUserRetriever retriever = new DingdangUserRetriever(idToken, publicKey);
        DingdangUserRetriever.User user = retriever.retrieve();
        String username = user.getUsername();
        //保存id_token
        session.setAttribute("idToken",idToken);
        //保存用户名
        session.setAttribute("username",username);
        return ResultVo.success();
    }
    //判断用户登录
    @Override
    public ResultVo getUserAccessToken(JSONObject jsonObject){
        //TODO
        //user_access_token(格式不确定,封装成bean,在session中获取进行判断)
        /* *//**
         * 通过username去找缓存判断该用户是否已经登陆过
         * 登陆过找到该用户对应的TGT
         * 调用logout进行注销操作
         *//*
        if(CacheUtil.users.contains(username)){
            final Collection<Ticket> ticketsInCache = this.ticketRegistry.getTickets();
            for (final Ticket ticket : ticketsInCache) {
                TicketGrantingTicket t = null;
                try {
                    t = (TicketGrantingTicketImpl)ticket;
                }catch (Exception e){
                    t = ((ServiceTicketImpl)ticket).getGrantingTicket();
                }
                if(t.getAuthentication().getPrincipal().getId().equals(username) && t.getId()!=null){
                    *//***
         * 注销方法一
         * 涉及到cookie的删除，但是无法获取response
         * 该方法有待考究
         * 未测试
         *//*
//                    centralAuthenticationService.destroyTicketGrantingTicket(t.getId());
                    *//***
         * 注销方法二
         *//*
                    t.expire();
                    //t.markTicketExpired()
                    ticketRegistry.deleteTicket(t.getId());
                    CacheUtil.users.remove(username);
                }
            }
        }*/
        return ResultVo.success();
    }
    /**
     * 获取API访问令牌
     *
     * @param challenge     用以解密mid的挑战码，可传null
     * @param mid           密文设备ID，可传null
     * @param primary_token 平台通知用户上线获得的令牌，或者TAP访问令牌
     * @param url           申请token的url
     * @return Map<String, Object> 返回信息
     */
    public ResultVo requestAPIAccessToken(String challenge, String mid, String primary_token, String url) {
        //检查参数
        if(StringUtils.isEmpty(primary_token)) {
            throw new InvalidParameterException("primary_token empty");
        }
        if(StringUtils.isEmpty(url)) {
            throw new InvalidParameterException("url empty");
        }
        //填充消息
        JSONObject jobj = new JSONObject();
        jobj.put("app_id", appid);
        jobj.put("primary_token", primary_token);
        //challenge和mid可以不传(建议传，提高安全性)。
        if(!StringUtils.isEmpty(challenge) && !StringUtils.isEmpty(mid)) {
            jobj.put("challenge", challenge);
            jobj.put("mid", mid);
        }
        //生成指纹
        String fingerprint = fingerTookit.buildFingerprint(jobj);
        jobj.put("fingerprint", fingerprint);
        HttpPost post = null;
        try {
            //https不验证证书
            HttpClient httpClient = createSSLClientDefault();
            post = new HttpPost(url);
            // 构造消息头
            post.setHeader("Content-type", "application/json; charset=utf-8");
            // 构建消息实体
            StringEntity entity = new StringEntity(jobj.toJSONString(), Charset.forName("UTF-8"));
            entity.setContentEncoding("UTF-8");
            // 发送Json格式的数据请求
            entity.setContentType("application/json");
            post.setEntity(entity);
            HttpResponse response = httpClient.execute(post);
            // 检验http返回码(返回失败递归10次，能否成功)
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_CREATED) {//TIP返回201
                String result = null;
                result = EntityUtils.toString(response.getEntity(),"UTF-8");
                JSONObject responseObj = JSONObject.parseObject(result);
                //校验指纹
                boolean b = fingerTookit.checkFingerprint(responseObj);
                System.out.println("check finger "+b);
                return ResultVo.success(responseObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (post != null) {
                try {
                    post.releaseConnection();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        //TODO return err
        return null;
    }

    public CloseableHttpClient createSSLClientDefault() {
        try {
            //使用 loadTrustMaterial() 方法实现一个信任策略，信任所有证书
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                // 信任所有
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            //NoopHostnameVerifier类:  作为主机名验证工具，实质上关闭了主机名验证，它接受任何
            //有效的SSL会话并匹配到目标主机。
            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }
    /**
     * 在http消息头传递令牌实例
     * @param userAccessToken  通过UserAccessTokenFilter得到的user_access_token
     * @param apiAccessToken   申请的TIP access_token
     */
    public void setHttpHeader(String userAccessToken,String apiAccessToken){
        HttpPost request = null;
        try {
            //HttpClient有很多，可以根据个人喜好选用
            HttpClient httpClient = HttpClients.createDefault();
            //根据http实际方法，构造HttpPost，HttpGet，HttpPut等
            request = new HttpPost();
            // 构造消息头
            //request.setHeader("Content-type", "application/json; charset=utf-8");
            // 填入双令牌
            request.setHeader("X-trustuser-access-token", userAccessToken);
            request.setHeader("X-trustagw-access-token", apiAccessToken);
            // 构建消息实体
            StringEntity entity = new StringEntity("", Charset.forName("UTF-8"));
            entity.setContentEncoding("UTF-8");
            // 发送Json格式的数据请求
            entity.setContentType("application/json");
            request.setEntity(entity);
            // 发送http请求
            HttpResponse response = httpClient.execute(request);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (request != null) {
                try {
                    request.releaseConnection();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
