/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.label;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商家标签数据
 * @author
 */
@Data
@ApiModel(value = "ShopUserLabel", description = "商家标签数据")
public class ShopUserLabel {

    /**
     * 标签id
     */
    @ApiModelProperty(value = "标签id")
    private Long labelId;

    /**
     * 标签名称
     */
    @ApiModelProperty(value = "标签名称")
    private String labelName;

    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息")
    private String remark;

    /**
     * 客户数量
     */
    @ApiModelProperty(value = "客户数量")
    private Integer person;
}
