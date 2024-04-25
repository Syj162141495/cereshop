/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.config;

import com.shop.cereshop.business.interceptor.AuthorizationInterceptor;
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
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/achievement/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/activity/getAll");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/activity/save");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/activity/checkPay");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/activity/getById");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/activity/getProducts");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/activity/getGroups");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/activity/getActivitySignDetail");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/after/getAll");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/after/getById");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/after/refundSuccess");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/after/refundRefuse");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/after/refundDilevery");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/after/success");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/after/refuse");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/after/confirmAndRefund");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/after/damaging");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/banner/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/level/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/distributor/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/distributor_order/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/express/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/finance/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/index/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/logistics/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/tool/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/order/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/product/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/ship/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/bank/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/shop/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/extension/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/group/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/shop_label/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/recruit/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/dict/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/role/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/permission/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/user/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/canvas/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/coupon/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/discount/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/work/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/seckill/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/user_label/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/buyer/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/crowd/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/operate/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/renovation/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/business/updatePassword");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/business/updateAvatar");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/business/build");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/compose/*");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/price/*");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/scene/*");
    }

}
