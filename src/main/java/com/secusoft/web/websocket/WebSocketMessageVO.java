package com.secusoft.web.websocket;

import java.io.Serializable;

/**
 * @ClassName WebSocketMessageVO
 * @Author jcyao
 * @Description
 * @Date 2018/9/10 15:02
 * @Version 1.0
 */
public class WebSocketMessageVO implements Serializable{

    private static final long serialVersionUID = -1033401171205066163L;

    private int type;

    private Object data;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
