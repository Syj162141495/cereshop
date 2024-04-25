/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.compose;

import com.shop.cereshop.commons.domain.tool.CereComposeProduct;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * 添加组合捆绑请求
 * @author
 */
@Data
@ApiModel(value = "ComposeSaveParam", description = "添加组合捆绑请求")
public class ComposeSaveParam {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 组合名称
     */
    @ApiModelProperty(value = "组合名称")
    @NotBlank(message = "组合名称不能为空")
    private String composeName;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    @NotBlank(message = "开始时间不能为空")
    private String startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    @NotBlank(message = "结束时间不能为空")
    private String endTime;

    /**
     * 定价类型
     */
    @ApiModelProperty(value = "定价类型")
    @NotNull(message = "定价类型不能为空")
    private Integer composeType;

    /**
     * 定价内容
     */
    @ApiModelProperty(value = "定价内容")
    @NotNull(message = "定价内容不能为空")
    private BigDecimal promote;

    /**
     * 组合商品数据
     */
    @ApiModelProperty(value = "组合商品数据")
    private List<CereComposeProduct> composeProducts;
}
