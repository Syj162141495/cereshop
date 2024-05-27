/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 客户类别返回数据实体类
 */
@Data
@ApiModel(value = "CustomerClassify", description = "客户类别返回数据实体类")
public class CustomerClassify {

    /**
     * 类别ID
     */
    @ApiModelProperty(value = "类别ID")
    private Long classifyId;


    /**
     * 分类父id
     */
    @ApiModelProperty(value = "分类父id")
    private Long classifyPid;

    /**
     * 类别名称
     */
    @ApiModelProperty(value = "类别名称")
    @NotBlank(message = "类别名称不能为空")
    private String classifyName;

    /**
     * 类别级别
     */
    @ApiModelProperty(value = "类别级别")
    private Integer classifyLevel;

    /**
     * 子节点数据
     */
    @ApiModelProperty(value = "子节点数据")
    private List<CustomerClassify> children;


    /**
     * 类别介绍
     */
    @ApiModelProperty(value = "类别介绍")
    private String description;

    /**
     * 排序号
     */
    @ApiModelProperty(value = "类别介绍")
    private Integer sort;
}
