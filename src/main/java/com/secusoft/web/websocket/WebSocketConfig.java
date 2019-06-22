/**
 * Company
 * Copyright (C) 2004-2019 All Rights Reserved.
 */
package com.secusoft.web.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author Administrator
 * @version $Id WebSocketConfig.java, v 0.1 2019-05-26 19:12 Administrator Exp $$
 */
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}