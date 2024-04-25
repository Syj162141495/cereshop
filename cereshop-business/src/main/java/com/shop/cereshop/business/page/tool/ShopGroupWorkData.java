/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.tool;

import com.shop.cereshop.commons.domain.common.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 拼团活动数据效果
 */
@Data
@ApiModel(value = "ShopGroupWorkData", description = "拼团活动数据效果返回数据")
public class ShopGroupWorkData implements Serializable {
    /**
     * 拼团活动名称
     */
    @ApiModelProperty(value = "优惠券名称")
    private String groupName;

    /**
     * 成团数量
     */
    @ApiModelProperty(value = "成团数量")
    private Integer total;

    /**
     * 拉新转化数
     */
    @ApiModelProperty(value = "拉新转化数")
    private Integer conversion;

    /**
     * 活动售出金额
     */
    @ApiModelProperty(value = "活动售出金额")
    private BigDecimal money;

    /**
     * 使用优惠券购买的商品明细数据
     */
    @ApiModelProperty(value = "使用优惠券购买的商品明细数据")
    private Page<CouponProduct> products;

    private static final long serialVersionUID = 1L;

}
