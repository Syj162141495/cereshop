/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.buyer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_buyer_bank 客户绑定银行卡实体
 * @author 
 */
@Data
@ApiModel(value = "CereBuyerBank", description = "客户绑定银行卡实体")
public class CereBuyerBank implements Serializable {

    /**
     * 银行卡id
     */
    @ApiModelProperty(value = "银行卡id")
    private Long bankId;

    /**
     * 客户id
     */
    @ApiModelProperty(value = "客户id")
    private Long buyerUserId;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 银行名称
     */
    @ApiModelProperty(value = "银行名称")
    private String bankName;

    /**
     * 卡号
     */
    @ApiModelProperty(value = "卡号")
    private String bankCard;

    private static final long serialVersionUID = 1L;
}
