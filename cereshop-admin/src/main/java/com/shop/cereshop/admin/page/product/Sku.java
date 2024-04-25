/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.product;

import com.shop.cereshop.commons.domain.product.CereProductSku;
import com.shop.cereshop.commons.domain.product.CereSkuName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Sku 商品规格返回实体类
 * @author
 */
@Data
@ApiModel(value = "Sku", description = "商品规格返回实体类")
public class Sku extends CereProductSku {

    /**
     * 规格名和值数组
     */
    @ApiModelProperty(value = "规格名数组")
    private List<SkuNameValueParam> skuAttrCodeDTOList;

    /**
     * 规格属性信息
     */
    private List<CereSkuName> cereSkuNames;
}
