/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.exception;


import com.shop.cereshop.commons.constant.CoReturnFormat;
import lombok.Data;
import org.redisson.api.RLock;

/**
 * 统一异常处理
 * 
 * @author Shen Yijie
 *
 */
@Data
public abstract class BaseException extends Exception {

	/**
	 */
	private static final long serialVersionUID = 7814122870859813741L;

	/**
	 * 错误代码
	 */
	protected String code = CoReturnFormat.SYS_ERROR;

	/**
	 * 错误信息（可以是包装的业务异常）
	 */
	protected String message = "系统异常，请联系系统管理员!";

	/**
	 * 错误数据返回
	 */
	protected Object errorData;

	/**
	 * 错误详细信息
	 */
	protected String detail;

	/**
	 * 分布式锁对象
	 */
	private RLock redissonLock;
	
	protected BaseException(String errorMessage) {
		super(errorMessage);
		this.message = errorMessage;
	}

	protected BaseException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.code = errorCode;
		this.message = errorMessage;
	}

	protected BaseException(String errorCode, String errorMessage,Object errorData) {
		super(errorMessage);
		this.code = errorCode;
		this.message = errorMessage;
		this.errorData= errorData;
	}

	protected BaseException(String errorCode, String errorMessage,RLock redissonLock) {
		super(errorMessage);
		this.code = errorCode;
		this.message = errorMessage;
		this.redissonLock=redissonLock;
	}

	protected BaseException(String errorCode, String errorMessage, Throwable t) {
		super(errorMessage, t);
		this.code = errorCode;
		this.message = errorMessage;
		this.detail = t.getMessage();
	}

	/**
	 * 异常堆栈增加错误代码和绑定变量
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("系统异常，异常代码[").append(this.code).append("]\n");
		sb.append("异常信息:[").append(this.message).append("]\n");
		sb.append("异常详情:[").append(this.message).append("]\n");
		sb.append(super.toString());
		return sb.toString();
	}
	
}
