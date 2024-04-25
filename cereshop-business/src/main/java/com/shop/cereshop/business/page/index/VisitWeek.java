/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.index;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 每周访问人数实体类
 */
@Data
@ApiModel(value = "VisitWeek", description = "每周访问人数实体类")
public class VisitWeek {

    /**
     * 周时间
     */
    @ApiModelProperty(value = "周时间")
    private List<String> time;

    /**
     * 访问人数
     */
    @ApiModelProperty(value = "访问人数")
    private List<Integer> total;
}
