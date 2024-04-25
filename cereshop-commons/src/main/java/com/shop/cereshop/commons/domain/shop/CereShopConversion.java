/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.shop;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_shop_conversion 店铺转化实体
 * @author 
 */
@Data
public class CereShopConversion implements Serializable {
    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 转化类型 1-访问人数 2-加购人数 3-结账人数 4-调用支付 5-支付成功
     */
    private Integer type;

    /**
     * 转化时间
     */
    private String createTime;

    private static final long serialVersionUID = 1L;

}
