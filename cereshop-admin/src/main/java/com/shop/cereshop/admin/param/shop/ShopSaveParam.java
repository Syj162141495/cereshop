/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 添加商家请求
 */
@Data
@ApiModel(value = "ShopSaveParam", description = "添加商家请求")
public class ShopSaveParam {

    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    @NotBlank(message = "店铺名称不能为空")
    private String shopName;

    /**
     * 店铺账号
     */
    @ApiModelProperty(value = "店铺账号")
    @NotBlank(message = "店铺账号不能为空")
    private String shopPhone;

    /**
     * 店铺密码
     */
    @ApiModelProperty(value = "店铺密码")
    @NotBlank(message = "店铺密码不能为空")
    private String shopPassword;

    /**
     * 店铺负责人
     */
    @ApiModelProperty(value = "店铺负责人")
    @NotBlank(message = "店铺负责人不能为空")
    private String chargePersonName;

    /**
     * 负责人电话
     */
    @ApiModelProperty(value = "负责人电话")
    @NotBlank(message = "负责人电话不能为空")
    private String chargePersonPhone;

    /**
     * 店铺地址
     */
    @ApiModelProperty(value = "店铺地址")
    @NotBlank(message = "店铺地址不能为空")
    private String shopAdress;

    /**
     * 生效日期
     */
    @ApiModelProperty(value = "生效日期")
    private String effectiveDate;

    /**
     * 生效时限（年）
     */
    @ApiModelProperty(value = "生效时限（年）")
    private Integer effectiveYear;

    /**
     * 合同状态 1-有效 0-无效
     */
    @ApiModelProperty(value = "合同状态 1-有效 0-无效")
    private Integer contractState;
}
