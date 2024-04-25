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
 * 删除分销员等级请求
 */
@Data
@ApiModel(value = "LevelDeleteParam", description = "删除分销员等级请求")
public class LevelDeleteParam {

    /**
     * 分销员等级id
     */
    @ApiModelProperty(value = "分销员等级id")
    private Long distributorLevelId;
}
