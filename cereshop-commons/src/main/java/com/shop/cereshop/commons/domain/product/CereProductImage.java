/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.product;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_product_image 商品图片信息实体类
 * @author 
 */
@Data
public class CereProductImage implements Serializable {
    /**
     * 关联商品id
     */
    private Long productId;

    /**
     * 图片地址
     */
    private String productImage;

    /**
     * 排序值
     */
    private Integer sort;

    private static final long serialVersionUID = 1L;

}
