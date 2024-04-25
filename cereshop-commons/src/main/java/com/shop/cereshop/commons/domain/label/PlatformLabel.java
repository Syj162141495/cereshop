/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.label;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 标签返回数据实体类
 */
@Data
@ApiModel(value = "PlatformLabel", description = "标签返回数据实体类")
public class PlatformLabel extends CerePlatformLabel {

    /**
     * 条件
     */
    @ApiModelProperty(value = "条件")
    private List<String> conditions;

    /**
     * 多选框条件数组
     */
    @ApiModelProperty(value = "多选框条件数组")
    private List<Integer> consumptions;

    /**
     * 客户数
     */
    @ApiModelProperty(value = "客户数")
    private Integer users;
}
