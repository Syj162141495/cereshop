/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.group;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分组列表数据
 */
@Data
@ApiModel(value = "Group", description = "分组列表数据")
public class Group {
    /**
     * 商品分组id
     */
    @ApiModelProperty(value = "商品分组id")
    private Long shopGroupId;

    /**
     * 分组名称
     */
    @ApiModelProperty(value = "分组名称")
    private String groupName;

    /**
     * 分组描述
     */
    @ApiModelProperty(value = "分组描述")
    private String groupDescribe;

    /**
     * 商品数量
     */
    @ApiModelProperty(value = "商品数量")
    private Integer number;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private String updateTime;
}
