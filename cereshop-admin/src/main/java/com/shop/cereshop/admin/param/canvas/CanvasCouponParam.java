/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.canvas;

import com.shop.cereshop.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 画布获取平台活动请求
 */
@Data
@ApiModel(value = "CanvasCouponParam", description = "画布获取平台活动请求")
public class CanvasCouponParam extends PageParam {

    /**
     * 搜索字段
     */
    @ApiModelProperty(value = "搜索字段")
    private String search;

    /**
     * 优惠券id数组
     */
    @ApiModelProperty(value = "优惠券id数组")
    private List<Long> ids;
}
