/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.buyer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_buyer_search 客户历史搜索实体
 * @author 
 */
@Data
@ApiModel(value = "CereBuyerSearch", description = "客户历史搜索实体")
public class CereBuyerSearch implements Serializable {
    /**
     * 搜索id
     */
    @ApiModelProperty(value = "搜索id")
    private Long searchId;

    /**
     * 客户id
     */
    @ApiModelProperty(value = "客户id")
    private Long buyerUserId;

    /**
     * 搜索内容
     */
    @ApiModelProperty(value = "搜索内容")
    private String search;

    private static final long serialVersionUID = 1L;

}
