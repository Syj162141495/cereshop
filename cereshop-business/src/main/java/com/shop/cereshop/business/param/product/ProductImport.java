/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.product;

import com.shop.cereshop.commons.poi.IsNeeded;
import com.shop.cereshop.commons.utils.EmptyUtils;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品导入参数实体类
 */
@Data
public class ProductImport {

    /**
     * 商家名称
     */
    @IsNeeded
    private String shopName;

    /**
     * 一级类别名称
     */
    @IsNeeded
    private String oneClassifyName;

    /**
     * 二级类别名称
     */
    @IsNeeded
    private String twoClassifyName;

    /**
     * 三级类别名称
     */
    @IsNeeded
    private String threeClassifyName;

    /**
     * 商品名称
     */
    @IsNeeded
    private String productName;

    /**
     * 供应商名称
     */
    @IsNeeded
    private String supplierName;

    /**
     * 是否需要物流
     */
    @IsNeeded
    private String ifLogistics;

    /**
     * 上架状态
     */
    @IsNeeded
    private String shelveState;

    /**
     * 是否允许超卖
     */
    @IsNeeded
    private String ifOversold;

    /**
     * 规格值
     */
    @IsNeeded
    private String skuValue;

    /**
     * 售价
     */
    @IsNeeded
    private BigDecimal price;

    /**
     * 原价
     */
    @IsNeeded
    private BigDecimal originalPrice;

    /**
     * 库存数量
     */
    @IsNeeded
    private Integer stockNumber;

    /**
     * 重量
     */
    @IsNeeded
    private BigDecimal weight;

    public void setPrice(BigDecimal price) {
        if(!EmptyUtils.isEmpty(price)){
            this.price = price.setScale(2,BigDecimal.ROUND_HALF_UP);
        }
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        if(!EmptyUtils.isEmpty(originalPrice)){
            this.originalPrice = originalPrice.setScale(2,BigDecimal.ROUND_HALF_UP);
        }
    }

    public void setWeight(BigDecimal weight) {
        if(!EmptyUtils.isEmpty(weight)){
            this.weight = weight.setScale(2,BigDecimal.ROUND_HALF_UP);
        }
    }
}
