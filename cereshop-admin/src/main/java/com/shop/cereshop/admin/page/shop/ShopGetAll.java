/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.shop;

import com.shop.cereshop.commons.domain.shop.CerePlatformShop;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商家列表
 */
@Data
@ApiModel(value = "ShopGetAll", description = "商家列表")
public class ShopGetAll extends CerePlatformShop {

    /**
     * 商家用户id
     */
    @ApiModelProperty(value = "商家用户id")
    private Long businessUserId;
}
