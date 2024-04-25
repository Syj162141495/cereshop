/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.shopDistributor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 添加分销员请求
 */
@Data
@ApiModel(value = "ShopDistributorSaveParam", description = "添加分销员请求")
public class ShopDistributorSaveParam {

    /**
     * 邀请人id
     */
    @ApiModelProperty(value = "邀请人id")
    private Long invitees;

    /**
     * 分销员名称
     */
    @ApiModelProperty(value = "分销员名称")
    @NotBlank(message = "分销员名称不能为空")
    private String distributorName;

    /**
     * 分销员手机号
     */
    @ApiModelProperty(value = "分销员手机号")
    @NotBlank(message = "分销员手机号不能为空")
    private String distributorPhone;

    /**
     * 分销员等级id
     */
    @ApiModelProperty(value = "分销员等级id")
    @NotNull(message = "分销员等级id不能为空")
    private Long distributorLevelId;

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;
}
