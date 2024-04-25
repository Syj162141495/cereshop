/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.seckill;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 参与店铺数据
 */
@Data
@ApiModel(value = "SeckillShop", description = "参与店铺数据")
public class SeckillShop {

    /**
     * 报名id
     */
    @ApiModelProperty(value = "报名id")
    private Long signId;

    /**
     * 平台秒杀活动id
     */
    @ApiModelProperty(value = "平台秒杀活动id")
    private Long seckillId;

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
     * 参与商品数
     */
    @ApiModelProperty(value = "参与商品数")
    private Integer products;

    /**
     * 审核次数
     */
    @ApiModelProperty(value = "审核次数")
    private Integer examines;

    /**
     * 审核状态 0-待审核 1-报名成功 2-报名失败 3-报名进行中(未支付)
     */
    @ApiModelProperty(value = "审核状态 0-待审核 1-报名成功 2-报名失败 3-报名进行中(未支付)")
    private Integer state;
}
