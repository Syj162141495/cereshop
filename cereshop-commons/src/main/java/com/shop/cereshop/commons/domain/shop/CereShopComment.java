/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.shop;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_shop_comment 客户评论信息实体类
 * @author 
 */
@Data
public class CereShopComment implements Serializable {

    /**
     * 评论id
     */
    private Long commentId;

    /**
     * 关联店铺id
     */
    private Long shopId;

    /**
     * 商家名称
     */
    private String shopName;

    /**
     * 商家编码
     */
    private String shopCode;

    /**
     * 关联商品id
     */
    private Long productId;

    /**
     * 规格id
     */
    private Long skuId;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 关联客户id
     */
    private Long buyerUserId;

    /**
     * 图片地址（多个以逗号隔开）
     */
    private String image;

    /**
     * 追加图片
     */
    private String addImage;

    /**
     * 评论
     */
    private String comment;

    /**
     * 追加评论
     */
    private String addComment;

    /**
     * 是否隐藏 1-是 0-否
     */
    private Integer state;

    /**
     * 是否敏感词审核 1-是 0-否
     */
    private Integer ifSensitive;

    /**
     * 追评时间
     */
    private String addTime;

    /**
     * 商品满意度(宝贝描述) 1-一星 2-二星 3-三星 4-四星 5-五星
     */
    private Integer star;

    /**
     * 描述相符 1-一星 2-二星 3-三星 4-四星 5-五星
     */
    private Integer des;

    /**
     * 物流服务 1-一星 2-二星 3-三星 4-四星 5-五星
     */
    private Integer delivery;

    /**
     * 服务态度 1-一星 2-二星 3-三星 4-四星 5-五星
     */
    private Integer attitude;

    /**
     * 商品印象
     */
    private String impression;

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
