package com.secusoft.web.common.exception;

/**
 * @Description 所有业务异常的枚举
 *
 * @date 2016年11月12日 下午5:04:51
 */
public enum BizExceptionEnum {


    REQUEST_NULL(400, "请求有错误"),
    NO_PERMITION(403, "权限不足"),
    SERVER_ERROR(500, "服务器异常"),
	/**
	 * 登录问题
	 */
    USER_NOT_LOGIN(401,"用户未登录"),
	USER_NOT_EXISTED(600, "没有此用户"),
	ACCOUNT_FREEZED(601, "账号被冻结"),
    ACCOUNT_ERROR(602,"账号错误"),
    ACCOUNT_PWD_ERROR(603,"账号密码错误"),
    INVALID_KAPTCHA(604,"验证码不正确"),
	/**
	 * 会话相关
	 */
	SESSION_TIMEOUT(610, "会话超时"),
    SESSION_UNKNOW(611,"未知的会话异常"),

    /**
     * 文件上传
     */
    FILE_READING_ERROR(620,"FILE_READING_ERROR!"),
    FILE_NOT_FOUND(621,"FILE_NOT_FOUND!"),
    UPLOAD_ERROR(622,"上传图片出错"),

    /**
     * 数据问题
     */
    DB_RESOURCE_NULL(630,"数据库中没有该资源"),
	ERROR_WRAPPER_FIELD(640,"包装字典属性失败");




	BizExceptionEnum(int code, String message) {
		this.friendlyCode = code;
		this.friendlyMsg = message;
	}
	
	BizExceptionEnum(int code, String message,String urlPath) {
		this.friendlyCode = code;
		this.friendlyMsg = message;
		this.urlPath = urlPath;
	}

	private int friendlyCode;

	private String friendlyMsg;
	
	private String urlPath;

	public int getCode() {
		return friendlyCode;
	}

	public void setCode(int code) {
		this.friendlyCode = code;
	}

	public String getMessage() {
		return friendlyMsg;
	}

	public void setMessage(String message) {
		this.friendlyMsg = message;
	}

	public String getUrlPath() {
		return urlPath;
	}

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

}
