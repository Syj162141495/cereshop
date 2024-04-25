/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 活动参与店铺返回数据实体类
 */
@Data
@ApiModel(value = "ShopActivity", description = "活动参与店铺返回数据实体类")
public class ShopActivity {

    /**
     * 报名id
     */
    @ApiModelProperty(value = "报名id")
    private Long signId;

    /**
     * 活动id
     */
    @ApiModelProperty(value = "活动id")
    private Long activityId;

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String shopName;

    /**
     * 店铺编码
     */
    @ApiModelProperty(value = "店铺编码")
    private String shopCode;

    /**
     * 审核状态 0-待审核 1-报名成功 2-报名失败
     */
    @ApiModelProperty(value = "审核状态 0-待审核 1-报名成功 2-报名失败")
    private Integer state;

    /**
     * 参与商品数
     */
    @ApiModelProperty(value = "参与商品数")
    private Integer productNumber;

    /**
     * 审核次数
     */
    @ApiModelProperty(value = "审核次数")
    private Integer examine;
}
