/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.exception;


import com.shop.cereshop.commons.constant.CoReturnFormat;
import org.redisson.api.RLock;

/**
 * 业务异常统一处理
 * 
 * @author ShenYijie
 *
 */
public class CoBusinessException extends BaseException {

	public CoBusinessException(String errorCode) {
		super(errorCode, CoReturnFormat.getMessage(errorCode));
	}

	public CoBusinessException(String errorCode, RLock redissonLock) {
		super(errorCode, CoReturnFormat.getMessage(errorCode),redissonLock);
	}

	public CoBusinessException(String errorCode, Object errorData) {
		super(errorCode, CoReturnFormat.getMessage(errorCode),errorData);
	}

	public CoBusinessException(String errorCode, Throwable t) {
		super(errorCode, CoReturnFormat.getMessage(errorCode), t);
	}

	/**
	 * 异常堆栈增加错误代码和绑定变量
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("业务异常，异常代码[").append(this.code).append("]\n");
		sb.append("异常信息:[").append(this.message).append("]\n");
		return sb.toString();
	}

}
