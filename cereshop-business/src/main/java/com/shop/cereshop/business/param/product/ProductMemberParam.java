/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.product;

import com.shop.cereshop.business.page.member.MemberPrice;
import com.shop.cereshop.business.page.member.ProductMember;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.lang.reflect.Member;
import java.math.BigDecimal;
import java.util.List;

/**
 * 设置商品会员价请求
 * @author
 */
@Data
@ApiModel(value = "ProductMemberParam", description = "设置商品会员价请求")
public class ProductMemberParam {

    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id")
    private Long productId;

    /**
     * 商品会员数据明细
     */
    @ApiModelProperty(value = "商品id")
    private List<ProductMember> members;
}
