package com.secusoft.web.tusouapi.model;


public class BKTaskRocketMQInfo extends BKTaskChannel{

    /**
     * rocketmq地址 如"192.168.0.133:9876;192.168.0.134:9876"
     */
    private String address;

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    
}