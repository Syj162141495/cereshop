/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.compose;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

/**
 * 组合捆绑列表
 * @author
 */
@Data
@ApiModel(value = "ShopCompose", description = "组合捆绑列表")
public class ShopCompose {

    /**
     * 组合id
     */
    @ApiModelProperty(value = "组合id")
    private Long composeId;

    /**
     * 组合名称
     */
    @ApiModelProperty(value = "组合名称")
    private String composeName;

    /**
     * 活动起始时间
     */
    @ApiModelProperty(value = "活动起始时间")
    private String time;

    /**
     * 商品数量
     */
    @ApiModelProperty(value = "商品数量")
    private Integer number;

    /**
     * 定价类型
     */
    @ApiModelProperty(value = "定价类型")
    private Integer composeType;

    /**
     * 状态 0-未开始 1-进行中 2-已结束 3-已停用
     */
    @ApiModelProperty(value = "状态 0-未开始 1-进行中 2-已结束 3-已停用")
    private Integer state;
}
