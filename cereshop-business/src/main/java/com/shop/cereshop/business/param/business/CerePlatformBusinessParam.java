/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.business;

import com.shop.cereshop.commons.domain.common.PageParam;
import lombok.Data;

/**
 * 商家用户接收参数实体类
 */
@Data
public class CerePlatformBusinessParam extends PageParam {

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 验证码
     */
    private String code;

    /**
     * 是否7日内自动登录
     */
    private Boolean rememberMe;
}
