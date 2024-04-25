/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.shop;

import lombok.Data;

/**
 * 商品分组接收参数实体类
 */
@Data
public class CereShopGroupParam {

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 搜索字段
     */
    private String search;
}
