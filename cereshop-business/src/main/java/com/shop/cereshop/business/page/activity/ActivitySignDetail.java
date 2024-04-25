/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.activity;

import com.shop.cereshop.commons.domain.activity.CereActivitySign;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 平台活动报名详情
 */
@Data
@ApiModel
public class ActivitySignDetail extends CereActivitySign {

    @ApiModelProperty("商品id列表")
    List<Long> productIdList;
}
