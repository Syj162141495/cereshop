/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.shop;

import lombok.Data;

import java.util.List;

/**
 * 素材接收参数实体类
 */
@Data
public class CereLabelSourceParam {

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 标签id
     */
    private Long labelId;

    /**
     * 图片地址
     */
    private List<String> images;

    /**
     * 单张图片地址
     */
    private String image;

    /**
     * 链接
     */
    private String link;

}
