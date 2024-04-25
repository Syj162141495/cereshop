/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.shop;

import com.shop.cereshop.commons.domain.shop.CereShopReturn;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 修改店铺logo请求
 */
@Data
@ApiModel(value = "ShopUpdateLogoParam", description = "修改店铺logo请求")
public class ShopUpdateLogoParam {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 店铺logo
     */
    @ApiModelProperty(value = "店铺logo")
    private String shopLogo;

    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String shopName;

    /**
     * 简介
     */
    @ApiModelProperty(value = "简介")
    private String shopBrief;

    /**
     * 注册手机号
     */
    @ApiModelProperty(value = "注册手机号")
    private String shopPhone;

    /**
     * 店铺地址
     */
    @ApiModelProperty(value = "店铺地址")
    private String shopAdress;

    /**
     * 退货地址信息
     */
    @ApiModelProperty(value = "店铺地址")
    private CereShopReturn shopReturn;

}
