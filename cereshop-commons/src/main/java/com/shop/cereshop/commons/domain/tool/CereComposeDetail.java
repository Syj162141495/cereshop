/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.tool;

import com.alipay.api.domain.SkuDescInfo;
import com.alipay.api.domain.SkuInfo;
import com.shop.cereshop.commons.domain.product.CereProductSku;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 组合商品详情
 * @author
 */
@Data
public class CereComposeDetail implements Serializable {

    /**
     * 组合捆绑id
     */
    private Long composeId;

    /**
     * 组合捆绑名称
     */
    private String composeName;

    /**
     * 优惠类型
     */
    private Integer composeType;

    /**
     * 优惠值(元/折)
     */
    private BigDecimal promote;

    /**
     * 商品列表
     */
    private List<CereComposeProductInfo> composeProductInfoList;

}
