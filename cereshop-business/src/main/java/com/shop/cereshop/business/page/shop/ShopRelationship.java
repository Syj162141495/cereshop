/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 关系设置返回数据实体类
 */
@Data
@ApiModel(value = "ShopRelationship", description = "关系设置返回数据实体类")
public class ShopRelationship {

    /**
     * 客户id
     */
    @ApiModelProperty(value = "客户id")
    private Long buyerUserId;

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 客户昵称
     */
    @ApiModelProperty(value = "客户昵称")
    private String name;

    /**
     * 客户手机号
     */
    @ApiModelProperty(value = "客户手机号")
    private String phone;

    /**
     * 分销员id
     */
    @ApiModelProperty(value = "分销员id")
    private Long distributorId;

    /**
     * 分销员昵称
     */
    @ApiModelProperty(value = "分销员昵称")
    private String distributorName;

    /**
     * 分销员手机号
     */
    @ApiModelProperty(value = "分销员手机号")
    private String distributorPhone;

    /**
     * 绑定状态
     */
    @ApiModelProperty(value = "绑定状态")
    private Integer ifBind;

    /**
     * 关系更新时间
     */
    @ApiModelProperty(value = "关系更新时间")
    private String updateTime;
}
