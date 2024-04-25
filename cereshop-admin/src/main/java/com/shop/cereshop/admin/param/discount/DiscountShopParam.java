/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.discount;

import com.shop.cereshop.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 参与店铺列表请求
 */
@Data
@ApiModel(value = "DiscountShopParam", description = "参与店铺列表请求")
public class DiscountShopParam extends PageParam implements Serializable {

    /**
     * 平台限时折扣活动id
     */
    @ApiModelProperty(value = "平台限时折扣活动id")
    private Long discountId;

    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String shopName;

    /**
     * 店铺编码
     */
    @ApiModelProperty(value = "店铺编码")
    private String shopCode;

    /**
     * 审核状态 0-待审核 1-报名成功 2-报名失败 3-报名进行中(未支付)
     */
    @ApiModelProperty(value = "审核状态 0-待审核 1-报名成功 2-报名失败 3-报名进行中(未支付)")
    private Integer state;
}
