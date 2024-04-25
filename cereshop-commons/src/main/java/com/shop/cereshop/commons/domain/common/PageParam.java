/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页通用实体
 */
@Data
@ApiModel(value = "PageParam", description = "分页通用实体")
public class PageParam {

    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页")
    private Integer page;

    /**
     * 每页记录数
     */
    @ApiModelProperty(value = "每页记录数")
    private Integer pageSize;
}
