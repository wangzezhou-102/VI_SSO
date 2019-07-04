/**
 * Company
 * Copyright (C) 2004-2019 All Rights Reserved.
 */
package com.secusoft.web.websocket;

/**
 * @author Administrator
 * @version $Id WebSock.java, v 0.1 2019-05-26 19:13 Administrator Exp $$
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @ServerEndPoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端，
 * 注解的值将被用于监听用户连接的终端访问URL地址，客户端可以通过这个URL连接到websocket服务器端
 */
@ServerEndpoint("/socketServer")
@Component
public class WebSock {

    private static Logger log = LoggerFactory.getLogger(WebSock.class);

    private static int onlineCount=0;
    private static CopyOnWriteArrayList<WebSock> webSocketSet=new CopyOnWriteArrayList<WebSock>();
    private static Session session;

    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.session=session;
        webSocketSet.add(this);//加入set中
        addOnlineCount();
        log.info("有新连接加入！当前在线人数为"+getOnlineCount());
    }

    @OnClose
    public void onClose(){
        webSocketSet.remove(this);
        subOnlineCount();
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    public   void main(String[] args) {
        onMessage("xxxxxxxxxx",session);
    }

    @OnMessage
    public void onMessage(String message,Session session){
        log.info("来自客户端的消息："+message);
//        群发消息
        for (WebSock item:webSocketSet){
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    @OnError
    public  void onError(Session session,Throwable throwable){
        log.info("发生错误！");
        throwable.printStackTrace();
    }
    //   下面是自定义的一些方法
    public  void sendMessage(String message) throws IOException {
      //  this.session.getBasicRemote().sendText(message);
        try {
            if (webSocketSet.size() != 0) {
                for (WebSock s : webSocketSet) {
                    if (s != null) {
                        // 判断是否为终端信息。如果是终端信息则查询数据库获取detail
                        log.info("webSocket发送消息开始：");
                        s.session.getBasicRemote().sendText(message);
                    }
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static synchronized int getOnlineCount(){
        return onlineCount;
    }
    public static synchronized void addOnlineCount(){
        WebSock.onlineCount++;
    }
    public static synchronized void subOnlineCount(){
        WebSock.onlineCount--;
    }


}