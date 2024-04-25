/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品规格会员价数据
 * @author
 */
@Data
@ApiModel(value = "MemberPrice", description = "商品规格会员价数据")
public class MemberPrice {

    /**
     * 会员等级id
     */
    @ApiModelProperty(value = "会员等级id")
    private Long memberLevelId;

    /**
     * 会员等级名称
     */
    @ApiModelProperty(value = "会员等级名称")
    private String memberLevelName;

    /**
     * 优惠方式   1-折扣 2-指定价格
     */
    @ApiModelProperty(value = "优惠方式   1-折扣 2-指定价格")
    private Integer mode;

    /**
     * 多少元/几折
     */
    @ApiModelProperty(value = "多少元/几折")
    private BigDecimal price;
}
