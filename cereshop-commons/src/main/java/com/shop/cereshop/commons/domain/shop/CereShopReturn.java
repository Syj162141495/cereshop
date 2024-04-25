/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_shop_return 店铺退货地址信息实体类
 * @author 
 */
@Data
@ApiModel(value = "CereShopReturn", description = "店铺退货地址信息实体类")
public class CereShopReturn implements Serializable {
    /**
     * 关联店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 退货地址
     */
    @ApiModelProperty(value = "退货地址")
    private String returnAdress;

    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人")
    private String returnPerson;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String returnPhone;

    private static final long serialVersionUID = 1L;

}
