/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.app.page.settlement;

import com.shop.cereshop.app.page.product.ProductCoupon;
import com.shop.cereshop.commons.domain.buyer.CereBuyerReceive;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 结算查询返回数据
 */
@Data
@ApiModel(value = "Settlement", description = "结算查询返回数据")
public class Settlement {

    /**
     * 收货地址信息
     */
    @ApiModelProperty(value = "收货地址信息")
    private CereBuyerReceive receive;

    /**
     * 商品明细数据
     */
    @ApiModelProperty(value = "商品明细数据")
    private List<SettlementShop> shops;

    /**
     * 置灰商品数据
     */
    @ApiModelProperty(value = "商品明细数据")
    private List<SettlementShop> invalidShops;

    /**
     * 平台优惠券数据
     */
    @ApiModelProperty(value = "平台优惠券数据")
    private List<ProductCoupon> coupons;

    /**
     * 花呗手续费支付方式 1-商户支付 2-用户支付
     */
    @ApiModelProperty(value = "花呗手续费支付方式 1-商户支付 2-用户支付")
    private Integer huabeiChargeType;

    /**
     * 花呗手续费比例列表 3期 6期 12期
     */
    @ApiModelProperty(value = "花呗手续费比例列表 3期 6期 12期")
    private List<Double> huabeiFeerateList;
}
