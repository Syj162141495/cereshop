/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_customer_classify 客户分类实体类
 * @author
 */
@Data
@ApiModel(value = "CereCustomerClassify", description = "客户分类实体类")
public class CereCustomerClassify implements Serializable {

    /**
     * 分类id
     */
    @ApiModelProperty(value = "分类id")
    private Long classifyId;

    /**
     * 分类父id
     */
    @ApiModelProperty(value = "分类父id")
    private Long classifyPid;

    /**
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称")
    private String classifyName;

    /**
     * 分类级别
     */
    @ApiModelProperty(value = "分类级别")
    private Integer classifyLevel;

    /**
     * 分类层级结构名称
     */
    @ApiModelProperty(value = "分类层级结构名称")
    private String ClassifyHierarchy;

    /**
     * 分类层级结构
     */
    @ApiModelProperty(value = "分类层级结构")
    private String classifyLevelHierarchy;


    /**
     * 类别介绍
     */
    @ApiModelProperty(value = "类别介绍")
    private String description;

    /**
     * 排序号
     */
    @ApiModelProperty(value = "排序号")
    private Integer sortID;


    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private String updateTime;


    private static final long serialVersionUID = 1L;

}
