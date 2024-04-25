/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.shop;

import com.shop.cereshop.commons.domain.common.PageParam;
import lombok.Data;


/**
 * 店铺标签接收参数实体类
 */
@Data
public class CereShopLabelParam extends PageParam {

    /**
     * 标签id
     */
    private Long labelId;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 标签名称
     */
    private String labelName;

}
