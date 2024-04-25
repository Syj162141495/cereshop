/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.app.config;

import com.shop.cereshop.app.interceptor.AuthorizationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
//@ImportResource(value = "classpath:service/service.xml")
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/after/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/bank/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/receive/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/cart/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/distributor/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/order/getSettlement");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/order/submit");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/order/getUrl");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/order/checkPay");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/order/getAll");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/order/getById");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/order/gotoPay");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/order/returnExpress");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/order/getExpressSelect");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/order/gotoAppPay");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/order/gotoH5Pay");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/order/confirm");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/order/cancel");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/order/delete");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/order/addComment");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/order/addToComment");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/order/refund");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/order/getDilevery");
        //registry.addInterceptor(authorizationInterceptor).addPathPatterns("/order/pay");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/order/getOrderPolite");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/coupon/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/user/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/comment/getAll");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/comment/getCommentList");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/comment/like");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/collect/*");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/footprint/*");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/seckill/addProblem");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/seckill/addAnswer");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/seckill/selectedProblem");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/seckill/deleteProblem");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/seckill/selectedAnswer");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/seckill/deleteAnswer");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/work/getSettlement");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/work/getInvite");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/check/updatePersonalCheck");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/check/updateIndividualCheck");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/check/updateEnterpriseCheck");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/check/updateOrganizationsCheck");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/shop/getSharePic");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/member/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/notice/removeById");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/notice/readNotice");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/notice/readAll");
    }

}
