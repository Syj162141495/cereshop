/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.app.param.price;

import com.shop.cereshop.commons.domain.common.PageParam;
import lombok.Data;

@Data
public class ShopPricePageParam extends PageParam {

    /**
     * 定价捆绑id
     */
    private Long priceId;

}
