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
 * cere_label_source  店铺素材信息实体类
 * @author 
 */
@Data
@ApiModel(value = "CereLabelSource", description = "店铺素材信息实体类")
public class CereLabelSource implements Serializable {
    /**
     * 店铺标签id
     */
    @ApiModelProperty(value = "店铺标签id")
    private Long labelId;

    /**
     * 素材类型 1-图片 2-视频
     */
    @ApiModelProperty(value = "素材类型 1-图片 2-视频")
    private Integer labelType;

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
