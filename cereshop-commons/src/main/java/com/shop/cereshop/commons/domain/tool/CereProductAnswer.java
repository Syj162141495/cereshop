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
 * cere_product_answer 商品提问回答信息实体
 * @author 
 */
@Data
public class CereProductAnswer implements Serializable {
    /**
     * 回答id
     */
    private Long answerId;

    /**
     * 提问id
     */
    @ApiModelProperty(value = "提问id")
    private Long problemId;

    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id")
    private Long productId;

    /**
     * 客户id
     */
    private Long buyerUserId;

    /**
     * 回答
     */
    @ApiModelProperty(value = "回答")
    private String answer;

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
     * 回答时间
     */
    private String createTime;

    private static final long serialVersionUID = 1L;

}
