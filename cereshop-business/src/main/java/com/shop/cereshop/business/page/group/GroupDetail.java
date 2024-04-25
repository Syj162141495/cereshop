/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.group;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 分组详情数据
 */
@Data
@ApiModel(value = "GroupDetail", description = "分组详情数据")
public class GroupDetail {
    /**
     * 商品分组id
     */
    @ApiModelProperty(value = "商品分组id")
    private Long shopGroupId;

    /**
     * 关联店铺id
     */
    @ApiModelProperty(value = "关联店铺id")
    private Long shopId;

    /**
     * 分组名称
     */
    @ApiModelProperty(value = "分组名称")
    private String groupName;

    /**
     * 分组封面图片地址
     */
    @ApiModelProperty(value = "分组封面图片地址")
    private String groupImage;

    /**
     * 分组描述
     */
    @ApiModelProperty(value = "分组描述")
    private String groupDescribe;

    /**
     * 关联商品数据
     */
    @ApiModelProperty(value = "关联商品数据")
    private List<GroupProduct> products;
}
