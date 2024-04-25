/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.tool;

import com.shop.cereshop.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_shop_seckill 获取店铺秒杀活动详情
 * @author 
 */
@Data
@ApiModel(value = "ShopSeckillGetByIdParam", description = "获取店铺秒杀活动详情请求")
public class ShopSeckillGetByIdParam extends PageParam implements Serializable {
    /**
     * 店铺秒杀活动id
     */
    @ApiModelProperty(value = "店铺秒杀活动id")
    private Long shopSeckillId;

}
