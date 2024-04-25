/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.exception;

import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * 统一异常处理
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
	 * 处理异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =Exception.class)
	@ResponseBody
	public Result exceptionHandler(HttpServletRequest req, Exception e){
		log.error("", e);
		if(e instanceof CoBusinessException){
			CoBusinessException exs = (CoBusinessException) e;
			return new Result(exs.getCode(),exs);
		}
		if(e instanceof MethodArgumentNotValidException){
			MethodArgumentNotValidException exs = (MethodArgumentNotValidException) e;
			//解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
			for (FieldError error : exs.getBindingResult().getFieldErrors()) {
				return new Result(error.getDefaultMessage());
			}
		}
       	return new Result(CoReturnFormat.SYS_ERROR);
    }
}
