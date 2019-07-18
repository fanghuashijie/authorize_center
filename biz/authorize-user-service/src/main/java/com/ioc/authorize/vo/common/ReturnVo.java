package com.ioc.authorize.vo.common;

import java.io.Serializable;

public class ReturnVo implements Serializable {

	/**常用应答码定义：业务处理成功 */
	public static final int CODE_SUCCESS = 200;

	/**常用应答码定义：业务需要重定向 */
	public static final int CODE_REDIRECT = 302;

	/**常用应答码定义：没有权限 */
	public static final int CODE_FORBIDDEN = 403;

	/**常用应答码定义：请求的服务或版本号不存在 */
	public static final int CODE_NOT_FOUND = 404;

	/**常用应答码定义：业务处理失败 */
	public static final int CODE_EXCEPTION = 500;

	/** 接口实现 */
	public static final int CODE_NOT_IMPLEMENTATION = 501;

	/**
	 * 响应编码
	 */
	private Integer code;

	/**
	 * 响应消息
	 */
	private String msg;

	/**
	 * 返回对象
	 */
	private Object data;

	/**
	 * 请求重定向地址
	 */
	private String redirectUrl;


	public ReturnVo() {
		super();
	}
	
	public ReturnVo(Integer code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public ReturnVo(Integer code, String msg, Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public ReturnVo(Integer code, String msg, Object data, String redirectUrl) {
		this.code = code;
		this.msg = msg;
		this.data = data;
		this.redirectUrl = redirectUrl;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
}


