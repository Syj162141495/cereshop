/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.scene;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取详情请求
 * @author
 */
@Data
@ApiModel(value = "SceneGetByIdParam", description = "获取详情请求")
public class SceneGetByIdParam {

    /**
     * 场景营销id
     */
    @ApiModelProperty(value = "场景营销id")
    private Long sceneId;

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;
}
