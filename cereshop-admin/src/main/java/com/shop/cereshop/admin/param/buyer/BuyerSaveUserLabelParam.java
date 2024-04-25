/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.buyer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 贴标签请求
 */
@Data
@ApiModel(value = "BuyerSaveUserLabelParam", description = "贴标签请求")
public class BuyerSaveUserLabelParam {

    /**
     * 关联客户id
     */
    @ApiModelProperty(value = "关联客户id")
    private Long buyerUserId;

    /**
     * 关联平台标签id数组
     */
    @ApiModelProperty(value = "关联平台标签id数组")
    private List<Long> buyerLabelIds;
}
