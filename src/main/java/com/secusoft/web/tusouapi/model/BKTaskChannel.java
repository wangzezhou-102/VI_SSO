package com.secusoft.web.tusouapi.model;

public class BKTaskChannel{

    /**
     * 输⼊、输出通道类型  "datahub" / "rocketmq"
     */
    private String type;
    /**
     *  topic信息
     */
    private String topic;

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the topic
     */
    public String getTopic() {
        return topic;
    }

    /**
     * @param topic the topic to set
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }

    

}