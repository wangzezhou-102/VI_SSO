package com.secusoft.web.core.exception;

/**
 * @Description 所有业务异常的枚举
 *
 * @date 2016年11月12日 下午5:04:51
 */
public enum BizExceptionEnum {

	OK(1001010,"成功"),
	REPEAT(1001011,"数据重复"),
	PARAM_NULL(1001012,"参数为空"),
	PARAM_ERROR(1001013,"参数有误"),
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
	TASK_DATE_WRONG(701,"开始时间不能大于结束时间"),
	TASK_DATE_NULL(702,"开始时间或结束时间不能为空"),
	TASK_NANE_NULL(703,"布控任务名称不能为空"),
	TASK_NANE_REPEATED(703,"该名称已存在，请输入不存在的名称"),
	TASK_ADD_FAIL(704,"添加布控任务失败"),
	TASK_ID_NULL(705,"布控任务id为null或0"),
	TASK_START_FAIL(706,"开始布控任务失败"),
	TASK_STOP_FAIL(707,"停止布控任务失败"),
	TASK_DELETE_FAIL(708,"删除布控任务失败"),

	/**
	 * 布控库相关
	 */
	REPO_NAME_NULL(801,"布控库名称不能为空"),
	RRPO_NAME_REPEATED(802,"不能输入重复的自定义库名称"),
	RRPO_ID_EXISTED(802,"id不能为空"),

	/**
	 * 自定义布控库相关
	 */
	PRIVATEREPO_IDENTITYNAME_NULL(901,"姓名不能为空"),
	PRIVATEREPO_IDENTITYID_NULL(902,"身份证号不能为空"),
	PRIVATEREPO_IMAGEURL_NULL(903,"图片地址不能为空"),
	PRIVATEREPO_REPOID_NULL(904,"关联的布控库不能为空"),

	/**
	 * 布控目标相关
	 */
	BKMEMBER_DELETE_FAIL(1001,"布控目标删除失败"),
	BKMEMBER_ADD_FAIL(1002,"布控目标添加失败"),
	BKMEMBER_DELETE_NULL(1003,"id/objectId 为空"),
	BKMEMBER_UPDATE_FAIL(1004,"布控目标更新失败"),
	BKMEMBER_EXISTED(1005,"布控目标不存在"),;

	BizExceptionEnum(int code, String message) {
		this.friendlyCode = code;
		this.friendlyMsg = message;
	}

	BizExceptionEnum(int code, String message, String urlPath) {
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
