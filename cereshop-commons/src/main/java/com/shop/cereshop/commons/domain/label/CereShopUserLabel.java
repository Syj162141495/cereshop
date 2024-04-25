/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.label;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_shop_user_label 店铺用户标签信息表
 * @author 
 */
@Data
@ApiModel(value = "CereShopUserLabel", description = "店铺标签实体")
public class CereShopUserLabel implements Serializable {
    /**
     * 店铺标签id
     */
    @ApiModelProperty(value = "店铺标签id")
    private Long labelId;

    /**
     * 关联店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 标签名称
     */
    @ApiModelProperty(value = "标签名称")
    private String labelName;

    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息")
    private String remark;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    private static final long serialVersionUID = 1L;

}
