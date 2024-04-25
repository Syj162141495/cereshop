/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.group;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author  新增商品分组
 */
@Data
@ApiModel(value = "ShopGroupSaveParam", description = "新增商品分组请求")
public class ShopGroupSaveParam implements Serializable {

    /**
     * 关联店铺id
     */
    @ApiModelProperty(value = "关联店铺id")
    private Long shopId;

    /**
     * 分组名称
     */
    @ApiModelProperty(value = "分组名称")
    private String groupName;

    /**
     * 分组封面图片地址
     */
    @ApiModelProperty(value = "分组封面图片地址")
    private String groupImage;

    /**
     * 分组描述
     */
    @ApiModelProperty(value = "分组描述")
    private String groupDescribe;

    /**
     * 商品id数组
     */
    @ApiModelProperty(value = "商品id数组")
    private List<Long> ids;

    /**
     * 满足条件 1-全部满足 2-任意满足
     */
    @ApiModelProperty(value = "满足条件 1-全部满足 2-任意满足")
    private Integer condition;

    /**
     * 筛选条件数组
     */
    @ApiModelProperty(value = "筛选条件数组")
    private List<GroupCondition> conditions;

    private static final long serialVersionUID = 1L;

}
