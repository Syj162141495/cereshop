/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.interceptor;

import com.alibaba.fastjson.JSON;
import com.shop.cereshop.business.service.business.CereBusinessShopService;
import com.shop.cereshop.business.service.business.CerePlatformBusinessService;
import com.shop.cereshop.business.utils.ContextUtil;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.result.Result;
import com.shop.cereshop.commons.utils.EmptyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "AuthorizationInterceptor")
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    public static final String USER = "user";

    @Autowired
    private CerePlatformBusinessService cerePlatformBusinessService;

    @Autowired
    private CereBusinessShopService cereBusinessShopService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Result result=new Result();
        //设置跨域
    	HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            setHeader(httpRequest,httpResponse);
        }

        //从header中获取内部token
        String token = request.getHeader("Authorization-business");
        if(!EmptyUtils.isEmpty(token)&&token.equals("undefined")){
            token="";
        }
        //String shopId = request.getHeader("shopId");
        //如果header中不存在token，则从参数中获取token
         if(StringUtils.isBlank(token)){
            token = request.getParameter("Authorization-business");
        }

        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");

        //token为空
        if(StringUtils.isBlank(token)){
            try {
                writer = response.getWriter();
//                writer.print("token为空");
                result=new Result(CoReturnFormat.TOKEN_IS_NULL);
                writer.println(JSON.toJSONString(result).toString());
                return false;
            } catch (IOException e) {
                log.error("response error",e);
            } finally {
                if (writer != null)
                    writer.close();
            }
        }
        CerePlatformBusiness user=null;
        //查询token信息
        try {
            user=cerePlatformBusinessService.findByToken(token);
            if (user != null) {
                ContextUtil.setShopId(user.getShopId());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        request.setAttribute(USER, user);
        if(user == null){
            try {
                writer = response.getWriter();
                result=new Result(CoReturnFormat.TOKEN_APPROVE_ERROR);
                writer.println(JSON.toJSONString(result).toString());
                return false;
            } catch (IOException e) {
                log.error("response error",e);
            } finally {
                if (writer != null)
                    writer.close();
            }
        }
        return true;
    }

    /**
     * 为response设置header，实现跨域
     */
    private void setHeader(HttpServletRequest request, HttpServletResponse response){
        //跨域的header设置
        response.setHeader("Access-control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", request.getMethod());
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        //防止乱码，适用于传输JSON数据
        response.setHeader("Content-Type","application/json;charset=UTF-8");
    }
}
