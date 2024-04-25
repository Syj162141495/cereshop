/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.tool;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cere_shop_group_work_detail 店铺拼团活动商品明细实体
 * @author 
 */
@Data
public class CereShopGroupWorkDetail implements Serializable {
    /**
     * 店铺拼团活动id
     */
    @ApiModelProperty(value = "店铺拼团活动id",required = true)
    private Long shopGroupWorkId;

    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id",required = true)
    private Long productId;

    /**
     * 规格id
     */
    @ApiModelProperty(value = "规格id",required = true)
    private Long skuId;

    /**
     * 拼团价格
     */
    @ApiModelProperty(value = "拼团价格",required = true)
    @NotNull(message = "拼团价格不能为空")
    private BigDecimal price;

}
