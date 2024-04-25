/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_shop_bank 店铺收款账户信息实体类
 * @author 
 */
@Data
@ApiModel(value = "CereShopBank", description = "店铺收款账户信息实体类")
public class CereShopBank implements Serializable {
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

    private static final long serialVersionUID = 1L;

}
