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
 * 平台秒杀活动列表返回数据
 */
@Data
@ApiModel(value = "Seckill", description = "平台秒杀活动列表返回数据")
public class Seckill {

    /**
     * 平台秒杀活动id
     */
    @ApiModelProperty(value = "平台秒杀活动id")
    private Long seckillId;

    /**
     * 活动名称
     */
    @ApiModelProperty(value = "活动名称")
    private String seckillName;

    /**
     * 活动内容
     */
    @ApiModelProperty(value = "活动内容")
    private String content;

    /**
     * 活动报名时间
     */
    @ApiModelProperty(value = "活动报名时间")
    private String signTime;

    /**
     * 活动起止时间
     */
    @ApiModelProperty(value = "活动起止时间")
    private String time;

    /**
     * 参与商家数
     */
    @ApiModelProperty(value = "参与商家数")
    private Integer shops;

    /**
     * 商品数量
     */
    @ApiModelProperty(value = "商品数量")
    private Integer products;

    /**
     * 活动状态  0-报名未开始 1-报名进行中 2-活动待开始 3-活动进行中 4-活动已结束
     */
    @ApiModelProperty(value = "活动状态  0-报名未开始 1-报名进行中 2-活动待开始 3-活动进行中 4-活动已结束")
    private Integer state;
}
