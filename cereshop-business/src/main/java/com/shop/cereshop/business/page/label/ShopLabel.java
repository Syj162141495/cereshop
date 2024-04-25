/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.label;

import com.shop.cereshop.commons.domain.label.CereLabelSource;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 店铺标签数据
 * @author
 */
@Data
@ApiModel(value = "ShopLabel", description = "店铺标签数据")
public class ShopLabel {

    /**
     * 标签id
     */
    @ApiModelProperty(value = "标签id")
    private Long labelId;

    /**
     * 关联店铺id
     */
    @ApiModelProperty(value = "关联店铺id")
    private Long shopId;

    /**
     * 标签名称
     */
    @ApiModelProperty(value = "标签名称")
    private String labelName;

    /**
     * 素材数据
     */
    @ApiModelProperty(value = "素材数据")
    private List<CereLabelSource> sources;
}
