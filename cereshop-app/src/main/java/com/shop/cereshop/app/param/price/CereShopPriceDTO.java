/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.app.param.price;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CereShopPriceDTO {

    private Long shopId;

    private Long priceId;

    private Integer composeType;

    private BigDecimal promote;

    private Integer productId;
}
