/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.shop;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_shop_visit 店铺访问信息实体类
 * @author 
 */
@Data
public class CereShopVisit implements Serializable {
    /**
     * 关联店铺id
     */
    private Long shopId;

    /**
     * 关联客户id
     */
    private Long buyerUserId;

    /**
     * 访问时间
     */
    private String visitTime;

    /**
     * 访问终端  1-APP 2-小程序
     */
    private Integer terminal;

    /**
     * 操作系统1-Android 2-IOS
     */
    private Integer system;

    /**
     * 所在地区
     */
    private String city;

    private static final long serialVersionUID = 1L;

}
