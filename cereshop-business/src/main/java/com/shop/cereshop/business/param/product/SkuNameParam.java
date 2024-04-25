/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.product;

import com.shop.cereshop.commons.constant.IntegerEnum;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 商品规格名和值级别数据
 */
@Data
@ApiModel(value = "SkuNameParam", description = "商品规格名和值级别数据")
public class SkuNameParam {

    /**
     * 规格id
     */
    private Long skuId;

    /**
     * 是否需要配图 1-是 0-否
     */
    private Integer need;

    public void setNeed(Integer need) {
        if(IntegerEnum.YES.getCode().equals(need)){
            this.needImg=true;
        }else {
            this.needImg=false;
        }
    }

    /**
     * 是否需要配图
     */
    private Boolean needImg;

    /**
     * 规格名级别
     */
    @ApiModelProperty(value = "规格名级别")
    private String code;

    /**
     * 规格名
     */
    @ApiModelProperty(value = "规格名")
    private String skuName;

    /**
     * 规格值数据
     */
    @ApiModelProperty(value = "规格值数据")
    @Valid
    private List<SkuValueParam> values;

}
