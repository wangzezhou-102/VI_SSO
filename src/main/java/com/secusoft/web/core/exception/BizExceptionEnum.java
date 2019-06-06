package com.secusoft.web.core.exception;

/**
 * @Description 所有业务异常的枚举
 *
 * @date 2016年11月12日 下午5:04:51
 */
public enum BizExceptionEnum {

	OK(1001010,"成功"),
	REPEAT(10001011,"数据重复"),
	PARAM_NULL(10001012,"参数为空"),
	NOT_FOUND(404,"Not Found"),
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
	ERROR_WRAPPER_FIELD(640,"包装字典属性失败"),

	/**
	 *服务器问题
	 */
	SHIPIN_SERVER_ERROR(650,"视频平台服务器异常"),
	REQUEST_SERVER_ERROR(660,"服务器请求失败"),

	/**
	 * 布控任务相关
	 * @param code
	 * @param message
	 */
	TASK_DATE_WRONG(670,"开始时间不能大于结束时间"),
	TASK_DATE_NULL(680,"开始时间或结束时间不能为空"),
	TASK_NANE_NULL(690,"布控任务名称不能为空"),

	/**
	 * 布控库相关
	 */
	REPO_NAME_NULL(700,"布控库名称不能为空"),

	/**
	 * 自定义布控库相关
	 */
	PRIVATEREPO_IDENTITYNAME_NULL(710,"姓名不能为空"),
	PRIVATEREPO_IDENTITYID_NULL(720,"身份证号不能为空"),
	PRIVATEREPO_IMAGEURL_NULL(720,"图片地址不能为空"),
	PRIVATEREPO_REPOID_NULL(720,"关联的布控库不能为空"),;

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
