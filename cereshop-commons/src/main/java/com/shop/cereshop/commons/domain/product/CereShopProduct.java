/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_shop_product 店铺商品信息实体类
 * @author
 */
@Data
public class CereShopProduct implements Serializable {
    /**
     * 商品id
     */
    private Long productId;

    /**
     * 关联店铺id
     */
    private Long shopId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 卖点简介
     */
    private String productBrief;

    /**
     * 关联商品分组id
     */
    private Long shopGroupId;

    /**
     * 关联分类id
     */
    private Long classifyId;

    /**
     * 关联供应商id
     */
    private Long supplierId;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 是否需要物流 1-是 0-否
     */
    private Integer ifLogistics;

    /**
     * 商品状态 0-已下架 1-已上架 2-待审核 3-审核失败
     */
    private Integer shelveState;

    /**
     * 是否允许超卖 1-是 0-否
     */
    private Integer ifOversold;

    /**
     * 是否支持花呗分期 1-是 0-否
     */
    private Integer ifHuabei;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 商品描述（富文本）
     */
    private String productText;

    /**
     * 虚拟销量
     */
    private Integer fictitiousNumber;

    /**
     * 驳回原因
     */
    private String reject;

    private static final long serialVersionUID = 1L;

}
