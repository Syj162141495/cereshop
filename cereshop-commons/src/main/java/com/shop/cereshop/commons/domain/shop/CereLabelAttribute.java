/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.shop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_label_attribute 店铺标签属性实体类
 * @author 
 */
@Data
public class CereLabelAttribute implements Serializable {
    /**
     * 关联标签id
     */
    @ApiModelProperty(value = "关联标签id")
    private Long labelId;

    /**
     * 图片地址
     */
    @ApiModelProperty(value = "图片地址")
    private String image;

    /**
     * 链接
     */
    @ApiModelProperty(value = "链接")
    private String link;

    private static final long serialVersionUID = 1L;

}
