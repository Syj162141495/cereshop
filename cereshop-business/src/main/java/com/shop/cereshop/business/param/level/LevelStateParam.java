/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.level;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 开启/关闭自购分佣请求
 */
@Data
@ApiModel(value = "LevelStateParam", description = "开启/关闭自购分佣请求")
public class LevelStateParam {

    /**
     * 是否开启自购分佣  1-是 0-否
     */
    @ApiModelProperty(value = "是否开启自购分佣  1-是 0-否")
    private Integer ifSelf;
}
