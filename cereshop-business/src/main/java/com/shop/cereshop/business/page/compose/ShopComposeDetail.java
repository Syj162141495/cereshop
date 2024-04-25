/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.compose;

import com.shop.cereshop.commons.domain.tool.CereComposeProduct;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * 组合捆绑详情
 * @author
 */
@Data
@ApiModel(value = "ShopComposeDetail", description = "组合捆绑详情")
public class ShopComposeDetail {

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
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    private String startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    private String endTime;

    /**
     * 定价类型
     */
    @ApiModelProperty(value = "定价类型")
    private Integer composeType;

    /**
     * 定价内容
     */
    @ApiModelProperty(value = "定价内容")
    private BigDecimal promote;

    /**
     * 组合商品数据
     */
    @ApiModelProperty(value = "组合商品数据")
    private List<ComposeProduct> composeProducts;
}
