/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.label;

import com.shop.cereshop.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取商家标签列表请求
 */
@Data
@ApiModel(value = "UserLabelGetAllParam", description = "获取商家标签列表请求")
public class UserLabelGetAllParam extends PageParam {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 标签名称
     */
    @ApiModelProperty(value = "标签名称")
    private Integer labelName;

    /**
     * 客户数量最小值
     */
    private Integer minPerson;

    /**
     * 客户数量最大值
     */
    private Integer maxPerson;
}
