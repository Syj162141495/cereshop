/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.price;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 定价捆绑列表
 * @author
 */
@Data
@ApiModel(value = "ShopPrice", description = "定价捆绑列表")
public class ShopPrice {

    /**
     * 定价id
     */
    @ApiModelProperty(value = "定价id")
    private Long priceId;

    /**
     * 组合名称
     */
    @ApiModelProperty(value = "组合名称")
    private String composeName;

    /**
     * 活动起始时间
     */
    @ApiModelProperty(value = "活动起始时间")
    private String time;

    /**
     * 商品数量
     */
    @ApiModelProperty(value = "商品数量")
    private Integer number;

    /**
     * 活动规则
     */
    @ApiModelProperty(value = "活动规则")
    private String rules;

    /**
     * 状态 0-未开始 1-进行中 2-已结束 3-已停用
     */
    @ApiModelProperty(value = "状态 0-未开始 1-进行中 2-已结束 3-已停用")
    private Integer state;
}
