/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.bank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 更换账户请求
 */
@Data
@ApiModel(value = "BankUpdateParam", description = "更换账户请求")
public class BankUpdateParam {

    /**
     * 关联店铺id
     */
    @ApiModelProperty(value = "关联店铺id")
    private Long shopId;

    /**
     * 持卡人姓名
     */
    @ApiModelProperty(value = "持卡人姓名")
    private String cardName;

    /**
     * 银行卡号
     */
    @ApiModelProperty(value = "银行卡号")
    private String cardNumber;

    /**
     * 所属银行（取数据字典）
     */
    @ApiModelProperty(value = "所属银行（取数据字典）")
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
