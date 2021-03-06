package com.wuhenjian.aurora.utils.constant;

/**
 * @author 無痕剑
 * @date 2017/12/12 15:48
 */
public class CommonContant {
	/**
	 * Token缓存前缀名称
	 * <p>redis缓存关键字组成：</p>
	 * <p>类型-用户账号-缓存关键字</p>
	 * <p>例如：M-1387593084-MfSdfhGFK28sdiS4348JD7sdDYJ</p>
	 */
	public final static String SEPARATOR = "-";
	/** 用户 */
	public final static String MEMBER_REDIS_PRE = "M";
	/** Token缓存时间 */
	public final static int TOKEN_EXPIRE = 60;
	/** SQL查询的redis缓存 */
	public final static String SQL_REDIS_PRE = "S";
	/** 请求中的用户信息key */
	public final static String REQUEST_MEMBER_INFO = "mi";
	/** 登录类型-手机 */
	public final static String LOGIN_TYPE_PHONE = "phone";
	/** 登录类型-邮箱 */
	public final static String LOGIN_TYPE_EMAIL = "service";
	/** 验证码进制 */
	public final static int CAPTCHA_RADIX = 36;
	/** 账号已存在 */
	public final static Integer ACCOUNT_EXIST = 1;
	/** 账号不存在 */
	public final static Integer ACCOUNT_NOT_EXIST = 2;
	/** 数据库 */
	public final static String SQL = "/sql";
	public final static String NO_SQL = "/nosql";
}
