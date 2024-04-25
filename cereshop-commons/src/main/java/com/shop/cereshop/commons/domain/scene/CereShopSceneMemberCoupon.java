/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.scene;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_shop_scene_member_coupon 商家场景营销规则会员等级优惠券信息实体
 * @author 
 */
@Data
public class CereShopSceneMemberCoupon implements Serializable {
    /**
     * 场景营销id
     */
    private Long sceneId;

    /**
     * 会员等级id
     */
    private Long memberLevelId;

    /**
     * 商家优惠券id
     */
    private Long couponId;

    private static final long serialVersionUID = 1L;

}
