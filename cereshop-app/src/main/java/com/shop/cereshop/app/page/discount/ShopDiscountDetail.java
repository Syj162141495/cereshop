/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.app.page.discount;

import com.shop.cereshop.app.page.tool.ToolProduct;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * cere_shop_discount 店铺限时折扣活动实体
 * @author 
 */
@Data
@ApiModel(value = "ShopDiscountDetail", description = "店铺限时折扣活动详情返回数据")
public class ShopDiscountDetail implements Serializable {

    /**
     * 店铺限时折扣活动id
     */
    @ApiModelProperty(value = "店铺限时折扣活动id")
    private Long shopDiscountId;

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 活动名称
     */
    @ApiModelProperty(value = "活动名称")
    private String discountName;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 活动开始时间
     */
    @ApiModelProperty(value = "活动开始时间")
    private String startTime;

    /**
     * 活动结束时间
     */
    @ApiModelProperty(value = "活动结束时间")
    private String endTime;

    /**
     * 商品限购 1-不限购 2-限购
     */
    @ApiModelProperty(value = "商品限购 1-不限购 2-限购")
    private Integer ifLimit;

    /**
     * 限购几件/人
     */
    @ApiModelProperty(value = "限购几件/人")
    private Integer limitNumber;

    /**
     * 是否限量 1-是 0-否
     */
    @ApiModelProperty(value = "是否限量 1-是 0-否",required = true)
    private Integer ifNumber;

    /**
     * 限量数量
     */
    @ApiModelProperty(value = "限量数量")
    private Integer number;

    /**
     * 活动预热   1-停用  2-启用
     */
    @ApiModelProperty(value = "活动预热   1-停用  2-启用")
    private Integer ifEnable;

    /**
     * 预热几小时前
     */
    @ApiModelProperty(value = "预热几小时前")
    private Integer enableTime;

    /**
     * 优惠券是否叠加 1-是 0-否
     */
    @ApiModelProperty(value = "优惠券是否叠加 1-是 0-否")
    private Integer ifAdd;

    /**
     * 限时折扣活动状态 0-未开始 1-进行中 2-已结束
     */
    @ApiModelProperty(value = "限时折扣活动状态 0-未开始 1-进行中 2-已结束")
    private Integer state;

    /**
     * 商品明细数据
     */
    @ApiModelProperty(value = "商品明细数据")
    private List<ToolProduct> products;

    private static final long serialVersionUID = 1L;
}
