/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.business;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_business_shop 商家用户绑定店铺信息实体类
 * @author 
 */
@Data
public class CereBusinessShop implements Serializable {
    /**
     * 商家用户id
     */
    private Long businessUserId;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 是否绑定当前店铺 1-是 0-否
     */
    private Integer ifBinding;

    private static final long serialVersionUID = 1L;

}
