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
 * 获取拼团活动详情
 */
@Data
@ApiModel(value = "ShopGroupWorkGetByIdParam", description = "获取拼团活动详情请求")
public class ShopGroupWorkGetByIdParam extends PageParam implements Serializable {
    /**
     * 店铺拼团活动id
     */
    @ApiModelProperty(value = "店铺拼团活动id")
    private Long shopGroupWorkId;

    private static final long serialVersionUID = 1L;

}
