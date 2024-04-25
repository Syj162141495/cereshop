/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 店铺报名审核记录
 */
@Data
@ApiModel(value = "SignExamineLog", description = "店铺报名审核记录")
public class SignExamineLog implements Serializable {
    /**
     * 操作功能描述
     */
    @ApiModelProperty(value = "操作功能描述")
    private String operationDescribtion;

    /**
     * 操作人姓名
     */
    @ApiModelProperty(value = "操作人姓名")
    private String name;

    /**
     * 其他信息
     */
    @ApiModelProperty(value = "其他信息")
    private String remark;

    /**
     * 操作时间
     */
    @ApiModelProperty(value = "操作时间")
    private String createTime;

    private static final long serialVersionUID = 1L;

}
