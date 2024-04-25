/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.shop;

import com.shop.cereshop.commons.domain.common.PageParam;
import lombok.Data;

import java.util.List;

/**
 * banner接收参数实体类
 */
@Data
public class CereShopBannerParam extends PageParam {

    /**
     * bannerid
     */
    private Long bannerId;

    /**
     * 样式
     */
    private Integer bannerStyle;

    /**
     * 图片数组
     */
    private List<String> images;

    /**
     * 跳转地址
     */
    private String bannerUrl;
}
