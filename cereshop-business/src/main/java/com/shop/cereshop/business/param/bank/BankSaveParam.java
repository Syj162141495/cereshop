/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.bank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 绑定账户请求
 */
@Data
@ApiModel(value = "BankSaveParam", description = "绑定账户请求")
public class BankSaveParam {

    /**
     * 关联店铺id
     */
    @ApiModelProperty(value = "关联店铺id")
    private Long shopId;

    /**
     * 持卡人姓名
     */
    @ApiModelProperty(value = "持卡人姓名")
    @NotBlank(message = "持卡人姓名不能为空")
    private String cardName;

    /**
     * 银行卡号
     */
    @ApiModelProperty(value = "银行卡号")
    @NotBlank(message = "银行卡号不能为空")
    private String cardNumber;

    /**
     * 所属银行（取数据字典）
     */
    @ApiModelProperty(value = "所属银行（取数据字典）")
    @NotNull(message = "所属银行不能为空")
    private Long bank;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码")
    private String code;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;
}
