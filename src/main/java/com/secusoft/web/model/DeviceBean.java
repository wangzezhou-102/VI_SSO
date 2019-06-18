package com.secusoft.web.model;

public class DeviceBean extends Page {

    private static final long serialVersionUID = 1L;
    
    private String id;
    private String deviceId; //设备ID
    private String parentId; //父节点
    private String deviceName;  //设备名称
    private Integer type;     //摄像机类型：1-球机；2-半球；3-固定枪机；4-遥控枪机
    private String ip;
    private Integer port;
    private String status; //状态 ON-在线 OFF-离线
    private String longitude;//经度
    private String latitude;//纬度
    private String civilCode; //行政区划
    private String tqApi; //天擎地址
    private String source; //0-真实设备  1-自定义设备
    private Integer streamState; //码流状态 0:未启动; 1:已启动
    private String playUrl; //视频播放地址
    private String description; //描述信息
    public DeviceBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCivilCode() {
        return civilCode;
    }

    public void setCivilCode(String civilCode) {
        this.civilCode = civilCode;
    }

    public String getTqApi() {
        return tqApi;
    }

    public void setTqApi(String tqApi) {
        this.tqApi = tqApi;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getStreamState() {
        return streamState;
    }

    public void setStreamState(Integer streamState) {
        this.streamState = streamState;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    /**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DeviceBean [id=" + id + ", deviceId=" + deviceId + ", parentId=" + parentId + ", deviceName="
				+ deviceName + ", type=" + type + ", ip=" + ip + ", port=" + port + ", status=" + status
				+ ", longitude=" + longitude + ", latitude=" + latitude + ", civilCode=" + civilCode + ", tqApi="
				+ tqApi + ", source=" + source + ", streamState=" + streamState + ", playUrl=" + playUrl
				+ ", description=" + description + "]";
	}
}
