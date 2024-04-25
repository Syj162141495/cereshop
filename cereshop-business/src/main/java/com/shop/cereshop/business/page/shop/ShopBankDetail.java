/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.shop;

import com.shop.cereshop.commons.domain.shop.CereShopBank;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 店铺收款账户数据
 */
@Data
@ApiModel(value = "ShopBankDetail", description = "店铺收款账户数据")
public class ShopBankDetail extends CereShopBank {

    /**
     * 绑定手机号
     */
    @ApiModelProperty(value = "绑定手机号")
    private String phone;
}
