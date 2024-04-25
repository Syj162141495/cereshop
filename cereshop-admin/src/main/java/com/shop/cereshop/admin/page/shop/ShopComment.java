/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评论返回数据实体类
 */
@Data
@ApiModel(value = "ShopComment", description = "评论返回数据实体类")
public class ShopComment {

    /**
     * 评论id
     */
    @ApiModelProperty(value = "评论id")
    private Long commentId;

    /**
     * 商家名称
     */
    @ApiModelProperty(value = "商家名称")
    private String shopName;

    /**
     * 商家编码
     */
    @ApiModelProperty(value = "商家编码")
    private String shopCode;

    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id")
    private String productId;

    /**
     * 评论人
     */
    @ApiModelProperty(value = "评论人")
    private String name;

    /**
     * 评论日期
     */
    @ApiModelProperty(value = "评论日期")
    private String createTime;

    /**
     * 是否隐藏 1-是 0-否
     */
    @ApiModelProperty(value = "是否隐藏 1-是 0-否")
    private Integer state;
}
