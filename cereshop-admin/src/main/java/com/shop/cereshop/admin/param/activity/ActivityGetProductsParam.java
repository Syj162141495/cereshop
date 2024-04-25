/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.activity;

import com.shop.cereshop.commons.domain.common.PageParam;
import com.shop.cereshop.commons.domain.activity.CerePlatformActivityDetail;
import com.shop.cereshop.commons.utils.EmptyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 营销活动接收参数实体类
 */
@Data
@ApiModel(value = "ActivityGetProductsParam", description = "查询商品接收参数实体类")
public class ActivityGetProductsParam extends PageParam {

    /**
     * 报名id
     */
    @ApiModelProperty(value = "报名id")
    private Long signId;

}
