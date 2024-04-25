/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.finance;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 提现银行卡返回数据实体类
 */
@Data
@ApiModel(value = "Bank", description = "提现银行卡返回数据实体类")
public class Bank {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 店铺姓名
     */
    @ApiModelProperty(value = "店铺姓名")
    private String shopName;

    /**
     * 店铺编码
     */
    @ApiModelProperty(value = "店铺编码")
    private String shopCode;

    /**
     * 银行名称
     */
    @ApiModelProperty(value = "银行名称")
    private String bankName;

    /**
     * 银行卡号
     */
    @ApiModelProperty(value = "银行卡号")
    private String bankCard;

    /**
     * 收款人姓名
     */
    @ApiModelProperty(value = "收款人姓名")
    private String collectionNme;

}
