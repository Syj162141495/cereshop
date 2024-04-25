/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.tool;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_product_problem 商品提问信息实体
 * @author 
 */
@Data
public class CereProductProblem implements Serializable {
    /**
     * 提问id
     */
    private Long problemId;

    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id")
    private Long productId;

    /**
     * 问题
     */
    @ApiModelProperty(value = "问题")
    private String problem;

    /**
     * 客户id
     */
    private Long buyerUserId;

    /**
     * 是否匿名 1-是 0-否
     */
    @ApiModelProperty(value = "是否匿名 1-是 0-否")
    private Integer ifAnonymous;

    /**
     * 是否选中 1-是 0-否
     */
    private Integer selected;

    /**
     * 提问时间
     */
    private String createTime;

    private static final long serialVersionUID = 1L;

}
