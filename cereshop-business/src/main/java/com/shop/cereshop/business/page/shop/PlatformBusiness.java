/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.shop;

import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商家用户返回数据实体类
 */
@Data
@ApiModel(value = "PlatformBusiness", description = "商家用户返回数据实体类")
public class PlatformBusiness extends CerePlatformBusiness {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 店铺入住处理状态 0-未处理 1-通过 2-拒绝
     */
    @ApiModelProperty(value = "店铺入住处理状态 0-未处理 1-通过 2-拒绝")
    private Integer checkState;
}
