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
 * cere_shop_group 店铺商品分组实体类
 * @author 
 */
@Data
@ApiModel(value = "CereShopGroup", description = "店铺商品分组实体类")
public class CereShopGroup implements Serializable {
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
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private String updateTime;

    private static final long serialVersionUID = 1L;

}
